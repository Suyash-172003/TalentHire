package com.talenthire.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAssessmentRequest {
	
    private Integer jobId;

    private String title;

    private String description;

    private Integer duration;



}
