package com.talenthire.application.dto;

import java.time.LocalDateTime;

import com.talenthire.application.entity.ScreeningStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobResponse {

	 private Integer applicationId;

	    private String status;

	    private String message;
	    
	    private ScreeningStatus screeningStatus;

}
