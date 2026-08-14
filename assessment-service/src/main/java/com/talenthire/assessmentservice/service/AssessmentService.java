package com.talenthire.assessmentservice.service;

import java.util.List;

import com.talenthire.assessmentservice.dto.AssessmentRequest;
import com.talenthire.assessmentservice.dto.AssessmentResponse;

public interface AssessmentService {

    AssessmentResponse createAssessment(AssessmentRequest request);

    AssessmentResponse getAssessmentById(Long id);

    List<AssessmentResponse> getAllAssessments();

    AssessmentResponse updateAssessment(Long id, AssessmentRequest request);

    void deleteAssessment(Long id);

}