package com.talenthire.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talenthire.auth.dto.CandidateDetailsRequest;
import com.talenthire.auth.dto.LoginRequest;
import com.talenthire.auth.dto.RegisterRequest;
import com.talenthire.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private AuthService authService;
	
	public AuthController(AuthService authService)
	{
		this.authService=authService;
	}
	
	
	@PostMapping("/users/details")
	public ResponseEntity<?> getCandidateDetails(
	        @RequestBody CandidateDetailsRequest request) {

	    return ResponseEntity.ok(
	            authService.getCandidateDetails(request));
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request)
	{
		return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
	}
	
	
	

}
