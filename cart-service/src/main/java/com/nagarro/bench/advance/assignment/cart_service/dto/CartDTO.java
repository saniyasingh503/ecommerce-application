package com.nagarro.bench.advance.assignment.cart_service.dto;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO {
	
	private String id;
    private String userId;
    private List<CartItemDTO> cartItems;
    private Double total;

}
