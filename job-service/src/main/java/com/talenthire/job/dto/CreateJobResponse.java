package com.talenthire.job.dto;

import java.time.LocalDateTime;

import com.talenthire.job.entity.JobStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

     @Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class CreateJobResponse {

	    private Integer jobId;

	    private String status;

	    private LocalDateTime createdAt;

	    private String message;
	}


