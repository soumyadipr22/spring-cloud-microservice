package org.spring.microservice.product.service;

import java.util.List;
import java.util.Optional;

import org.spring.microservice.product.entity.Product;
import org.spring.microservice.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public void saveProduct(Product product) {
		log.info("Inside saveProduct method of ProductService");
		productRepository.save(product);
	}

	public List<Product> findProducts() {
		log.info("Inside findProducts method of ProductService");
		return productRepository.findAll();
	}

	public Optional<Product> findProductById(Long productId) {
		log.info("Inside findProductById method of ProductService");
		return productRepository.findById(productId);
	}
}
