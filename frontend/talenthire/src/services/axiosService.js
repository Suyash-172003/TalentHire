import axios from "axios";

const axiosService = axios.create({
    baseURL: "https://16-192-202-172.nip.io"
});

export default axiosService;