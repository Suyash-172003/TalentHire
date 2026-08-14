import "../Login/Login.css";
import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { verifyOtp } from "../../api/axiosService";

function VerifyOtp() {

    const navigate = useNavigate();

    const location = useLocation();

    const email = location.state?.email;

    const [otp, setOtp] = useState("");

    const verify = async (e) => {

        e.preventDefault();

        try {

            await verifyOtp({
                email,
                otp
            });

            alert("OTP Verified Successfully.");

            navigate("/reset-password", {
                state: {
                    email
                }
            });

        } catch (error) {

            console.log(error);

            alert("Invalid or Expired OTP.");

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
                            🔑 Verify OTP
                        </span>

                        <h1>
                            Email Verification
                        </h1>

                        <p>

                            Enter the OTP sent to

                            <br />

                            <b>{email}</b>

                        </p>

                        <form onSubmit={verify}>

                            <div className="mb-4">

                                <label className="form-label">

                                    OTP

                                </label>

                                <input

                                    type="text"

                                    className="form-control custom-input"

                                    placeholder="Enter OTP"

                                    value={otp}

                                    onChange={(e)=>setOtp(e.target.value)}

                                    required

                                />

                            </div>

                            <button

                                className="btn login-btn w-100"

                            >

                                Verify OTP

                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default VerifyOtp;