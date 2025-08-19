package com.fintech.demo.prestamos.usuario.repositorio;

import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    // Metodos de crud por JPA

    //Metodos propios basados en nombre de las propiedad de la clase
    Optional<Usuario> findByCorreo(String correo);

    Boolean existsByCorreo(String correo);
}
