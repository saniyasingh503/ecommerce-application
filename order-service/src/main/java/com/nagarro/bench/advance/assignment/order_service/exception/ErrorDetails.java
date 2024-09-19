package com.nagarro.bench.advance.assignment.order_service.exception;

public class ErrorDetails {
	
	private String errorCode;
	private String message;
	
	public ErrorDetails() {
		super();
	}

	public ErrorDetails(String errorCode, String message) {
		super();
		this.setErrorCode(errorCode);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
