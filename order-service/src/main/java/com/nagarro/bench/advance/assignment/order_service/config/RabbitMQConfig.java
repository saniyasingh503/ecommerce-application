package com.nagarro.bench.advance.assignment.order_service.config;

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
	
	@Value("${rabbitmq.queue.orderConfirmedEventQueue}")
	private String orderConfirmedEventQueue;
	
	@Value("${rabbitmq.queue.productNotAvailableEventQueue}")
	private String productNotAvailableEventQueue;
	
	@Value("${rabbitmq.exchange.orderExchange}")
	private String orderExchange;
	
	@Value("${rabbitmq.routing.key.orderCreateEventKey}")
	private String orderCreateEvent_routingKey;
	
	@Value("${rabbitmq.routing.key.orderConfirmedEventKey}")
	private String orderConfirmedEventKey_routingKey;
	
	@Value("${rabbitmq.routing.key.productNotAvailableEventKey}")
	private String productNotAvailable_routingKey;
	
	@Bean
	public Queue orderCreatedEventQueue() {
		return new Queue(orderCreatedEventQueue);
	}
	
	@Bean
	public Queue orderConfirmedEventQueue() {
		return new Queue(orderConfirmedEventQueue);
	}
	
	@Bean
	public Queue productNotAvailableEventQueue() {
		return new Queue(productNotAvailableEventQueue);
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
	public TopicExchange orderExchange() {
		return new TopicExchange(orderExchange);
	}
	
	@Bean
	public Binding orderCreatedEventBinding() {
		return BindingBuilder
				.bind(orderCreatedEventQueue())
				.to(orderExchange())
				.with(orderCreateEvent_routingKey);
	}
	
	@Bean
	public Binding orderConfirmedEventBinding() {
		return BindingBuilder
				.bind(orderConfirmedEventQueue())
				.to(orderExchange())
				.with(orderConfirmedEventKey_routingKey);
	}
	
	@Bean
	public Binding productNotAvailableBinding() {
		return BindingBuilder
				.bind(productNotAvailableEventQueue())
				.to(orderExchange())
				.with(productNotAvailable_routingKey);
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
