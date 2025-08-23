package com.fintech.demo.prestamos.prestamos.dto;


import com.fintech.demo.prestamos.prestamos.enumerador.EPrestamos;

import java.util.Date;

public record PrestamoDTO (
        Long monto,
        Date fechaDevolucion,
        Long usuarioId) {
}
