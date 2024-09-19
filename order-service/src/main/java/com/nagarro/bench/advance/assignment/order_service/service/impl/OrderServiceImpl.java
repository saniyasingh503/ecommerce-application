package com.nagarro.bench.advance.assignment.order_service.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.bench.advance.assignment.order_service.dto.CartDTO;
import com.nagarro.bench.advance.assignment.order_service.dto.CartItemDTO;
import com.nagarro.bench.advance.assignment.order_service.dto.OrderLineItemDTO;
import com.nagarro.bench.advance.assignment.order_service.dto.OrderRequestDTO;
import com.nagarro.bench.advance.assignment.order_service.dto.OrderResponseDTO;
import com.nagarro.bench.advance.assignment.order_service.dto.UserDTO;
import com.nagarro.bench.advance.assignment.order_service.exception.ResourceNotFoundException;
import com.nagarro.bench.advance.assignment.order_service.model.Order;
import com.nagarro.bench.advance.assignment.order_service.model.OrderLineItem;
import com.nagarro.bench.advance.assignment.order_service.model.OrderStatus;
import com.nagarro.bench.advance.assignment.order_service.repository.OrderLineItemRepository;
import com.nagarro.bench.advance.assignment.order_service.repository.OrderRepository;
import com.nagarro.bench.advance.assignment.order_service.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${rabbitmq.exchange.orderExchange}")
	private String orderExchange;
	
	@Value("${rabbitmq.exchange.inventoryExchange}")
	private String inventoryExchange;
	
	@Value("${rabbitmq.routing.key.orderCreateEventKey}")
	private String orderCreateEvent_routingKey;
	
	@Value("${rabbitmq.routing.key.orderConfirmedEventKey}")
	private String orderConfirmedEvent_routingKey;
	
	@Value("${rabbitmq.routing.key.productNotAvailableEventKey}")
	private String productNotAvailableEvent_routingKey;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static final String CART_SERVICE_URL = "http://localhost:9003/api/v1/carts";
	private static final String USER_SERVICE_URL = "http://localhost:9006/api/v1/users";

	@Override
	public List<OrderResponseDTO> getAllOrders() {
	    // Fetch all orders from the repository
	    List<Order> orders = orderRepository.findAll();

	    // Convert each Order entity to OrderResponseDTO
	    List<OrderResponseDTO> ordersResponse = orders.stream()
	                 .map(order -> {
	                	 OrderResponseDTO orderResponse = convertOrderEntityToOrderResponseDto(order);
	                	 UserDTO userDetails = getUserDetailsById(order.getUserId());
	                	 orderResponse.setUser(userDetails);
	                	 return orderResponse;
	                 })
	                 .collect(Collectors.toList());
	    return ordersResponse;
	}

	@Override
	public List<OrderResponseDTO> getAllOrdersByUserId(String userId) {
		 List<Order> orders = orderRepository.findByUserId(userId);

		    // Convert each Order entity to OrderResponseDTO
		 List<OrderResponseDTO> ordersResponse = orders.stream()
		                 .map(order -> {
		                	 OrderResponseDTO orderResponse = convertOrderEntityToOrderResponseDto(order);
		                	 UserDTO userDetails = getUserDetailsById(order.getUserId());
		                	 orderResponse.setUser(userDetails);
		                	 return orderResponse;
		                 })
		                 .collect(Collectors.toList());
		 
		 return ordersResponse;
	}

	@Override
	public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDto) {
	    // fetch cart details
	    CartDTO cart = fetchCartDetails(orderRequestDto.getCartId());
	    if (cart == null) {
	        throw new ResourceNotFoundException("Cart not found by id: " + orderRequestDto.getCartId());
	    }

	    UserDTO user = new UserDTO();
	    if (cart.getUserId() != null) {
	        // fetch user details and store it on userdto
	       user = getUserDetailsById(cart.getUserId());
	    }

	    Order order = new Order();
	    order.setUserId(cart.getUserId());
	    order.setStatus(OrderStatus.OPEN); // Assuming a new order is pending
	    order.setTotal(cart.getTotal());

	    List<OrderLineItem> lineItems = cart.getCartItems().stream()
	        .map(dto -> {
	            OrderLineItem item = convertToOrderLineItem(dto);
	            item.setOrder(order); // Ensure the back reference is set
	            return item;
	        })
	        .collect(Collectors.toList());

	    order.setLineItems(lineItems);

	    // Save the order
	    Order savedOrder = orderRepository.save(order);

	    OrderResponseDTO orderResponseDTO = convertOrderEntityToOrderResponseDto(savedOrder);
	    orderResponseDTO.setUser(user);

	    // sending order creation message to message broker
	    rabbitTemplate.convertAndSend(orderExchange,  orderCreateEvent_routingKey, orderResponseDTO);
	    
	    //once order is created deleting the user cart
	    deleteCartDetails(orderRequestDto.getCartId());
	    return orderResponseDTO;
	}
	
	//confirm order if inventory is available
	@RabbitListener(queues = "${rabbitmq.queue.inventoryAvailableEventQueue}")
	public void confirmOrderEvent(String orderId) {
		try {
			OrderResponseDTO orderResponseDTO = updateOrderStatus(orderId, OrderStatus.COMPLETED);
			rabbitTemplate.convertAndSend(orderExchange, orderConfirmedEvent_routingKey, orderResponseDTO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//unconfirm order if inventory is not available
	@RabbitListener(queues = "${rabbitmq.queue.inventoryNotAvailableEventQueue}")
	public void UnConfirmOrderEvent(String orderId) {
		try {
			OrderResponseDTO orderResponseDTO = updateOrderStatus(orderId, OrderStatus.FAILED);
			rabbitTemplate.convertAndSend(orderExchange, productNotAvailableEvent_routingKey, orderResponseDTO);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



	public CartDTO fetchCartDetails(String cartId) {
		String url = String.format("%s/%s",CART_SERVICE_URL, cartId);
		
		try {
			ResponseEntity<CartDTO> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					CartDTO.class
			);
			
			if(response.getStatusCode() == HttpStatus.OK)
				return response.getBody();
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	public String deleteCartDetails(String cartId) {
		String url = String.format("%s/%s",CART_SERVICE_URL, cartId);
		
		try {
			ResponseEntity<Object> response = restTemplate.exchange(
					url,
					HttpMethod.DELETE,
					null,
					Object.class
			);
			
			if(response.getStatusCode() == HttpStatus.NO_CONTENT)
				return "cart is deleted successfully!!";
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	@Override
	public OrderResponseDTO getOrderById(String orderId) {
		Optional<Order> orderOptional = orderRepository.findById(orderId);
		
		if(orderOptional.isEmpty())
			throw new ResourceNotFoundException("Order not found by id: " + orderId);
		
		OrderResponseDTO orderResponse = convertOrderEntityToOrderResponseDto(orderOptional.get());
		UserDTO userDetails = getUserDetailsById(orderOptional.get().getUserId());
		orderResponse.setUser(userDetails);
		return orderResponse;
	}
	
	public UserDTO getUserDetailsById(String userId) {
		//fetching details from userservice
		String url = String.format("%s/%s",USER_SERVICE_URL, userId);
		
		try {
			ResponseEntity<UserDTO> response = restTemplate.exchange(
					url,
					HttpMethod.GET,
					null,
					UserDTO.class
			);
			
			if(response.getStatusCode() == HttpStatus.OK)
				return response.getBody();
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	public OrderResponseDTO convertOrderEntityToOrderResponseDto(Order order) {
		return modelMapper.map(order, OrderResponseDTO.class);
	}
	
	public Order convertOrderResponseDtoToOrderEntity(OrderResponseDTO orderDto) {
		return modelMapper.map(orderDto, Order.class);
	}
	
	public OrderLineItemDTO convertOrderItemEntityToOrderItemDto(OrderLineItem orderLineItem) {
		return modelMapper.map(orderLineItem, OrderLineItemDTO.class);
	}
	
	public OrderLineItem convertOrderItemDtoToOrderItemEntity(OrderLineItemDTO orderLineItemDto) {
		return modelMapper.map(orderLineItemDto, OrderLineItem.class);
	}
	
	private OrderLineItem convertToOrderLineItem(CartItemDTO cartItemDto) {
//	    return modelMapper.map(cartItemDto, OrderLineItem.class);
		OrderLineItem item = new OrderLineItem();
	    item.setProductId(cartItemDto.getProductId());
	    item.setProductName(cartItemDto.getProductName());
	    item.setPrice(cartItemDto.getPrice());
	    item.setQuantity(cartItemDto.getQuantity());
	    item.setItemTotal(cartItemDto.getItemTotal());
	    // Don't set the order here directly if it's detached or not yet managed
	    return item;
	}

	@Override
	public OrderResponseDTO updateOrderStatus(String orderId, OrderStatus status) {
		// check whether order exist or not
		Optional<Order> orderOptional = orderRepository.findById(orderId);
		
		if(orderOptional.isEmpty())
			throw new ResourceNotFoundException("Order not found by id: " + orderId);
		
		Order existingOrderDetails = orderOptional.get();
		
		existingOrderDetails.setStatus(status);
		
		//update order
		Order updatedOrder = orderRepository.save(existingOrderDetails);
		OrderResponseDTO orderResponse = convertOrderEntityToOrderResponseDto(updatedOrder);
		UserDTO userDetails = getUserDetailsById(orderOptional.get().getUserId());
		orderResponse.setUser(userDetails);
		return orderResponse;
	}

}
