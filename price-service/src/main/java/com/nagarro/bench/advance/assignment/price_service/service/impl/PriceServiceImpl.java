package com.nagarro.bench.advance.assignment.price_service.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.bench.advance.assignment.price_service.dto.PriceDTO;
import com.nagarro.bench.advance.assignment.price_service.exception.ResourceConflictException;
import com.nagarro.bench.advance.assignment.price_service.exception.ResourceNotFoundException;
import com.nagarro.bench.advance.assignment.price_service.model.Price;
import com.nagarro.bench.advance.assignment.price_service.repository.PriceRepository;
import com.nagarro.bench.advance.assignment.price_service.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService {
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String PRODUCT_SERVICE_URL = "http://localhost:9001/api/v1/products/";

	@Override
	public PriceDTO getPriceByProductId(Long productId) {
		Price price = priceRepository.findByProductId(productId)
					.orElseThrow(() -> new ResourceNotFoundException("Price not found by productId: " + productId));
		return convertEntityToPriceDTO(price);
	}

	@Override
	public PriceDTO createProductPrice(PriceDTO price) {
		Optional<Price> existingPriceDetails = priceRepository.findByProductId(price.getProductId());
		
		if(existingPriceDetails.isPresent())
			throw new ResourceConflictException("Price already exist for this productId: " + price.getProductId());
		
		// Check if the product exists using the ProductService
//	    try {
//	        ResponseEntity<Object> productExistsResponse = restTemplate.getForEntity(PRODUCT_SERVICE_URL + price.getProductId(), Object.class);
//
//	        if (productExistsResponse.getStatusCode() == HttpStatus.OK) {
//	            Price priceDetails = convertPriceDTOToEntity(price);
//	            return convertEntityToPriceDTO(priceRepository.save(priceDetails));
//	        } else if (productExistsResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
//	            throw new ResourceNotFoundException("Product not found by Id: " + price.getProductId());
//	        } else {
//	            throw new RuntimeException("Unexpected status code: " + productExistsResponse.getStatusCode());
//	        }
//	    } catch (HttpClientErrorException e) {
//	        throw new ResourceNotFoundException("Product not found by Id: " + price.getProductId());
//	    } catch (Exception e) {
//	        throw new RuntimeException("Error checking product existence: " + e.getMessage(), e);
//	    }
		 Price priceDetails = convertPriceDTOToEntity(price);
         return convertEntityToPriceDTO(priceRepository.save(priceDetails));
		
	}

	@Override
	public PriceDTO updateProductPrice(PriceDTO price, Long productId) {
		Optional<Price> existingPriceDetails = priceRepository.findByProductId(productId);
		
		if(!existingPriceDetails.isPresent())
			throw new ResourceNotFoundException("Price not found by productId: " + productId);
		
		Price priceDetails = existingPriceDetails.get();
		priceDetails.setAmount(price.getAmount());
		
		return convertEntityToPriceDTO(priceRepository.save(priceDetails));
	}

	@Override
	public void deletePriceByProductId(Long productId) {
		Optional<Price> priceDetails = priceRepository.findByProductId(productId);
		
		if(!priceDetails.isPresent())
			throw new ResourceNotFoundException("Price not found by productId: " + productId);
	
		priceRepository.deleteById(priceDetails.get().getId());
	}
	
	public Price convertPriceDTOToEntity(PriceDTO priceDto) {
		return modelMapper.map(priceDto, Price.class);
	}
	
	public PriceDTO convertEntityToPriceDTO(Price price) {
		return modelMapper.map(price, PriceDTO.class);
	}

}
