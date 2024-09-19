package com.nagarro.bench.advance.assignment.product_service.service;

import java.util.List;
import com.nagarro.bench.advance.assignment.product_service.dto.ProductRequestDTO;


public interface ProductService {
	
	ProductRequestDTO createProduct(ProductRequestDTO product);
	List<ProductRequestDTO> getAllProducts();
	ProductRequestDTO getProductDetailsById(Long productId);
	ProductRequestDTO updateProductDetailsById(ProductRequestDTO productRequestDto, Long productId);
	void deleteProductById(Long productId);
	boolean isProductAvailable(Long productId, Integer quantity);
	ProductRequestDTO updateProductInventory(Long productId, Integer quantity);

}
