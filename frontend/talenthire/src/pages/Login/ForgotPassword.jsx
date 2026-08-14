
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { forgotPassword } from "../../api/axiosService";
import "../Login/Login.css";


function ForgotPassword() {

    const [email, setEmail] = useState("");

    const navigate = useNavigate();

    const sendOtp = async (e) => {

        e.preventDefault();

        try {

            await forgotPassword(email);

            alert("OTP sent successfully.");

            navigate("/verify-otp", {
                state: {
                    email: email
                }
            });

        } catch (error) {

            console.log(error);

            alert(
                error.response?.data ||
                "Unable to send OTP."
            );
        }

    };

    return (

        <div className="login-page container-fluid">

            <div className="blur blur-one"></div>
            <div className="blur blur-two"></div>
            <div className="blur blur-three"></div>

            <div className="row justify-content-center align-items-center min-vh-100">

                <div className="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-5">

                    <div className="login-card">

                        <h2 className="logo">
                            TalentHire
                        </h2>

                        <span className="welcome-tag">
                            🔐 Forgot Password
                        </span>

                        <h1>
                            Reset Password
                        </h1>

                        <p>
                            Enter your registered email address.
                            We'll send you a verification OTP.
                        </p>

                        <form onSubmit={sendOtp}>

                            <div className="mb-4">

                                <label className="form-label">
                                    Email Address
                                </label>

                                <input
                                    type="email"
                                    className="form-control custom-input"
                                    placeholder="Enter your email"
                                    value={email}
                                    onChange={(e) =>
                                        setEmail(e.target.value)
                                    }
                                    required
                                />

                            </div>

                            <button
                                type="submit"
                                className="btn login-btn w-100"
                            >
                                Send OTP
                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default ForgotPassword;