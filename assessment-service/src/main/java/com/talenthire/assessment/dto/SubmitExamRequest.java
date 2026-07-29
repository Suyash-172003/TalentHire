package com.talenthire.assessment.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitExamRequest {

    private List<SubmitAnswerRequest> answers;

}