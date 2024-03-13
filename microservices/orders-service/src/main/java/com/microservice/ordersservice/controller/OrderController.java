package com.microservice.ordersservice.controller;

import com.microservice.ordersservice.model.dtos.OrderRequest;
import com.microservice.ordersservice.model.dtos.OrderResponse;
import com.microservice.ordersservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // http://localhost:8082/api/order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        this.orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }


    // http://localhost:8082/api/order
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders() {
        return this.orderService.getAllOrders();
    }
}
