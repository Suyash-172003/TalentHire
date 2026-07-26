import "./Login.css";
import { Link } from "react-router-dom";
import { useState } from "react";
import { FaGoogle, FaEye, FaEyeSlash } from "react-icons/fa";

function Login() {

    const [showPassword, setShowPassword] = useState(false);

    return (

        <div className="login-page">

            <div className="blur blur-one"></div>
            <div className="blur blur-two"></div>
            <div className="blur blur-three"></div>

            <div className="login-card">

                <h2 className="logo">TalentHire</h2>

                <span className="welcome-tag">
                    🚀 Welcome Back
                </span>

                <h1>Sign In</h1>

                <p>
                    Sign in to continue your hiring journey.
                </p>

                <form>

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
                                placeholder="Enter your password"
                            />

                            <span
                                className="eye"
                                onClick={() =>
                                    setShowPassword(!showPassword)
                                }
                            >

                                {showPassword ? <FaEyeSlash /> : <FaEye />}

                            </span>

                        </div>

                    </div>

                    <div className="options">

                        <label>

                            <input type="checkbox" />

                            Remember Me

                        </label>

                        <Link to="/">
                            Forgot Password?
                        </Link>

                    </div>

                    <button className="login-btn">

                        Sign In

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

                    Don't have an account?

                    <Link to="/signup">

                        Sign Up

                    </Link>

                </div>

            </div>

        </div>

    );

}

export default Login;