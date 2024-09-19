package com.nagarro.bench.advance.assignment.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nagarro.bench.advance.assignment.user_service.dto.AuthenticationRequestDTO;
import com.nagarro.bench.advance.assignment.user_service.dto.AuthenticationResponseDTO;
import com.nagarro.bench.advance.assignment.user_service.service.AuthService;



@Controller
@RequestMapping("api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@PostMapping("/token")
	public ResponseEntity<AuthenticationResponseDTO> generateToken(@RequestBody AuthenticationRequestDTO authRequest){
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (!authenticate.isAuthenticated()) 
            throw new BadCredentialsException("Invalid user credentials") ;
            
        return new ResponseEntity<AuthenticationResponseDTO>(
        		authService.generateToken(authRequest),
        		HttpStatus.OK);
        
	}

}
