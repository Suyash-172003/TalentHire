package com.talenthire.assessment.dto;

import java.time.LocalDateTime;

import com.talenthire.assessment.entity.AssessmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentRequest {

    private Integer jobId;

    private String title;

    private String description;

    private AssessmentType assessmentType;

    // MCQ
    private Integer duration;
    private Integer totalMarks;
    private Integer passMarks;

    // Coding
    private Integer codingDuration;
    private Integer codingTotalMarks;
    private Integer codingPassMarks;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer createdBy;
}