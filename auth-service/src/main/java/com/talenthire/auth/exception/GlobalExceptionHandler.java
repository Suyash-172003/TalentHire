package com.talenthire.auth.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.talenthire.auth.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Failed",ex.getMessage()));
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Failed",ex.getMessage()));
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {

	    return ResponseEntity
	            .status(HttpStatus.UNAUTHORIZED)
	            .body(new ErrorResponse(
	                    "Failed",
	                    "Invalid Email or Password"));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		System.out.println("in catch validation err ");
		List<FieldError> fieldErrList=e.getFieldErrors();
	Map<String,String> map=new HashMap<>();
	fieldErrList.forEach(fieldErr -> map.put(fieldErr.getField(), fieldErr.getDefaultMessage()));
		return map;
	}

}
