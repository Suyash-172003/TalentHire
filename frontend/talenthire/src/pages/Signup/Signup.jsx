import "./Signup.css";
import { Link } from "react-router-dom";
import { useState } from "react";
import { FaGoogle, FaEye, FaEyeSlash } from "react-icons/fa";
import { registerUser } from "../../api/axiosService";

function Signup() {

    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    const [data, setData] = useState({
        name: "",
        email: "",
        password: "",
        confirmPassword: "",
        userRole: "",
    });

    const onTextChange = (e) => {
        setData({
            ...data,
            [e.target.name]: e.target.value,
        });
    };

    const create = async (e) => {
        e.preventDefault();

        if (data.password !== data.confirmPassword) {
            alert("Passwords do not match");
            return;
        }

        try {
            console.log(data);

            const response = await registerUser(data);

            console.log(response);

            alert("Registration Successful");

        } catch (error) {

            console.log(error);

            alert("Registration Failed");

        }
    };

    return (

        <div className="signup-page container-fluid">

            {/* Background Blur */}
            <div className="blur blur-one"></div>
            <div className="blur blur-two"></div>
            <div className="blur blur-three"></div>

            <div className="row justify-content-center align-items-center min-vh-100">

                <div className="col-12 col-sm-10 col-md-8 col-lg-7 col-xl-5">

                    <div className="signup-card">

                        <h2 className="logo">
                            TalentHire
                        </h2>

                        <span className="welcome-tag">
                            ✨ Join TalentHire
                        </span>

                        <h1>Create Account</h1>

                        <p>
                            Start your hiring journey with TalentHire.
                        </p>

                        <form onSubmit={create}>

                            {/* Name */}

                            <div className="mb-4">

                                <label className="form-label">
                                    Full Name
                                </label>

                                <input
                                    type="text"
                                    name="name"
                                    className="form-control custom-input"
                                    placeholder="Enter your full name"
                                    value={data.name}
                                    onChange={onTextChange}
                                    required
                                />

                            </div>

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
                                        placeholder="Create password"
                                        value={data.password}
                                        onChange={onTextChange}
                                        required
                                    />

                                    <span
                                        className="eye"
                                        onClick={() => setShowPassword(!showPassword)}
                                    >
                                        {showPassword ? <FaEyeSlash /> : <FaEye />}
                                    </span>

                                </div>

                            </div>

                            {/* Confirm Password */}

                            <div className="mb-4">

                                <label className="form-label">
                                    Confirm Password
                                </label>

                                <div className="position-relative">

                                    <input
                                        type={showConfirmPassword ? "text" : "password"}
                                        name="confirmPassword"
                                        className="form-control custom-input pe-5"
                                        placeholder="Confirm password"
                                        value={data.confirmPassword}
                                        onChange={onTextChange}
                                        required
                                    />

                                    <span
                                        className="eye"
                                        onClick={() =>
                                            setShowConfirmPassword(!showConfirmPassword)
                                        }
                                    >
                                        {showConfirmPassword ? (
                                            <FaEyeSlash />
                                        ) : (
                                            <FaEye />
                                        )}
                                    </span>

                                </div>

                            </div>

                            {/* Role */}

                            <div className="mb-4">

                                <label className="form-label">
                                    Select Role
                                </label>

                                <div className="role-selection">

                                    <div
                                        className={`role-card ${
                                            data.userRole === "CANDIDATE"
                                                ? "active-role"
                                                : ""
                                        }`}
                                        onClick={() =>
                                            setData({
                                                ...data,
                                                userRole: "CANDIDATE",
                                            })
                                        }
                                    >

                                        <div className="role-icon">
                                            👨‍💼
                                        </div>

                                        <h3>Candidate</h3>

                                        <p>
                                            Apply for jobs and track applications.
                                        </p>

                                    </div>

                                    <div
                                        className={`role-card ${
                                            data.userRole === "RECRUITER"
                                                ? "active-role"
                                                : ""
                                        }`}
                                        onClick={() =>
                                            setData({
                                                ...data,
                                                userRole: "RECRUITER",
                                            })
                                        }
                                    >

                                        <div className="role-icon">
                                            🏢
                                        </div>

                                        <h3>Recruiter</h3>

                                        <p>
                                            Post jobs and hire talented candidates.
                                        </p>

                                    </div>

                                </div>

                            </div>

                            {/* Signup Button */}

                            <button
                                type="submit"
                                className="btn signup-btn w-100"
                            >
                                Create Account
                            </button>

                            {/* Divider */}

                            <div className="divider">

                                <span>OR</span>

                            </div>

                            {/* Google */}

                            <button
                                type="button"
                                className="btn google-btn w-100"
                            >

                                <FaGoogle />

                                Continue with Google

                            </button>

                        </form>

                        <div className="bottom-text">

                            Already have an account?

                            <Link to="/login">

                                Log In

                            </Link>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );
}

export default Signup;