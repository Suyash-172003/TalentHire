package com.talenthire.assessment.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.assessment.dto.AssessmentRequest;
import com.talenthire.assessment.dto.AssessmentResponse;
import com.talenthire.assessment.dto.CodeExecutionRequest;
import com.talenthire.assessment.dto.CodingQuestionResponse;
import com.talenthire.assessment.dto.CreateAssessmentRequest;
import com.talenthire.assessment.dto.CreateCodingQuestionRequest;
import com.talenthire.assessment.dto.CreateTestCaseRequest;
import com.talenthire.assessment.excelService.CodingQuestionImportService;
import com.talenthire.assessment.service.AssessmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assessment")

@CrossOrigin(origins = "http://localhost:5173/")

public class AssessmentController {

	
	private final AssessmentService assessmentService;
	private final CodingQuestionImportService codingQuestionImportService;
	
	
	@PostMapping("/{assessmentId}/coding/upload")
	public ResponseEntity<?> uploadCodingQuestions(
	        @PathVariable Integer assessmentId,
	        @RequestParam("file") MultipartFile file) throws IOException {

	    codingQuestionImportService.importQuestions(file,assessmentId);

	    return ResponseEntity.ok("Questions imported successfully");
	}
	
	
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
	
	@GetMapping("/{assessmentId}/coding-questions")
	public ResponseEntity<List<CodingQuestionResponse>> getCodingQuestions(
	        @PathVariable Integer assessmentId) {

	    return ResponseEntity.ok(
	            assessmentService.getCodingQuestions(assessmentId));
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
	
	
	
	  @PostMapping
	    public ResponseEntity<AssessmentResponse> createAssessmentCrud(
	            @RequestBody AssessmentRequest request) {

	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(assessmentService.createAssessment(request));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<AssessmentResponse> getAssessmentById(
	            @PathVariable Integer id) {

	        return ResponseEntity.ok(
	                assessmentService.getAssessmentById(id));
	    }

	    @GetMapping
	    public ResponseEntity<List<AssessmentResponse>> getAllAssessments() {

	        return ResponseEntity.ok(
	                assessmentService.getAllAssessments());
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<AssessmentResponse> updateAssessment(
	            @PathVariable Integer id,
	            @RequestBody AssessmentRequest request) {

	        return ResponseEntity.ok(
	                assessmentService.updateAssessment(id, request));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteAssessment(
	            @PathVariable Integer id) {

	        assessmentService.deleteAssessment(id);
	        return ResponseEntity.ok("Assessment deleted successfully.");
	    }
	    
	    @GetMapping("/job/{jobId}")
	    public ResponseEntity<AssessmentResponse> getAssessmentByJobId(
	            @PathVariable Integer jobId) {

	        return ResponseEntity.ok(
	                assessmentService.getAssessmentByJobId(jobId));
	    }
	    
	    
	    @GetMapping("/coding/template")
	    public ResponseEntity<Resource> downloadCodingTemplate() throws IOException {

	        ClassPathResource resource =
	                new ClassPathResource("templates/Coding_Template.xlsx");

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=Coding_Template.xlsx")
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(resource);
	    }
	
	
	
	
	 
	
}
