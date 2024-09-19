package com.nagarro.bench.advance.assignment.cart_service.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.bench.advance.assignment.cart_service.dto.CartDTO;
import com.nagarro.bench.advance.assignment.cart_service.dto.CartItemDTO;
import com.nagarro.bench.advance.assignment.cart_service.dto.UserDTO;
import com.nagarro.bench.advance.assignment.cart_service.exception.ResourceNotFoundException;
import com.nagarro.bench.advance.assignment.cart_service.model.Cart;
import com.nagarro.bench.advance.assignment.cart_service.model.CartItem;
import com.nagarro.bench.advance.assignment.cart_service.repository.CartItemRepository;
import com.nagarro.bench.advance.assignment.cart_service.repository.CartRepository;
import com.nagarro.bench.advance.assignment.cart_service.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String USER_SERVICE_URL = "http://localhost:9006/api/v1/users";

	@Override
	public CartDTO createCart(CartDTO cartDetails) {
		// fetch user details from user service
		UserDTO userDetails = getUserDetailsById(cartDetails.getUserId());
		
		if(userDetails == null) 
			throw new ResourceNotFoundException("User not found by id: " + cartDetails.getUserId());
		
		Cart cart = convertCartDTOToCart(cartDetails);
		
		if (cartDetails.getCartItems() == null) {
		    cart.setCartItems(new ArrayList<>());
		}
		cart.setTotal(0.00);
		Cart savedCart = cartRepository.save(cart);
		return convertCartEntityToCartDTO(savedCart);
	}

	@Override
	public CartDTO getCart(String cartId) {
		Optional<Cart> cartOptional = cartRepository.findById(cartId);
		
		if(cartOptional.isEmpty())
			throw new ResourceNotFoundException("Cart not found by id: " + cartId);
		
		return convertCartEntityToCartDTO(cartOptional.get());
	}
	
	@Override
	public void deleteCart(String cartId) {
		// delete cart Items
		Optional<Cart> cart = cartRepository.findById(cartId);
		
		if(cart.isEmpty())
			throw new ResourceNotFoundException("Cart not found by id: " + cartId);
		
		if(cart.get().getCartItems().size() > 0) {
			for(CartItem item: cart.get().getCartItems()) {
				cartItemRepository.deleteById(item.getId());
			}
		}
		
		cartRepository.deleteById(cartId);
		
	}
	
	public UserDTO getUserDetailsById(String userId) {
	        String url = String.format("%s/%s", USER_SERVICE_URL, userId);
	        System.out.println(url);
	        try {
				ResponseEntity<UserDTO> response = restTemplate.exchange(
						url,
						HttpMethod.GET,
						null,
						UserDTO.class
				);
				System.out.println(response.getBody());
				if(response.getStatusCode() == HttpStatus.OK)
					return response.getBody();
				else 
					return null;
				
			} catch (Exception e) {
		        return null;
			}
	    
	}
	
	public CartDTO convertCartEntityToCartDTO(Cart cart) {
		return modelMapper.map(cart, CartDTO.class);
	}
	
	public Cart convertCartDTOToCart(CartDTO cartDto) {
		return modelMapper.map(cartDto, Cart.class);
	}
	
	public CartItemDTO convertCartItemEntityToCartItemDTO(CartItem cartItem) {
		return modelMapper.map(cartItem, CartItemDTO.class);
	}
	
	public CartItem convertCartItemDTOToCartItem(CartItemDTO cartItemDto) {
		return modelMapper.map(cartItemDto, CartItem.class);
	}

	
	
}
