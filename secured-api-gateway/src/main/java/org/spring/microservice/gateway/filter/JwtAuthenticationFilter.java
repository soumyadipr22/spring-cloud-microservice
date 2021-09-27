package org.spring.microservice.gateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.spring.microservice.gateway.exception.JwtTokenMalformedException;
import org.spring.microservice.gateway.exception.JwtTokenMissingException;
import org.spring.microservice.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();

		final List<String> apiEndpoints = List.of("/api/authenticate");

		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));

		if (isApiSecured.test(request)) {
			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
			}

			String jwt = null;
			String authorizationHeader = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
			if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
				jwt = authorizationHeader.substring(7);
			}
			try {
				jwtUtil.validateToken(jwt);
			} catch (JwtTokenMalformedException | JwtTokenMissingException e) {
				// e.printStackTrace();
				return onError(exchange, "JWT validation failed", HttpStatus.UNAUTHORIZED);
			}

			Claims claims = jwtUtil.getClaims(jwt);
			exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
		}

		return chain.filter(exchange);
	}
	
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

}
