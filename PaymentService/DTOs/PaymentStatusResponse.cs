namespace PaymentService.DTOs;

public class PaymentStatusResponse
{
    public long UserId { get; set; }

    public bool IsPaid { get; set; }

    public string Status { get; set; } = "";

    public string? PlanName { get; set; }

    public DateTime? ExpiryDate { get; set; }
}