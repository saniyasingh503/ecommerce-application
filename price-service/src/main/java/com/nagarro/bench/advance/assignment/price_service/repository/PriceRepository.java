package com.nagarro.bench.advance.assignment.price_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.bench.advance.assignment.price_service.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
	
	Optional<Price> findByProductId(Long productId);

}
