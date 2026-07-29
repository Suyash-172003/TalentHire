import "./Hero.css";
import { Link } from "react-router-dom";

function Hero() {

  return (

    <section className="hero">

      <div className="gradient-one"></div>
      <div className="gradient-two"></div>
      <div className="gradient-three"></div>

      <div className="hero-left">

        <span className="tag">
          🚀 AI Powered Recruitment Platform
        </span>

        <h1>

          Hire the <br />

          <span>Best Talent.</span><br />

          Faster Than Ever.

        </h1>

        <p>

          TalentHire helps recruiters discover top candidates,
          conduct AI-powered assessments and hire with confidence.

        </p>

        <div className="hero-buttons">

          <Link to="/signup" className="primary-btn">

            Get Started

          </Link>

          <Link to="/login" className="secondary-btn">

            Login

          </Link>

        </div>

      </div>

      <div className="hero-right">

        <div className="profile-card card-one">

          <img
            src="https://i.pravatar.cc/150?img=15"
            alt="Sarah Johnson"
          />

          <h3>Sarah Johnson</h3>

          <p>Frontend Developer</p>

          <span>★★★★★</span>

        </div>

        <div className="profile-card card-two">

          <img
            src="https://i.pravatar.cc/150?img=32"
            alt="David Miller"
          />

          <h3>David Miller</h3>

          <p>Java Developer</p>

          <span>★★★★★</span>

        </div>

        <div className="stats-card">

          <h2>10K+</h2>

          <p>Successful Hires</p>

        </div>

      </div>

    </section>

  );

}

export default Hero;