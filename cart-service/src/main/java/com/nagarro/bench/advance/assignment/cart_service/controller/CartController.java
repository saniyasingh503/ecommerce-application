package com.nagarro.bench.advance.assignment.cart_service.controller;

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

import com.nagarro.bench.advance.assignment.cart_service.dto.CartDTO;
import com.nagarro.bench.advance.assignment.cart_service.dto.CartItemDTO;
import com.nagarro.bench.advance.assignment.cart_service.service.CartItemService;
import com.nagarro.bench.advance.assignment.cart_service.service.CartService;

@Controller
@RequestMapping("api/v1/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@PostMapping
	public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDto) {
		return new ResponseEntity<CartDTO>(cartService.createCart(cartDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<CartDTO> getCart(@PathVariable("cartId") String cartId) {
		return new ResponseEntity<CartDTO>(cartService.getCart(cartId), HttpStatus.OK);
	}
	
	@DeleteMapping("/{cartId}")
	public ResponseEntity<Object> deleteCart(@PathVariable("cartId") String cartId) {
		cartService.deleteCart(cartId);
		return new ResponseEntity<Object>("Cart is deleted successfully", HttpStatus.NO_CONTENT);
	}
	
	 @GetMapping("/{cartId}/items")
	 public ResponseEntity<List<CartItemDTO>> getCartLineItems(@PathVariable("cartId") String cartId) {
	     List<CartItemDTO> cartItems = cartItemService.getCartLineItems(cartId);
	     return new ResponseEntity<List<CartItemDTO>>(cartItems, HttpStatus.OK);
	 }

	 @PostMapping("/{cartId}/items")
	 public ResponseEntity<CartItemDTO> createCartLineItem(@RequestBody CartItemDTO cartItemDto, @PathVariable("cartId") String cartId) {
		 CartItemDTO createdCartItem = cartItemService.createCartLineItem(cartItemDto, cartId);
		 return new ResponseEntity<CartItemDTO>(createdCartItem, HttpStatus.CREATED);
	 }

	 @GetMapping("/{cartId}/items/{cartItemId}")
	 public ResponseEntity<CartItemDTO> getCartLineItem(@PathVariable("cartId") String cartId, @PathVariable("cartItemId") String cartItemId) {
	     CartItemDTO cartItemDto = cartItemService.getCartLineItem(cartId, cartItemId);
	     return new ResponseEntity<CartItemDTO>(cartItemDto, HttpStatus.OK);
	 }

	 @PutMapping("/{cartId}/items/{cartItemId}")
	 public ResponseEntity<CartItemDTO> updateCartLineItem(@RequestBody CartItemDTO cartItemDto, @PathVariable("cartId") String cartId, @PathVariable("cartItemId") String cartItemId) {
	     CartItemDTO updatedCartItem = cartItemService.updateCartLineItem(cartItemDto, cartId, cartItemId);
	     return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
	 }

	 @DeleteMapping("/{cartId}/items/{cartItemId}")
	 public ResponseEntity<Void> deleteCartLineItem(@PathVariable("cartId") String cartId, @PathVariable("cartItemId") String cartItemId) {
	     cartItemService.deleteCartLineItem(cartId, cartItemId);
	     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }

}
