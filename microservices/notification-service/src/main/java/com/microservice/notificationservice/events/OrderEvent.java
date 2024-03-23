package com.microservice.notificationservice.events;

import com.microservice.notificationservice.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
