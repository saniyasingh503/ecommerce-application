package com.nagarro.bench.advance.assignment.notification_service.model;

import java.time.LocalDateTime;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class Notification {
	
	private String id;
	private String recipient;
	private String type;
	private String title;
	private String message;
	private LocalDateTime publishedAt;
	
	public Notification() {
		this.publishedAt = LocalDateTime.now();
	}

}
