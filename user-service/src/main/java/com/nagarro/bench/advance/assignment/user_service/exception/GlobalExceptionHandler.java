package com.nagarro.bench.advance.assignment.user_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(UserNotFoundException exception){
		ErrorDetails errorDetails = new ErrorDetails("Not Found", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorDetails> handleResourceAlreadyExistException(UserAlreadyExistException exception){
		ErrorDetails errorDetails = new ErrorDetails("Resource Conflict", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception){
		ErrorDetails errorDetails = new ErrorDetails("Internal Server Error", exception.getMessage());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
