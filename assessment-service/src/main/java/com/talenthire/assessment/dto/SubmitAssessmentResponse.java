package com.talenthire.assessment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitAssessmentResponse {

    private Integer obtainedMarks;

    private Integer totalMarks;

    private String status;

}