package com.talenthire.assessment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultResponse {

	private Integer attemptId;

    private Integer totalQuestions;

    private Integer totalMarks;

    private Integer obtainedMarks;

    private Double percentage;

    private String result;

}