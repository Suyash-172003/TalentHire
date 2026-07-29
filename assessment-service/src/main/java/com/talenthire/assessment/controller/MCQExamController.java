package com.talenthire.assessment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talenthire.assessment.dto.AssessmentResultResponse;
import com.talenthire.assessment.dto.ExamResultResponse;
import com.talenthire.assessment.dto.StartExamRequest;
import com.talenthire.assessment.dto.StartExamResponse;
import com.talenthire.assessment.dto.SubmitExamRequest;
import com.talenthire.assessment.service.MCQExamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mcq/exam")
@RequiredArgsConstructor
public class MCQExamController {

    private final MCQExamService mcqExamService;

    
    @PostMapping("/start")
    public ResponseEntity<StartExamResponse> startExam(
            @RequestBody StartExamRequest request) {

        StartExamResponse response = mcqExamService.startExam(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    
    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<ExamResultResponse> submitExam(
            @PathVariable Integer attemptId,
            @RequestBody SubmitExamRequest request) {

        return ResponseEntity.ok(
                mcqExamService.submitExam(attemptId, request));
    }

    
    @GetMapping("/{attemptId}/result")
    public ResponseEntity<ExamResultResponse> getResult(
            @PathVariable Integer attemptId) {

        return ResponseEntity.ok(
                mcqExamService.getResult(attemptId));
    }

 
    @GetMapping("/assessment/{assessmentId}/results")
    public ResponseEntity<List<AssessmentResultResponse>> getAssessmentResults(
            @PathVariable Integer assessmentId) {

        return ResponseEntity.ok(
                mcqExamService.getAssessmentResults(assessmentId));
    }
}