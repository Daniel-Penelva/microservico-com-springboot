package com.microservice.ordersservice.dtos;

import java.util.List;

public record OrderResponse(Long id, String orderNumber, List<OrderItemResponse> orderItems) {
}
