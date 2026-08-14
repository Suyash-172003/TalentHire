package com.talenthire.assessment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.talenthire.assessment.dto.VerifyJobResponse;

@FeignClient(name="job-service")
public interface JobClient {
	
@GetMapping("/job/verify/{jobId}") 
VerifyJobResponse getJobById(@PathVariable("jobId") Integer jobId);


}
