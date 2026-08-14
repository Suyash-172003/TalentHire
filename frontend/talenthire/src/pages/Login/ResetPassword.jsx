import "../Login/Login.css";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { resetPassword } from "../../api/axiosService";
import { validatePassword } from "../../utils/passwordValidation";

function ResetPassword() {

    const location = useLocation();

    const navigate = useNavigate();

    const email = location.state?.email;

    const [showPassword, setShowPassword] = useState(false);

    const [showConfirmPassword, setShowConfirmPassword] = useState(false);

    const [data, setData] = useState({

        newPassword: "",

        confirmPassword: ""

    });

    const onTextChange = (e) => {

        setData({

            ...data,

            [e.target.name]: e.target.value

        });

    };

    const changePassword = async (e) => {

        e.preventDefault();

        if (!validatePassword(data.newPassword)) {

            alert(
                "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character."
            );

            return;
        }

        if (data.newPassword !== data.confirmPassword) {

            alert("Passwords do not match.");

            return;
        }

        try {

            await resetPassword({

                email: email,

                newPassword: data.newPassword

            });

            alert("Password changed successfully.");

            navigate("/login");

        } catch (error) {

            console.log(error);

            alert("Unable to reset password.");

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

                            🔒 Reset Password

                        </span>

                        <h1>

                            Create New Password

                        </h1>

                        <p>

                            Enter your new password.

                        </p>

                        <form onSubmit={changePassword}>

                            <div className="mb-4">

                                <label className="form-label">
                                    New Password
                                </label>

                                <div className="position-relative">

                                    <input
                                        type={showPassword ? "text" : "password"}
                                        name="newPassword"
                                        className="form-control custom-input pe-5"
                                        placeholder="Enter new password"
                                        value={data.newPassword}
                                        onChange={onTextChange}
                                        required
                                        minLength={8}
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

                                <small className="text-muted">
                                    Password must contain 8+ characters, uppercase,
                                    lowercase, number and special character.
                                </small>

                            </div>

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
                                            setShowConfirmPassword(
                                                !showConfirmPassword
                                            )
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

                            <button

                                type="submit"

                                className="btn login-btn w-100"

                            >

                                Reset Password

                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default ResetPassword;