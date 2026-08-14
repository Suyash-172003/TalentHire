package com.talenthire.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
public class VerifyJobResponse {
	private Integer jobId;

    private String status;

    private Integer recruiterId;
}
