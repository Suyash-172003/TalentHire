import axiosService from "../../services/axiosService";

export const getCodingQuestions = async (assessmentId) => {

    const token = localStorage.getItem("token");

    const response = await axiosService.get(
        `/assessment/${assessmentId}/coding-questions`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};

export const runCode = async (request) => {

    const token = localStorage.getItem("token");

    const response = await axiosService.post(
        `/assessment/run`,
        request,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};

export const submitAssessment = async (request) => {

    const token = localStorage.getItem("token");

    const response = await axiosService.post(
        `/assessment/submit`,
        request,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};