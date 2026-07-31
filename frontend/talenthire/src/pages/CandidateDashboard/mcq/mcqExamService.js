import axios from "axios";

const BASE_URL = "http://localhost:8084/assessment/mcq";

/**
 * Start or Resume MCQ Exam
 * POST /assessment/mcq/exam/start
 */
export const startExam = async (assessmentId, candidateId) => {

    return axios.post(`${BASE_URL}/exam/start`, {
        assessmentId,
        candidateId
    });

};

/**
 * Get all MCQ questions for an assessment
 * GET /assessment/mcq/assessments/{assessmentId}/questions
 */
export const getQuestions = async (assessmentId) => {

    return axios.get(
        `${BASE_URL}/assessments/${assessmentId}/questions`
    );

};

/**
 * Submit Exam
 * POST /assessment/mcq/exam/{attemptId}/submit
 */
export const submitExam = async (attemptId, payload) => {

    return axios.post(
        `${BASE_URL}/exam/${attemptId}/submit`,
        payload
    );

};

/**
 * Get Result
 * GET /assessment/mcq/exam/{attemptId}/result
 */
export const getResult = async (attemptId) => {

    return axios.get(
        `${BASE_URL}/exam/${attemptId}/result`
    );

};