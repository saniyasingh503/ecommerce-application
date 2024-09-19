package com.nagarro.bench.advance.assignment.order_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nagarro.bench.advance.assignment.order_service.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	
	List<Order> findByUserId(String userId);

}
