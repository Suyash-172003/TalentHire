import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getApplicantsByJob, getJobById, viewResume, assignAssessment, getAssessmentResults, getAssessmentByJobId } from "./recruiterDashboardService";
import "./Applicants.css";



function Applicants() {

    const { jobId } = useParams();
    const navigate = useNavigate();

    const [job, setJob] = useState({});
    const [applicants, setApplicants] = useState([]);
    const [loading, setLoading] = useState(true);
    const [results, setResults] = useState([]);
    const [expandedResult, setExpandedResult] = useState(null);


    useEffect(() => {
        loadData();
    }, []);


    const loadData = async () => {

        try {

            const jobResponse = await getJobById(jobId);
            setJob(jobResponse.data);


            const applicantResponse = await getApplicantsByJob(jobId);

            setApplicants(applicantResponse.data);


        } catch (error) {

            console.log(error);

        } finally {

            setLoading(false);

        }

    };

    const openResume = async (resumeId, candidateId) => {

        try {

            const response = await viewResume(resumeId, candidateId);

            const file = new Blob([response.data], {
                type: "application/pdf"
            });

            const url = URL.createObjectURL(file);

            window.open(url, "_blank");

        } catch (error) {

            console.error(error);
            alert("Unable to open resume.");

        }

    };
    const handleAssignAssessment = async (app) => {

        try {

            await assignAssessment({

                applicationId: app.applicationId,

                candidateId: app.candidateId,

                jobId: job.jobId

            });

            alert("Assessment Assigned Successfully");

        } catch (error) {

            console.log(error);

            alert("Unable to assign assessment.");

        }

    };

    const viewAssessmentResult = async () => {

        try {

            // get assessment using job id
            const assessmentResponse =
                await getAssessmentByJobId(jobId);


            const assessmentId =
                assessmentResponse.data.assessmentId;


            // get final results
            const resultResponse =
                await getAssessmentResults(assessmentId);


            setResults(resultResponse.data);


        } catch (error) {

            console.log(error);

            alert("Unable to load results");

        }

    };

    const getCandidateResult = (candidateId) => {

        return results.find(
            r => r.candidateId === candidateId
        );

    };

    return (

        <div className="applicants-page">

            <div className="container">

                {/* Header */}

                <div className="applicant-header">

                    <h1>
                        Applicants
                    </h1>


                    <button
                        className="back-btn"
                        onClick={() => navigate(-1)}
                    >
                        ← Back
                    </button>

                </div>



                {/* Job Information */}

                <div className="info-section">


                    <div className="info-box">

                        <p>Company</p>

                        <h3>
                            {job.companyName}
                        </h3>

                    </div>



                    <div className="info-box">

                        <p>Role</p>

                        <h3>
                            {job.title}
                        </h3>

                    </div>



                    <div className="info-box">

                        <p>Total Applicants</p>

                        <h3>
                            {applicants.length}
                        </h3>

                    </div>

                    <button
                        className="assessment-btn"
                        onClick={viewAssessmentResult}
                    >
                        View Assessment Results
                    </button>


                </div>




                {/* Table */}

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
                                        <th>#</th>
                                        <th>Application ID</th>
                                        <th>Candidate Name</th>
                                        <th>Email</th>
                                        <th>Application</th>
                                        <th>Screening</th>
                                        <th>Resume</th>
                                        <th>Assessment</th>
                                        <th>Result</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    {
                                        applicants.map((app, index) => {

                                            const result = getCandidateResult(app.candidateId);


                                            return (

                                                <React.Fragment key={app.applicationId}>


                                                    <tr>


                                                        <td>{index + 1}</td>


                                                        <td>
                                                            {app.applicationId}
                                                        </td>


                                                        <td>
                                                            {app.candidateName}
                                                        </td>


                                                        <td>
                                                            {app.candidateEmail}
                                                        </td>


                                                        <td>
                                                            <span className="status">
                                                                {app.applicationStatus}
                                                            </span>
                                                        </td>


                                                        <td>

                                                            <span
                                                                className={
                                                                    app.screeningStatus === "SHORTLISTED"
                                                                        ?
                                                                        "screening shortlisted"
                                                                        :
                                                                        "screening rejected"
                                                                }
                                                            >

                                                                {app.screeningStatus}

                                                            </span>

                                                        </td>



                                                        <td>

                                                            <button
                                                                className="resume-btn"
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
                                                                app.screeningStatus === "SHORTLISTED"

                                                                    ?

                                                                    <button
                                                                        className="assessment-btn"
                                                                        onClick={() => handleAssignAssessment(app)}
                                                                    >
                                                                        Assign Assessment
                                                                    </button>

                                                                    :

                                                                    <span className="disabled-text">
                                                                        Not Eligible
                                                                    </span>

                                                            }

                                                        </td>




                                                        <td>


                                                            {

                                                                result &&

                                                                <div className="result-action">


                                                                    <span
                                                                        className={
                                                                            result.finalResult === "PASS"
                                                                                ?
                                                                                "screening shortlisted"
                                                                                :
                                                                                "screening rejected"
                                                                        }
                                                                    >

                                                                        {result.finalResult }{"   "}

                                                                    </span>



                                                                    <button

                                                                        className="expand-btn"

                                                                        onClick={() =>


                                                                            setExpandedResult(
                                                                                expandedResult === app.candidateId
                                                                                    ?
                                                                                    null
                                                                                    :
                                                                                    app.candidateId
                                                                            )

                                                                        }

                                                                    >

                                                                        {

                                                                            expandedResult === app.candidateId
                                                                                ?
                                                                                "−"
                                                                                :
                                                                                "+"

                                                                        }

                                                                    </button>


                                                                </div>


                                                            }



                                                        </td>



                                                    </tr>



                                                    {

                                                        expandedResult === app.candidateId && result &&


                                                        <tr>

                                                            <td colSpan="9">


                                                                <div className="result-details">


                                                                    <div>

                                                                        <b>
                                                                            MCQ Marks
                                                                        </b>

                                                                        <br />

                                                                        {result.mcqMarks}

                                                                    </div>



                                                                    <div>

                                                                        <b>
                                                                            MCQ Result
                                                                        </b>

                                                                        <br />

                                                                        {result.mcqResult}

                                                                    </div>



                                                                    <div>

                                                                        <b>
                                                                            Coding Marks
                                                                        </b>

                                                                        <br />

                                                                        {result.codingMarks}

                                                                    </div>



                                                                    <div>

                                                                        <b>
                                                                            Coding Result
                                                                        </b>

                                                                        <br />

                                                                        {result.codingResult}

                                                                    </div>



                                                                    <div>

                                                                        <b>
                                                                            Final Result
                                                                        </b>

                                                                        <br />

                                                                        <span
                                                                            className={
                                                                                result.finalResult === "PASS"
                                                                                    ?
                                                                                    "screening shortlisted"
                                                                                    :
                                                                                    "screening rejected"
                                                                            }
                                                                        >

                                                                            {result.finalResult}

                                                                        </span>


                                                                    </div>



                                                                </div>


                                                            </td>


                                                        </tr>


                                                    }



                                                </React.Fragment>


                                            )

                                        })

                                    }


                                </tbody>    



                            </table>




                        </div>


                    )
                }







            </div>


        </div>

    );

}


export default Applicants;