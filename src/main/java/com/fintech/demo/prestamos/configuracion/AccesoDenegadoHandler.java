package com.fintech.demo.prestamos.configuracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementacion para el manejo de informacion
 * en Endpoints no autorizados
 */
@Component
public class AccesoDenegadoHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // es el codigo 403
        response.setContentType("application/json");

        Map<String, Object> map = new HashMap<>();
        map.put("exito", false);
        map.put("mensaje", "Acceso no permitido");
        map.put("path", request.getRequestURI());

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().println(mapper.writeValueAsString(map));
    }

}
