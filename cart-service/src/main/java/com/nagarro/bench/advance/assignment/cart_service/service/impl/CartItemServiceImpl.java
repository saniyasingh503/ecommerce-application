package com.nagarro.bench.advance.assignment.cart_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.bench.advance.assignment.cart_service.dto.CartItemDTO;
import com.nagarro.bench.advance.assignment.cart_service.dto.ProductDTO;
import com.nagarro.bench.advance.assignment.cart_service.exception.ResourceNotFoundException;
import com.nagarro.bench.advance.assignment.cart_service.model.Cart;
import com.nagarro.bench.advance.assignment.cart_service.model.CartItem;
import com.nagarro.bench.advance.assignment.cart_service.repository.CartItemRepository;
import com.nagarro.bench.advance.assignment.cart_service.repository.CartRepository;
import com.nagarro.bench.advance.assignment.cart_service.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String PRODUCT_SERVICE_URL = "http://localhost:9001/api/v1/products";

	@Override
	public List<CartItemDTO> getCartLineItems(String cartId) {
		 Cart cart = cartRepository.findById(cartId)
	                .orElseThrow(() -> new ResourceNotFoundException("Cart not found by id: " + cartId));

	     return cart.getCartItems().stream()
	                .map(this::convertCartItemEntityToCartItemDTO)
	                .collect(Collectors.toList());
	}

	@Override
	public CartItemDTO createCartLineItem(CartItemDTO cartItemDto, String cartId) {
		 Cart cart = cartRepository.findById(cartId)
	                .orElseThrow(() -> new ResourceNotFoundException("Cart not found by id: " + cartId));

		// Fetch Product Details from Product Service
		ProductDTO product = getProductById(cartItemDto.getProductId());
		
	    if (product == null) {
	            throw new ResourceNotFoundException("Product not found by id: " + cartItemDto.getProductId());
	    }

	    // Validate and set Product Details in CartItem
	    CartItem cartItem = convertCartItemDTOToCartItem(cartItemDto);
	    cartItem.setProductName(product.getName());
        cartItem.setPrice(product.getPrice().getAmount());
	    cartItem.setCart(cart);
	    
	 // Calculate and set itemTotal
        cartItem.calculateItemTotal();

	    CartItem savedCartItem = cartItemRepository.save(cartItem);
	    
	 // Update Cart Total
        cart.updateCartTotal();
        cartRepository.save(cart);
	    return convertCartItemEntityToCartItemDTO(savedCartItem);
	}

	@Override
	public CartItemDTO getCartLineItem(String cartId, String cartItemId) {
		Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found by id: " + cartId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found by id: " + cartItemId));

        if (!cart.getCartItems().contains(cartItem)) {
            throw new RuntimeException("CartItem does not belong to this Cart");
        }

        return convertCartItemEntityToCartItemDTO(cartItem);
	}

	@Override
	public CartItemDTO updateCartLineItem(CartItemDTO cartItemDto, String cartId, String cartItemId) {
		Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found by id: " + cartId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found by id: " + cartItemId));

        if (!cart.getCartItems().contains(cartItem)) {
            throw new RuntimeException("CartItem does not belong to this Cart");
        }
        
        // Validate Product Details if needed
        ProductDTO product = getProductById(cartItemDto.getProductId());
		
        if (product == null) {
            throw new ResourceNotFoundException("Product not found by id: " + cartItemDto.getProductId());
        }

        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setProductName(product.getName());
        cartItem.setPrice(product.getPrice().getAmount());
        cartItem.setQuantity(cartItemDto.getQuantity());
        
     // Calculate and set itemTotal
        cartItem.calculateItemTotal();

        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        
     // Update Cart Total
        cart.updateCartTotal();
        cartRepository.save(cart);
        return convertCartItemEntityToCartItemDTO(updatedCartItem);
	}

	@Override
	public void deleteCartLineItem(String cartId, String cartItemId) {
		Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found by id: " + cartId));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found by id: " + cartItemId));

	    if (!cart.getCartItems().contains(cartItem)) {
	         throw new RuntimeException("CartItem does not belong to this Cart");
	    }

	    cart.removeCartItem(cartItem);
	    
	 // Update Cart Total
        cart.updateCartTotal();
        
	    cartItemRepository.deleteById(cartItemId);
	    cartRepository.save(cart);

	}
	
	public ProductDTO getProductById(Long productId) {
        String url = String.format("%s/%d", PRODUCT_SERVICE_URL, productId);
        try {
			ResponseEntity<ProductDTO> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					ProductDTO.class
			);
			
			if(response.getStatusCode() == HttpStatus.OK)
				return response.getBody();
		} catch (Exception e) {
			System.err.println("Error occurred: " + e.getMessage());
	        return null;
		}
        return null;
    }
	
	public CartItemDTO convertCartItemEntityToCartItemDTO(CartItem cartItem) {
		return modelMapper.map(cartItem, CartItemDTO.class);
	}
	
	public CartItem convertCartItemDTOToCartItem(CartItemDTO cartItemDto) {
		return modelMapper.map(cartItemDto, CartItem.class);
	}

}
