package com.fintech.demo.prestamos.usuario.servicio.Impl;

import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import com.fintech.demo.prestamos.usuario.repositorio.UsuarioRepositorio;
import com.fintech.demo.prestamos.usuario.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class userDetalisServiceImpl implements UserDetailsService {

    private final UsuarioRepositorio repositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = this.repositorio.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro el usuario"));

        String rolePrefix = "ROLE_" + user.getRol().toUpperCase();

        return new User(
                user.getCorreo(),
                user.getClave(),
                Collections.singletonList(new SimpleGrantedAuthority(rolePrefix)));
    }
}
