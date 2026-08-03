import axios from "axios";
import axiosService from "../../../services/axiosService";

/**
 * Start or Resume MCQ Exam
 * POST /assessment/mcq/exam/start
 */
export const startExam = (assessmentId, candidateId) => {
    const token = localStorage.getItem("token");

    return axiosService.post(
        "/assessment/mcq/exam/start",
        {
            assessmentId,
            candidateId
        },
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
};

/**
 * Get all MCQ questions for an assessment
 * GET /assessment/mcq/assessments/{assessmentId}/questions
 */
export const getQuestions = (assessmentId) => {
    const token = localStorage.getItem("token");

    return axiosService.get(
        `/assessment/mcq/assessments/${assessmentId}/questions`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
};

/**
 * Submit Exam
 * POST /assessment/mcq/exam/{attemptId}/submit
 */
export const submitExam = (attemptId, payload) => {
    const token = localStorage.getItem("token");

    return axiosService.post(
        `/assessment/mcq/exam/${attemptId}/submit`,
        payload,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
};

/**
 * Get Result
 * GET /assessment/mcq/exam/{attemptId}/result
 */
export const getResult = (attemptId) => {
    const token = localStorage.getItem("token");

    return axiosService.get(
        `/assessment/mcq/exam/${attemptId}/result`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
};