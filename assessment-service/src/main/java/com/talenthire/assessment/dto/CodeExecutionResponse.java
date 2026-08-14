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
public class CodeExecutionResponse {

	private ExecutionStatus status;

    private Integer passedTestCases;

    private Integer totalTestCases;

    private List<TestCaseResultDto> results;

    private String compileError;

    private String runtimeError;

    private Long executionTime;
    
    private Integer totalMarks;

    private Integer obtainedMarks;

}
