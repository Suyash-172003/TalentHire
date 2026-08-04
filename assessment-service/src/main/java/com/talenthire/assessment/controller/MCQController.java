package com.talenthire.assessment.controller;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.assessment.dto.*;
import com.talenthire.assessment.dto.*;
import com.talenthire.assessment.service.ExcelService;
import com.talenthire.assessment.service.MCQService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/assessment/mcq")
@RequiredArgsConstructor
public class MCQController {

    private final MCQService mcqService;
    private final ExcelService excelService;

    @PostMapping("/questions")
    public ResponseEntity<MCQQuestionResponse> createQuestion(
            @RequestBody MCQQuestionRequest request) {

        MCQQuestionResponse response = mcqService.createQuestion(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

  
    @GetMapping("/questions/{id}")
    public ResponseEntity<MCQQuestionResponse> getQuestionById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(mcqService.getQuestionById(id));
    }

    
    @GetMapping("/assessments/{assessmentId}/questions")
    public ResponseEntity<List<MCQQuestionResponse>> getQuestionsByAssessment(
            @PathVariable Integer assessmentId) {

        return ResponseEntity.ok(
                mcqService.getQuestionsByAssessment(assessmentId));
    }

    
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Integer id) {

        mcqService.deleteQuestion(id);

        return ResponseEntity.ok("Question deleted successfully.");
    }

    @PostMapping("/upload/{assessmentId}")
    public ResponseEntity<String> uploadQuestions(
            @PathVariable Integer assessmentId,
            @RequestParam("file") MultipartFile file) {

        excelService.uploadQuestions(assessmentId, file);

        return ResponseEntity.ok("MCQ Questions uploaded successfully.");
    }
    
    @GetMapping("/template")
    public ResponseEntity<Resource> downloadTemplate() throws IOException {

        ClassPathResource resource =
                new ClassPathResource("templates/MCQ_Template.xlsx");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=MCQ_Template.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}