package com.talenthire.assessment.dto;

import java.time.LocalDateTime;

import com.talenthire.assessment.entity.AssignmentStatus;
import com.talenthire.assessment.entity.AssessmentType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CandidateAssessmentResponse {

    private Integer assignmentId;

    private Integer assessmentId;

    private String title;

    private AssessmentType assessmentType;

    // MCQ
    private Integer duration;
    private Integer passMarks;

    // Coding
    private Integer codingDuration;
    private Integer codingPassMarks;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private AssignmentStatus assignmentStatus;

}