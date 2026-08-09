import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./McqExam.css";
import { getQuestions, submitExam } from "./mcqExamService";

const McqExam = () => {

    const navigate = useNavigate();

    const location = useLocation();


    const [submitting, setSubmitting] = useState(false);
    const [showCodingModal, setShowCodingModal] = useState(false);

    // Prevent crash on refresh/direct URL
    if (!location.state) {

        return (
            <div style={{ padding: "40px" }}>
                <h2>Invalid Exam Session</h2>

                <p>Please start the exam from the Instructions page.</p>

                <button
                    onClick={() => navigate("/candidate/dashboard")}
                >
                    Back to Dashboard
                </button>
            </div>
        );

    }

    const {
        assessmentId,
        attemptId,
        duration,
        totalQuestions,
        totalMarks,
        assessmentType
    } = location.state;

    const [questions, setQuestions] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const [loading, setLoading] = useState(true);
    const [answers, setAnswers] = useState({});
    const [visited, setVisited] = useState([]);
    const [timeLeft, setTimeLeft] = useState(duration * 60);
    const [markedForReview, setMarkedForReview] = useState([]);


    const handleAnswerSelect = (questionId, option) => {

        setAnswers((prev) => ({
            ...prev,
            [questionId]: option
        }));

        // Remove from review if answered
        setMarkedForReview(prev =>
            prev.filter(id => id !== questionId)
        );
    };

    const handleSubmitExam = async () => {

        try {

            setSubmitting(true);

            const payload = {

                answers: Object.entries(answers).map(

                    ([questionId, selectedAnswer]) => ({

                        questionId: Number(questionId),

                        selectedAnswer

                    })

                )

            };

            const response = await submitExam(
                attemptId,
                payload
            );
            console.log("Payload:");
            console.log(JSON.stringify(payload, null, 2));

            if (assessmentType === "BOTH") {

                setShowCodingModal(true);

                setTimeout(() => {

                    navigate(`/assessment/${assessmentId}`, {
                        state: {
                            assessmentId,
                            attemptId
                        }
                    });

                }, 5000);

            } else {

                navigate("/candidate/dashboard");

            }

        }

        catch (error) {

            console.log(error);

            alert("Unable to submit exam.");

        }

        finally {

            setSubmitting(false);

        }

    };

    useEffect(() => {

        loadQuestions();

    }, []);

    const loadQuestions = async () => {

        try {

            const response = await getQuestions(assessmentId);

            setQuestions(response.data);

        } catch (error) {

            console.error(error);

            alert("Unable to load questions.");

        } finally {

            setLoading(false);

        }

    };

   
    

    useEffect(() => {

        if (timeLeft <= 0 && !submitting) {

            handleSubmitExam();
            return;

        }

        const timer = setInterval(() => {

            setTimeLeft((prev) => prev - 1);

        }, 1000);

        return () => clearInterval(timer);

    }, [timeLeft]);

    const formatTime = () => {

        const minutes = Math.floor(timeLeft / 60);

        const seconds = timeLeft % 60;

        return `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;

    };

    if (loading) {

        return <h2>Loading Questions...</h2>;

    }

    if (questions.length === 0) {

        return <h2>No Questions Found.</h2>;

    }

    const currentQuestion = questions[currentIndex];



    return (

        <div className="exam-page">

            <div className="exam-header">

                <div>

                    <h2>MCQ Assessment</h2>

                    <p>
                        Question {currentIndex + 1} / {questions.length}
                    </p>

                </div>

                <div className="timer">

                    ⏱ {formatTime()}

                </div>

            </div>


            <div className="exam-body">

                {/* LEFT PANEL */}

                <div className="question-palette">

                    <h3>Questions</h3>
                    <div className="question-stats">

                        <div className="stat-box solved">

                            <span>{Object.keys(answers).length}</span>

                            <small>Solved</small>

                        </div>

                        <div className="stat-box review">

                            <span>
                                {
                                    markedForReview.filter(id => !answers[id]).length
                                }
                            </span>

                            <small>Review</small>

                        </div>

                        <div className="stat-box unsolved">

                            <span>
                                {
                                    questions.length -
                                    Object.keys(answers).length -
                                    markedForReview.filter(
                                        id => !answers[id]
                                    ).length
                                }
                            </span>

                            <small>Unsolved</small>

                        </div>

                    </div>

                    <div className="palette-grid">

                        {questions.map((q, index) => (

                            <button
                                key={q.id}
                                className={`palette-btn
                                ${currentIndex === index
                                        ? "current"
                                        : answers[q.id]
                                            ? "answered"
                                            : markedForReview.includes(q.id)
                                                ? "review"
                                                : visited.includes(index)
                                                    ? "visited"
                                                    : ""
                                    }
                                `}
                                onClick={() => {

                                    setVisited((prev) => {

                                        if (prev.includes(index)) {
                                            return prev;
                                        }

                                        return [...prev, index];

                                    });

                                    setCurrentIndex(index);

                                }}
                            >
                                {index + 1}
                            </button>

                        ))}

                    </div>


                    <button

                        className="submit-btn"
                        onClick={handleSubmitExam}
                        disabled={submitting}
                    >


                        {
                            submitting
                                ? "Submitting..."
                                : "Submit Assessment"
                        }

                    </button>

                </div>



                {/* RIGHT PANEL */}

                <div className="question-section">

                    <h3>

                        Question {currentIndex + 1}

                    </h3>

                    <p className="question-text">

                        {currentQuestion.questionText}

                    </p>




                    <div className="options">

                        <label>
                            <input
                                type="radio"
                                name={`question-${currentQuestion.id}`}
                                checked={answers[currentQuestion.id] === "A"}
                                onChange={() => handleAnswerSelect(currentQuestion.id, "A")}
                            />
                            {currentQuestion.optionA}
                        </label>

                        <label>
                            <input
                                type="radio"
                                name={`question-${currentQuestion.id}`}
                                checked={answers[currentQuestion.id] === "B"}
                                onChange={() => handleAnswerSelect(currentQuestion.id, "B")}
                            />
                            {currentQuestion.optionB}
                        </label>

                        <label>
                            <input
                                type="radio"
                                name={`question-${currentQuestion.id}`}
                                checked={answers[currentQuestion.id] === "C"}
                                onChange={() => handleAnswerSelect(currentQuestion.id, "C")}
                            />
                            {currentQuestion.optionC}
                        </label>


                        <label>
                            <input
                                type="radio"
                                name={`question-${currentQuestion.id}`}
                                checked={answers[currentQuestion.id] === "D"}
                                onChange={() => handleAnswerSelect(currentQuestion.id, "D")}
                            />
                            {currentQuestion.optionD}
                        </label>

                    </div>



                    <div className="navigation-buttons">

                        <button
                            disabled={currentIndex === 0}
                            onClick={() => {

                                setVisited((prev) => {

                                    if (prev.includes(currentIndex)) {
                                        return prev;
                                    }

                                    return [...prev, currentIndex];

                                });

                                setCurrentIndex(currentIndex - 1);

                            }}
                        >
                            Previous
                        </button>

                        <button
                            className="review-btn"
                            onClick={() => {
                                if (!markedForReview.includes(currentQuestion.id)) {
                                    setMarkedForReview(prev => [...prev, currentQuestion.id]);
                                }

                                if (currentIndex < questions.length - 1) {
                                    setCurrentIndex(currentIndex + 1);
                                }
                            }}
                        >
                            Mark For Review & Next
                        </button>
                        <button
                            className="clear-btn"
                            onClick={() => {
                                const updated = { ...answers };
                                delete updated[currentQuestion.id];
                                setAnswers(updated);
                            }}
                        >
                            Clear Response
                        </button>


                        <button
                            onClick={() => {

                                // If this question has an answer, remove it from Review
                                if (answers[currentQuestion.id]) {
                                    setMarkedForReview(prev =>
                                        prev.filter(id => id !== currentQuestion.id)
                                    );
                                }

                                setVisited(prev => {

                                    if (prev.includes(currentIndex)) {
                                        return prev;
                                    }

                                    return [...prev, currentIndex];

                                });

                                if (currentIndex < questions.length - 1) {
                                    setCurrentIndex(currentIndex + 1);
                                }

                            }}
                        >
                            Save & Next
                        </button>

                    </div>

                </div>

            </div>
            {
                showCodingModal && (

                    <div className="modal-overlay">

                        <div className="success-modal">

                            <div className="success-icon">
                                ✓
                            </div>

                            <h2>MCQ Assessment Completed</h2>

                            <p>
                                Your MCQ responses have been submitted successfully.
                            </p>

                            <p>
                                The Coding Assessment will begin shortly.
                            </p>

                            <div className="loader"></div>

                            <small>
                                Redirecting in a few seconds...
                            </small>

                        </div>

                    </div>

                )
            }

        </div>

    );

};

export default McqExam;