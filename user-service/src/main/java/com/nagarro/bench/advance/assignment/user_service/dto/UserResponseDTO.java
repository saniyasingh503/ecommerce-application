package com.nagarro.bench.advance.assignment.user_service.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
	
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String roles;

}
