package com.talenthire.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalAssessmentResultResponse {

    private Integer candidateId;

    private String mcqResult;

    private String codingResult;

    private String finalResult;

    private Integer mcqMarks;

    private Integer codingMarks;
}