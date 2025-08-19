package com.fintech.demo.prestamos.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;

@Configuration
@EnableWebSecurity
public class SeguridadWebConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(
                        req-> req.requestMatchers("/autenticacion/**").permitAll() // solo la parte autenticacion permitimos acceso
                                .anyRequest().authenticated()
                )
                .build();
    }

}
