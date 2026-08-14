package com.talenthire.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtsScoreResponse {

    private Double matchPercentage;
    private Integer resumeScore;
}
