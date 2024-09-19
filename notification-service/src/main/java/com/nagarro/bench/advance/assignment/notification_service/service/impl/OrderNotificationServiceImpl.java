package com.nagarro.bench.advance.assignment.notification_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nagarro.bench.advance.assignment.notification_service.model.Notification;
import com.nagarro.bench.advance.assignment.notification_service.service.OrderNotificationService;


@Service
public class OrderNotificationServiceImpl implements OrderNotificationService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderNotificationServiceImpl.class);

	@Override
	public void sendOrderCreationNotification(Notification orderNotificationDetails) {
		logger.info("Sending Order Confirmation Notification");
		
		logger.info("Title: " + orderNotificationDetails.getTitle());
		logger.info(orderNotificationDetails.getMessage());
		
	}

	@Override
	public void sendOrderFailedNotification(Notification orderNotificationDetails) {
		logger.info("Sending Product Out of Stock Notification");
		
		logger.info("Title: " + orderNotificationDetails.getTitle());
		logger.info(orderNotificationDetails.getMessage());
		
	}

}
