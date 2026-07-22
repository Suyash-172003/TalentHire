package com.talenthire.assessment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RunResult {

    private String output;

    private String runtimeError;

    private Long executionTime;

}