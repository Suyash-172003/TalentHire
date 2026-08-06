import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { getAssessmentById } from "../../RecruiterDashboard/RecruiterAssessmentService";


import { startExam } from "./mcqExamService";

import "./McqExam.css";

const McqInstructions = () => {

    const { assessmentId } = useParams();

    const navigate = useNavigate();

    const user = JSON.parse(localStorage.getItem("user"));

    const [assessment, setAssessment] = useState(null);

    const [loading, setLoading] = useState(true);

    const [starting, setStarting] = useState(false);

    useEffect(() => {

        fetchAssessment();

    }, []);

    const fetchAssessment = async () => {

        try {

            const response = await getAssessmentById(assessmentId);

            setAssessment(response.data);

        } catch (error) {

            console.error(error);

            alert("Unable to load assessment.");

        } finally {

            setLoading(false);

        }

    };

    const handleStartExam = async () => {

        try {

            setStarting(true);

            const response = await startExam(
                Number(assessmentId),
                user.userId
            );

            navigate(`/candidate/mcq/exam/${response.data.attemptId}`, {
                state: {
                    assessmentId: Number(assessmentId),
                    attemptId: response.data.attemptId,
                    duration: response.data.duration,
                    totalQuestions: response.data.totalQuestions,
                    totalMarks: response.data.totalMarks,
                    assessmentType: assessment.assessmentType   // add this
                }
            });

        } catch (error) {

            console.error(error);

            alert(
                error.response?.data?.message ||
                "Unable to start exam."
            );

        } finally {

            setStarting(false);

        }

    };

    if (loading) {

        return <h3>Loading...</h3>;

    }

    return (

        <div className="mcq-instruction-container">

            <div className="mcq-instruction-card">

                <h2>{assessment.title}</h2>

                <hr />

                <div className="instruction-info">

                  
                    <p>
                        <strong>Assessment Type :</strong>{" "}
                        {assessment.assessmentType}
                    </p>

                    <p>
                        <strong>MCQ Duration :</strong>{" "}
                        {assessment.duration} Minutes
                    </p>

                    
                    <p>
                        <strong>Coding Duration :</strong>{" "}
                        {assessment.codingDuration} Minutes
                    </p>


                    <p>
                        <strong>Assessment Start :</strong>{" "}
                        {new Date(assessment.startTime).toLocaleString()}
                    </p>

                    <p>
                        <strong>Assessment End :</strong>{" "}
                        {new Date(assessment.endTime).toLocaleString()}
                    </p>

                    <p>
                        <strong>Total Duration :</strong>{" "}
                        {assessment.duration + assessment.codingDuration} Minutes
                    </p>

                </div>  

                <hr />

                <h3>Instructions</h3>

                <ul>

                    <li>
                        Read every question carefully before answering.
                    </li>

                    <li>
                        Timer starts immediately after clicking Start Exam.
                    </li>

                    <li>
                        Do not refresh or close the browser during the exam.
                    </li>

                    <li>
                        Each question has only one correct answer.
                    </li>

                    <li>
                        You can navigate between questions anytime.
                    </li>

                    <li>
                        Click Submit once you complete the assessment.
                    </li>

                    <li>
                        The exam will auto-submit when the timer ends.
                    </li>

                </ul>

                <button
                    className="start-exam-btn"
                    onClick={handleStartExam}
                    disabled={starting}
                >

                    {starting ? "Starting..." : "Start Exam"}

                </button>

            </div>

        </div>

    );

};

export default McqInstructions;