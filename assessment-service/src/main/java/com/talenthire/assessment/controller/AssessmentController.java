package com.talenthire.assessment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talenthire.assessment.dto.CodeExecutionRequest;
import com.talenthire.assessment.dto.CreateAssessmentRequest;
import com.talenthire.assessment.dto.CreateCodingQuestionRequest;
import com.talenthire.assessment.dto.CreateTestCaseRequest;
import com.talenthire.assessment.service.AssessmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assessment")
public class AssessmentController {

	
	private final AssessmentService assessmentService;
	
	
	 @PostMapping("/create")
	    public ResponseEntity<?> createAssessment(
	            @RequestBody CreateAssessmentRequest request,
	            @RequestHeader("X-User-Id") Integer recruiterId) {

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(assessmentService.createAssessment(request, recruiterId));
	    }

	
	@PostMapping("/run")
	public ResponseEntity<?> runCode(@RequestBody CodeExecutionRequest request)
	{
		return ResponseEntity.status(HttpStatus.OK).body(assessmentService.execute(request));
	}
	
	
	@PostMapping("/{assessmentId}/coding-question")
	public ResponseEntity<?> addCodingQuestion(
	        @PathVariable Integer assessmentId,
	        @RequestBody CreateCodingQuestionRequest request,
	        @RequestHeader("X-User-Id") Integer recruiterId) {

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(assessmentService.addCodingQuestion(
	                    assessmentId,
	                    recruiterId,
	                    request));
	}
	
	@PostMapping("/coding-question/{codingQuestionId}/testcase")
	public ResponseEntity<?> addTestCase(
	        @PathVariable Integer codingQuestionId,
	        @RequestBody CreateTestCaseRequest request,
	        @RequestHeader("X-User-Id") Integer recruiterId) {

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(assessmentService.addTestCase(
	                    codingQuestionId,
	                    recruiterId,
	                    request));
	}
	
	
}
