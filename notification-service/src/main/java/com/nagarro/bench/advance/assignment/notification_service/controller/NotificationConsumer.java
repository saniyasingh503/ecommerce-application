package com.nagarro.bench.advance.assignment.notification_service.controller;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nagarro.bench.advance.assignment.notification_service.dto.OrderResponseDTO;
import com.nagarro.bench.advance.assignment.notification_service.model.Notification;
import com.nagarro.bench.advance.assignment.notification_service.service.OrderNotificationService;

@Component
public class NotificationConsumer {
	
	@Autowired
	private OrderNotificationService orderNotificationService;
	
	@RabbitListener(queues = "${rabbitmq.queue.orderConfirmedEventQueue}")
	public void orderConfirmNotificationEvent(OrderResponseDTO orderResponse) {
		
		String messageContent = orderConfirmationMsgBody(orderResponse);
		String messageTitle = "Your order is " + orderResponse.getStatus() + "!!";

        Notification orderNotification = new Notification();
        orderNotification.setId(UUID.randomUUID().toString());
        orderNotification.setRecipient(orderResponse.getUser().getEmail());
        orderNotification.setTitle(messageTitle);
        orderNotification.setType("Email");
        orderNotification.setMessage(messageContent);

        orderNotificationService.sendOrderCreationNotification(orderNotification);
	}
	
	@RabbitListener(queues = "${rabbitmq.queue.productNotAvailableEventQueue}")
	public void productNotAvailableNotificationEvent(OrderResponseDTO orderResponse) {
		
		String messageContent = productOutOfStockMsgBody(orderResponse);
		String messageTitle = "Your order is " + orderResponse.getStatus() + "!!";
		
		Notification orderNotification = new Notification();
		
		orderNotification.setId(UUID.randomUUID().toString());
        orderNotification.setRecipient(orderResponse.getUser().getEmail());
        orderNotification.setTitle("Your order is " + orderResponse.getStatus());
        orderNotification.setType("Email");
        orderNotification.setTitle(messageTitle);
        orderNotification.setMessage(messageContent);
        
        orderNotificationService.sendOrderFailedNotification(orderNotification);
	}
	
	private String orderConfirmationMsgBody(OrderResponseDTO orderResponse) {
		// Build the line items message
		String lineItemsMessage = orderResponse.getLineItems().stream()
		    .map(lineItem -> String.format("%s (Quantity: %d, Price: %.2f)",
		        lineItem.getProductName(),
		        lineItem.getQuantity(),
		        lineItem.getPrice()))
		    .collect(Collectors.joining(", "));

		// Build the full message
		String message = String.format(
		    "Hi %s %s! Your order has been placed successfully.\n" +
		    "Items: %s.\n" +
		    "Total Price: %.2f",
		    orderResponse.getUser().getFirstName(),
		    orderResponse.getUser().getLastName(),
		    lineItemsMessage,
		    orderResponse.getTotal()
		);
        
        return message;
	}
	
	private String productOutOfStockMsgBody(OrderResponseDTO orderResponse) {
		String message = String.format(
			    "Hi %s %s!,\n\n" + 
			    "We regret to inform you that some items in your recent order are out of stock.\n",
			    orderResponse.getUser().getFirstName(),
			    orderResponse.getUser().getLastName()
			);
		
		return message;
		
	}

}
