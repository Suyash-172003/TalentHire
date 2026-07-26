import "./Signup.css";
import { Link } from "react-router-dom";
import { useState } from "react";
import { FaGoogle, FaEye, FaEyeSlash } from "react-icons/fa";

function Signup() {

    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    return (

        <div className="signup-page">

            <div className="blur blur-one"></div>
            <div className="blur blur-two"></div>
            <div className="blur blur-three"></div>

            <div className="signup-card">

                <h2 className="logo">TalentHire</h2>

                <span className="welcome-tag">
                    ✨ Join TalentHire
                </span>

                <h1>Create Account</h1>

                <p>
                    Start your hiring journey with TalentHire.
                </p>

                <form>

                    <div className="input-group">

                        <label>Full Name</label>

                        <input
                            type="text"
                            placeholder="Enter your full name"
                        />

                    </div>

                    <div className="input-group">

                        <label>Email Address</label>

                        <input
                            type="email"
                            placeholder="Enter your email"
                        />

                    </div>

                    <div className="input-group">

                        <label>Password</label>

                        <div className="password-box">

                            <input
                                type={showPassword ? "text" : "password"}
                                placeholder="Create password"
                            />

                            <span
                                className="eye"
                                onClick={() => setShowPassword(!showPassword)}
                            >
                                {showPassword ? <FaEyeSlash /> : <FaEye />}
                            </span>

                        </div>

                    </div>

                    <div className="input-group">

                        <label>Confirm Password</label>

                        <div className="password-box">

                            <input
                                type={showConfirmPassword ? "text" : "password"}
                                placeholder="Confirm password"
                            />

                            <span
                                className="eye"
                                onClick={() =>
                                    setShowConfirmPassword(!showConfirmPassword)
                                }
                            >
                                {showConfirmPassword ? <FaEyeSlash /> : <FaEye />}
                            </span>

                        </div>

                    </div>

                    <button className="signup-btn">

                        Create Account

                    </button>

                    <div className="divider">

                        <span>OR</span>

                    </div>

                    <button
                        type="button"
                        className="google-btn"
                    >

                        <FaGoogle />

                        Continue with Google

                    </button>

                </form>

                <div className="bottom-text">

                    Already have an account?

                    <Link to="/login">

                        Sign In

                    </Link>

                </div>

            </div>

        </div>

    );

}

export default Signup;