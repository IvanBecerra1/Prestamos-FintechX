package com.fintech.demo.prestamos.usuario.controlador;

import com.fintech.demo.prestamos.usuario.dto.IniciarSesionDTO;
import com.fintech.demo.prestamos.usuario.dto.RegistroDTO;
import com.fintech.demo.prestamos.usuario.excepciones.AutenticacionExcepcion;
import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import com.fintech.demo.prestamos.usuario.servicio.Impl.UsuarioServicioImp;
import com.fintech.demo.prestamos.usuario.servicio.UsuarioServicio;
import com.fintech.demo.prestamos.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/autenticacion")
@RequiredArgsConstructor
public class AutenticacionControlador {


    private final UsuarioServicioImp servicio;

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<Usuario>> registrar(@RequestBody RegistroDTO usuario) {
        try{
            Usuario registrado = this.servicio.registrar(usuario);

            ApiResponse<Usuario> apiResponse = new ApiResponse<Usuario>(
                    true,
                    "Usuario registrado existosamente",
                    registrado
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (AutenticacionExcepcion ex){
            ApiResponse<Usuario> error = new ApiResponse<>(false, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        catch (Exception ex){
            ApiResponse<Usuario> error = new ApiResponse<>(false, "Error interno del servidor.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<String> iniciarSesion(@RequestBody IniciarSesionDTO usuario){
        return ResponseEntity.ok("iniciado");
    }

}
