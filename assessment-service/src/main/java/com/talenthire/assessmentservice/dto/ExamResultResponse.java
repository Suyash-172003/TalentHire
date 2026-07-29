package com.talenthire.assessmentservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultResponse {

    private Long attemptId;

    private Integer totalQuestions;

    private Integer totalMarks;

    private Integer obtainedMarks;

    private Double percentage;

    private String result;

}