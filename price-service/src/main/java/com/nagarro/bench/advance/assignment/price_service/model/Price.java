package com.nagarro.bench.advance.assignment.price_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "prices")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Price {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Double amount;
	
	@Column(nullable = false)
	private Long productId;
	
}
