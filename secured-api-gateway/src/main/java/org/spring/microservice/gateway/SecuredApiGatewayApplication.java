package org.spring.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SecuredApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuredApiGatewayApplication.class, args);
	}

}
