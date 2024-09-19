package com.nagarro.bench.advance.assignment.user_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.bench.advance.assignment.user_service.dto.AuthenticationRequestDTO;
import com.nagarro.bench.advance.assignment.user_service.dto.AuthenticationResponseDTO;
import com.nagarro.bench.advance.assignment.user_service.service.AuthService;
import com.nagarro.bench.advance.assignment.user_service.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public AuthenticationResponseDTO generateToken(AuthenticationRequestDTO authRequest) {	
		String jwtToken = jwtUtil.generateToken(authRequest.getUsername());
		return new AuthenticationResponseDTO(jwtToken);
	}

}
