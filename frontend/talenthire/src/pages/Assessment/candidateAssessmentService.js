import axiosService from "../../services/axiosService";

export const getMyAssessments = async () => {

    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user"));

    return axiosService.get(
        `/assessment/assignment/candidate/${user.userId}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

};


export const getAssessmentById = async (assessmentId) => {

    const token = localStorage.getItem("token");

    return axiosService.get(
        `/assessment/${assessmentId}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

};