import { useEffect, useState } from "react";
import { getMyAssessments } from "./candidateAssessmentService"
import { getAssessmentById } from "./candidateAssessmentService";
import { useNavigate } from "react-router-dom";
import "./CandidateAssessments.css"


function CandidateAssessment() {

    const [assessments, setAssessments] = useState([]);

    useEffect(() => {
        loadAssessments();
    }, []);

    const loadAssessments = async () => {

        try {

            const response = await getMyAssessments();

            setAssessments(response.data);

        } catch (error) {

            console.log(error);

        }

    };

    const navigate = useNavigate();

    const startAssessment = (assessmentId) => {

        navigate(`/candidate/mcq/${assessmentId}`);

    };

    return (

        <div className="assessment-page">

            <h1>My Assessments</h1>

            <div className="assessment-list">

                {assessments.map((assessment) => (

                    <div
                        className="assessment-card"
                        key={assessment.assignmentId}
                    >

                        <h2>{assessment.title}</h2>

                        <h4>{assessment.companyName}</h4>

                        <div className="assessment-info">

                            <p>
                                <strong>Assessment Type :</strong>{" "}
                                {assessment.assessmentType}
                            </p>


                            <span className="assessment-status">
                                {assessment.assignmentStatus}
                            </span>

                            <p>
                                <strong>Assessment Start :</strong>{" "}
                                {new Date(assessment.startTime).toLocaleString()}
                            </p>
                            <p>
                                <strong>Assessment End :</strong>{" "}
                                {new Date(assessment.endTime).toLocaleString()}
                            </p>

                        </div>

                        <button
                            className="assessment-btn"
                            onClick={() => startAssessment(assessment.assessmentId)}
                        >
                            🚀 Start Assessment
                        </button>

                    </div>

                ))}

            </div>

        </div>

    );

}

export default CandidateAssessment;