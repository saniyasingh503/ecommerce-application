package com.nagarro.bench.advance.assignment.user_service.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequestDTO {
	
	private String username;
	private String password;

}
