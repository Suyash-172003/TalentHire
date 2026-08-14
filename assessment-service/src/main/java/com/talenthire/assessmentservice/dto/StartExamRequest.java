package com.talenthire.assessmentservice.dto;

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