package com.nagarro.bench.advance.assignment.cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.bench.advance.assignment.cart_service.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

}
