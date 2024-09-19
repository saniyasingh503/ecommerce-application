package com.nagarro.bench.advance.assignment.cart_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
	
	private Long id;
	private String name;
	private String description;
	private Integer quantityAvailable;
	private PriceDTO price;

}
