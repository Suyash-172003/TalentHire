package com.talenthire.job.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.talenthire.job.entity.EmploymentType;
import com.talenthire.job.entity.WorkMode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateJobRequest {

	    private String title;
	    
	    private String companyName;

	    private String description;

	    private String location;

	    private BigDecimal salary;

	    private Integer experienceRequired;

	    private Integer vacancies;
	    
	 

	    private String employmentType;

	    private String workMode;


	    private List<String> skills;
}
