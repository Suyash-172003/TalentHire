import axios from "axios";
import { MdFormatIndentDecrease } from "react-icons/md";

var  baseURL="http://localhost:8080";

var  baseURLForTesting="http://localhost:8084";

export const registerUser = async (user) => {

    const response = await axios.post(
        baseURL + "/auth/register",
        user,
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


export const forgotPassword = async (email) => {

    const response = await axios.post(
        baseURL + "/auth/forgot-password",
        { email }
    );

    return response.data;
};


export const verifyOtp = async (request) => {

    const response = await axios.post(

        baseURL + "/auth/verify-otp",

        request

    );
    return response.data;
};

export const resetPassword = async (request) => {

    const response = await axios.post(

        baseURL + "/auth/reset-password",

        request

    );

    return response.data;

};

