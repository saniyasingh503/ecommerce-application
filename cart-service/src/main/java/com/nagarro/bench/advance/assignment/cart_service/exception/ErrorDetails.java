package com.nagarro.bench.advance.assignment.cart_service.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetails {
	
	private String errorCode;
	private String message;

}
