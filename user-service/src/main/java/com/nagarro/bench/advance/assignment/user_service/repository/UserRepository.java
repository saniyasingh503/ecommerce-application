package com.nagarro.bench.advance.assignment.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.bench.advance.assignment.user_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	Optional<User> findByEmail(String email);

}
