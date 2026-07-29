import axios from "axios";
import { MdFormatIndentDecrease } from "react-icons/md";

var  baseURL="http://localhost:8080";

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