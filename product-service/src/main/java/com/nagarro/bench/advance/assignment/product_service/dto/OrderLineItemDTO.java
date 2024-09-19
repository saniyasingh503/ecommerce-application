package com.nagarro.bench.advance.assignment.product_service.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderLineItemDTO {
	
	private String id;
	private Long productId;
	private String productName;
	private Double price;
	private Integer quantity;
	private Double lineTotal;

}
