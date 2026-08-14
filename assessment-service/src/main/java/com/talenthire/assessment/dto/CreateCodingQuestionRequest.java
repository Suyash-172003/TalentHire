package com.talenthire.assessment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCodingQuestionRequest {
	
	private String title;

    private String problemStatement;

    private Integer marks;

    private Integer questionOrder;

}
