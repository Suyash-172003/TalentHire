package com.talenthire.job.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talenthire.job.dto.CreateJobRequest;
import com.talenthire.job.service.JobService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {
	
	private final JobService jobService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createJob(@RequestBody CreateJobRequest request)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(request));
	}

}
