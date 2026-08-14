package com.talenthire.assessment.dto;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MCQQuestionRequest {


    private Integer assessmentId;


    private String questionText;


    private String optionA;


    private String optionB;


    private String optionC;


    private String optionD;


    private String correctAnswer;


    private String difficulty;


    private Integer marks;


    private Integer questionOrder;

}