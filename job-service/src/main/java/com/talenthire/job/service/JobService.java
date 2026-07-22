package com.talenthire.job.service;

import com.talenthire.job.dto.CreateJobRequest;
import com.talenthire.job.dto.CreateJobResponse;

public interface JobService {

	
	CreateJobResponse createJob(CreateJobRequest request);

}
