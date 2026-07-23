package com.talenthire.assessmentservice.mcq.exam.dto;

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