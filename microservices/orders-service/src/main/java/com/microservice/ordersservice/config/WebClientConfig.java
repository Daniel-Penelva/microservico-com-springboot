package com.microservice.ordersservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /* Este método cria e retorna um objeto do tipo WebClient.Builder, para construir instâncias de WebClient, que são usadas para fazer requisições
    HTTP em aplicações web.*/
    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }
}
