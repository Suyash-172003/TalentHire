package com.talenthire.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

	@NotBlank(message="Name cannot be blank")
    private String name;
	
	@Email
	private String email;
	
	private String password;
	
	private String userRole;
	
}
