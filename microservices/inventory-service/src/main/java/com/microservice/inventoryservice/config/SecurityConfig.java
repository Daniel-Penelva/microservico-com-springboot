package com.microservice.inventoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity  // é uma configuração de segurança e que o Spring Security deve ser habilitado para a aplicação.
public class SecurityConfig {

    /* Este método cria e configura um filtro de segurança para a aplicação. Ele aceita um objeto HttpSecurity como argumento, que é usado para
    definir as regras de segurança para as solicitações HTTP.*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()                  // Esta linha desabilita a proteção CSRF (Cross-Site Request Forgery).
                .securityMatcher("/**")   // Especifica que todas as solicitações devem ser tratadas pelo filtro de segurança.
                .authorizeHttpRequests()          // Indica que as solicitações devem ser autorizadas antes de serem processadas.
                .anyRequest().authenticated()     // Exige que todas as solicitações sejam autenticadas.
                .and()
                .oauth2ResourceServer(configure -> configure.jwt().jwtAuthenticationConverter(jwtAuthConverter())); //  configura o servidor de recursos OAuth 2.0 para validar tokens JWT

        return http.build();
    }

    /* Este método cria um conversor para converter um token JWT em um token de autenticação do Spring Security. O JwtAuthenticationConverter
    personalizado definido aqui utiliza um KeycloakRealmRoleConverter para mapear as funções (roles) do Keycloak em autoridades do Spring Security.*/
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthConverter(){
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return converter;
    }

} // fim da classe SecurityConfig

/* Esta classe é uma implementação do Converter do Spring Security, que converte os dados do token JWT do Keycloak em autoridades do Spring Security.
Ele extrai as roles do campo realm_access do token JWT e converte cada uma delas em uma autoridade do Spring Security, prefixando-as com "ROLE_" e
criando instâncias de SimpleGrantedAuthority.b*/
@SuppressWarnings("unchecked")
class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>>{
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        if (jwt.getClaims() == null){
            return List.of();
        }

        final Map<String, List<String>> realmAccess = (Map<String, List<String>>) jwt.getClaims().get("realm_access");

        return realmAccess.get("roles").stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
