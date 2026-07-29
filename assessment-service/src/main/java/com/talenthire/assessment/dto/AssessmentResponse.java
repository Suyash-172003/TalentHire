package com.talenthire.assessment.dto;

import java.time.LocalDateTime;

import com.talenthire.assessment.entity.AssessmentStatus;
import com.talenthire.assessment.entity.AssessmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentResponse {

	private Integer id;

    private Integer jobId;

    private String title;

    private AssessmentType assessmentType;

    private Integer duration;

    private Integer passMarks;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private AssessmentStatus status;

    private Integer createdBy;

    private LocalDateTime createdAt;

}