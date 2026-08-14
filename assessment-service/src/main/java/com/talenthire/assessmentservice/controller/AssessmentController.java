package com.talenthire.assessmentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talenthire.assessmentservice.dto.AssessmentRequest;
import com.talenthire.assessmentservice.dto.AssessmentResponse;
import com.talenthire.assessmentservice.service.AssessmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;

    @PostMapping
    public ResponseEntity<AssessmentResponse> createAssessment(
            @RequestBody AssessmentRequest request) {

        AssessmentResponse response = assessmentService.createAssessment(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponse> getAssessmentById(
            @PathVariable Long id) {

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
            @PathVariable Long id,
            @RequestBody AssessmentRequest request) {

        return ResponseEntity.ok(
                assessmentService.updateAssessment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssessment(
            @PathVariable Long id) {

        assessmentService.deleteAssessment(id);

        return ResponseEntity.ok("Assessment deleted successfully.");
    }

}