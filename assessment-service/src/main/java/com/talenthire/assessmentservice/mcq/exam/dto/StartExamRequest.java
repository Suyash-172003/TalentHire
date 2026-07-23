package com.talenthire.assessmentservice.mcq.exam.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartExamRequest {

    private Long assessmentId;

    private Long candidateId;

}