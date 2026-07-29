package com.talenthire.assessment.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartExamResponse {

    private Integer attemptId;

    private Integer  assessmentId;

    private Integer candidateId;

    private LocalDateTime startTime;

    private Integer duration;

    private Integer totalQuestions;

    private Integer totalMarks;

    private String message;

}