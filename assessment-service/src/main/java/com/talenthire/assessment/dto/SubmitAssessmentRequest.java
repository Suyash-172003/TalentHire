package com.talenthire.assessment.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitAssessmentRequest {

    private Integer assessmentId;

    private Integer candidateId;

    private List<CodeExecutionRequest> answers;

}