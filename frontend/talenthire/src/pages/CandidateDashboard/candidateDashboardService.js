import axios from "axios";
import axiosService from "../../services/axiosService";

/// Get All Jobs
export const getAllJobs = async () => {
    const token = localStorage.getItem("token");

    return axiosService.get("/job/getAll", {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

// Apply Job
export const applyJob = async (jobId, resumeFile) => {
    const user = JSON.parse(localStorage.getItem("user"));
    const token = localStorage.getItem("token");

    const formData = new FormData();
    formData.append("resumeFile", resumeFile);

    return axiosService.post(
        `/application/jobs/${jobId}/apply`,
        formData,
        {
            headers: {
                "X-User-Id": user.userId,
                Authorization: `Bearer ${token}`,
                "Content-Type": "multipart/form-data"
            }
        }
    );
};

// Upload Resume
export const uploadResume = async (file) => {
    const user = JSON.parse(localStorage.getItem("user"));
    const token = localStorage.getItem("token");

    const formData = new FormData();
    formData.append("file", file);

    return axiosService.post("/resume/upload", formData, {
        headers: {
            "X-User-Id": user.userId,
            Authorization: `Bearer ${token}`
        }
    });
};

// Get My Applications
export const getMyApplications = async () => {
    const user = JSON.parse(localStorage.getItem("user"));
    const token = localStorage.getItem("token");

    return axiosService.get("/application/my", {
        headers: {
            "X-User-Id": user.userId,
            Authorization: `Bearer ${token}`
        }
    });
};