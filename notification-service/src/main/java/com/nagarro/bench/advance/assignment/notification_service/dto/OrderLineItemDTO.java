package com.nagarro.bench.advance.assignment.notification_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLineItemDTO {
	
	private String id;
	private Long productId;
	private String productName;
	private Integer quantity;
	private Double price;
	private Double lineTotal;
	
}
