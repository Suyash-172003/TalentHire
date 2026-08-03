import axios from "axios";
import axiosService from "../../services/axiosService";



export const getMyJobs = async () => {

    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));

    return axiosService.get(`/job/my`, {
        headers: {
            Authorization: `Bearer ${token}`,
            "X-User-Id": user.id
        }
    });

};

export const createJob = async (jobData) => {

    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));

    return axiosService.post(
        `/job/create`,
        jobData,
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "X-User-Id": user.id
            }
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

export const updateJob = async (jobId, jobData) => {

    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));

    return axiosService.put(
        `/job/${jobId}`,
        jobData,
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "X-User-Id": user.id
            }
        }
    );

};


export const getApplicantsByJob = async (jobId) => {

    const user = JSON.parse(localStorage.getItem("user"));
    const token = localStorage.getItem("token");
  

    return axiosService.get(`/application/job/${jobId}`,
        {
            headers: {
                "X-User-Id": user.userId,
                Authorization: `Bearer ${token}`
            }
        }
    );
};


export const viewResume = (resumeId, candidateId) => {
    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));

    return axiosService.get("/resume/view", {

        params: {
            resumeId: resumeId
        },

        headers: {
            "X-User-Id": candidateId,
             Authorization: `Bearer ${token}`
        },

        responseType: "blob"

    });

};

export const closeJob = async (jobId) => {

    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));
    console.log(jobId);
    return axiosService.patch(
        `/job/${jobId}/close`,
        {},
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "X-User-Id": user.userId
            }
        }
    );
};