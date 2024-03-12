package com.microservice.ordersservice.dtos;

public record OrderItemResponse(Long id, String sku, Double price, Long quatity) {
}
