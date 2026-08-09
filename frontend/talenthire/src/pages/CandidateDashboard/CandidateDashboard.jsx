import "./CandidateDashboard.css";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import {
    getAllJobs,
    applyJob,
    uploadResume, getMyApplications, getMyInterviews
} from "./candidateDashboardService";
import { getMyAssessments } from "../Assessment/candidateAssessmentService"

function CandidateDashboard() {

    useEffect(() => {
        loadJobs();
        loadAppliedJobs();
        loadAppliedJobsCount();
        loadAssessmentCount();
        loadInterviewCount();

    }, []);



    const user = JSON.parse(localStorage.getItem("user"));

    const navigate = useNavigate();

    const [jobs, setJobs] = useState([]);

    const [loading, setLoading] = useState(true);

    const [showApplyModal, setShowApplyModal] = useState(false);

    const [selectedJobId, setSelectedJobId] = useState(null);

    const [selectedJob, setSelectedJob] = useState(null);

    const [resumeFile, setResumeFile] = useState(null);

    const [submitting, setSubmitting] = useState(false);

    const [appliedJobsCount, setAppliedJobsCount] = useState(0);

    const [appliedJobIds, setAppliedJobIds] = useState([]);

    const [searchTerm, setSearchTerm] = useState("");

    const [sortBy, setSortBy] = useState("newest");

    const [assessmentCount, setAssessmentCount] = useState(0);

    const [interviewCount, setInterviewCount] = useState(0);

    const [latestApplication, setLatestApplication] = useState(null);




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

            const applications = response.data;

            setAppliedJobIds(
                applications.map(app => app.jobId)
            );

            if (applications.length > 0) {
                setLatestApplication(
                    applications[applications.length - 1]
                );
            }

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

    const openApplyModal = (job) => {

        setSelectedJob(job);
        setSelectedJobId(job.jobId);

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
            setSelectedJob(null);

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


    const loadInterviewCount = async () => {

        try {

            const response = await getMyInterviews();

            setInterviewCount(
                response.data?.length || 0
            );

        } catch (error) {

            console.log("Error fetching interviews:", error);

            setInterviewCount(0);

        }

    };



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

                    <li
                        onClick={() => navigate("/candidate/interviews")}
                    >
                        📅 Interviews
                    </li>




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

                    <div className="dashboard-card latest-card">

                        <h3>📄 Latest Application</h3>

                        {
                            latestApplication ? (
                                <>
                                    <h4>{latestApplication.jobTitle}</h4>

                                    <p>📍 {latestApplication.location}</p>

                                    <span className="status-pill">
                                        {latestApplication.applicationStatus}
                                    </span>
                                </>
                            ) : (
                                <p>No applications yet.</p>
                            )
                        }

                    </div>

                    <div className="dashboard-card">
                        <h2>{interviewCount}</h2>
                        <p>Interviews</p>
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
                                                    onClick={() => openApplyModal(job)}
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

                        <h2>Apply for this Job</h2>

                        <div className="apply-job-header">

                            <h3>{selectedJob?.title}</h3>

                            <h4>{selectedJob?.companyName || "Confidential Company"}</h4>

                        </div>

                        <div className="apply-job-details">

                            <span>📍 {selectedJob?.location}</span>

                            <span>
                                💰 ₹{selectedJob?.salary?.toLocaleString("en-IN")}
                            </span>

                            <span>
                                🧑 {selectedJob?.experience} Year(s)
                            </span>

                            <span>
                                💼 {selectedJob?.employmentType}
                            </span>

                            <span>
                                🏠 {selectedJob?.workMode}
                            </span>

                            {
                                selectedJob?.vacancies &&
                                <span>
                                    👥 {selectedJob.vacancies} Vacancies
                                </span>
                            }

                        </div>

                        <hr />

                        <h4>Job Description</h4>

                        <p className="job-description">
                            {selectedJob?.description}
                        </p>

                        <hr />

                        <h4>Required Skills</h4>

                        <div className="job-skills">

                            {selectedJob?.skills?.map((skill, index) => (

                                <span
                                    key={index}
                                    className="skill"
                                >
                                    {skill}
                                </span>

                            ))}

                        </div>

                        <hr />

                        <h4>Applying As</h4>

                        <p><strong>Name:</strong> {user.name}</p>

                        <p><strong>Email:</strong> {user.email}</p>

                        <hr />

                        <h4>Upload Resume</h4>

                        <input
                            type="file"
                            accept=".pdf,.doc,.docx"
                            className="form-control"
                            onChange={(e) => setResumeFile(e.target.files[0])}
                        />

                        {
                            resumeFile && (
                                <p className="selected-file">
                                    📄 {resumeFile.name}
                                </p>
                            )
                        }

                        <div className="modal-buttons">

                            <button
                                onClick={() => {

                                    setShowApplyModal(false);
                                    setResumeFile(null);
                                    setSelectedJob(null);
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