package com.fintech.demo.prestamos.usuario.dto;

public record RegistroDTO (
        String nombre,
        String apellido,
        String rol,

        String correo,
        String clave
) {
}
