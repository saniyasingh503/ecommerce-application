package com.nagarro.bench.advance.assignment.product_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.nagarro.bench.advance.assignment.product_service.dto.PriceDTO;
import com.nagarro.bench.advance.assignment.product_service.dto.ProductRequestDTO;
import com.nagarro.bench.advance.assignment.product_service.exception.ResourceNotFoundException;
import com.nagarro.bench.advance.assignment.product_service.external.Price;
import com.nagarro.bench.advance.assignment.product_service.model.Product;
import com.nagarro.bench.advance.assignment.product_service.repository.ProductRepository;
import com.nagarro.bench.advance.assignment.product_service.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String PRICE_SERVICE_URL = "http://localhost:9002/api/v1/prices";

	@Override
	public ProductRequestDTO createProduct(ProductRequestDTO productRequestDto) {
//		Optional<Product> productDetails = productRepository.findById(productRequestDto.getId());
//		
//		if(productDetails.isPresent())
//			throw new ResourceConflictException("Product already exist with this prductId: " + productRequestDto.getId());
//		
		// creating productDetails
		
		Product createdProduct = productRepository.save(convertProductRequestDTOToProductEntity(productRequestDto));
		
		// creating product price
		if(createdProduct == null) 
			throw new RuntimeException("Internal Server Error");
		
		System.out.println(createdProduct.getId());
		PriceDTO price = createProductPrice(productRequestDto.getPrice(), createdProduct.getId());
		
		return convertEntityToProductRequestDTO(createdProduct, convertPriceDTOToPriceEntity(price));
	}

	@Override
	public List<ProductRequestDTO> getAllProducts() {
		  // Fetch all products from the repository
	    List<Product> products = productRepository.findAll();

	    List<ProductRequestDTO> productRequestDTOs = new ArrayList<>();

	    // Iterate over each product to fetch its price details
	    for (Product product : products) {
	        Price price = convertPriceDTOToPriceEntity(getPriceDetails(product.getId()));

	        // Convert Product entity and PriceDTO to ProductRequestDTO
	        ProductRequestDTO productRequestDTO = convertEntityToProductRequestDTO(product, price);
	        productRequestDTOs.add(productRequestDTO);
	    }

	    return productRequestDTOs;
	}

	@Override
	public ProductRequestDTO getProductDetailsById(Long productId) {
	    Optional<Product> productOptional = productRepository.findById(productId);
	    if (productOptional.isEmpty())
	        throw new ResourceNotFoundException("Product not found with id: " + productId);
	    
	    Product product = productOptional.get();

	    Price price = convertPriceDTOToPriceEntity(getPriceDetails(productId));

	    // Convert Product entity and PriceDTO to ProductRequestDTO
	    ProductRequestDTO productRequestDTO = convertEntityToProductRequestDTO(product, price);

	    return productRequestDTO;
	}

	@Override
	public ProductRequestDTO updateProductDetailsById(ProductRequestDTO productRequestDto, Long productId) {
		Optional<Product> existingProductOptional = productRepository.findById(productId);
	    if (existingProductOptional.isEmpty()) {
	        throw new ResourceNotFoundException("Product not found with id: " + productId);
	    }
	    Product existingProduct = existingProductOptional.get();

	    // Update the existing product entity with the new details
	    existingProduct.setName(productRequestDto.getName());
	    existingProduct.setDescription(productRequestDto.getDescription());
	    existingProduct.setQuantityAvailable(productRequestDto.getQuantityAvailable());

	    // Save the updated product entity
	    Product updatedProduct = productRepository.save(existingProduct);
	    Price updatedPrice = null;
	    // If price information is also updated in productRequestDto, update price details
	    if (productRequestDto.getPrice() != null) {
	        updatedPrice = convertPriceDTOToPriceEntity(updatePriceDetails(productId, productRequestDto.getPrice()));
	    }

	    return convertEntityToProductRequestDTO(updatedProduct, updatedPrice);
	}

	@Override
	public void deleteProductById(Long productId) {
		 Optional<Product> existingProductOptional = productRepository.findById(productId);
		    if (existingProductOptional.isEmpty()) {
		        throw new ResourceNotFoundException("Product not found with id: " + productId);
		    }

		    // Delete the product
		    productRepository.deleteById(productId);

		    // Optionally, delete associated price details
		    deletePriceDetails(productId);
	}
	
	public PriceDTO createProductPrice(PriceDTO priceDto, Long productId) {
		priceDto.setProductId(productId);
		try {
	        System.out.println(PRICE_SERVICE_URL);
	        System.out.println(priceDto);
			ResponseEntity<PriceDTO> response = restTemplate.postForEntity(PRICE_SERVICE_URL, priceDto, PriceDTO.class);
		
			if(response.getStatusCode() == HttpStatus.CREATED)
				return response.getBody();
			else {
	            System.err.println("Unexpected status code: " + response.getStatusCode());
	            System.err.println("Response body: " + response.getBody());
	        }
	    } catch (HttpClientErrorException | HttpServerErrorException ex) {
	        System.err.println("HTTP Error: " + ex.getStatusCode() + " - " + ex.getStatusText());
	        System.err.println("Response body: " + ex.getResponseBodyAsString());
	        ex.printStackTrace();
	    } catch (RestClientException ex) {
	        System.err.println("RestClientException: " + ex.getMessage());
	        ex.printStackTrace();
	    } catch (Exception e) {
	        System.err.println("Error occurred: " + e.getMessage());
	        e.printStackTrace();
	    }
		return null;
	}
	
	public PriceDTO getPriceDetails(Long productId) {
		String priceServiceUrl = String.format("%s/%d", PRICE_SERVICE_URL, productId);;

	    try {
	        ResponseEntity<PriceDTO> response = restTemplate.exchange(
	            priceServiceUrl,         
	            HttpMethod.GET,        
	            null,                  
	            PriceDTO.class           
	        );

	        if (response.getStatusCode() == HttpStatus.OK)
	            return response.getBody();
	    }  catch (Exception e) {
	        System.err.println("Error occurred: " + e.getMessage());
	        return null;
	    }
		return null;
	}
	
	public void deletePriceDetails(Long productId) {
	    String priceServiceUrl = String.format("%s/%d", PRICE_SERVICE_URL, productId);;

	    try {
	        restTemplate.delete(priceServiceUrl);
	        System.out.println("Price details deleted successfully for productId: " + productId);
	    } catch (HttpClientErrorException.NotFound e) {
	        throw new ResourceNotFoundException("Price details not found for productId: " + productId);
	    } catch (Exception e) {
	        System.err.println("Error occurred while deleting price details: " + e.getMessage());
	    }
	}
	
	public PriceDTO updatePriceDetails(Long productId, PriceDTO updatedPrice) {
	    String priceServiceUrl = String.format("%s/%d", PRICE_SERVICE_URL, productId);

	    try {

	        HttpEntity<PriceDTO> requestEntity = new HttpEntity<>(updatedPrice);

	        ResponseEntity<PriceDTO> response = restTemplate.exchange(
	            priceServiceUrl,
	            HttpMethod.PUT,
	            requestEntity,
	            PriceDTO.class
	        );

	        if (response.getStatusCode() == HttpStatus.OK) {
	           return response.getBody();
	        } 
	    } catch (HttpClientErrorException.NotFound e) {
	        throw new ResourceNotFoundException("Price details not found for productId: " + productId);
	    } catch (Exception e) {
	        System.err.println("Error occurred while updating price details: " + e.getMessage());
	        return null;
	    }
	    return null;
	}

	public ProductRequestDTO convertProductEntityToProductRequestDTO(Product product) {
		return modelMapper.map(product, ProductRequestDTO.class);
	}

	public Product convertProductRequestDTOToProductEntity(ProductRequestDTO productRequestDTO) {
		return modelMapper.map(productRequestDTO, Product.class);
	}
	
	public Price convertProductRequestDTOToPriceEntity(ProductRequestDTO productRequestDTO) {
		return modelMapper.map(productRequestDTO, Price.class);
	}
	
	public ProductRequestDTO convertEntityToProductRequestDTO(Product product, Price price) {
		 ProductRequestDTO productRequestDto = modelMapper.map(product, ProductRequestDTO.class);
		 PriceDTO priceDto = modelMapper.map(price, PriceDTO.class);
		 productRequestDto.setPrice(priceDto);
		 return productRequestDto;
	}
	
	public PriceDTO convertPriceEntityToPriceDTO(Price price) {
		return modelMapper.map(price, PriceDTO.class);
	}
	
	public Price convertPriceDTOToPriceEntity(PriceDTO priceDto) {
		return modelMapper.map(priceDto, Price.class);
	}

	@Override
	public boolean isProductAvailable(Long productId, Integer quantity) {
		Optional<Product> existingProductOptional = productRepository.findById(productId);
	    if (existingProductOptional.isEmpty()) {
	        throw new ResourceNotFoundException("Product not found with id: " + productId);
	    }
	    
	    Product product = existingProductOptional.get();
	    
		return (product.getQuantityAvailable() > 0 && 
				product.getQuantityAvailable() >= quantity);
	}

	@Override
	public ProductRequestDTO updateProductInventory(Long productId, Integer quantity) {
		Optional<Product> existingProductOptional = productRepository.findById(productId);
	    if (existingProductOptional.isEmpty()) {
	        throw new ResourceNotFoundException("Product not found with id: " + productId);
	    }
	    
	    PriceDTO priceDto = getPriceDetails(productId);
	    Product product = existingProductOptional.get();
	    product.setQuantityAvailable(product.getQuantityAvailable() - quantity);
	    
	    // Save the updated product to the database
	    productRepository.save(product); 
	    
		ProductRequestDTO productResponse = convertProductEntityToProductRequestDTO(product);
		productResponse.setPrice(priceDto);
		return productResponse;
	}
	
}
