import axiosService from "../../services/axiosService";

// Create Assessment
export const createAssessment = (data) => {
    const token = localStorage.getItem("token");

    return axiosService.post("/assessment", data, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

// Get Assessment By Id
export const getAssessmentById = (id) => {
    const token = localStorage.getItem("token");

    return axiosService.get(`/assessment/${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

// Get Assessment By Job Id
export const getAssessmentByJobId = (jobId) => {
    const token = localStorage.getItem("token");

    return axiosService.get(`/assessment/job/${jobId}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

// Upload MCQ Excel
export const uploadMcqExcel = (assessmentId, file) => {
    const token = localStorage.getItem("token");

    const formData = new FormData();
    formData.append("file", file);

    return axiosService.post(
        `/assessment/mcq/upload/${assessmentId}`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "multipart/form-data"
            }
        }
    );
};

// Download MCQ Template
export const downloadMCQTemplate = () => {
    const token = localStorage.getItem("token");

    return axiosService.get("/assessment/mcq/template", {
        responseType: "blob",
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

// Get Job By Id
export const getJobById = (jobId) => {
    const token = localStorage.getItem("token");

    return axiosService.get(`/job/${jobId}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};

// Upload Coding Excel
export const uploadCodingExcel = (assessmentId, file) => {
    const token = localStorage.getItem("token");

    const formData = new FormData();
    formData.append("file", file);

    return axiosService.post(
        `/assessment/${assessmentId}/coding/upload`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "multipart/form-data"
            }
        }
    );
};

// Download Coding Template
export const downloadCodingTemplate = () => {
    const token = localStorage.getItem("token");

    return axiosService.get("/assessment/coding/template", {
        responseType: "blob",
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};





// Update Assessment
export const updateAssessment = (assessmentId, data) => {
    const token = localStorage.getItem("token");

    return axiosService.put(
        `/assessment/${assessmentId}`,
        data,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
};


// Delete Assessment
export const deleteAssessment = (assessmentId) => {
    const token = localStorage.getItem("token");

    return axiosService.delete(
        `/assessment/${assessmentId}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
};