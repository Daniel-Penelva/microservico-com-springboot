package com.microservice.ordersservice.service;

import com.microservice.ordersservice.model.dtos.*;
import com.microservice.ordersservice.model.entities.Order;
import com.microservice.ordersservice.model.entities.OrderItems;
import com.microservice.ordersservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {

        /* Este método é responsável por realizar o processo de colocação de um pedido. Ele primeiro verifica o estoque fazendo uma requisição POST
        para um serviço de inventário usando o WebClient, e com base na resposta, salva o pedido no banco de dados através do orderRepository.*/
        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();

        if (result != null && !result.hasErrors()) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream()
                    .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                    .toList());

            this.orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Alguns dos produtos não estão em estoque");
        }
    }

    /* mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order): Método privado utilizado para mapear um objeto OrderItemRequest
    para um objeto OrderItems, que representa um item de pedido.*/
    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }

    /* getAllOrders(): Método privado que retorna uma lista de todos os pedidos existentes no banco de dados, mapeando-os para objetos OrderResponse. */
    List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    /* mapToOrderResponse(Order order): Método privado utilizado para mapear um objeto Order para um objeto OrderResponse, que contém informações
    resumidas sobre o pedido. */
    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber(), order.getOrderItems().stream().map(this::mapToOrderItemRequest).toList());
    }

    /* mapToOrderItemRequest(OrderItems orderItems): Método privado utilizado para mapear um objeto OrderItems para um objeto OrderItemResponse, que
    contém informações sobre um item de pedido. */
    private OrderItemResponse mapToOrderItemRequest(OrderItems orderItems) {
        return new OrderItemResponse(orderItems.getId(), orderItems.getSku(), orderItems.getPrice(), orderItems.getQuantity());
    }

}
