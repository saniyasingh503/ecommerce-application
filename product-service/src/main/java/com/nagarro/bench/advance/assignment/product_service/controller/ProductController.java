package com.nagarro.bench.advance.assignment.product_service.controller;

import java.util.List;

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

import com.nagarro.bench.advance.assignment.product_service.dto.ProductRequestDTO;
import com.nagarro.bench.advance.assignment.product_service.service.ProductService;

@Controller
@RequestMapping("api/v1/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<ProductRequestDTO>> getAllProducts() {
		return new ResponseEntity<List<ProductRequestDTO>>(productService.getAllProducts(),
				HttpStatus.OK); 
	}
	
	@PostMapping
	public ResponseEntity<ProductRequestDTO> createProduct(@RequestBody ProductRequestDTO product){
		return new ResponseEntity<ProductRequestDTO>(productService.createProduct(product),
				HttpStatus.CREATED);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductRequestDTO> getProductDetails(@PathVariable("productId") Long productId){
		return new ResponseEntity<ProductRequestDTO>(productService.getProductDetailsById(productId),
				HttpStatus.OK);
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductRequestDTO> updateProduct(@RequestBody ProductRequestDTO product, @PathVariable("productId") Long productId){
		return new ResponseEntity<ProductRequestDTO>(productService.updateProductDetailsById(product, productId),
				HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("productId") Long productId){
		productService.deleteProductById(productId);
		return new ResponseEntity<Object>("Product is deleted successfully !!",
				HttpStatus.NO_CONTENT);
	}

}
