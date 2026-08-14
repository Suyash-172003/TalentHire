using System.Net.Http.Headers;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;
using MySqlConnector;
using PaymentService.Data;
using PaymentService.DTOs;

namespace PaymentService.Services;

public class PaymentService
{
    private readonly PaymentDbContext _context;
    private readonly IConfiguration _configuration;
    private readonly HttpClient _httpClient;

    private const int MonthlyAmount = 49900; // ₹499 in paise

    public PaymentService(
        PaymentDbContext context,
        IConfiguration configuration,
        HttpClient httpClient)
    {
        _context = context;
        _configuration = configuration;
        _httpClient = httpClient;
    }

    // =========================================================
    // CREATE RAZORPAY ORDER
    // =========================================================

    public async Task<object> CreateOrder(long userId)
    {
        // Check whether user already has an active subscription
        using var connection =
            await _context.CreateConnectionAsync();

        const string checkSql = @"
            SELECT expiry_date
            FROM recruiter_payments
            WHERE recruiter_id = @userId
              AND status = 'PAID'
              AND expiry_date IS NOT NULL
              AND expiry_date > UTC_TIMESTAMP()
            ORDER BY expiry_date DESC
            LIMIT 1";

        using var checkCommand =
            new MySqlCommand(checkSql, connection);

        checkCommand.Parameters.AddWithValue(
            "@userId",
            userId);

        var existingExpiry =
            await checkCommand.ExecuteScalarAsync();

        if (existingExpiry != null &&
            existingExpiry != DBNull.Value)
        {
            return new
            {
                alreadyPaid = true,
                message = "Your subscription is already active.",
                expiryDate = Convert.ToDateTime(existingExpiry)
            };
        }

        string keyId =
            _configuration["Razorpay:KeyId"]
            ?? throw new Exception(
                "Razorpay KeyId missing.");

        string keySecret =
            _configuration["Razorpay:KeySecret"]
            ?? throw new Exception(
                "Razorpay KeySecret missing.");

        // Razorpay Basic Authentication
        string credentials =
            Convert.ToBase64String(
                Encoding.UTF8.GetBytes(
                    $"{keyId}:{keySecret}"));

        _httpClient.DefaultRequestHeaders.Authorization =
            new AuthenticationHeaderValue(
                "Basic",
                credentials);

        // Unique receipt
        string receipt =
            $"TH_{userId}_{DateTimeOffset.UtcNow.ToUnixTimeSeconds()}";

        var razorpayRequest = new
        {
            amount = MonthlyAmount,
            currency = "INR",
            receipt = receipt
        };

        string json =
            JsonSerializer.Serialize(razorpayRequest);

        using var content =
            new StringContent(
                json,
                Encoding.UTF8,
                "application/json");

        HttpResponseMessage response =
            await _httpClient.PostAsync(
                "https://api.razorpay.com/v1/orders",
                content);

        string responseBody =
            await response.Content.ReadAsStringAsync();

        if (!response.IsSuccessStatusCode)
        {
            throw new Exception(
                $"Razorpay order creation failed: {responseBody}");
        }

        using JsonDocument document =
            JsonDocument.Parse(responseBody);

        JsonElement root =
            document.RootElement;

        string orderId =
            root.GetProperty("id").GetString()!;

        int amount =
            root.GetProperty("amount").GetInt32();

        string currency =
            root.GetProperty("currency").GetString()!;

        // Store pending payment
        const string insertSql = @"
            INSERT INTO recruiter_payments
            (
                recruiter_id,
                razorpay_order_id,
                plan_name,
                amount,
                status,
                created_at
            )
            VALUES
            (
                @recruiterId,
                @orderId,
                'MONTHLY',
                @amount,
                'PENDING',
                UTC_TIMESTAMP()
            )
            ON DUPLICATE KEY UPDATE
                razorpay_order_id = @orderId,
                plan_name = 'MONTHLY',
                amount = @amount,
                status = 'PENDING',
                created_at = UTC_TIMESTAMP()";

        using var insertCommand =
            new MySqlCommand(insertSql, connection);

        insertCommand.Parameters.AddWithValue(
            "@recruiterId",
            userId);

        insertCommand.Parameters.AddWithValue(
            "@orderId",
            orderId);

        insertCommand.Parameters.AddWithValue(
            "@amount",
            MonthlyAmount / 100m);

        await insertCommand.ExecuteNonQueryAsync();

        return new
        {
            alreadyPaid = false,
            orderId = orderId,
            amount = amount,
            currency = currency,
            keyId = keyId
        };
    }


