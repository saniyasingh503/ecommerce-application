package com.nagarro.bench.advance.assignment.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.bench.advance.assignment.order_service.model.OrderLineItem;

@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, String> {

}
