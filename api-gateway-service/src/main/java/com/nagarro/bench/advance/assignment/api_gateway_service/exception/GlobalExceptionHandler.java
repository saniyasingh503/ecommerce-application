package com.nagarro.bench.advance.assignment.api_gateway_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorDetails> handleUnauthorizedException(UnauthorizedException exception){
		ErrorDetails errorDetails = new ErrorDetails("Unauthorized", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorDetails> handleForbiddenException(ForbiddenException exception){
		ErrorDetails errorDetails = new ErrorDetails("Access Forbidden", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.FORBIDDEN);
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception){
		ErrorDetails errorDetails = new ErrorDetails("Internal Server Error", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
