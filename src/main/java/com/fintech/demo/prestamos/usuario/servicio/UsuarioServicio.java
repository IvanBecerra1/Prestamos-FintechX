package com.fintech.demo.prestamos.usuario.servicio;

import com.fintech.demo.prestamos.usuario.dto.IniciarSesionDTO;
import com.fintech.demo.prestamos.usuario.dto.RegistroDTO;
import com.fintech.demo.prestamos.usuario.excepciones.AutenticacionExcepcion;
import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import org.springframework.web.bind.annotation.RequestBody;

public interface UsuarioServicio {

    public Usuario registrar( RegistroDTO usuario) throws  AutenticacionExcepcion;

    public Boolean estaRegistrado( Usuario usuario);

    public Usuario iniciarSesion(IniciarSesionDTO usuario) throws AutenticacionExcepcion;

    public void validaciones(RegistroDTO usuario) throws AutenticacionExcepcion;

}
