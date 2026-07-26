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
public class UploadResumeResponse {
	
	    private Integer resumeId;
	    private String fileName;
	    private String filePath;
	    private String message;

}
