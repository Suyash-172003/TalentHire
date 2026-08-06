import "./CandidateDashboard.css";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import {
    getAllJobs,
    applyJob,
    uploadResume, getMyApplications
} from "./candidateDashboardService";
import { getMyAssessments } from "../Assessment/candidateAssessmentService"

function CandidateDashboard() {

    useEffect(() => {
        loadJobs();
        loadAppliedJobs();
        loadAppliedJobsCount();
        loadAssessmentCount();
    }, []);



    const user = JSON.parse(localStorage.getItem("user"));
    const navigate = useNavigate();

    const [jobs, setJobs] = useState([]);
    const [loading, setLoading] = useState(true);

    const [showApplyModal, setShowApplyModal] = useState(false);

    const [selectedJobId, setSelectedJobId] = useState(null);

    const [resumeFile, setResumeFile] = useState(null);

    const [submitting, setSubmitting] = useState(false);

    const [appliedJobsCount, setAppliedJobsCount] = useState(0);

    const [appliedJobIds, setAppliedJobIds] = useState([]);

    const [searchTerm, setSearchTerm] = useState("");

    const [sortBy, setSortBy] = useState("newest");

    const [assessmentCount, setAssessmentCount] = useState(0);



    const loadAppliedJobsCount = async () => {
        try {
            const response = await getMyApplications();
            setAppliedJobsCount(response.data.length);
        } catch (error) {
            console.error("Error fetching applied jobs:", error);
        }
    };
    const loadAppliedJobs = async () => {

        try {

            const response = await getMyApplications();

            const ids = response.data.map(
                app => app.jobId
            );

            setAppliedJobIds(ids);

        } catch (error) {

            console.log(error);

        }

    };
    const loadAssessmentCount = async () => {

        try {

            const response = await getMyAssessments();

            setAssessmentCount(response.data.length);

        } catch (error) {

            console.log(error);

        }

    };

    const openApplyModal = (jobId) => {

        setSelectedJobId(jobId);

        setShowApplyModal(true);

    };


    const loadJobs = async () => {
        try {
            const response = await getAllJobs();

            const openJobs = response.data.filter(
                (job) => job.status === "OPEN"
            );

            setJobs(openJobs);

        } catch (error) {
            console.error("Error fetching jobs:", error);
        } finally {
            setLoading(false);
        }
    };

    const logout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("user");

        navigate("/login");
    };



    const submitApplication = async () => {

        if (!resumeFile) {
            alert("Please select a resume.");
            return;
        }

        try {

            setSubmitting(true);

            await applyJob(selectedJobId, resumeFile);

            setAppliedJobIds((prev) => [...prev, selectedJobId]);
            setAppliedJobsCount((prev) => prev + 1);

            await loadAppliedJobsCount();

            alert("Application Submitted Successfully");

            setShowApplyModal(false);
            setResumeFile(null);
            setSelectedJobId(null);

        } catch (error) {

            console.error(error);

            alert("Failed");

        } finally {

            setSubmitting(false);

        }
    };

    const filteredJobs = jobs.filter((job) => {
        const search = searchTerm.toLowerCase();

        return (
            job.title?.toLowerCase().includes(search) ||
            job.companyName?.toLowerCase().includes(search) ||
            job.location?.toLowerCase().includes(search) ||
            job.employmentType?.toLowerCase().includes(search) ||
            job.workMode?.toLowerCase().includes(search) ||
            job.skills?.join(" ").toLowerCase().includes(search)
        );
    });

    const sortedJobs = [...filteredJobs].sort((a, b) => {

        if (sortBy === "newest") {
            return new Date(b.createdAt) - new Date(a.createdAt);
        }

        return new Date(a.createdAt) - new Date(b.createdAt);

    });



    return (

        <div className="dashboard">

            {/* Sidebar */}

            <aside className="sidebar">

                <h2>TalentHire</h2>

                <ul>

                    <li className="active">🏠 Dashboard</li>

                    <li
                        onClick={() =>
                            document
                                .getElementById("all-jobs")
                                ?.scrollIntoView({ behavior: "smooth" })
                        }
                    >
                        💼 Browse Jobs
                    </li>

                    <li onClick={() => navigate("/candidate/assessments")}>
                        📝 Assessments
                    </li>

                    <li>📅 Interviews</li>




                    <li onClick={logout}>🚪 Logout</li>

                </ul>

            </aside>

            {/* Main */}

            <div className="main">

                {/* Topbar */}

                <header className="topbar">

                    <div className="profile">

                        <span className="notification">🔔</span>

                        <div className="profile-details">
                            <h4>{user?.name || "Candidate"}</h4>
                        </div>

                        <button
                            className="logout-btn"
                            onClick={logout}
                        >
                            Logout
                        </button>

                    </div>

                </header>

                {/* Welcome */}

                <section className="welcome">

                    <h1>Welcome Back,</h1>

                    <h2>{user?.name || "Candidate"}</h2>

                    <p>
                        Find your next career opportunity and discover jobs from top companies
                        that match your skills and career goals.
                    </p>

                    <div className="welcome-actions">

                        <button
                            onClick={() =>
                                document
                                    .getElementById("all-jobs")
                                    ?.scrollIntoView({ behavior: "smooth" })
                            }   >
                            Browse Jobs

                        </button>

                        <input
                            className="welcome-search"
                            type="text"
                            placeholder="Search jobs..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                        />

                        <select
                            className="sort-select"
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

                    <div className="dashboard-card">
                        <h2>{appliedJobsCount}</h2>
                        <p>Applied Jobs</p>
                    </div>

                    <div className="dashboard-card">
                        <h2>{assessmentCount}</h2>
                        <p>Assessments</p>
                    </div>

                    <div>
                        <h2>4</h2>
                        <p>Shortlisted</p>
                    </div>



                    <div>
                        <h2>1</h2>
                        <p>Interview</p>
                    </div>

                </section>

                {/* Content */}

                <section id="all-jobs" className="content">

                    {/* Left */}

                    <div className="left">

                        <h2>Recommended Jobs</h2>

                        {loading ? (

                            <p>Loading jobs...</p>

                        ) : filteredJobs.length === 0 ? (

                            <p>No matching jobs found.</p>

                        ) : (

                            sortedJobs.map((job) => (

                                <div className="job" key={job.jobId}>

                                    <div className="job-info">

                                        <h3 className="job-company">
                                            {job.companyName || "Demo"}
                                        </h3>

                                        <p className="job-title">
                                            {job.title}
                                        </p>

                                        <div className="job-details">

                                            <span>📍 {job.location}</span>

                                            <span>💰 ₹{job.salary.toLocaleString("en-IN")}</span>

                                            <span>🧑‍💻 {job.experience} Year(s)</span>

                                            <span>{job.employmentType}</span>

                                            <span>{job.workMode}</span>

                                        </div>

                                        <div className="job-skills">

                                            {job.skills?.map((skill, index) => (

                                                <span className="skill" key={index}>

                                                    {skill}

                                                </span>

                                            ))}

                                        </div>

                                    </div>

                                    <div className="job-action">

                                        {
                                            appliedJobIds.includes(job.jobId) ?

                                                <button className="applied-btn" disabled>
                                                    Applied✅
                                                </button>

                                                :

                                                <button
                                                    onClick={() => openApplyModal(job.jobId)}
                                                >
                                                    Apply
                                                </button>
                                        }

                                    </div>

                                </div>

                            ))

                        )}

                    </div>

                </section>

            </div>
            {showApplyModal && (

                <div className="modal-overlay">

                    <div className="apply-modal">

                        <h2>Apply Job</h2>

                        <p>
                            <strong>Name : </strong>
                            {user.name}
                        </p>

                        <p>
                            <strong>Email : </strong>
                            {user.email}
                        </p>

                        <div className="resume-upload">

                            <input
                                type="file"
                                accept=".pdf,.doc,.docx"
                                onChange={(e) => setResumeFile(e.target.files[0])}
                            />

                        </div>
                        {resumeFile && (
                            <p className="selected-file">
                                📄 Selected: {resumeFile.name}
                            </p>
                        )}

                        <div className="modal-buttons">

                            <button
                                onClick={() => {
                                    setShowApplyModal(false);
                                    setResumeFile(null);
                                    setSelectedJobId(null);
                                }}
                            >
                                Cancel
                            </button>

                            <button
                                onClick={submitApplication}
                                disabled={submitting}
                            >
                                {submitting ? "Applying..." : "Apply Now"}
                            </button>

                        </div>

                    </div>

                </div>

            )}

        </div>

    );

}

export default CandidateDashboard;