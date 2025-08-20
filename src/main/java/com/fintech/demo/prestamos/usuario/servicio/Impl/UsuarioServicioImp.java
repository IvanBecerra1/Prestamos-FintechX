package com.fintech.demo.prestamos.usuario.servicio.Impl;

import com.fintech.demo.prestamos.usuario.dto.IniciarSesionDTO;
import com.fintech.demo.prestamos.usuario.dto.RegistroDTO;
import com.fintech.demo.prestamos.usuario.excepciones.AutenticacionExcepcion;
import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import com.fintech.demo.prestamos.usuario.repositorio.UsuarioRepositorio;
import com.fintech.demo.prestamos.usuario.servicio.UsuarioServicio;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UsuarioServicioImp implements UsuarioServicio {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepositorio repositorio;

    public UsuarioServicioImp(UsuarioRepositorio repositorio,  AuthenticationManager auth) {
        this.repositorio = repositorio;
        this.authenticationManager = auth;
    }

    @Override
    public Usuario iniciarSesion(IniciarSesionDTO usuario) throws AutenticacionExcepcion {
        try {

            // Dejamos la responsabilidad a gestion de Spring boot el inicio de sesion
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.correo(), usuario.clave())
            );

            UserDetails user = (UserDetails) authentication.getPrincipal();

            return this.repositorio.findByCorreo(user.getUsername())
                    .orElseThrow(() -> new AuthenticationException("Usuario no encontrado"){

                    });
        }
         catch (Exception e) {
            throw new AutenticacionExcepcion("Credenciales no validos");
         }

    }

    @Override
    public Usuario registrar(RegistroDTO usuarioRegistro) throws  AutenticacionExcepcion {
        Usuario usuario = new Usuario();
        this.validaciones(usuarioRegistro);

        usuario.setCorreo(usuarioRegistro.correo());
        usuario.setNombre(usuarioRegistro.nombre());
        usuario.setApellido(usuarioRegistro.apellido());
        usuario.setRol(usuarioRegistro.rol());
        usuario.setClave(this.encriptarClave(usuarioRegistro.clave()));


        return this.repositorio.save(usuario);
    }

    @Override
    public Boolean estaRegistrado(Usuario usuario) {
        return null;
    }

    @Override
    public void validaciones(RegistroDTO usuario) throws AutenticacionExcepcion {
        if (this.repositorio.existsByCorreo(usuario.correo())) {
            throw new AutenticacionExcepcion("El correo ya existe");
        }

        if (usuario.correo() == null || usuario.correo().isBlank()) {
            throw new AutenticacionExcepcion("El correo es obligatorio");
        }

        if (usuario.clave() == null || usuario.clave().length() < 6) {
            throw new AutenticacionExcepcion("La clave debe tener minimo 6 caracteres");
        }
    }


    private String encriptarClave(String clave) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(clave);
    }

    private Boolean verificarClave(String clave, String claveUsuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // {Cadena Clave, Encode Clave}
        return encoder.matches(claveUsuario, clave);
    }
}
