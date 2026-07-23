package com.talenthire.assessmentservice.mcq.dto;

import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MCQQuestionResponse {


    private Long id;


    private Long assessmentId;


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