package com.nagarro.bench.advance.assignment.product_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequestDTO {
	
	private Long id;
	private String name;
	private String description;
	private Integer quantityAvailable;
	private PriceDTO price;

}
