package com.microservice.notificationservice.listeners;

import com.microservice.notificationservice.events.OrderEvent;
import com.microservice.notificationservice.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {

    /* OBS. A anotação @KafkaListener indica que o método handleOrdersNotifications é um ouvinte (consumer) de mensagens do tópico "orders-topic" no Kafka. Quando
    uma mensagem é publicada nesse tópico, o método é acionado para processar a mensagem. */

    /* Este método é responsável por lidar com as notificações de pedidos recebidas do tópico Kafka. Ele recebe uma mensagem como uma string e a
    desserializa em um objeto OrderEvent usando o método fromJson da classe JsonUtils. Ou seja, esse método gerencia as mensagens que são publicadas no
    topico orders-topic que serão consumidas para serem processadas as mensagens. */
    @KafkaListener(topics = "orders-topic")
    public void handleOrdersNotifications(String message) {
        var orderEvent = JsonUtils.fromJson(message, OrderEvent.class);

        //Enviar e-mail para o cliente, enviar SMS para o cliente, etc.
        //Notificar outro serviço...

        log.info("Order {} event received for order : {} with {} items", orderEvent.orderStatus(), orderEvent.orderNumber(), orderEvent.itemsCount());
    }
}

/* Em resumo, esse código define um ouvinte (consumer kafka) de eventos Kafka que processa mensagens do tópico "orders-topic", desserializa as mensagens
em objetos OrderEvent, e registra informações relevantes sobre o evento do pedido recebido. Além disso, ele fornece um ponto de entrada para executar
ações com base nos eventos recebidos, como notificar clientes ou outros serviços. */
