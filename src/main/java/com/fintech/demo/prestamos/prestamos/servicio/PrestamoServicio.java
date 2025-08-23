package com.fintech.demo.prestamos.prestamos.servicio;

import com.fintech.demo.prestamos.prestamos.dto.PrestamoDTO;
import com.fintech.demo.prestamos.prestamos.excepcion.PrestamoExcepcion;
import com.fintech.demo.prestamos.prestamos.modelo.Prestamos;

public interface PrestamoServicio {

    public Prestamos solicitarPrestamo(PrestamoDTO prestamo) throws Exception;

    public void validarPrestamo(PrestamoDTO prestamo) throws Exception;
}
