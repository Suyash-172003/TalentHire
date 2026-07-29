package com.talenthire.assessment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResultResponse {

    private Integer attemptId;

    private Integer candidateId;

    private Integer obtainedMarks;

    private Integer totalMarks;

    private Double percentage;

    private String result;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String status;
}