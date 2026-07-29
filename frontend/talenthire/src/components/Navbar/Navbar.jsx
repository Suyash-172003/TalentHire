import "./Navbar.css";
import { Link } from "react-router-dom";

function Navbar() {

  return (

    <nav className="th-navbar">

    <div className="logo">
        TalentHire
    </div>

    <ul className="nav-links">

        <li>Hire Talent</li>

        <li>About</li>

        <li>Recruiters</li>

    </ul>

    <div className="nav-buttons">

        <Link to="/login" className="login">
            Login
        </Link>

        <Link to="/signup" className="signup">
            Sign Up
        </Link>

    </div>

</nav>

  );

}

export default Navbar;