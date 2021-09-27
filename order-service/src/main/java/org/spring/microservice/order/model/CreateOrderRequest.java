package org.spring.microservice.order.model;

import java.util.List;

import org.spring.microservice.order.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

	private List<OrderItem> orderItems;
}
