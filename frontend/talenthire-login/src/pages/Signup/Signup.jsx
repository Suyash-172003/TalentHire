import "./Signup.css";
import { Link } from "react-router-dom";
import { useState } from "react";
import { FaGoogle, FaEye, FaEyeSlash } from "react-icons/fa";
import { registerUser } from "../../api/axiosService";

function Signup() {

    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [data,setData]=useState({name:"",email:"",password:"",confirmPassword:"",userRole:""});

     const onTextChange=(e)=>{
var copyData={...data};
copyData[e.target.name]=e.target.value;
setData(copyData);
    }

     const  create = async(e)=>{
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


     }

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
                        onChange={onTextChange} value={data.name} name="name"
                            type="text"
                            placeholder="Enter your full name"
                        />

                    </div>

                    <div className="input-group">

                        <label>Email Address</label>

                        <input
                        onChange={onTextChange} value={data.email} name="email"
                            type="email"
                            placeholder="Enter your email"
                        />

                    </div>

                    <div className="input-group">

                        <label>Password</label>

                        <div className="password-box">

                            <input
                            onChange={onTextChange} value={data.password} name="password"
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
                            onChange={onTextChange} value={data.confirmPassword} name="confirmPassword"
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

                    <div className="input-group">

               <label>Role</label>

                <select
                onChange={onTextChange} value={data.userRole} 
        name="userRole"
    >
        <option value="">-- Select Role --</option>
        <option value="CANDIDATE">Candidate</option>
        <option value="RECRUITER">Recruiter</option>
    </select>

</div>

                    <button className="signup-btn" onClick={create}>

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