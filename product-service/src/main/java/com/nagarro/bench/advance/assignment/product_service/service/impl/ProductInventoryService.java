package com.nagarro.bench.advance.assignment.product_service.service.impl;

import java.util.List;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.nagarro.bench.advance.assignment.product_service.dto.OrderLineItemDTO;
import com.nagarro.bench.advance.assignment.product_service.dto.OrderResponseDTO;
import com.nagarro.bench.advance.assignment.product_service.service.ProductService;

@Service
public class ProductInventoryService {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ProductService productService;
	
	@Value("${rabbitmq.exchange.inventoryExchange}")
	private String inventoryExchange;
	
	@Value("${rabbitmq.routing.key.inventoryNotAvailableEventKey}")
	private String inventoryNotAvailableEvent_routingKey;
	
	@Value("${rabbitmq.routing.key.inventoryAvailableEventKey}")
	private String inventoryAvailableEvent_routingKey;
	
	@RabbitListener(queues = "${rabbitmq.queue.orderCreateEventQueue}")
	public void confirmOrderEvent(OrderResponseDTO orderResponse) {
	    List<OrderLineItemDTO> orderLineItems = orderResponse.getLineItems();
	    boolean allItemsAvailable = true;
	    
	    for (OrderLineItemDTO item : orderLineItems) {
	    	boolean isProductAvailable = productService.isProductAvailable(item.getProductId(), item.getQuantity());
	    	
	        if (!isProductAvailable) {
	        	 allItemsAvailable = false;
	        } 
	    }
	        
	    if (allItemsAvailable) {
	    	//updating product inventory
	    	for (OrderLineItemDTO item : orderLineItems) {
		    	productService.updateProductInventory(item.getProductId(), item.getQuantity());
		    }
	        rabbitTemplate.convertAndSend(inventoryExchange, inventoryAvailableEvent_routingKey, orderResponse.getId());
	    } else {
	        rabbitTemplate.convertAndSend(inventoryExchange, inventoryNotAvailableEvent_routingKey, orderResponse.getId());
	    }
	    
	}

	
	

}
