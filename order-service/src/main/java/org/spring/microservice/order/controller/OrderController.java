package org.spring.microservice.order.controller;

import org.spring.microservice.order.model.CreateOrderRequest;
import org.spring.microservice.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public String createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
		log.info("Inside createOrder method of Order Controller");
		return orderService.createOrder(createOrderRequest);
	}
}
