import "./RecruiterDashboard.css";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getMyJobs } from "./recruiterDashboardService";

function RecruiterDashboard() {

  const navigate = useNavigate();
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const totalJobs = jobs.length;
  const activeJobs = jobs.filter(job => job.status === "OPEN").length;

  const logout = () => {

    localStorage.removeItem("token");
    localStorage.removeItem("user");

    navigate("/login");
  };

  useEffect(() => {
    loadJobs();
  }, []);

  const loadJobs = async () => {

    try {

      const response = await getMyJobs();

      setJobs(response.data);

    } catch (error) {

      console.log(error);

    } finally {

      setLoading(false);

    }

  };



  return (

    <div className="recruiter-dashboard">

      {/* Sidebar */}

      <aside className="sidebar">

        <h2>TalentHire</h2>

        <ul>

          <li className="active">🏠 Dashboard</li>

          <li>💼 My Jobs</li>

          <li onClick={() => navigate("/recruiter/create-job")}>➕ Create Job</li>

          <li>👥 Applicants</li>

          <li>📝 Assessments</li>

          <li>📅 Interviews</li>

          <li>📊 Reports</li>

          <li>⚙ Settings</li>

          <li onClick={logout}>🚪 Logout</li>

        </ul>

      </aside>



      {/* Main */}

      <main className="main">

        {/* Topbar */}

        <header className="topbar">

          <input
            type="text"
            placeholder="Search jobs..."
          />

          <div className="profile">

            <span>🔔</span>

            <img
              src="https://i.pravatar.cc/100?img=5"
              alt=""
            />

            <p>ABC Technologies</p>

          </div>

        </header>



        {/* Welcome */}

        <section className="welcome">

          <span className="tag">
            🚀 Recruiter Dashboard
          </span>

          <h1>
            Welcome Back
          </h1>

          <p>
            Manage jobs, applicants and assessments from one place.
          </p>

          <button
            onClick={() => navigate("/recruiter/create-job")}
          >
            + Create New Job
          </button>

        </section>



        {/* Stats */}

        <section className="stats">


          <div className="stat-card">
            <h2>{totalJobs}</h2>
            <p>Jobs Posted</p>
          </div>

          <div className="stat-card">
            <h2>{activeJobs}</h2>
            <p>Active Jobs</p>
          </div>

          <div className="stat-card">

            <h2>352</h2>

            <p>Applications</p>

          </div>

          <div className="stat-card">

            <h2>22</h2>

            <p>Candidates Hired</p>

          </div>

        </section>



        <section className="dashboard-content">

          {/* Left */}

          <div className="left">

            <h2>Recent Job Posts</h2>

            {loading ? (

              <p>Loading jobs...</p>

            ) : jobs.length === 0 ? (

              <div className="job-card">

                <h3>No Jobs Found</h3>

                <p>Create your first job to start hiring candidates.</p>

              </div>

            ) : (

              jobs.map((job) => (

                <div className="job-card" key={job.jobId}>

                  <div className="job-header">

                    <div>
                      <h3>{job.title}</h3>
                      <h4>{job.companyName}</h4>
                    </div>

                    <div className="job-actions">

                      <button
                       className="view-btn"
                        onClick={() => navigate(`/recruiter/applicants/${job.jobId}`)}
                      >
                        View Applicants
                      </button>

                      <button
                        className="edit-btn"
                        onClick={() => navigate(`/recruiter/edit-job/${job.jobId}`)}
                      >
                        Edit
                      </button>

                    </div>

                  </div>

                  <div className="job-info">

                    <span>📍 {job.location}</span>

                    <span>💼 {job.employmentType.replace("_", " ")}</span>

                    <span className="status">
                      {job.status}
                    </span>

                  </div>

                </div>

              ))

            )}

          </div>



          {/* Right */}

          <div className="right">

            <div className="info-card">

              <h3>Latest Applicants</h3>

              <p>👤 Rahul Sharma</p>

              <p>👤 Sneha Patil</p>

              <p>👤 Aman Singh</p>

            </div>

            <div className="info-card">

              <h3>Quick Actions</h3>

              <button>Create Job</button>

              <button>Assign Assessment</button>

              <button>View Reports</button>

            </div>

          </div>

        </section>

      </main>

    </div>

  );

}

export default RecruiterDashboard;