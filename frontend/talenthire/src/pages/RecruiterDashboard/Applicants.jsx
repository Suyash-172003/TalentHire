import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

import {
    getApplicantsByJob,
    getJobById,
    viewResume,
    assignAssessment,
    getAssessmentResults,
    getAssessmentByJobId,
    getAssignedCandidateIds,
    scheduleInterview,
    getInterviewByApplication
} from "./recruiterDashboardService";

import "./Applicants.css";

function Applicants() {

    const { jobId } = useParams();
    const navigate = useNavigate();

    const [job, setJob] = useState({});
    const [applicants, setApplicants] = useState([]);
    const [loading, setLoading] = useState(true);
    const [results, setResults] = useState([]);
    const [expandedResult, setExpandedResult] = useState(null);
    const [assignedCandidates, setAssignedCandidates] = useState([]);
    const [interviews, setInterviews] = useState({});

    const [showInterviewModal, setShowInterviewModal] = useState(false);
    const [selectedApplicant, setSelectedApplicant] = useState(null);
    const [interviewDate, setInterviewDate] = useState("");
    const [meetingLink, setMeetingLink] = useState("");
    const [scheduling, setScheduling] = useState(false);

    useEffect(() => {
        loadData();
    }, [jobId]);

    const loadData = async () => {

        try {

            const jobResponse =
                await getJobById(jobId);

            setJob(jobResponse.data);

            const applicantResponse =
                await getApplicantsByJob(jobId);

            const applicantList =
                applicantResponse.data || [];

            setApplicants(applicantList);

            const assessmentResponse =
                await getAssessmentByJobId(jobId);

            const assessmentId =
                assessmentResponse.data?.assessmentId;

            if (assessmentId) {

                try {

                    const assignedResponse =
                        await getAssignedCandidateIds(
                            assessmentId
                        );

                    setAssignedCandidates(
                        assignedResponse.data || []
                    );

                } catch (error) {

                    console.log(
                        "Unable to load assigned candidates:",
                        error
                    );

                    setAssignedCandidates([]);

                }

                try {

                    const resultResponse =
                        await getAssessmentResults(
                            assessmentId
                        );

                    setResults(
                        resultResponse.data || []
                    );

                } catch (error) {

                    console.log(
                        "Unable to load assessment results:",
                        error
                    );

                    setResults([]);

                }

            }

            const interviewResults =
                await Promise.all(
                    applicantList.map(async (app) => {

                        try {

                            const response =
                                await getInterviewByApplication(
                                    app.applicationId
                                );

                            return {
                                applicationId:
                                    app.applicationId,
                                interview:
                                    response.data
                            };

                        } catch (error) {

                            return {
                                applicationId:
                                    app.applicationId,
                                interview: null
                            };

                        }

                    })
                );

            const interviewMap = {};

            interviewResults.forEach(item => {

                if (item.interview) {

                    interviewMap[
                        item.applicationId
                    ] = item.interview;

                }

            });

            setInterviews(interviewMap);

        } catch (error) {

            console.log(
                "Error loading applicants:",
                error
            );

        } finally {

            setLoading(false);

        }

    };

    const openResume = async (
        resumeId,
        candidateId
    ) => {

        try {

            const response =
                await viewResume(
                    resumeId,
                    candidateId
                );

            const file =
                new Blob(
                    [response.data],
                    {
                        type: "application/pdf"
                    }
                );

            const url =
                URL.createObjectURL(file);

            window.open(
                url,
                "_blank"
            );

        } catch (error) {

            console.error(error);

            alert(
                "Unable to open resume."
            );

        }

    };

    const handleAssignAssessment =
        async (app) => {

            try {

                await assignAssessment({

                    applicationId:
                        app.applicationId,

                    candidateId:
                        app.candidateId,

                    jobId:
                        job.jobId

                });

                setAssignedCandidates(
                    prev => {

                        if (
                            prev.includes(
                                app.candidateId
                            )
                        ) {

                            return prev;

                        }

                        return [
                            ...prev,
                            app.candidateId
                        ];

                    }
                );

                alert(
                    "Assessment Assigned Successfully"
                );

            } catch (error) {

                console.log(error);

                alert(
                    "Unable to assign assessment."
                );

            }

        };

    const getCandidateResult =
        (candidateId) => {

            return results.find(
                r =>
                    Number(r.candidateId) ===
                    Number(candidateId)
            );

        };

    const openScheduleInterview =
        (app) => {

            setSelectedApplicant(app);
            setInterviewDate("");
            setMeetingLink("");
            setShowInterviewModal(true);

        };

    const closeInterviewModal =
        () => {

            setShowInterviewModal(false);
            setSelectedApplicant(null);
            setInterviewDate("");
            setMeetingLink("");

        };

    const handleScheduleInterview =
        async () => {

            if (!selectedApplicant) {
                return;
            }

            if (!interviewDate) {

                alert(
                    "Please select interview date and time."
                );

                return;

            }

            if (!meetingLink) {

                alert(
                    "Please enter meeting link."
                );

                return;

            }

            try {

                setScheduling(true);

                const payload = {

                    applicationId:
                        selectedApplicant.applicationId,

                    candidateId:
                        selectedApplicant.candidateId,

                    interviewDate:
                        interviewDate,

                    meetingLink:
                        meetingLink

                };

                const response =
                    await scheduleInterview(
                        payload
                    );

                setInterviews(
                    prev => ({

                        ...prev,

                        [
                            selectedApplicant.applicationId
                        ]:
                            response.data

                    })
                );

                alert(
                    "Interview Scheduled Successfully"
                );

                closeInterviewModal();

            } catch (error) {

                console.log(
                    "Schedule interview error:",
                    error
                );

                alert(
                    "Unable to schedule interview."
                );

            } finally {

                setScheduling(false);

            }

        };

    const formatInterviewDate =
        (date) => {

            if (!date) {
                return "";
            }

            const d =
                new Date(date);

            return d.toLocaleString(
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

        <div className="applicants-page">

            <div className="container">

                <div className="applicant-header">

                    <h1>
                        Applicants
                    </h1>

                    <button
                        className="back-btn"
                        onClick={() =>
                            navigate(-1)
                        }
                    >
                        ← Back
                    </button>

                </div>

                <div className="info-section">

                    <div className="info-box">

                        <p>
                            Company
                        </p>

                        <h3>
                            {job.companyName}
                        </h3>

                    </div>

                    <div className="info-box">

                        <p>
                            Role
                        </p>

                        <h3>
                            {job.title}
                        </h3>

                    </div>

                    <div className="info-box">

                        <p>
                            Total Applicants
                        </p>

                        <h3>
                            {applicants.length}
                        </h3>

                    </div>

                </div>

                {
                    loading ? (

                        <div className="loading">
                            Loading Applicants...
                        </div>

                    ) : applicants.length === 0 ? (

                        <div className="loading">
                            No Applicants Found
                        </div>

                    ) : (

                        <div className="table-card">

                            <table className="table responsive">

                                <thead>

                                    <tr>

                                        <th>
                                            #
                                        </th>

                                        <th>
                                            Application ID
                                        </th>

                                        <th>
                                            Candidate Name
                                        </th>

                                        <th>
                                            Email
                                        </th>

                                        <th>
                                            Application
                                        </th>

                                        <th>
                                            Screening
                                        </th>

                                        <th>
                                            Resume
                                        </th>

                                        <th>
                                            Assessment
                                        </th>

                                        <th>
                                            Result
                                        </th>

                                        <th>
                                            Interview
                                        </th>

                                    </tr>

                                </thead>

                                <tbody>

                                    {
                                        applicants.map(
                                            (app, index) => {

                                                const result =
                                                    getCandidateResult(
                                                        app.candidateId
                                                    );

                                                const interview =
                                                    interviews[
                                                        app.applicationId
                                                    ];

                                                return (

                                                    <React.Fragment
                                                        key={
                                                            app.applicationId
                                                        }
                                                    >

                                                        <tr>

                                                            <td>
                                                                {index + 1}
                                                            </td>

                                                            <td>
                                                                {
                                                                    app.applicationId
                                                                }
                                                            </td>

                                                            <td>
                                                                {
                                                                    app.candidateName
                                                                }
                                                            </td>

                                                            <td>
                                                                {
                                                                    app.candidateEmail
                                                                }
                                                            </td>

                                                            <td>

                                                                <span className="status">

                                                                    {
                                                                        app.applicationStatus
                                                                    }

                                                                </span>

                                                            </td>

                                                            <td>

                                                                <span
                                                                    className={
                                                                        app.screeningStatus ===
                                                                        "SHORTLISTED"
                                                                            ?
                                                                            "screening shortlisted"
                                                                            :
                                                                            "screening rejected"
                                                                    }
                                                                >

                                                                    {
                                                                        app.screeningStatus
                                                                    }

                                                                </span>

                                                            </td>

                                                            <td>

                                                                <button
                                                                    className="btn btn-info"
                                                                    onClick={() =>
                                                                        openResume(
                                                                            app.resumeId,
                                                                            app.candidateId
                                                                        )
                                                                    }
                                                                >
                                                                    Open Resume
                                                                </button>

                                                            </td>

                                                            <td>

                                                                {
                                                                    app.screeningStatus !==
                                                                    "SHORTLISTED"

                                                                        ? (

                                                                            <span className="disabled-text">
                                                                                Not Eligible
                                                                            </span>

                                                                        )

                                                                        :

                                                                        assignedCandidates.includes(
                                                                            app.candidateId
                                                                        )

                                                                            ? (

                                                                                <button
                                                                                    className="btn btn-success"
                                                                                    disabled
                                                                                >
                                                                                    Assigned
                                                                                </button>

                                                                            )

                                                                            : (

                                                                                <button
                                                                                    className="btn btn-dark"
                                                                                    onClick={() =>
                                                                                        handleAssignAssessment(
                                                                                            app
                                                                                        )
                                                                                    }
                                                                                >
                                                                                    Assign Assessment
                                                                                </button>

                                                                            )
                                                                }

                                                            </td>

                                                            <td>

                                                                {
                                                                    result ? (

                                                                        <div className="result-action">

                                                                            <span
                                                                                className={
                                                                                    result.finalResult ===
                                                                                    "PASS"
                                                                                        ?
                                                                                        "screening shortlisted"
                                                                                        :
                                                                                        "screening rejected"
                                                                                }
                                                                            >

                                                                                {
                                                                                    result.finalResult
                                                                                }

                                                                            </span>

                                                                            <button
                                                                                className="expand-btn"
                                                                                onClick={() =>
                                                                                    setExpandedResult(
                                                                                        expandedResult ===
                                                                                        app.candidateId
                                                                                            ?
                                                                                            null
                                                                                            :
                                                                                            app.candidateId
                                                                                    )
                                                                                }
                                                                            >

                                                                                {
                                                                                    expandedResult ===
                                                                                    app.candidateId
                                                                                        ?
                                                                                        "−"
                                                                                        :
                                                                                        "+"
                                                                                }

                                                                            </button>

                                                                        </div>

                                                                    ) : (

                                                                        <span className="disabled-text">
                                                                            Not Available
                                                                        </span>

                                                                    )
                                                                }

                                                            </td>

                                                            <td>

                                                                {
                                                                    interview ? (

                                                                        <div className="interview-status">

                                                                            <span className="screening shortlisted">
                                                                                Scheduled
                                                                            </span>

                                                                            <small>
                                                                                {
                                                                                    formatInterviewDate(
                                                                                        interview.interviewDate
                                                                                    )
                                                                                }
                                                                            </small>

                                                                        </div>

                                                                    ) : result?.finalResult ===
                                                                        "PASS" ? (

                                                                        <button
                                                                            className="btn btn-dark"
                                                                            onClick={() =>
                                                                                openScheduleInterview(
                                                                                    app
                                                                                )
                                                                            }
                                                                        >
                                                                            Schedule
                                                                        </button>

                                                                    ) : (

                                                                        <span className="disabled-text">
                                                                            Not Eligible
                                                                        </span>

                                                                    )
                                                                }

                                                            </td>

                                                        </tr>

                                                        {
                                                            expandedResult ===
                                                            app.candidateId &&
                                                            result && (

                                                                <tr>

                                                                    <td
                                                                        colSpan="10"
                                                                    >

                                                                        <div className="result-details">

                                                                            <div>

                                                                                <b>
                                                                                    MCQ Marks
                                                                                </b>

                                                                                <br />

                                                                                {
                                                                                    result.mcqMarks
                                                                                }

                                                                            </div>

                                                                            <div>

                                                                                <b>
                                                                                    MCQ Result
                                                                                </b>

                                                                                <br />

                                                                                {
                                                                                    result.mcqResult
                                                                                }

                                                                            </div>

                                                                            <div>

                                                                                <b>
                                                                                    Coding Marks
                                                                                </b>

                                                                                <br />

                                                                                {
                                                                                    result.codingMarks
                                                                                }

                                                                            </div>

                                                                            <div>

                                                                                <b>
                                                                                    Coding Result
                                                                                </b>

                                                                                <br />

                                                                                {
                                                                                    result.codingResult
                                                                                }

                                                                            </div>

                                                                            <div>

                                                                                <b>
                                                                                    Final Result
                                                                                </b>

                                                                                <br />

                                                                                <span
                                                                                    className={
                                                                                        result.finalResult ===
                                                                                        "PASS"
                                                                                            ?
                                                                                            "screening shortlisted"
                                                                                            :
                                                                                            "screening rejected"
                                                                                    }
                                                                                >
                                                                                    {
                                                                                        result.finalResult
                                                                                    }
                                                                                </span>

                                                                            </div>

                                                                        </div>

                                                                    </td>

                                                                </tr>

                                                            )
                                                        }

                                                    </React.Fragment>

                                                );

                                            }
                                        )
                                    }

                                </tbody>

                            </table>

                        </div>

                    )
                }

                {
                    showInterviewModal &&
                    selectedApplicant && (

                        <div className="interview-modal-overlay">

                            <div className="interview-modal">

                                <div className="interview-modal-header">

                                    <h2>
                                        Schedule Interview
                                    </h2>

                                    <button
                                        onClick={
                                            closeInterviewModal
                                        }
                                    >
                                        ×
                                    </button>

                                </div>

                                <div className="interview-candidate-info">

                                    <p>

                                        Candidate:

                                        <strong>
                                            {" "}
                                            {
                                                selectedApplicant.candidateName
                                            }
                                        </strong>

                                    </p>

                                    <p>

                                        Email:

                                        <strong>
                                            {" "}
                                            {
                                                selectedApplicant.candidateEmail
                                            }
                                        </strong>

                                    </p>

                                </div>

                                <div className="form-group">

                                    <label>
                                        Interview Date & Time
                                    </label>

                                    <input
                                        type="datetime-local"
                                        value={
                                            interviewDate
                                        }
                                        onChange={(e) =>
                                            setInterviewDate(
                                                e.target.value
                                            )
                                        }
                                    />

                                </div>

                                <div className="form-group">

                                    <label>
                                        Meeting Link
                                    </label>

                                    <input
                                        type="text"
                                        placeholder="https://zoom.us/..."
                                        value={
                                            meetingLink
                                        }
                                        onChange={(e) =>
                                            setMeetingLink(
                                                e.target.value
                                            )
                                        }
                                    />

                                </div>

                                <div className="interview-modal-actions">

                                    <button
                                        className="btn btn-secondary"
                                        onClick={
                                            closeInterviewModal
                                        }
                                        disabled={
                                            scheduling
                                        }
                                    >
                                        Cancel
                                    </button>

                                    <button
                                        className="btn btn-dark"
                                        onClick={
                                            handleScheduleInterview
                                        }
                                        disabled={
                                            scheduling
                                        }
                                    >

                                        {
                                            scheduling
                                                ?
                                                "Scheduling..."
                                                :
                                                "Schedule Interview"
                                        }

                                    </button>

                                </div>

                            </div>

                        </div>

                    )
                }

            </div>

        </div>

    );

}

export default Applicants;