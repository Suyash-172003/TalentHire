using System.ComponentModel.DataAnnotations;

namespace PaymentService.Models;

public class Payment
{
    [Key]
    public int Id { get; set; }

    public long UserId { get; set; }

    [Required]
    public string PlanName { get; set; } = "MONTHLY";

    public decimal Amount { get; set; }

    public string Currency { get; set; } = "INR";

    public string Status { get; set; } = "PENDING";

    public string? RazorpayOrderId { get; set; }

    public string? RazorpayPaymentId { get; set; }

    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;

    public DateTime? PaidAt { get; set; }

    public DateTime? ExpiryDate { get; set; }
}