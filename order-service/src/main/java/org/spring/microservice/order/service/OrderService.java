package org.spring.microservice.order.service;

import java.util.UUID;

import org.apache.camel.ProducerTemplate;
import org.spring.microservice.order.client.InventoryClient;
import org.spring.microservice.order.entity.Order;
import org.spring.microservice.order.model.CreateOrderRequest;
import org.spring.microservice.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	private static final String INVENTORY_SERVICE = "inventory-service";
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private InventoryClient inventoryClient;
	@Autowired
	ProducerTemplate template;
	@Value("order.notification.queue")
	private String orderNotificationQueue;
	
	
	@CircuitBreaker(name = INVENTORY_SERVICE, fallbackMethod = "createOrderFallback")
	public String createOrder(CreateOrderRequest createOrderRequest) {
		log.info("Inside createOrder method of Order Service");
		
		boolean allProductsInStock = createOrderRequest.getOrderItems().stream().allMatch(orderItem -> {
			log.info("Invoking inventory-service for skuCode : {}", orderItem.getSkuCode());
			return inventoryClient.checkStock(orderItem.getSkuCode());
		});
		
		if (allProductsInStock) {
			Order order = new Order();
			order.setOrderNumber(UUID.randomUUID().toString());
			order.setOrderItems(createOrderRequest.getOrderItems());

			orderRepository.save(order);
			template.sendBody("activemq:"+orderNotificationQueue, order.getOrderNumber());
			return "Order placed successfully, order number is : "+order.getOrderNumber();
		} else {
			return "Order Failed : one/more of the product in your Order is out of stock";
		}

	}
	
	public String createOrderFallback(CreateOrderRequest order, Exception e) {
		return "We have trouble fetching your product from the inventory, please try again later.";
	}
}
