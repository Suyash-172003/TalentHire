package com.talenthire.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talenthire.application.service.ApplicationService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {
	
	private final ApplicationService applicationService;

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<?> applyJob(
            @PathVariable Integer jobId,
            @RequestHeader("X-User-Id") Integer candidateId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationService.applyJob(jobId, candidateId));
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
