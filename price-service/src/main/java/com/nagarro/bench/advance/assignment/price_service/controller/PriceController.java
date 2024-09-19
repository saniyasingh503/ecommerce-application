package com.nagarro.bench.advance.assignment.price_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nagarro.bench.advance.assignment.price_service.dto.PriceDTO;
import com.nagarro.bench.advance.assignment.price_service.service.PriceService;

@Controller
@RequestMapping("api/v1/prices")
public class PriceController {
	
	@Autowired
	private PriceService priceService;
	
	@PostMapping()
	public ResponseEntity<PriceDTO> createPrice(@RequestBody PriceDTO price) {
		return new ResponseEntity<PriceDTO>(priceService.createProductPrice(price), HttpStatus.CREATED);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<PriceDTO> getPriceByProductId(@PathVariable("productId") Long productId) {
		return new ResponseEntity<PriceDTO>(priceService.getPriceByProductId(productId),
				HttpStatus.OK);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<PriceDTO> updatePriceByProductId(@RequestBody PriceDTO price, @PathVariable("productId") Long productId) {
		return new ResponseEntity<PriceDTO>(priceService.updateProductPrice(price, productId),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Object> deletePriceByProductId(@PathVariable("productId") Long productId) {
		priceService.deletePriceByProductId(productId);
		return new ResponseEntity<Object>("Price is deleted successfully !!", HttpStatus.NO_CONTENT);
	}

}
