package com.talenthire.assessment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTestCaseRequest {

    private String input;

    private String expectedOutput;

    private Boolean isSample;

}