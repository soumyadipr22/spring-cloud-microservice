package org.spring.microservice.notification.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationRoute extends RouteBuilder {

	@Value("order.notification.queue")
	private String orderNotificationQueue;
	
	@Override
	public void configure() throws Exception {
		from("activemq:"+orderNotificationQueue)
			.process("notificationProcessor");
	}

}
