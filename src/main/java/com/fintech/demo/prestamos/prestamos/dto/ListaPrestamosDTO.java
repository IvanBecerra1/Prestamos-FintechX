package com.fintech.demo.prestamos.prestamos.dto;

import com.fintech.demo.prestamos.prestamos.enumerador.EPrestamos;

import java.util.Date;

public record ListaPrestamosDTO(
        Long monto,
        Date fechaDevolucion,
        Date fechaSolicitud,
        EPrestamos estado,
        Long usuarioId
) {
}
