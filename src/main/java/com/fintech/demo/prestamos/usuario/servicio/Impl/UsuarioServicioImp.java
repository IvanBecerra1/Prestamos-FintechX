package com.fintech.demo.prestamos.usuario.servicio.Impl;

import com.fintech.demo.prestamos.usuario.dto.IniciarSesionDTO;
import com.fintech.demo.prestamos.usuario.dto.RegistroDTO;
import com.fintech.demo.prestamos.usuario.excepciones.AutenticacionExcepcion;
import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import com.fintech.demo.prestamos.usuario.repositorio.UsuarioRepositorio;
import com.fintech.demo.prestamos.usuario.servicio.UsuarioServicio;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class UsuarioServicioImp implements UsuarioServicio {


    private final UsuarioRepositorio repositorio;

    public UsuarioServicioImp(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Usuario iniciarSesion(IniciarSesionDTO usuario) throws AutenticacionExcepcion {
        Optional<Usuario> usuarioOptional = this.repositorio.findByCorreo(usuario.correo());

        if (usuarioOptional.isEmpty()) {
            throw new AutenticacionExcepcion("Usuario no encontrado");
        }

        Usuario usuarioEncontrado = usuarioOptional.get();

        if (!this.verificarClave(usuarioEncontrado.getClave(), usuario.clave())) {
            throw new AutenticacionExcepcion("Credenciales no validas");
        }

        return usuarioEncontrado;
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
