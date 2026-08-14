package com.talenthire.assessmentservice.dto;

import java.time.LocalDateTime;

import com.talenthire.assessmentservice.enums.AssessmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentRequest {

    private Long companyId;

    private Long jobId;

    private String title;

    private AssessmentType assessmentType;

    private Integer duration;

    private Integer passMarks;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long createdBy;

}