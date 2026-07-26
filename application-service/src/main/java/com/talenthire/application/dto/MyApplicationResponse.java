package com.talenthire.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyApplicationResponse {
	
	 private Integer applicationId;

	    private Integer jobId;

	    private String jobTitle;

	  private String description;

	    private String location;

	    private String applicationStatus;

	   

}
