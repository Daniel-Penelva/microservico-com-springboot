package com.microservice.ordersservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /* Este método cria e retorna um objeto do tipo WebClient.Builder, para construir instâncias de WebClient, que são usadas para fazer requisições
    HTTP em aplicações web.*/
    @Bean
    @LoadBalanced  // para ativar o balanceamento de carga automático ao fazer a solicitação HTTP para serviços cadastrados em um servidor Eureka
    public WebClient.Builder webClient() {
        return WebClient.builder().filter(new ServletBearerExchangeFilterFunction());
    }
}
