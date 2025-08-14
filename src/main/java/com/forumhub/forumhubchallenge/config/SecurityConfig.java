package com.forumhub.forumhubchallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desabilita CSRF (necessário para APIs REST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // Permite TODAS as requisições sem autenticação
                );

        return http.build();
    }
}