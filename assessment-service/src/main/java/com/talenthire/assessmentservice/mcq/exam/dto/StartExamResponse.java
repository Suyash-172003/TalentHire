package com.talenthire.assessmentservice.mcq.exam.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartExamResponse {

    private Long attemptId;

    private Long assessmentId;

    private Long candidateId;

    private LocalDateTime startTime;

    private Integer duration;

    private Integer totalQuestions;

    private Integer totalMarks;

    private String message;

}