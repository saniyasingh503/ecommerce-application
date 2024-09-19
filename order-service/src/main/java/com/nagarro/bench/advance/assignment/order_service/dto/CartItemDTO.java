package com.nagarro.bench.advance.assignment.order_service.dto;

public class CartItemDTO {
	
	private String id;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double itemTotal;
    
	public CartItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartItemDTO(String id, Long productId, String productName, Double price, Integer quantity, Double itemTotal) {
		super();
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.itemTotal = itemTotal;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(Double itemTotal) {
		this.itemTotal = itemTotal;
	}
	

}
