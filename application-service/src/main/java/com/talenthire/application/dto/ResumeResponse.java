package com.talenthire.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResumeResponse {
	 private Integer resumeId;

	    private String fileName;

	    private String fileUrl;
}
