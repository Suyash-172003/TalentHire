import axios from "axios";

const axiosService = axios.create({
    // baseURL: "http://localhost:8080"
    baseURL: "http://16.171.67.13:8080"
});

export default axiosService;