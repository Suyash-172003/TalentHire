import axiosService from "../../services/axiosService";
import axios from "axios";

export const createAssessment = (data) => {
    return axios.post("http://localhost:8084/assessment", data);
};


export const getAssessmentById = (id) => {
    return axios.get(`http://localhost:8084/assessment/${id}`);
};

export const getAssessmentByJobId = (jobId) => {
    return axios.get(`http://localhost:8084/assessment/job/${jobId}`);
};


export const uploadMcqExcel = (assessmentId, file) => {

    const formData = new FormData();

    formData.append("file", file);

    return axios.post(
        `http://localhost:8084/assessment/mcq/upload/${assessmentId}`,
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );

};

export const downloadMCQTemplate = () => {
    return axios.get(
        "http://localhost:8084/assessment/mcq/template",
        {
            responseType: "blob"
        }
    );
};


export const getJobById = async (jobId) => {

    const token = localStorage.getItem("token");

    return axiosService.get(`/job/${jobId}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

};

export const uploadCodingExcel = async (assessmentId, file) => {

    const token = localStorage.getItem("token");

    const formData = new FormData();

    formData.append("file", file);

    return axios.post(
        `http://localhost:8084/assessment/${assessmentId}/coding/upload`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "multipart/form-data"
            }
        }
    );

};

export const downloadCodingTemplate = async () => {

    const token = localStorage.getItem("token");

    return axios.get(
        "http://localhost:8084/assessment/coding/template",
        {
            responseType: "blob",
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

};

export const deleteAssessment = async (assessmentId) => {

    const token = localStorage.getItem("token");

    return axios.delete(`http://localhost:8084/assessment/${assessmentId}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

};

