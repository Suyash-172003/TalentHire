package com.talenthire.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseResultDto {
	
	private Integer testCaseNumber;

    private String input;

    private String expectedOutput;

    private String actualOutput;

    private Boolean passed;

}
