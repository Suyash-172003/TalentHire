using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using PaymentService.Services;
using System.Security.Claims;

namespace PaymentService.Controllers;

[ApiController]
[Route("payment")]
[Authorize(Roles = "RECRUITER")]    
public class PaymentController : ControllerBase
{
    private readonly PaymentService.Services.PaymentService _paymentService;

    public PaymentController(
        PaymentService.Services.PaymentService paymentService)
    {
        _paymentService = paymentService;
    }

    // =========================================================
    // GET PAYMENT STATUS
    // =========================================================

    [HttpGet("status")]
    public async Task<IActionResult> GetPaymentStatus()
    {
        long recruiterId = GetRecruiterId();

        var result =
            await _paymentService.GetPaymentStatus(
                recruiterId);

        return Ok(result);
    }


    // =========================================================
    // CREATE RAZORPAY ORDER
    // =========================================================

    [HttpPost("create-order")]
    public async Task<IActionResult> CreateOrder()
    {
        long recruiterId = GetRecruiterId();

        var result =
            await _paymentService.CreateOrder(
                recruiterId);

        return Ok(result);
    }


    // =========================================================
    // VERIFY PAYMENT
    // =========================================================

    [HttpPost("verify")]
    public async Task<IActionResult> VerifyPayment(
        [FromBody] VerifyPaymentRequest request)
    {
        long recruiterId = GetRecruiterId();

        bool success =
            await _paymentService.VerifyPayment(
                recruiterId,
                request.RazorpayOrderId,
                request.RazorpayPaymentId,
                request.RazorpaySignature);

        if (!success)
        {
            return BadRequest(new
            {
                success = false,
                message = "Payment verification failed."
            });
        }

        return Ok(new
        {
            success = true,
            message = "Payment verified successfully."
        });
    }


    // =========================================================
    // GET RECRUITER ID FROM JWT
    // =========================================================

    private long GetRecruiterId()
    {
        var claim =
            User.FindFirst("user_id");

        if (claim == null)
        {
            throw new UnauthorizedAccessException(
                "user_id claim not found in JWT.");
        }

        if (!long.TryParse(
                claim.Value,
                out long recruiterId))
        {
            throw new UnauthorizedAccessException(
                "Invalid user_id in JWT.");
        }

        return recruiterId;
    }
}


// =============================================================
// VERIFY PAYMENT REQUEST
// =============================================================

public class VerifyPaymentRequest
{
    public string RazorpayOrderId { get; set; } = "";

    public string RazorpayPaymentId { get; set; } = "";

    public string RazorpaySignature { get; set; } = "";
}