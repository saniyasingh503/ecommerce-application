package com.nagarro.bench.advance.assignment.user_service.service;

import com.nagarro.bench.advance.assignment.user_service.dto.AuthenticationRequestDTO;
import com.nagarro.bench.advance.assignment.user_service.dto.AuthenticationResponseDTO;

public interface AuthService {
	
	AuthenticationResponseDTO generateToken(AuthenticationRequestDTO authRequest);

}
