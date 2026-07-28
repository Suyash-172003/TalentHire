package com.talenthire.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
	
	private final ApplicationService applicationService;

    @PostMapping("/jobs/{jobId}/apply")
    public ResponseEntity<?> applyJob(
            @PathVariable Integer jobId,
            @RequestParam MultipartFile resumeFile,
            @RequestHeader("X-User-Id") Integer candidateId) {
     

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationService.applyJob(resumeFile,jobId,candidateId));
    }
    
    @GetMapping("/my")
    public ResponseEntity<?> getMyApplications(
            @RequestHeader("X-User-Id") Integer candidateId) {

        return ResponseEntity.ok(
                applicationService.getMyApplications(candidateId));
    }
    
    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getApplicationsByJob(
            @PathVariable Integer jobId,
            @RequestHeader("X-User-Id") Integer recruiterId) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByJob(jobId, recruiterId));
    }

}
