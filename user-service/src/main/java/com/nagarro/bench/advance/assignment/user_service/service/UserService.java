package com.nagarro.bench.advance.assignment.user_service.service;

import java.util.List;

import com.nagarro.bench.advance.assignment.user_service.dto.UserRequestDTO;
import com.nagarro.bench.advance.assignment.user_service.dto.UserResponseDTO;

public interface UserService {
	
	UserResponseDTO createUser(UserRequestDTO user);
	List<UserResponseDTO> getAllUsers();
	UserResponseDTO getUserByEmail(String email);
	UserResponseDTO getUserById(String userId);
	UserResponseDTO updateUserById(UserRequestDTO user, String userId);
	void deleteUserById(String userId);

}
