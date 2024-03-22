package com.microservice.ordersservice.events;

import com.microservice.ordersservice.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
