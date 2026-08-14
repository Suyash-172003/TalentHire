package com.talenthire.application.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobApplicationResponse {

	   private Integer applicationId;

	    private Integer candidateId;

	    private String candidateName;

	    private String candidateEmail;

	    private Integer resumeId;

	    private String applicationStatus;
	    
	    private String screeningStatus;

}