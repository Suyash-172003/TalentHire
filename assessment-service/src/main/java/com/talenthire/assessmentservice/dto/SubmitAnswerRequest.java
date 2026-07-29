package com.talenthire.assessmentservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitAnswerRequest {

    private Long questionId;

    private String selectedAnswer;

}