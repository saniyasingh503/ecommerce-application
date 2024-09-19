package com.nagarro.bench.advance.assignment.price_service.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetails {
	
	private String errorCode;
	private String message;
	
}
