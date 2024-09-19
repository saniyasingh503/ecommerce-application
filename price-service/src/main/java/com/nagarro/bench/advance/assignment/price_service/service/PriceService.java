package com.nagarro.bench.advance.assignment.price_service.service;

import com.nagarro.bench.advance.assignment.price_service.dto.PriceDTO;

public interface PriceService {
	
	PriceDTO getPriceByProductId(Long productId);
	PriceDTO createProductPrice(PriceDTO price);
	PriceDTO updateProductPrice(PriceDTO price, Long productId);
	void deletePriceByProductId(Long productId);

}
