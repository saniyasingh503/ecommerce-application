package com.nagarro.bench.advance.assignment.order_service.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable = false)
	private OrderStatus status;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderLineItem> lineItems = new ArrayList<>();
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private Double total;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String id, OrderStatus status, List<OrderLineItem> lineItems, String userId, Double total) {
		super();
		this.id = id;
		this.status = status;
		this.lineItems = lineItems;
		this.userId = userId;
		this.total = total;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<OrderLineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<OrderLineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public void addOrderLineItem(OrderLineItem orderLineItem) {
        lineItems.add(orderLineItem);
        orderLineItem.setOrder(this);
    }

    public void removeOrderLineItem(OrderLineItem orderLineItem) {
        lineItems.add(orderLineItem);
        orderLineItem.setOrder(this);
    }

	

}
