package org.spring.microservice.gateway.config;

import org.spring.microservice.gateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredGatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes().route("authentication-service", r -> r.path("/api/authenticate").filters(f -> f.filter(filter)).uri("lb://authentication-service"))
				.route("product-service", r -> r.path("/api/products/**").filters(f -> f.filter(filter)).uri("lb://product-service"))
				.route("order-service", r -> r.path("/api/order/**").filters(f -> f.filter(filter)).uri("lb://order-service"))
				.route("inventory-service", r -> r.path("/api/inventory/**").filters(f -> f.filter(filter)).uri("lb://inventory-service")).build();
	}

}
