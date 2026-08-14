import "./Login.css";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { FaGoogle, FaEye, FaEyeSlash } from "react-icons/fa";
import { loginUser, getPaymentStatus } from "../../api/axiosService";

function Login() {
    const [showPassword, setShowPassword] = useState(false);
    const [data, setData] = useState({
        email: "",
        password: "",
    });

    const navigate = useNavigate();

    const onTextChange = (e) => {
        setData({
            ...data,
            [e.target.name]: e.target.value,
        });
    };

    const login = async (e) => {
        e.preventDefault();

        try {

            const response = await loginUser(data);

            // Save JWT
            localStorage.setItem("token", response.token);

            // Save user information
            localStorage.setItem(
                "user",
                JSON.stringify(response)
            );

            alert("Login Successful");

            // CANDIDATE

            if (response.userRole === "CANDIDATE") {

                navigate("/candidate/dashboard");

                return;
            }

            // RECRUITER


            if (response.userRole === "RECRUITER") {

                try {

                    const paymentStatus =
                        await getPaymentStatus(response.token);

                    console.log(
                        "Payment Status:",
                        paymentStatus
                    );

                    // PAYMENT ACTIVE

                    if (
                        paymentStatus.isPaid === true &&
                        paymentStatus.status === "PAID"
                    ) {

                        navigate("/recruiter/dashboard");

                        return;
                    }

                    // NOT PAID / EXPIRED

                    navigate("/payment");

                    return;

                } catch (paymentError) {

                    console.error(
                        "Payment status error:",
                        paymentError
                    );

                    // If payment service cannot be reached,
                    // don't allow recruiter directly into dashboard.

                    alert(
                        "Unable to check payment status. Please try again."
                    );

                    return;
                }
            }

            navigate("/");

        } catch (error) {

            console.log(error);

            alert(
                error.response?.data?.message ||
                "Login Failed"
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
                            🚀 Welcome Back
                        </span>

                        <h1>Sign In</h1>

                        <p>
                            Sign in to continue your hiring journey.
                        </p>

                        <form onSubmit={login}>

                            {/* Email */}

                            <div className="mb-4">

                                <label className="form-label">
                                    Email Address
                                </label>

                                <input
                                    type="email"
                                    name="email"
                                    className="form-control custom-input"
                                    placeholder="Enter your email"
                                    value={data.email}
                                    onChange={onTextChange}
                                    required
                                />

                            </div>

                            {/* Password */}

                            <div className="mb-4">

                                <label className="form-label">
                                    Password
                                </label>

                                <div className="position-relative">

                                    <input
                                        type={showPassword ? "text" : "password"}
                                        name="password"
                                        className="form-control custom-input pe-5"
                                        placeholder="Enter your password"
                                        value={data.password}
                                        onChange={onTextChange}
                                        required
                                    />

                                    <span
                                        className="eye"
                                        onClick={() =>
                                            setShowPassword(!showPassword)
                                        }
                                    >
                                        {showPassword ? (
                                            <FaEyeSlash />
                                        ) : (
                                            <FaEye />
                                        )}
                                    </span>

                                </div>

                            </div>

                            {/* Remember Me */}

                            <div className="d-flex justify-content-between align-items-center flex-wrap mb-4">

                                <div className="form-check">

                                    <input
                                        className="form-check-input"
                                        type="checkbox"
                                        id="remember"
                                    />

                                    {/* <label
                                        className="form-check-label"
                                        htmlFor="remember"
                                    >
                                        Remember Me
                                    </label> */}

                                </div>

                                <Link
                                    to="/forgot-password"
                                    className="text-decoration-none fw-semibold"
                                >
                                    Forgot Password?
                                </Link>

                            </div>

                            {/* Login Button */}

                            <button
                                type="submit"
                                className="btn login-btn w-100"
                            >
                                Sign In
                            </button>



                            {/* <div className="divider">

                                <span>OR</span>

                            </div>

                          

                            <button
                                type="button"
                                className="btn google-btn w-100"
                            >

                                <FaGoogle />

                                Continue with Google

                            </button> */}

                        </form>

                        <div className="bottom-text">

                            Don't have an account?

                            <Link to="/signup">
                                Sign Up
                            </Link>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default Login;