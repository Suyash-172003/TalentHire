package com.talenthire.auth.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
	
	private LocalDateTime timeStamp;
	private String message;
	private String status;
	
	public ErrorResponse(String message, String status) {
		super();
		this.message = message;
		this.status = status;
		this.timeStamp=LocalDateTime.now();
	}

}
