package com.nagarro.bench.advance.assignment.cart_service.service;

import java.util.List;

import com.nagarro.bench.advance.assignment.cart_service.dto.CartItemDTO;

public interface CartItemService {
	
	List<CartItemDTO> getCartLineItems(String cartId);
	CartItemDTO createCartLineItem(CartItemDTO cartItemDto, String cartId);
	CartItemDTO getCartLineItem(String cartId, String cartItemId);
	CartItemDTO updateCartLineItem(CartItemDTO cartItemDto, String cartId, String cartItemId);
	void deleteCartLineItem(String cartId, String cartItemId);

}
