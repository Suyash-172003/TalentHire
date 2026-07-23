package com.talenthire.assessmentservice.mcq.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.assessmentservice.mcq.dto.MCQQuestionRequest;
import com.talenthire.assessmentservice.mcq.dto.MCQQuestionResponse;
import com.talenthire.assessmentservice.mcq.excel.ExcelService;
import com.talenthire.assessmentservice.mcq.service.MCQService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mcq")
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
            @PathVariable Long id) {

        return ResponseEntity.ok(mcqService.getQuestionById(id));
    }

    
    @GetMapping("/assessments/{assessmentId}/questions")
    public ResponseEntity<List<MCQQuestionResponse>> getQuestionsByAssessment(
            @PathVariable Long assessmentId) {

        return ResponseEntity.ok(
                mcqService.getQuestionsByAssessment(assessmentId));
    }

    
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Long id) {

        mcqService.deleteQuestion(id);

        return ResponseEntity.ok("Question deleted successfully.");
    }

    @PostMapping("/upload/{assessmentId}")
    public ResponseEntity<String> uploadQuestions(
            @PathVariable Long assessmentId,
            @RequestParam("file") MultipartFile file) {

        excelService.uploadQuestions(assessmentId, file);

        return ResponseEntity.ok("MCQ Questions uploaded successfully.");
    }

}