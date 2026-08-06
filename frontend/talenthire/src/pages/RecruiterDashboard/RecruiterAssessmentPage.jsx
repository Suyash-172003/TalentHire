import "./RecruiterAssessmentPage.css";
import { useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import {
    createAssessment,
    getAssessmentByJobId,
    uploadMcqExcel,
    downloadMCQTemplate,
    getJobById,
    uploadCodingExcel,
    downloadCodingTemplate,
    updateAssessment
} from "./recruiterAssessmentService";

function RecruiterAssessmentPage() {

    const navigate = useNavigate();
    const { jobId } = useParams();
    const [mcqFile, setMcqFile] = useState(null);


    const user = JSON.parse(localStorage.getItem("user"));

    const [assessment, setAssessment] = useState(null);


    const [formData, setFormData] = useState({
        jobId: Number(jobId),
        title: "",
        assessmentType: "MCQ",

        // MCQ
        duration: "",
        passMarks: "",

        // Coding
        codingDuration: "",
        codingPassMarks: "",

        startTime: "",
        endTime: "",
        createdBy: user.userId
    });

    const [job, setJob] = useState(null);

    const loadJob = async () => {
        const response = await getJobById(jobId);
        setJob(response.data);
    };

    useEffect(() => {
        loadAssessment();
        loadJob();
    }, []);



    const loadAssessment = async () => {

        try {

            const response = await getAssessmentByJobId(jobId);

            console.log("Assessment Response:", response.data);

            // If backend returns a List
            if (Array.isArray(response.data)) {

                if (response.data.length > 0) {
                    setAssessment(response.data[0]);
                }

            } else {

                // If backend returns a single object
                setAssessment(response.data);

            }

        } catch (error) {

            console.log(error);

        }

    };

    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData({
            ...formData,
            [name]: value
        });

    };


    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await createAssessment(formData);

            console.log("Status:", response.status);
            console.log("Data:", response.data);

            alert("Assessment Created Successfully");

            setAssessment(response.data);
        } catch (error) {
            console.log("ERROR:", error);
            console.log("MESSAGE:", error.message);
            console.log("RESPONSE:", error.response);

            alert("Failed to Create Assessment");
        }
    };

    const handleMcqUpload = async () => {

        if (!mcqFile) {
            alert("Please choose an Excel file.");
            return;
        }

        try {
            await uploadMcqExcel(assessment.assessmentId, mcqFile);
            alert("MCQ uploaded successfully.");
        }
        catch (error) {
            console.log(error);
            alert("Upload failed.");
        }

    };

    const handleDownloadTemplate = async () => {

        try {

            const response = await downloadMCQTemplate();

            const url = window.URL.createObjectURL(
                new Blob([response.data])
            );

            const link = document.createElement("a");

            link.href = url;
            link.download = "MCQ_Template.xlsx";

            document.body.appendChild(link);

            link.click();

            link.remove();

        } catch (error) {

            console.log(error);

            alert("Failed to download template");

        }

    };

    const [codingFile, setCodingFile] = useState(null);

    const handleCodingFileChange = (e) => {
        setCodingFile(e.target.files[0]);
    };

    const handleCodingUpload = async () => {

        if (!codingFile) {
            alert("Please select a Coding Excel file.");
            return;
        }

        try {

            const response = await uploadCodingExcel(
                assessment.assessmentId,
                codingFile
            );

            alert(response.data);

        } catch (error) {

            console.log(error);

            alert("Failed to upload Coding Questions.");

        }

    };

    const handleCodingTemplateDownload = async () => {

        try {

            const response = await downloadCodingTemplate();

            const url = window.URL.createObjectURL(
                new Blob([response.data])
            );

            const link = document.createElement("a");

            link.href = url;
            link.download = "Coding_Template.xlsx";

            document.body.appendChild(link);

            link.click();

            link.remove();

        } catch (error) {

            console.log(error);

            alert("Failed to download Coding Template");

        }

    };

  

    const [isEditing, setIsEditing] = useState(false);
    const handleEdit = () => {

        setFormData({

            jobId: assessment.jobId,
            title: assessment.title,
            description: assessment.description || "",

            assessmentType: assessment.assessmentType,

            duration: assessment.duration,
            totalMarks: assessment.totalMarks || "",

            passMarks: assessment.passMarks,

            codingDuration: assessment.codingDuration || "",
            codingTotalMarks: assessment.codingTotalMarks || "",
            codingPassMarks: assessment.codingPassMarks || "",

            startTime: assessment.startTime.slice(0, 16),
            endTime: assessment.endTime.slice(0, 16),

            createdBy: assessment.createdBy

        });

        setIsEditing(true);
    };

    const handleUpdate = async (e) => {

        e.preventDefault();

        try {

            const response = await updateAssessment(
                assessment.assessmentId,
                formData
            );

            alert("Assessment Updated Successfully");

            setAssessment(response.data);

            setIsEditing(false);

        }
        catch (error) {

            console.log(error);

            alert("Failed to Update Assessment");

        }

    };

    return (

        <div className="assessment-page">

            <div className="assessment-header">


                <div className="assessment-actions">
                    <button
                        className="assessment-back-btn"
                        onClick={() => navigate(-1)}
                    >
                        ← Back
                    </button>

                   

                    <button
                        style={{ color: "yellow" }}
                        className="assessment-back-btn"
                        onClick={handleEdit}
                    >
                        ✏ Edit Assessment
                    </button>



                </div>



                <h1>Assessment Management</h1>

            </div>

            <div className="assessment-job-card">

                <div>
                    <h2>{job?.title}</h2>

                    <h4>{job?.companyName}</h4>

                    <p>
                        Create an assessment and then upload MCQ or Coding questions.
                    </p>

                </div>

            </div>

            {
                assessment == null || isEditing ? (

                    <div className="assessment-card">

                        <h2>Create Assessment</h2>

                        <form onSubmit={isEditing ? handleUpdate : handleSubmit}>

                            <div className="assessment-form-grid">

                                <div className="assessment-form-group">

                                    <label>Assessment Title</label>

                                    <input
                                        type="text"
                                        name="title"
                                        value={formData.title}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="assessment-form-group">

                                    <label>Assessment Type</label>

                                    <select
                                        name="assessmentType"
                                        value={formData.assessmentType}
                                        onChange={handleChange}
                                    >
                                        <option value="MCQ">MCQ</option>
                                        <option value="CODING">CODING</option>
                                        <option value="BOTH">BOTH</option>
                                    </select>

                                </div>

                                <div className="assessment-form-group">

                                    <label>MCQ Duration (Minutes)</label>

                                    <input
                                        type="number"
                                        name="duration"
                                        value={formData.duration}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="assessment-form-group">

                                    <label>Coding Duration (Minutes)</label>

                                    <input
                                        type="number"
                                        name="codingDuration"
                                        value={formData.codingDuration}
                                        onChange={handleChange}
                                    />

                                </div>

                                <div className="assessment-form-group">

                                    <label>MCQ Pass Marks</label>

                                    <input
                                        type="number"
                                        name="passMarks"
                                        value={formData.passMarks}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>
                                <div className="assessment-form-group">

                                    <label>Coding Pass Marks</label>

                                    <input
                                        type="number"
                                        name="codingPassMarks"
                                        value={formData.codingPassMarks}
                                        onChange={handleChange}
                                    />

                                </div>

                                <div className="assessment-form-group">

                                    <label>Start Time</label>

                                    <input
                                        type="datetime-local"
                                        name="startTime"
                                        value={formData.startTime}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                                <div className="assessment-form-group">

                                    <label>End Time</label>

                                    <input
                                        type="datetime-local"
                                        name="endTime"
                                        value={formData.endTime}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>

                            </div>

                            <div className="assessment-actions">

                                <button
                                    type="submit"
                                    className="primary-btn"
                                >
                                    {isEditing ? "Update Assessment" : "Create Assessment"}
                                </button>
                                {
                                    isEditing &&

                                    <button
                                        type="button"
                                        className="secondary-btn"
                                        onClick={() => setIsEditing(false)}
                                    >
                                        Cancel
                                    </button>
                                }

                            </div>


                        </form>

                    </div>

                ) : (

                    <div className="assessment-card">

                        <h2>Assessment Created Successfully ✅</h2>

                        <div className="assessment-grid">

                            <div className="assessment-grid">

                                <div className="assessment-grid-item">
                                    <h4>Title</h4>
                                    <p>{assessment.title}</p>
                                </div>

                                <div className="assessment-grid-item">
                                    <h4>Type</h4>
                                    <p>{assessment.assessmentType}</p>
                                </div>

                                <div className="assessment-grid-item">
                                    <h4>MCQ Duration</h4>
                                    <p>{assessment.duration} Minutes</p>
                                </div>

                                <div className="assessment-grid-item">
                                    <h4>MCQ Pass Marks</h4>
                                    <p>{assessment.passMarks}</p>
                                </div>

                                <div className="assessment-grid-item">
                                    <h4>Coding Duration</h4>
                                    <p>{assessment.codingDuration} Minutes</p>
                                </div>

                                <div className="assessment-grid-item">
                                    <h4>Coding Pass Marks</h4>
                                    <p>{assessment.codingPassMarks}</p>
                                </div>

                                <div className="assessment-grid-item">
                                    <h4>Start Time</h4>
                                    <p>{new Date(assessment.startTime).toLocaleString()}</p>
                                </div>

                                <div className="assessment-grid-item">
                                    <h4>End Time</h4>
                                    <p>{new Date(assessment.endTime).toLocaleString()}</p>
                                </div>

                            </div>

                        </div>

                    </div>

                )
            }
            <div className="assessment-section-wrapper">

                {/* MCQ Section */}

                <div className="assessment-section-card">

                    <h2>📝 MCQ Assessment</h2>

                    <p>Upload MCQ Excel after creating the assessment.</p>

                    <div className="assessment-section-buttons">

                        <button
                            type="button"
                            disabled={!assessment}
                            onClick={handleDownloadTemplate}
                        >
                            ⬇ Download MCQ Template
                        </button>

                        <input
                            type="file"
                            accept=".xlsx,.xls"
                            onChange={(e) => setMcqFile(e.target.files[0])}
                            className="form-control"
                        />

                        <button
                            type="button"
                            onClick={handleMcqUpload}
                            disabled={!assessment}
                        >
                            ⬆ Upload MCQ Excel
                        </button>

                        <button
                            type="button"
                            disabled={!assessment}
                        >
                            📋 View MCQ Questions
                        </button>

                    </div>

                </div>

                {/* Coding Section */}

                <div className="assessment-section-card">

                    <h2>💻 Coding Assessment</h2>

                    <p>Upload Coding Excel after creating the assessment.</p>

                    <div className="assessment-section-buttons">

                        <button
                            type="button"
                            disabled={!assessment}
                            onClick={handleCodingTemplateDownload}
                        >
                            ⬇ Download Coding Template
                        </button>

                        <input
                            className="form-control"
                            type="file"
                            accept=".xlsx,.xls"
                            onChange={handleCodingFileChange}
                        />

                        <button
                            type="button"
                            disabled={!assessment}
                            onClick={handleCodingUpload}
                        >
                            ⬆ Upload Coding Excel
                        </button>

                        <button
                            type="button"
                            disabled={!assessment}
                        >
                            👨‍💻 View Coding Questions
                        </button>

                    </div>

                </div>

            </div>

           

        </div>

    );

}

export default RecruiterAssessmentPage;