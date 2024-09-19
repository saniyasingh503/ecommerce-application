package com.nagarro.bench.advance.assignment.product_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PriceDTO {
	
	private Long id;
	private Double amount;
	private Long productId;

}
