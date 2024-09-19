package com.nagarro.bench.advance.assignment.cart_service.service;

import com.nagarro.bench.advance.assignment.cart_service.dto.CartDTO;

public interface CartService {
	
	CartDTO createCart(CartDTO cartDetails);
	CartDTO getCart(String cartId);
	void deleteCart(String cartId);

}
