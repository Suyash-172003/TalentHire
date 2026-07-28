package com.talenthire.application.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.dto.ApplyJobRequest;
import com.talenthire.application.dto.ApplyJobResponse;
import com.talenthire.application.dto.JobApplicationResponse;
import com.talenthire.application.dto.MyApplicationResponse;

public interface ApplicationService {

	ApplyJobResponse applyJob(MultipartFile resumeFile,
            Integer jobId,
            Integer candidateId);

	List<MyApplicationResponse>  getMyApplications(Integer candidateId);

	List<JobApplicationResponse> getApplicationsByJob(Integer jobId, Integer recruiterId);

}
