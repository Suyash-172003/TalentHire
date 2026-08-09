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
  const user = JSON.parse(localStorage.getItem("user")) || {};

  const [sortBy, setSortBy] = useState("newest");
  const [searchTerm, setSearchTerm] = useState("");

  const logout = () => {

    localStorage.removeItem("token");
    localStorage.removeItem("user");

    navigate("/login");
  };
  const newestJob = [...jobs].sort(
    (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
  )[0];

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

  const filteredJobs = jobs.filter((job) => {
    const search = searchTerm.toLowerCase();

    return (
      job.title?.toLowerCase().includes(search) ||
      job.companyName?.toLowerCase().includes(search) ||
      job.location?.toLowerCase().includes(search) ||
      job.status?.toLowerCase().includes(search) ||
      job.employmentType?.replace("_", " ").toLowerCase().includes(search)
    );
  });

  const sortedJobs = [...filteredJobs].sort((a, b) => {

    if (sortBy === "newest") {
      return new Date(b.createdAt) - new Date(a.createdAt);
    }

    return new Date(a.createdAt) - new Date(b.createdAt);

  });

  const totalVacancies = jobs.reduce(
    (sum, job) => sum + (job.vacancies || 0),
    0
  );





  return (

    <div className="recruiter-dashboard">

      {/* Sidebar */}

      <aside className="sidebar">

        <h2>TalentHire</h2>

        <ul>

          <li className="active">🏠 Dashboard</li>

          <li
            onClick={() =>
              document
                .getElementById("my-jobs")
                ?.scrollIntoView({ behavior: "smooth" })
            }
          >
            💼 My Jobs
          </li>

          <li onClick={() => navigate("/recruiter/create-job")}>➕ Create Job</li>



          <li onClick={logout}>🚪 Logout</li>

        </ul>

      </aside>



      {/* Main */}

      <main className="main">

        {/* Top Bar */}
        <header className="topbar">

          <div className="profile">

            <span className="notification">🔔</span>

            <div className="profile-details">
              <h4>{user.name || "Recruiter"}</h4>
            </div>

            <button
              className="logout-btn"
              onClick={logout}
            >
              Logout
            </button>

          </div>

        </header>


        {/* Hero Section */}

        <section className="welcome">

          <h1>Welcome Back,</h1>

          <h2>{user.name}</h2>

          <p>
            Manage your job postings, review applicants,
            assign assessments and track hiring progress
            from one place.
          </p>

          <div className="welcome-actions">

            <button
              onClick={() => navigate("/recruiter/create-job")}
            >
              + Create New Job
            </button>

            <input
              className="welcome-search"
              type="text"
              placeholder="Search jobs..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />


            <select
              className="sort-select "
              value={sortBy}
              onChange={(e) => setSortBy(e.target.value)}
            >
              <option value="newest">Newest First</option>
              <option value="oldest">Oldest First</option>
            </select>


          </div>

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
            <h2>{totalVacancies}</h2>
            <p>Total Vacancies</p>
          </div>

          <div className="stat-card latest-job-card">

            <span className="latest-label">
              Latest Job
            </span>

            <h3>{newestJob?.title || "No Jobs"}</h3>

            <p>{newestJob?.companyName}</p>

            <small>
              {newestJob
                ? new Date(newestJob.createdAt).toLocaleDateString()
                : ""}
            </small>

          </div>
        </section>



        <section id="my-jobs" className="dashboard-content">

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

              sortedJobs.map((job) => (

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
                        Applicants
                      </button>

                      <button
                        className="assessment-btn"
                        onClick={() => navigate(`/recruiter/assessment/${job.jobId}`)}
                      >
                        Assessment
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


        </section>

      </main>

    </div>

  );

}

export default RecruiterDashboard;