package com.talenthire.application.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.talenthire.application.dto.JobResponse;
import com.talenthire.application.dto.JobSkillResponse;
import com.talenthire.application.dto.VerifyJobResponse;

@FeignClient(name="job-service",url="${feign.client.job-service.url}")
public interface JobClient {
	
@GetMapping("/job/verify/{jobId}") 
VerifyJobResponse getJobById(@PathVariable("jobId") Integer jobId);


@GetMapping("/job/{jobId}") 
JobResponse getJob(@PathVariable("jobId") Integer jobId);


@GetMapping("/job/{jobId}")
JobSkillResponse getJobSkills(@PathVariable Integer jobId);

}
