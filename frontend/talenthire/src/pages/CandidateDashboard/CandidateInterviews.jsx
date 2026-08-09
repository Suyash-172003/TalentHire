import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getMyInterviews } from "./candidateDashboardService";
import "./CandidateInterviews.css";

function CandidateInterviews() {

    const navigate = useNavigate();

    const [interviews, setInterviews] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadInterviews();
    }, []);

    const loadInterviews = async () => {

        try {

            const response = await getMyInterviews();

            setInterviews(response.data || []);

        } catch (error) {

            console.error(
                "Error loading interviews:",
                error
            );

        } finally {

            setLoading(false);

        }

    };

    const formatDate = (date) => {

        if (!date) {
            return "Date not available";
        }

        return new Date(date).toLocaleString(
            "en-IN",
            {
                day: "2-digit",
                month: "short",
                year: "numeric",
                hour: "2-digit",
                minute: "2-digit"
            }
        );

    };

    return (

        <div className="candidate-interviews-page">

            <div className="candidate-interviews-container">

                <div className="interviews-header">

                    <div>

                        <h1>
                            My Interviews
                        </h1>

                        <p>
                            View your scheduled interviews
                        </p>

                    </div>

                    <button
                        className="back-btn"
                        onClick={() =>
                            navigate("/candidate/dashboard")
                        }
                    >
                        ← Dashboard
                    </button>

                </div>


                {loading ? (

                    <div className="interviews-empty">
                        Loading interviews...
                    </div>

                ) : interviews.length === 0 ? (

                    <div className="interviews-empty">

                        <div className="empty-icon">
                            📅
                        </div>

                        <h2>
                            No Interviews Scheduled
                        </h2>

                        <p>
                            You don't have any scheduled interviews yet.
                        </p>

                    </div>

                ) : (

                    <div className="interviews-list">

                        {interviews.map((interview) => (

                            <div
                                className="interview-card"
                                key={
                                    interview.interviewId ||
                                    interview.applicationId
                                }
                            >

                                <div className="interview-card-header">

                                    <div>

                                        <span className="interview-label">
                                            INTERVIEW
                                        </span>

                                        <h2>
                                            {
                                                interview.jobTitle ||
                                                interview.title ||
                                                "Interview"
                                            }
                                        </h2>

                                        <h3>
                                            {
                                                interview.companyName ||
                                                "Company"
                                            }
                                        </h3>

                                    </div>

                                    <span className="scheduled-pill">
                                        SCHEDULED
                                    </span>

                                </div>


                                <div className="interview-info">

                                    <div className="interview-info-item">

                                        <span>
                                            📅
                                        </span>

                                        <div>

                                            <small>
                                                Date & Time
                                            </small>

                                            <strong>
                                                {
                                                    formatDate(
                                                        interview.interviewDate
                                                    )
                                                }
                                            </strong>

                                        </div>

                                    </div>


                                    <div className="interview-info-item">

                                        <span>
                                            👤
                                        </span>

                                        <div>

                                            <small>
                                                Application ID
                                            </small>

                                            <strong>
                                                {
                                                    interview.applicationId
                                                }
                                            </strong>

                                        </div>

                                    </div>

                                </div>


                                <div className="interview-card-footer">

                                    <div>

                                        <span>
                                            Meeting
                                        </span>

                                        <p>
                                            Join your scheduled interview
                                        </p>

                                    </div>


                                    <button
                                        className="join-interview-btn"
                                        onClick={() =>
                                            window.open(
                                                interview.meetingLink,
                                                "_blank"
                                            )
                                        }
                                        disabled={
                                            !interview.meetingLink
                                        }
                                    >
                                        Join Interview →
                                    </button>

                                </div>

                            </div>

                        ))}

                    </div>

                )}

            </div>

        </div>

    );

}

export default CandidateInterviews;