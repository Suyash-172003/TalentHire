import React, { useState } from "react";
import axios from "axios";
import "./Payment.css";

const Payment = () => {

    const [loading, setLoading] = useState(false);

    const token = localStorage.getItem("token");

    const handlePayment = async () => {

        try {

            setLoading(true);

            // 1. Create Razorpay order
            const response = await axios.post(
                "http://localhost:8085/payment/create-order",
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            const data = response.data;

            console.log("Order response:", data);

            // Already paid
            if (data.alreadyPaid) {

                alert("Your subscription is already active.");

                return;
            }

            // 2. Razorpay checkout options
            const options = {

                key: data.keyId,

                amount: data.amount,

                currency: data.currency,

                name: "TalentHire",

                description: "TalentHire Recruiter Monthly Subscription",

                order_id: data.orderId,

                handler: async function (paymentResponse) {

                    console.log(
                        "Razorpay Response:",
                        paymentResponse
                    );

                    try {

                        // 3. Verify payment on backend
                        const verifyResponse =
                            await axios.post(
                                "http://localhost:8085/payment/verify",

                                {
                                    razorpayOrderId:
                                        paymentResponse.razorpay_order_id,

                                    razorpayPaymentId:
                                        paymentResponse.razorpay_payment_id,

                                    razorpaySignature:
                                        paymentResponse.razorpay_signature
                                },

                                {
                                    headers: {
                                        Authorization:
                                            `Bearer ${token}`
                                    }
                                }
                            );

                        console.log(
                            "Verification:",
                            verifyResponse.data
                        );

                        if (
                            verifyResponse.data.success
                        ) {

                            alert(
                                "Payment successful! Your subscription is active."
                            );

                            // Redirect to recruiter dashboard
                            window.location.href =
                                "/recruiter/dashboard";
                        }

                    } catch (error) {

                        console.error(
                            "Verification error:",
                            error
                        );

                        alert(
                            "Payment was completed but verification failed."
                        );
                    }
                },

                prefill: {
                    name: "TalentHire Recruiter",
                    email: "",
                    contact: ""
                },

                theme: {
                    color: "#3399cc"
                }
            };

            // 4. Open Razorpay
            const razorpay =
                new window.Razorpay(options);

            razorpay.open();

        } catch (error) {

            console.error(
                "Payment error:",
                error
            );

            alert(
                error.response?.data?.message ||
                "Unable to create payment order."
            );

        } finally {

            setLoading(false);
        }
    };

    return (
        <div className="payment-page">

            {/* Background Decorations */}
            <div className="payment-blur payment-blur-one"></div>
            <div className="payment-blur payment-blur-two"></div>
            <div className="payment-blur payment-blur-three"></div>

            <div className="payment-container">

                {/* Logo */}
                <div className="payment-logo">
                    TalentHire
                </div>

                <span className="payment-tag">
                    ✨ Recruiter Membership
                </span>

                <h1>
                    Unlock Your <span>Hiring Power</span>
                </h1>

                <p className="payment-subtitle">
                    Get everything you need to discover, evaluate and hire
                    the best talent with TalentHire.
                </p>

                {/* Pricing Card */}
                <div className="pricing-card">

                    <div className="plan-top">

                        <div>
                            <span className="plan-label">
                                MONTHLY PLAN
                            </span>

                            <h2>
                                Recruiter Pro
                            </h2>
                        </div>

                        <div className="plan-badge">
                            POPULAR
                        </div>

                    </div>

                    <div className="price-section">

                        <span className="currency">
                            ₹
                        </span>

                        <span className="price">
                            499
                        </span>

                        <span className="duration">
                            / month
                        </span>

                    </div>

                    <div className="price-line"></div>

                    {/* Features */}

                    <div className="features">

                        <div className="feature">
                            <span className="check">✓</span>
                            <span>Post and manage job openings</span>
                        </div>

                        <div className="feature">
                            <span className="check">✓</span>
                            <span>Access candidate applications</span>
                        </div>

                        <div className="feature">
                            <span className="check">✓</span>
                            <span>Create candidate assessments</span>
                        </div>

                        <div className="feature">
                            <span className="check">✓</span>
                            <span>Evaluate and shortlist candidates</span>
                        </div>

                        <div className="feature">
                            <span className="check">✓</span>
                            <span>Recruiter dashboard and analytics</span>
                        </div>

                    </div>

                    {/* Payment Button */}

                    <button
                        onClick={handlePayment}
                        disabled={loading}
                        className="payment-button"
                    >
                        {loading ? (
                            <>
                                <span className="payment-spinner"></span>
                                Processing...
                            </>
                        ) : (
                            <>
                                Continue to Payment
                                <span className="arrow">→</span>
                            </>
                        )}
                    </button>

                    <p className="secure-payment">
                        🔒 Secure payment powered by Razorpay
                    </p>

                </div>

                <p className="payment-footer">
                    One simple plan. Everything you need to hire better.
                </p>

            </div>

        </div>
    );
};

export default Payment;