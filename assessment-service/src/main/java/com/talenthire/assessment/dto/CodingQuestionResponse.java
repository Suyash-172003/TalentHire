package com.talenthire.assessment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodingQuestionResponse {
	
	private Integer codingQuestionId;

    private String title;

    private String problemStatement;

    private Integer marks;

    private Integer questionOrder;

   private List<SampleTestCaseResponse> sampleTestCases;
	
	

}
