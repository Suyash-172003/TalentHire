package com.talenthire.assessment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitAnswerRequest {

    private Integer questionId;

    private String selectedAnswer;

}