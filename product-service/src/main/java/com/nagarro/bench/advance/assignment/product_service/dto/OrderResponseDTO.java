package com.nagarro.bench.advance.assignment.product_service.dto;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDTO {
	
	private String id;
	private UserDTO user;
	private List<OrderLineItemDTO> lineItems;
	private OrderStatus status;
	private Double Total;

}
