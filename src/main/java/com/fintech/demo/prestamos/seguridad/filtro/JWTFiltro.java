package com.fintech.demo.prestamos.seguridad.filtro;

import com.fintech.demo.prestamos.seguridad.JWT;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFiltro extends OncePerRequestFilter {
    private final JWT jwt;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // seguimos al siguiente filtro.
            return;
        }

        String token = authorization.substring(7); // obtener el token
        String subject = this.jwt.obtenerClaim(token, Claims::getSubject);

        if (subject != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(subject);

            if (this.jwt.validarToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken autenticacionToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(autenticacionToken); // autenticacion con el TOKEN

            }
        }
        filterChain.doFilter(request, response); // pasamos al siguiente filtro.
    }
}
