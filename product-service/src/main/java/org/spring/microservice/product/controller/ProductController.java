package org.spring.microservice.product.controller;

import java.util.List;
import java.util.Optional;

import org.spring.microservice.product.entity.Product;
import org.spring.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService; 
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createProduct(@RequestBody Product product) {
		log.info("Inside createProduct method of ProductController");
		productService.saveProduct(product);
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<Product> getProducts(){
		log.info("Inside getProducts method of ProductController");
		return productService.findProducts();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Optional<Product> getProductById(@PathVariable("id") Long productId) {
		log.info("Inside getProductById method of ProductController");
		return productService.findProductById(productId);
	}
	
}
