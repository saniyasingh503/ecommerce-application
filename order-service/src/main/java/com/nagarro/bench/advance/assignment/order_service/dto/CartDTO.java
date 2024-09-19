package com.nagarro.bench.advance.assignment.order_service.dto;

import java.util.List;

public class CartDTO {
	
	private String id;
    private String userId;
    private List<CartItemDTO> cartItems;
    private Double total;
    
	public CartDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartDTO(String id, String userId, List<CartItemDTO> cartItems, Double total) {
		super();
		this.id = id;
		this.userId = userId;
		this.cartItems = cartItems;
		this.total = total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<CartItemDTO> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemDTO> cartItems) {
		this.cartItems = cartItems;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

}
