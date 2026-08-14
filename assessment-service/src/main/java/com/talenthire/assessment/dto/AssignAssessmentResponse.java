package com.talenthire.assessment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignAssessmentResponse {

    private Integer assignmentId;

    private Integer assessmentId;

    private String message;

}