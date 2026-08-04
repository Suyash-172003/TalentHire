package com.talenthire.assessment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignAssessmentRequest {

    private Integer applicationId;

    private Integer candidateId;

    private Integer jobId;

}