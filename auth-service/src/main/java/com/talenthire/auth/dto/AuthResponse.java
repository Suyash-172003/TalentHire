package com.talenthire.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	
	private Integer userId;
    
    private String name;
	
	private String email;
	
	private String userRole;
	
	private String message;
	
	private String token;

}
