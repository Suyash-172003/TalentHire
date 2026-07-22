package com.talenthire.assessment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.talenthire.assessment.dto.CodeExecutionRequest;
import com.talenthire.assessment.service.AssessmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AssessmentController {

	
	private final AssessmentService assessmentService;
	
	@PostMapping("/run")
	public ResponseEntity<?> runCode(@RequestBody CodeExecutionRequest request)
	{
		return ResponseEntity.status(HttpStatus.OK).body(assessmentService.execute(request));
	}
}
