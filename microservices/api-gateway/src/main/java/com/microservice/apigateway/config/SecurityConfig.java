package com.microservice.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange().anyExchange().authenticated()
                .and()
                .oauth2Login();

        return http.build();
    }
}

/*
Explicação linha por linha:

1. `@Bean`: Esta anotação indica que o método retorna um bean gerenciado pelo Spring.

2. `public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {`: Este método cria e configura um filtro de segurança para a
aplicação. Ele aceita um objeto `ServerHttpSecurity` como argumento, que é usado para definir as regras de segurança para as solicitações HTTP.

3. `.csrf().disable()`: Esta linha desabilita a proteção CSRF (Cross-Site Request Forgery). CSRF é um ataque em que um invasor engana o navegador do
usuário para realizar ações indesejadas em um aplicativo no qual o usuário está autenticado. Desabilitar o CSRF é comum em aplicativos que fornecem
autenticação por meio de tokens, como OAuth 2.0.

4. `.authorizeExchange().anyExchange().authenticated()`: Esta linha configura as regras de autorização para todas as trocas (exchanges) HTTP.
`anyExchange()` especifica que todas as solicitações devem ser autenticadas (`authenticated()`). Isso significa que qualquer solicitação feita ao
aplicativo exigirá autenticação.

5. `.and()`: Este método permite concatenar configurações.

6. `.oauth2Login()`: Esta linha habilita o suporte para OAuth 2.0 para autenticação do usuário. O `oauth2Login()` configura o fluxo de login OAuth 2.0,
que permite que os usuários façam login em um aplicativo usando provedores de identidade compatíveis com OAuth 2.0, como Google, Facebook ou Keycloak.

7. `return http.build();`: Este método constrói a configuração de segurança definida anteriormente e a retorna como um bean gerenciado pelo Spring.

Em resumo, este script configura um filtro de segurança para exigir autenticação para todas as solicitações HTTP e habilita o suporte para OAuth 2.0
para autenticação do usuário. Ele é adequado para aplicativos WebFlux que precisam de autenticação e proteção CSRF desativada.*/