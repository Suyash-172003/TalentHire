package com.talenthire.assessment.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.talenthire.assessment.dto.AssessmentRequest;
import com.talenthire.assessment.dto.AssessmentResponse;
import com.talenthire.assessment.entity.Assessment;
import com.talenthire.assessment.entity.AssessmentStatus;

@Component
public class AssessmentMapper {

    // Request DTO -> Entity
    public Assessment toEntity(AssessmentRequest request) {

        return Assessment.builder()
                .jobId(request.getJobId())
                .title(request.getTitle())
                .assessmentType(request.getAssessmentType())
                .duration(request.getDuration())
                .passMarks(request.getPassMarks())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .createdBy(request.getCreatedBy())
                .status(AssessmentStatus.DRAFT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // Entity -> Response DTO
    public AssessmentResponse toResponse(Assessment assessment) {

        return AssessmentResponse.builder()
                .id(assessment.getAssessmentId())
                .jobId(assessment.getJobId())
                .title(assessment.getTitle())
                .assessmentType(assessment.getAssessmentType())
                .duration(assessment.getDuration())
                .passMarks(assessment.getPassMarks())
                .startTime(assessment.getStartTime())
                .endTime(assessment.getEndTime())
                .status(assessment.getStatus())
                .createdBy(assessment.getCreatedBy())
                .createdAt(assessment.getCreatedAt())
                .build();
    }

}