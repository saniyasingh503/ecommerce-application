package com.nagarro.bench.advance.assignment.order_service.dto;

public class OrderRequestDTO {
	
	private String cartId;

	public OrderRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderRequestDTO(String cartId) {
		super();
		this.cartId = cartId;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

}
