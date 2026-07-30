package com.talenthire.job.dto;

import java.math.BigDecimal;
import java.util.List;

import com.talenthire.job.entity.EmploymentType;
import com.talenthire.job.entity.JobStatus;
import com.talenthire.job.entity.WorkMode;

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

	    private String companyName;
	    
	    private String description;
	    
	    private Integer vacancies;

	    private String location;

	    private BigDecimal salary;

	    private Integer experience;

	    private JobStatus status;
	    
	    private EmploymentType employmentType;

	    private WorkMode workMode;
	    
	    private Integer shortlistScore;


	    private List<String> skills;

}
