package org.spring.microservice.notification.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotificationProcessor implements Processor {

	@Autowired
	private EmailNotification emailNotification;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		log.info("Inside process method of NotificationProcessor class");
		String message = exchange.getIn().getBody(String.class);
		emailNotification.sendEmail(message);
	}

}
