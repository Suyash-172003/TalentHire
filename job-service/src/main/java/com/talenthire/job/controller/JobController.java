package com.talenthire.job.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	public ResponseEntity<?> createJob(@RequestBody CreateJobRequest request,@RequestHeader("X-User-Id") Integer recruiterId)
	{
		System.out.println("Header"+recruiterId);
		return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(request,recruiterId));
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllJobs() {
	    return ResponseEntity.ok().body(jobService.getAllJobs());
	}
	
	@GetMapping("/{jobId}")
	public ResponseEntity<?> getJobById(@PathVariable Integer jobId) {
	    return ResponseEntity.ok(jobService.getJobById(jobId));
	}
	
	@GetMapping("/my")
	public ResponseEntity<?> getMyJobs(
	        @RequestHeader("X-User-Id") Integer recruiterId) {

	    return ResponseEntity.ok(jobService.getMyJobs(recruiterId));
	}
	
	@PutMapping("/{jobId}")
	public ResponseEntity<?> updateJob(
	        @PathVariable Integer jobId,
	        @RequestHeader("X-User-Id") Integer recruiterId,
	        @RequestBody CreateJobRequest request) {

	    return ResponseEntity.ok(
	            jobService.updateJob(jobId, recruiterId, request));
	}
	
	@PatchMapping("/{jobId}/close")
	public ResponseEntity<?> closeJob(
	        @PathVariable Integer jobId,
	        @RequestHeader("X-User-Id") Integer recruiterId) {

	    return ResponseEntity.ok(
	            jobService.closeJob(jobId, recruiterId));
	}
	
	@GetMapping("/verify/{jobId}")
	public ResponseEntity<?> verifyJob(
	        @PathVariable Integer jobId) {

	    return ResponseEntity.ok(
	            jobService.verifyJob(jobId));
	}

}
