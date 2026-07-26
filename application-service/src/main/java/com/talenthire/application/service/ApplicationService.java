package com.talenthire.application.service;

import java.util.List;

import com.talenthire.application.dto.ApplyJobResponse;
import com.talenthire.application.dto.JobApplicationResponse;
import com.talenthire.application.dto.MyApplicationResponse;

public interface ApplicationService {

	ApplyJobResponse applyJob(Integer jobId, Integer candidateId);

	List<MyApplicationResponse>  getMyApplications(Integer candidateId);

	List<JobApplicationResponse> getApplicationsByJob(Integer jobId, Integer recruiterId);

}
