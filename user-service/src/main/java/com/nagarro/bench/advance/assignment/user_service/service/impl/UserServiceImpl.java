package com.nagarro.bench.advance.assignment.user_service.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nagarro.bench.advance.assignment.user_service.dto.UserRequestDTO;
import com.nagarro.bench.advance.assignment.user_service.dto.UserResponseDTO;
import com.nagarro.bench.advance.assignment.user_service.exception.UserAlreadyExistException;
import com.nagarro.bench.advance.assignment.user_service.exception.UserNotFoundException;
import com.nagarro.bench.advance.assignment.user_service.model.User;
import com.nagarro.bench.advance.assignment.user_service.repository.UserRepository;
import com.nagarro.bench.advance.assignment.user_service.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponseDTO createUser(UserRequestDTO user) {
		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
		
		if(userOptional.isPresent())
			throw new UserAlreadyExistException("User already exist by email " + user.getEmail());
		
		User userDetails = convertUserRequestDTOToEntity(user);
		String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
		userDetails.setPassword(encodedPassword);
		User savedUser = userRepository.save(userDetails);
		return convertUserEntityToUserResponseDTO(savedUser);
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		return userRepository.findAll().stream()
					.map(this::convertUserEntityToUserResponseDTO)
					.collect(Collectors.toList());
		
	}

	@Override
	public UserResponseDTO getUserByEmail(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		
		if(userOptional.isEmpty())
			throw new UserNotFoundException("User not found by email " + email);
		
		return convertUserEntityToUserResponseDTO(userOptional.get());
	}

	@Override
	public UserResponseDTO getUserById(String userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		
		if(userOptional.isEmpty())
			throw new UserNotFoundException("User not found by id " + userId);
		
		return convertUserEntityToUserResponseDTO(userOptional.get());
	}

	@Override
	public UserResponseDTO updateUserById(UserRequestDTO user, String userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		
		if(userOptional.isEmpty())
			throw new UserNotFoundException("User not found by id " + userId);
		
		User existingUser = userOptional.get();
		existingUser.setEmail(user.getEmail());
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		existingUser.setPassword(encodedPassword);
		
		User updatedUser = userRepository.save(existingUser);
		return convertUserEntityToUserResponseDTO(updatedUser);
	}

	@Override
	public void deleteUserById(String userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		
		if(userOptional.isEmpty())
			throw new UserNotFoundException("User not found by id " + userId);
		
		userRepository.deleteById(userId);
		
	}
	
	public User convertUserRequestDTOToEntity(UserRequestDTO userRequestDto) {
		return modelMapper.map(userRequestDto, User.class);
	}
	
	public UserResponseDTO convertUserEntityToUserResponseDTO(User user) {
		return modelMapper.map(user, UserResponseDTO.class);
	}

}
