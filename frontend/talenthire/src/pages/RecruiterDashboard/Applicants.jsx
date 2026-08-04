import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getApplicantsByJob, getJobById, viewResume, assignAssessment } from "./recruiterDashboardService";
import "./Applicants.css";



function Applicants() {

    const { jobId } = useParams();
    const navigate = useNavigate();

    const [job, setJob] = useState({});
    const [applicants, setApplicants] = useState([]);
    const [loading, setLoading] = useState(true);


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
                                    </tr>
                                </thead>

                                <tbody>

                                    {applicants.map((app, index) => (

                                        <tr key={app.applicationId}>

                                            <td>{index + 1}</td>

                                            <td>{app.applicationId}</td>

                                            <td>{app.candidateName}</td>

                                            <td>{app.candidateEmail}</td>

                                            <td>
                                                <span className="status">
                                                    {app.applicationStatus}
                                                </span>
                                            </td>

                                            <td>
                                                <span
                                                    className={
                                                        app.screeningStatus === "SHORTLISTED"
                                                            ? "screening shortlisted"
                                                            : "screening rejected"
                                                    }
                                                >
                                                    {app.screeningStatus}
                                                </span>
                                            </td>

                                            <td>
                                                <button
                                                    className="resume-btn"
                                                    onClick={() =>
                                                        openResume(app.resumeId, app.candidateId)
                                                    }
                                                >
                                                    Open Resume
                                                </button>
                                            </td>

                                            <td>
                                                {app.screeningStatus === "SHORTLISTED" ? (
                                                    <button
                                                        className="assessment-btn"
                                                        onClick={() => handleAssignAssessment(app)}
                                                    >
                                                        Assign Assessment
                                                    </button>
                                                ) : (
                                                    <span className="disabled-text">
                                                        Not Eligible
                                                    </span>
                                                )}
                                            </td>

                                        </tr>

                                    ))}

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