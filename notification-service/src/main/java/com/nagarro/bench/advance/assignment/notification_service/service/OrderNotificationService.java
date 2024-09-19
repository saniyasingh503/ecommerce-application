package com.nagarro.bench.advance.assignment.notification_service.service;

import com.nagarro.bench.advance.assignment.notification_service.model.Notification;

public interface OrderNotificationService {
	
	void sendOrderCreationNotification(Notification orderNotificationDetails);
	void sendOrderFailedNotification(Notification orderNotificationDetails);

}
