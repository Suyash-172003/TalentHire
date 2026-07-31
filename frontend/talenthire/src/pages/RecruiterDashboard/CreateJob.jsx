import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createJob } from "./recruiterDashboardService";
import "./CreateJob.css";

function CreateJob() {

    const navigate = useNavigate();

    const [job, setJob] = useState({
        title: "",
        companyName: "",
        description: "",
        location: "",
        salary: "",
        experienceRequired: "",
        vacancies: "",
        shortlistScore: "",
        employmentType: "FULL_TIME",
        workMode: "ONSITE",
        skills: ""
    });

    const handleChange = (e) => {
        setJob({
            ...job,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {

            const payload = {
                ...job,
                salary: Number(job.salary),
                experienceRequired: Number(job.experienceRequired),
                vacancies: Number(job.vacancies),
                shortlistScore: Number(job.shortlistScore),
                skills: job.skills
                    .split(",")
                    .map(skill => skill.trim())
                    .filter(skill => skill !== "")
            };

            await createJob(payload);

            alert("Job Created Successfully");

            navigate("/recruiter/dashboard");

        } catch (error) {

            console.error(error);
            alert("Failed to create job");

        }
    };

    return (
        <div className="create-job">

            <div className="create-job-card">

                <h1>Create New Job</h1>

                <form onSubmit={handleSubmit}>

                    <input
                        type="text"
                        name="title"
                        placeholder="Job Title"
                        value={job.title}
                        onChange={handleChange}
                        required
                    />

                    <input
                        type="text"
                        name="companyName"
                        placeholder="Company Name"
                        value={job.companyName}
                        onChange={handleChange}
                        required
                    />

                    <input
                        type="text"
                        name="location"
                        placeholder="Location"
                        value={job.location}
                        onChange={handleChange}
                        required
                    />

                    <input
                        type="number"
                        name="salary"
                        placeholder="Salary"
                        value={job.salary}
                        onChange={handleChange}
                        required
                    />

                    <input
                        type="number"
                        name="experienceRequired"
                        placeholder="Experience Required (Years)"
                        value={job.experienceRequired}
                        onChange={handleChange}
                        required
                    />

                    <input
                        type="number"
                        name="vacancies"
                        placeholder="Vacancies"
                        value={job.vacancies}
                        onChange={handleChange}
                        required
                    />

                    <input
                        type="number"
                        name="shortlistScore"
                        placeholder="Shortlist Score"
                        value={job.shortlistScore}
                        onChange={handleChange}
                        min="0"
                        max="100"
                        required
                    />

                    <select
                        name="employmentType"
                        value={job.employmentType}
                        onChange={handleChange}
                    >
                        <option value="FULL_TIME">Full Time</option>
                        <option value="PART_TIME">Part Time</option>
                        <option value="INTERNSHIP">Internship</option>
                        <option value="CONTRACT">Contract</option>
                        <option value="FREELANCE">Freelance</option>
                    </select>

                    <select
                        name="workMode"
                        value={job.workMode}
                        onChange={handleChange}
                    >
                        <option value="ONSITE">Onsite</option>
                        <option value="HYBRID">Hybrid</option>
                        <option value="REMOTE">Remote</option>
                    </select>

                    <input
                        type="text"
                        name="skills"
                        placeholder="Java, Spring Boot, React"
                        value={job.skills}
                        onChange={handleChange}
                        style={{ gridColumn: "1 / 3" }}
                        required
                    />

                    <textarea
                        name="description"
                        rows="6"
                        placeholder="Job Description"
                        value={job.description}
                        onChange={handleChange}
                        style={{ gridColumn: "1 / 3" }}
                        required
                    />

                    <button type="submit">
                        Create Job
                    </button>

                </form>

            </div>

        </div>
    );
}

export default CreateJob;