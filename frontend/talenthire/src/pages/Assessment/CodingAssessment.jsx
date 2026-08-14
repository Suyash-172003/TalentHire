import { useState } from "react";
import Editor from "@monaco-editor/react";
import "./CodingAssessment.css";
import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { getCodingQuestions, runCode, submitAssessment } from "./CodingAssessmentService";
import { useNavigate } from "react-router-dom";

import { getAssessmentById } from "../RecruiterDashboard/RecruiterAssessmentService";




function CodingAssessment() {

    const navigate = useNavigate();
    const { assessmentId } = useParams();
    const [questions, setQuestions] = useState([]);
    const [selectedQuestion, setSelectedQuestion] = useState(null);
    const [consoleOutput, setConsoleOutput] = useState("Click 'Run Code' to execute your program.");

    const [codeMap, setCodeMap] = useState({});
    const user = JSON.parse(localStorage.getItem("user"));
    const [language, setLanguage] = useState("java");

    const [timeLeft, setTimeLeft] = useState(0);
    const [assessment, setAssessment] = useState(null);
    const [submitting, setSubmitting] = useState(false);
    const [showSuccessModal, setShowSuccessModal] = useState(false);
    const loadAssessment = async () => {
        try {

            const response = await getAssessmentById(assessmentId);

            setAssessment(response.data);

            setTimeLeft(response.data.codingDuration * 60);

        } catch (error) {

            console.log(error);

        }
    };

    const codeTemplates = {
        java: `public class Main {
    public static void main(String[] args){

    }

}`,
        cpp: `#include <iostream>
using namespace std;

int main() {

    return 0;
}`,

        python: `def main():
    pass

if __name__ == "__main__":
    main()`
    };

    useEffect(() => {

        loadQuestions();
        loadAssessment();

    }, []);
    useEffect(() => {

        if (!assessment) return;

        if (timeLeft <= 0) {

            if (!submitting) {
                handleSubmit();
            }

            return;
        }

        const timer = setInterval(() => {

            setTimeLeft(prev => prev - 1);

        }, 1000);

        return () => clearInterval(timer);

    }, [timeLeft, assessment]);

    const formatTime = () => {

        const minutes = Math.floor(timeLeft / 60);

        const seconds = timeLeft % 60;

        return `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;

    };

    useEffect(() => {

        setConsoleOutput("Click 'Run Code' to execute your program.");

    }, [selectedQuestion]);

    const handleSubmit = async () => {

        if (submitting) return;

        try {

            setSubmitting(true);

            const request = {

                assessmentId: Number(assessmentId),

                candidateId: user.userId,

                answers: questions.map(question => ({

                    codingQuestionId: question.codingQuestionId,

                    language,

                    sourceCode:

                        codeMap[
                        `${question.codingQuestionId}-${language}`
                        ] || codeTemplates[language]

                }))

            };

            await submitAssessment(request);

            setShowSuccessModal(true);

            setTimeout(() => {

                navigate("/candidate/dashboard");

            }, 3000);

        }

        catch (error) {

            console.log(error);

            alert("Failed to submit assessment.");

            setSubmitting(false);

        }

    };


    const handleRunCode = async () => {
        try {
            const request = {
                language: language,

                sourceCode:
                    codeMap[
                    `${selectedQuestion.codingQuestionId}-${language}`
                    ] || codeTemplates[language],

                codingQuestionId: selectedQuestion.codingQuestionId
            };
            const response = await runCode(request);
            console.log(response);
            if (response.compileError) {
                setConsoleOutput(response.compileError);
                return;
            }

            if (response.runtimeError) {
                setConsoleOutput(response.runtimeError);
                return;
            }

            let output = "";

            output += `Status : ${response.status}\n`;
            output += `Passed : ${response.passedTestCases}/${response.totalTestCases}\n`;
            output += `Execution Time : ${response.executionTime} ms\n\n`;

            response.results.forEach(result => {

                output += `${result.passed ? "✅" : "❌"} Test Case ${result.testCaseNumber}\n`;
                output += `Input            : ${result.input}\n`;
                output += `Expected Output  : ${result.expectedOutput}\n`;
                output += `Actual Output    : ${result.actualOutput.trim()}\n`;
                output += `Result           : ${result.passed ? "Passed" : "Failed"}\n`;
                output += "----------------------------------------\n";

            });

            setConsoleOutput(output);

        } catch (error) {
            console.log(error);
        }
    };

    const loadQuestions = async () => {
        try {
            const data = await getCodingQuestions(assessmentId);
            setQuestions(data);
            console.log(data);
            if (data.length > 0) {
                setSelectedQuestion(data[0]);
            }
        } catch (error) {
            console.log(error);
        }
    };

    return (

        <div className="assessment-container">
            <div className="sidebar">

                <h3>Questions</h3>
                {
                    questions.map((question) => (

                        <div

                            key={question.codingQuestionId}

                            className={
                                selectedQuestion?.codingQuestionId === question.codingQuestionId
                                    ? "question active"
                                    : "question"
                            }

                            onClick={() => setSelectedQuestion(question)}

                        >

                            {question.title}

                        </div>

                    ))

                }

            </div>
            <div className="editor-section">

                <div className="top-bar">

                    <h2>TalentHire Coding Assessment</h2>

                    <div className="top-right">

                        <div className={`coding-timer ${timeLeft <= 300 ? "danger" : ""}`}>

                            ⏱ {formatTime()}

                        </div>

                        <select

                            value={language}

                            onChange={(e) => setLanguage(e.target.value)}

                            disabled={submitting}

                        >

                            <option value="java">Java</option>

                            <option value="cpp">C++</option>

                            <option value="python">Python</option>

                        </select>

                    </div>

                </div>

                <div className="problem">

                    {
                        selectedQuestion &&
                        <>
                            <h3>

                                {selectedQuestion.title}

                            </h3>
                            <p>

                                {selectedQuestion.problemStatement}
                            </p>
                        </>
                    }

                    <div className="sample">

                        <h4>Sample Test Case</h4>

                        <strong>Input</strong>
                        <pre>
                            {selectedQuestion?.sampleTestCases[0].input}

                        </pre>

                        <strong>Expected Output</strong>

                        <pre>

                            {selectedQuestion?.sampleTestCases[0].expectedOutput}

                        </pre>

                    </div>

                </div>

                <Editor
                    height="450px"
                    language={language}
                    theme="vs"
                    value={
                        codeMap[
                        `${selectedQuestion?.codingQuestionId}-${language}`
                        ] || codeTemplates[language]
                    }
                    onChange={(value) =>
                        setCodeMap(prev => ({
                            ...prev,
                            [`${selectedQuestion.codingQuestionId}-${language}`]:
                                value || ""
                        }))
                    }
                />
                <div className="action-bar">


                    <button
                        className="run-btn"
                        onClick={handleRunCode}
                        disabled={submitting}
                    >  ▶ Run Code</button>
                    <button
                        className="submit-btn"
                        onClick={handleSubmit}
                        disabled={submitting}
                    >
                        {submitting ? "Submitting..." : "✓ Submit Assessment"}

                    </button>
                </div>

                <div className="console">

                    <h4>Console</h4>

                    <pre>{consoleOutput}</pre>

                </div>

            </div>
            {
                submitting &&
                !showSuccessModal &&

                <div className="modal-overlay">

                    <div className="success-modal">

                        <div className="loader"></div>

                        <h2>Submitting Assessment...</h2>

                        <p>

                            Please wait while we evaluate and save your answers.

                        </p>

                    </div>

                </div>
            }
            {
                showSuccessModal &&

                <div className="modal-overlay">

                    <div className="success-modal">

                        <div className="success-icon">

                            ✓

                        </div>

                        <h2>

                            Assessment Submitted Successfully

                        </h2>

                        <p>

                            Your coding assessment has been submitted.

                        </p>

                        <div className="loader"></div>

                        <small>

                            Redirecting to Dashboard...

                        </small>

                    </div>

                </div>
            }

        </div>

    );

}

export default CodingAssessment;