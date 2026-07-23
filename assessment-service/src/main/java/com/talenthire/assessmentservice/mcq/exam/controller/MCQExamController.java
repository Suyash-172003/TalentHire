package com.talenthire.assessmentservice.mcq.exam.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talenthire.assessmentservice.mcq.exam.dto.AssessmentResultResponse;
import com.talenthire.assessmentservice.mcq.exam.dto.ExamResultResponse;
import com.talenthire.assessmentservice.mcq.exam.dto.StartExamRequest;
import com.talenthire.assessmentservice.mcq.exam.dto.StartExamResponse;
import com.talenthire.assessmentservice.mcq.exam.dto.SubmitExamRequest;
import com.talenthire.assessmentservice.mcq.exam.service.MCQExamService;

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
            @PathVariable Long attemptId,
            @RequestBody SubmitExamRequest request) {

        return ResponseEntity.ok(
                mcqExamService.submitExam(attemptId, request));
    }

    
    @GetMapping("/{attemptId}/result")
    public ResponseEntity<ExamResultResponse> getResult(
            @PathVariable Long attemptId) {

        return ResponseEntity.ok(
                mcqExamService.getResult(attemptId));
    }

 
    @GetMapping("/assessment/{assessmentId}/results")
    public ResponseEntity<List<AssessmentResultResponse>> getAssessmentResults(
            @PathVariable Long assessmentId) {

        return ResponseEntity.ok(
                mcqExamService.getAssessmentResults(assessmentId));
    }
}