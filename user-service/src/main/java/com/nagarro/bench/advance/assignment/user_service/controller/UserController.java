package com.nagarro.bench.advance.assignment.user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nagarro.bench.advance.assignment.user_service.dto.UserRequestDTO;
import com.nagarro.bench.advance.assignment.user_service.dto.UserResponseDTO;
import com.nagarro.bench.advance.assignment.user_service.service.UserService;

@Controller
@RequestMapping("api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userDetails){
		return new ResponseEntity<UserResponseDTO>(
				userService.createUser(userDetails),
				HttpStatus.CREATED);		
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
		return new ResponseEntity<List<UserResponseDTO>>(
				userService.getAllUsers(),
				HttpStatus.OK);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String userId){
		return new ResponseEntity<UserResponseDTO>(
				userService.getUserById(userId),
				HttpStatus.OK);		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUserById(@RequestBody UserRequestDTO user, @PathVariable("id") String userId){
		return new ResponseEntity<UserResponseDTO>(
				userService.updateUserById(user, userId),
				HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") String userId){
		userService.deleteUserById(userId);
		return new ResponseEntity<String>(
				"User is deleted successfully !!",
				HttpStatus.OK);		
	}

}
