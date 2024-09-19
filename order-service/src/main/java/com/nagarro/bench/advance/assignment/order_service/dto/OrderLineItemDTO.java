package com.nagarro.bench.advance.assignment.order_service.dto;

public class OrderLineItemDTO {
	
	private String id;
	private Long productId;
	private String productName;
	private Double price;
	private Integer quantity;
	private Double lineTotal;
	
	public OrderLineItemDTO() {
		super();
	}

	public OrderLineItemDTO(String id, Long productId, String productName, Integer quantity, Double price, Double lineTotal) {
		super();
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.lineTotal = lineTotal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
