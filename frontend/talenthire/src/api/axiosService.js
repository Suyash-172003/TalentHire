import axios from "axios";
import { MdFormatIndentDecrease } from "react-icons/md";

var  baseURL="http://localhost:8080";

var  baseURLForTesting="http://localhost:8084";

export const registerUser = async (user) => {

    const response = await axios.post(
        baseURL + "/auth/register",
        user
    );

    return response.data;
};

export const loginUser = async (user) => {

    const response = await axios.post(
        baseURL + "/auth/login",
        user
    );

    return response.data;
};



export const getCodingQuestions = async (assessmentId) => {

    const response = await axios.get(
        `${baseURLForTesting}/assessment/${assessmentId}/coding-questions`
    );

    return response.data;
};

export const runCode = async (request) => {

    const response = await axios.post(
        `${baseURLForTesting}/assessment/run`,
        request
    );

    return response.data;
};

export const submitAssessment = async (request) => {

    const response = await axios.post(
        `${baseURLForTesting}/assessment/submit`,
        request
    );

    return response.data;
};