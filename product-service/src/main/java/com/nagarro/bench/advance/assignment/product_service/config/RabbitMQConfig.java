package com.nagarro.bench.advance.assignment.product_service.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	@Value("${rabbitmq.queue.orderCreateEventQueue}")
	private String orderCreatedEventQueue;
	
	@Value("${rabbitmq.queue.inventoryAvailableEventQueue}")
	private String inventoryAvailableEventQueue;
	
	@Value("${rabbitmq.queue.inventoryNotAvailableEventQueue}")
	private String inventoryNotAvailableEventQueue;
	
	@Value("${rabbitmq.exchange.inventoryExchange}")
	private String inventoryExchange;
	
	@Value("${rabbitmq.routing.key.inventoryAvailableEventKey}")
	private String inventoryAvailableEvent_routingKey;
	
	@Value("${rabbitmq.routing.key.inventoryNotAvailableEventKey}")
	private String inventoryNotAvailableEvent_routingKey;
	
	@Bean
	public Queue orderCreatedEventQueue() {
		return new Queue(orderCreatedEventQueue);
	}
	
	@Bean
	public Queue inventoryAvailableEventQueue() {
		return new Queue(inventoryAvailableEventQueue);
	}
	
	@Bean
	public Queue inventoryNotAvailableEventQueue() {
		return new Queue(inventoryNotAvailableEventQueue);
	}
	
	@Bean
	public TopicExchange inventoryExchange() {
		return new TopicExchange(inventoryExchange);
	}
	
	@Bean
	public Binding inventoryAvailableEventBinding() {
		return BindingBuilder
				.bind(inventoryAvailableEventQueue())
				.to(inventoryExchange())
				.with(inventoryAvailableEvent_routingKey);
	}
	
	@Bean
	public Binding inventoryNotAvailableEventBinding() {
		return BindingBuilder
				.bind(inventoryNotAvailableEventQueue())
				.to(inventoryExchange())
				.with(inventoryNotAvailableEvent_routingKey);
	}
	
	@Bean
	public MessageConverter converter() {
	    return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
	    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(converter());
	    return rabbitTemplate;
	}

}
