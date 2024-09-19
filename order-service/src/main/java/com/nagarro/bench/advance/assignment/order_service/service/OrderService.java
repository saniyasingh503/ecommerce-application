package com.nagarro.bench.advance.assignment.order_service.service;

import java.util.List;

import com.nagarro.bench.advance.assignment.order_service.dto.OrderRequestDTO;
import com.nagarro.bench.advance.assignment.order_service.dto.OrderResponseDTO;
import com.nagarro.bench.advance.assignment.order_service.model.OrderStatus;

public interface OrderService {
	
	List<OrderResponseDTO> getAllOrders();
	List<OrderResponseDTO> getAllOrdersByUserId(String userId);
	OrderResponseDTO createOrder(OrderRequestDTO orderRequestDto);
	OrderResponseDTO getOrderById(String orderId);
	OrderResponseDTO updateOrderStatus(String orderId, OrderStatus status);

}
