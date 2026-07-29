package com.talenthire.application.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {
	
	  private Integer jobId;

	    private String title;

	    private String description;

	    private String location;

	    private BigDecimal salary;

	    private Integer experience;

	    private String status;
	    
	    private String employmentType;

	    private String workMode;


}