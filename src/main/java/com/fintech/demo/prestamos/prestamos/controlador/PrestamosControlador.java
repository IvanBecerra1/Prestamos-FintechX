package com.fintech.demo.prestamos.prestamos.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
public class PrestamosControlador {

    @PostMapping("/solicitar")
    public String solicitarPrestamo(){
        return "";
    }

    @GetMapping("")
    public String misPrestamo(){
        return "";
    }

    /**
     * Cancelar el prestamo del usuario
     * @param id
     * @return
     */
    @PostMapping("/cancelar/{id}")
    public String cancelarPrestamo(@PathVariable Integer id){
        return "";
    }

    /**
     * Listar los prestamos de un usuario
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public String listarPrestamos(@PathVariable Integer id){
        return "";
    }


    /**
     * Accion sobre el estado del prestamo.
     * Solo rol Admin
     * @param id : id del prestamo
     * @return
     */
    @PutMapping("/accion/{id}")
    public String accionPrestamos(@PathVariable Integer id){
        return "";
    }

    /**
     * Eliminar prestamo del BD
     * Solo rol admin
     * @param id
     * @return
     */
    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id){
        return "";
    }


}
