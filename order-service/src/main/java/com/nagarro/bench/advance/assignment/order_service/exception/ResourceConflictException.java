package com.nagarro.bench.advance.assignment.order_service.exception;

public class ResourceConflictException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ResourceConflictException(String message) {
		super(message);
	}

}
