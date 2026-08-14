import axios from "axios";
import { MdFormatIndentDecrease } from "react-icons/md";

var  baseURL="http://16.171.67.13:8080";

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

export const getPaymentStatus = async (token) => {

    const response = await axios.get(
        "http://16.171.67.13:8085/payment/status",
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    return response.data;
};