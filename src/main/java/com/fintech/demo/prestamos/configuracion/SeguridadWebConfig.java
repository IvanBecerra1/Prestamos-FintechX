package com.fintech.demo.prestamos.configuracion;

import com.fintech.demo.prestamos.seguridad.filtro.JWTFiltro;
import com.fintech.demo.prestamos.usuario.servicio.Impl.UsuarioServicioImp;
import com.fintech.demo.prestamos.usuario.servicio.Impl.userDetalisServiceImpl;
import com.fintech.demo.prestamos.usuario.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SeguridadWebConfig {

    private final JWTFiltro jwtFiltro;
    private final userDetalisServiceImpl userServicio;

    private final AccessDeniedHandler accessDeniedHandler;
    private final AutenticacionTokenEntryPoint autenticacionTokenEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(
                        req-> req.requestMatchers("/autenticacion/**").permitAll() // solo la parte autenticacion permitimos acceso
                                .anyRequest().authenticated()
                )
                .exceptionHandling(ex->ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(autenticacionTokenEntryPoint)
                )
                .httpBasic(Customizer.withDefaults()) // Agrega esto
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.jwtFiltro, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
