package com.nagarro.bench.advance.assignment.order_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nagarro.bench.advance.assignment.order_service.dto.OrderRequestDTO;
import com.nagarro.bench.advance.assignment.order_service.dto.OrderResponseDTO;
import com.nagarro.bench.advance.assignment.order_service.service.OrderService;

@Controller
@RequestMapping("api/v1/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest){
		return new ResponseEntity<OrderResponseDTO>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@RequestParam(defaultValue = "") String userId){
		if(!userId.isEmpty())
			return new ResponseEntity<List<OrderResponseDTO>>(orderService.getAllOrdersByUserId(userId), HttpStatus.OK);
		
		return new ResponseEntity<List<OrderResponseDTO>>(orderService.getAllOrders(), HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable("orderId") String orderId){
		return new ResponseEntity<OrderResponseDTO>(orderService.getOrderById(orderId), HttpStatus.OK);
	}

}
