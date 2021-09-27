package org.spring.microservice.notification.processor;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailNotification {

	public void sendEmail(String orderNumber) {
        log.info("Email Sent For Order Id {}", orderNumber);
	}
}
