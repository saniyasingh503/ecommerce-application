package com.nagarro.bench.advance.assignment.notification_service.dto;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDTO {
	
	private String id;
	private UserDTO user;
	private List<OrderLineItemDTO> lineItems;
	private OrderStatus status;
	private Double Total;

}
