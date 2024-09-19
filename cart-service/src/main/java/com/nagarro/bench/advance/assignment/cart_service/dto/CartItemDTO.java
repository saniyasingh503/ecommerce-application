package com.nagarro.bench.advance.assignment.cart_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemDTO {
	
	private String id;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double itemTotal;
	
}
