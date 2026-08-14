package com.talenthire.assessment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartExamRequest {

    private Integer assessmentId;

    private Integer candidateId;

}