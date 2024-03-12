package com.microservice.ordersservice.repositories;

import com.microservice.ordersservice.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
