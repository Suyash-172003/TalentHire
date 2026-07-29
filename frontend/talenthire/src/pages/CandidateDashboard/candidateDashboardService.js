import axios from "axios";
import axiosService from "../../services/axiosService";

// Get All Jobs
export const getAllJobs = async () => {

    const token = localStorage.getItem("token");

    return axiosService.get(
        `/job/getAll`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

};

// Apply Job (directly to Application Service)
export const applyJob = async (jobId, resumeFile) => {

    const user = JSON.parse(localStorage.getItem("user"));

    const formData = new FormData();

    formData.append("resumeFile", resumeFile);


    return axios.post(
        `http://localhost:8083/application/jobs/${jobId}/apply`,
        formData,
        {
            headers:{
                "X-User-Id": user.userId,
                "Content-Type":"multipart/form-data"
            }
        }
    );

};

// Upload Resume
export const uploadResume = async (file) => {

    const user = JSON.parse(localStorage.getItem("user"));

    const formData = new FormData();

    formData.append("file", file);

    return axios.post(
        "http://localhost:8083/resume/upload",
        formData,
        {
            headers: {
                "X-User-Id": user.userId
            }
        }
    );
};

// Get My Applications
export const getMyApplications = async () => {

    const user = JSON.parse(localStorage.getItem("user"));

    return axios.get(
        "http://localhost:8083/application/my",
        {
            headers: {
                "X-User-Id": user.userId
            }
        }
    );
};

