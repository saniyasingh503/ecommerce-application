package com.nagarro.bench.advance.assignment.order_service.dto;

import java.util.List;

import com.nagarro.bench.advance.assignment.order_service.model.OrderStatus;

public class OrderResponseDTO {
	
	private String id;
	private UserDTO user;
	private List<OrderLineItemDTO> lineItems;
	private OrderStatus status;
	private Double Total;
	
	public OrderResponseDTO() {
		super();
	}

	public OrderResponseDTO(String id, UserDTO user, List<OrderLineItemDTO> lineItems, OrderStatus status,
			Double total) {
		super();
		this.id = id;
		this.user = user;
		this.lineItems = lineItems;
		this.status = status;
		Total = total;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<OrderLineItemDTO> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<OrderLineItemDTO> lineItems) {
		this.lineItems = lineItems;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Double getTotal() {
		return Total;
	}

	public void setTotal(Double total) {
		Total = total;
	}

}
