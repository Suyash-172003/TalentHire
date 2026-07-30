package com.talenthire.application.dto;

import com.talenthire.application.entity.ScreeningStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScreenApplicationResponse {
	 private Double matchPercentage;
	 private Integer resumeScore;
	 private ScreeningStatus screeningStatus;

}