    // =========================================================
    // VERIFY PAYMENT
    // =========================================================

    public async Task<bool> VerifyPayment(
        long userId,
        string razorpayOrderId,
        string razorpayPaymentId,
        string razorpaySignature)
    {
        string keySecret =
            _configuration["Razorpay:KeySecret"]
            ?? throw new Exception(
                "Razorpay KeySecret missing.");

        using var connection =
            await _context.CreateConnectionAsync();

        // Find pending payment
        const string findSql = @"
            SELECT id
            FROM recruiter_payments
            WHERE recruiter_id = @userId
              AND razorpay_order_id = @orderId
              AND status = 'PENDING'
            LIMIT 1";

        using var findCommand =
            new MySqlCommand(findSql, connection);

        findCommand.Parameters.AddWithValue(
            "@userId",
            userId);

        findCommand.Parameters.AddWithValue(
            "@orderId",
            razorpayOrderId);

        var paymentId =
            await findCommand.ExecuteScalarAsync();

        if (paymentId == null ||
            paymentId == DBNull.Value)
        {
            return false;
        }

        // Razorpay signature payload
        string payload =
            $"{razorpayOrderId}|{razorpayPaymentId}";

        using var hmac =
            new HMACSHA256(
                Encoding.UTF8.GetBytes(keySecret));

        byte[] hash =
            hmac.ComputeHash(
                Encoding.UTF8.GetBytes(payload));

        string generatedSignature =
            Convert.ToHexString(hash)
                .ToLowerInvariant();

        bool signatureValid =
            CryptographicOperations.FixedTimeEquals(
                Encoding.UTF8.GetBytes(
                    generatedSignature),
                Encoding.UTF8.GetBytes(
                    razorpaySignature));

        if (!signatureValid)
        {
            return false;
        }

        // Payment verified successfully
        const string updateSql = @"
            UPDATE recruiter_payments
            SET
                status = 'PAID',
                razorpay_payment_id = @paymentId,
                payment_date = UTC_TIMESTAMP(),
                expiry_date = DATE_ADD(
                    UTC_TIMESTAMP(),
                    INTERVAL 1 MONTH
                )
            WHERE id = @id";

        using var updateCommand =
            new MySqlCommand(updateSql, connection);

        updateCommand.Parameters.AddWithValue(
            "@paymentId",
            razorpayPaymentId);

        updateCommand.Parameters.AddWithValue(
            "@id",
            Convert.ToInt64(paymentId));

        int rows =
            await updateCommand.ExecuteNonQueryAsync();

        return rows > 0;
    }


    // =========================================================
    // GET PAYMENT STATUS
    // =========================================================

    public async Task<PaymentStatusResponse> GetPaymentStatus(
        long userId)
    {
        using var connection =
            await _context.CreateConnectionAsync();

        const string sql = @"
            SELECT
                plan_name,
                status,
                expiry_date
            FROM recruiter_payments
            WHERE recruiter_id = @userId
              AND status = 'PAID'
            ORDER BY expiry_date DESC
            LIMIT 1";

        using var command =
            new MySqlCommand(sql, connection);

        command.Parameters.AddWithValue(
            "@userId",
            userId);

        using var reader =
            await command.ExecuteReaderAsync();

        if (!await reader.ReadAsync())
        {
            return new PaymentStatusResponse
            {
                UserId = userId,
                IsPaid = false,
                Status = "NOT_PAID"
            };
        }

        string status =
            reader["status"]?.ToString() ?? "";

        string? planName =
            reader["plan_name"]?.ToString();

        DateTime? expiryDate = null;

        if (reader["expiry_date"] != DBNull.Value)
        {
            expiryDate =
                Convert.ToDateTime(
                    reader["expiry_date"]);
        }

        // Subscription expired
        if (expiryDate == null ||
            expiryDate <= DateTime.UtcNow)
        {
            return new PaymentStatusResponse
            {
                UserId = userId,
                IsPaid = false,
                Status = "EXPIRED",
                PlanName = planName,
                ExpiryDate = expiryDate
            };
        }

        // Active subscription
        return new PaymentStatusResponse
        {
            UserId = userId,
            IsPaid = true,
            Status = "PAID",
            PlanName = planName,
            ExpiryDate = expiryDate
        };
    }
}