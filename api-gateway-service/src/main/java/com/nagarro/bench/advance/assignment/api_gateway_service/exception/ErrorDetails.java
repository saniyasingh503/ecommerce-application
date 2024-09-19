package com.nagarro.bench.advance.assignment.api_gateway_service.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetails {
	
	private String errorCode;
	private String message;
	
}
