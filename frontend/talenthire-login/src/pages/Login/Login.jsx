import "./Login.css";
import { Link } from "react-router-dom";
import { useState } from "react";
import { FaGoogle, FaEye, FaEyeSlash } from "react-icons/fa";
import { loginUser } from "../../api/axiosService";

function Login() {

    const [showPassword, setShowPassword] = useState(false);
     const [data,setData]=useState({email:"",password:""});


      const onTextChange=(e)=>{
     var copyData={...data};
     copyData[e.target.name]=e.target.value;
     setData(copyData);
         }
     
          const  login = async(e)=>{
      e.preventDefault();
     
     
         try {
             console.log(data);
     
             const response = await loginUser(data);
     
             console.log(response);
     
             alert("login Successful");
     
         } catch (error) {
     
             console.log(error);
     
             alert("login Failed");
     
         }
        }
     



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

                    <button className="login-btn" onClick={login}>

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