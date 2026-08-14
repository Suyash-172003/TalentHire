package com.talenthire.job.service;

import java.util.List;

import com.talenthire.job.dto.CreateJobRequest;
import com.talenthire.job.dto.CreateJobResponse;
import com.talenthire.job.dto.JobResponse;
import com.talenthire.job.dto.VerifyJobResponse;
import com.talenthire.job.entity.JobStatus;

public interface JobService {

	
	CreateJobResponse createJob(CreateJobRequest request,Integer recruiterId);

	List<JobResponse> getAllJobs();

	JobResponse getJobById(Integer jobId);

	List<JobResponse> getMyJobs(Integer recruiterId);

	CreateJobResponse updateJob(Integer jobId, Integer recruiterId, CreateJobRequest request);

	CreateJobResponse closeJob(Integer jobId, Integer recruiterId);

	VerifyJobResponse verifyJob(Integer jobId);

	

}
