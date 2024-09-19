package com.nagarro.bench.advance.assignment.notification_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	@Value("${rabbitmq.queue.orderConfirmedEventQueue}")
	private String orderConfirmedEventQueue;
	
	@Value("${rabbitmq.queue.productNotAvailableEventQueue}")
	private String productNotAvailableEventQueue;
	
	@Bean
	public Queue orderConfirmedEventQueue() {
		return new Queue(orderConfirmedEventQueue);
	}
	
	@Bean
	public Queue productNotAvailableEventQueue() {
		return new Queue(productNotAvailableEventQueue);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}

	 @Bean
	 public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
	    MessageConverter jsonMessageConverter) {
	    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	    factory.setConnectionFactory(connectionFactory);
	    factory.setMessageConverter(jsonMessageConverter);
	    return factory;
	 }

}
