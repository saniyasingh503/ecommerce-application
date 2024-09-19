package com.nagarro.bench.advance.assignment.product_service.external;


public class Price {
	
	private Long id;
	private Double amount;
	private Long productId;
	
	public Price() {
		super();
	}
	
	public Price(Long id, Double amount, Long productId) {
		super();
		this.id = id;
		this.amount = amount;
		this.productId = productId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "Price [id=" + id + ", amount=" + amount + ", productId=" + productId + "]";
	}
	
	

}
