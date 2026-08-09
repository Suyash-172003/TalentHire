package com.talenthire.assessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {


	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<String> handleResourceNotFound(
	            ResourceNotFoundException ex) {

	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(ex.getMessage());
	    }
	 
	 @ExceptionHandler(ExternalServiceException.class)
	 public ResponseEntity<String> handleExternalService(
	         ExternalServiceException ex) {

	     return ResponseEntity
	             .status(HttpStatus.SERVICE_UNAVAILABLE)
	             .body(ex.getMessage());
	 }

	    @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<String> handleAccessDenied(
	            AccessDeniedException ex) {

	        return ResponseEntity
	                .status(HttpStatus.FORBIDDEN)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(InvalidRequestException.class)
	    public ResponseEntity<String> handleInvalidRequest(
	            InvalidRequestException ex) {

	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(ex.getMessage());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGeneral(Exception ex) {

	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Something went wrong");
	    }

}
