package org.spring.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SecuredApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuredApiGatewayApplication.class, args);
	}

}
