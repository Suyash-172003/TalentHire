import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
    getJobById,
    updateJob
} from "./recruiterDashboardService";
import "./CreateJob.css";

function EditJob() {

    const navigate = useNavigate();
    const { jobId } = useParams();

    const [job, setJob] = useState({
        title: "",
        companyName: "",
        description: "",
        location: "",
        salary: "",
        experience: "",
        vacancies: "",
        employmentType: "FULL_TIME",
        workMode: "ONSITE",
        skills: ""
    });

    useEffect(() => {
        loadJob();
    }, []);

    const loadJob = async () => {

        try {

            const response = await getJobById(jobId);

            setJob({
                ...response.data,
                skills: response.data.skills.join(", ")
            });

        } catch (error) {

            console.error(error);
            alert("Failed to load job");

        }

    };

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
                title: job.title,
                companyName: job.companyName,
                description: job.description,
                location: job.location,
                salary: Number(job.salary),

                // Backend expects this field
                experienceRequired: Number(job.experience),

                vacancies: Number(job.vacancies),
                employmentType: job.employmentType,
                workMode: job.workMode,

                skills: job.skills
                    .split(",")
                    .map(skill => skill.trim())
            };

            await updateJob(jobId, payload);

            alert("Job Updated Successfully");

            navigate("/recruiter/dashboard");

        } catch (error) {

            console.error(error);
            alert("Failed to update job");

        }

    };

    return (

        <div className="create-job">

            <div className="create-job-card">

                <h1>Edit Job</h1>

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
                        name="experience"
                        placeholder="Experience (Years)"
                        value={job.experience}
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
                        Update Job
                    </button>

                </form>

            </div>

        </div>

    );

}

export default EditJob;