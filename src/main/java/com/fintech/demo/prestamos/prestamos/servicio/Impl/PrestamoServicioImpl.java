package com.fintech.demo.prestamos.prestamos.servicio.Impl;

import com.fintech.demo.prestamos.prestamos.dto.PrestamoDTO;
import com.fintech.demo.prestamos.prestamos.enumerador.EPrestamos;
import com.fintech.demo.prestamos.prestamos.excepcion.PrestamoExcepcion;
import com.fintech.demo.prestamos.prestamos.modelo.Prestamos;
import com.fintech.demo.prestamos.prestamos.repositorio.PrestamoRepositorio;
import com.fintech.demo.prestamos.prestamos.servicio.PrestamoServicio;
import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PrestamoServicioImpl implements PrestamoServicio {

    private final PrestamoRepositorio repositorio;


    @Override
    public Prestamos solicitarPrestamo(PrestamoDTO prestamo) throws Exception {
        this.validarPrestamo(prestamo);

        Prestamos crearPrestamo = new Prestamos();

        crearPrestamo.setEstado(EPrestamos.PENDIENTE);
        crearPrestamo.setFechaSolicitud(new Date());
        crearPrestamo.setFechaDevolucion(prestamo.fechaDevolucion());
        crearPrestamo.setMonto(prestamo.monto());
        crearPrestamo.setUsuario(new Usuario(prestamo.usuarioId()));
        return this.repositorio.save(crearPrestamo);
    }

    @Override
    public void validarPrestamo(PrestamoDTO prestamo) throws Exception {
        Date fecha = new Date();

        if (prestamo == null) {
            throw new PrestamoExcepcion("Objeto Prestamo invalido");
        }

        if (prestamo.usuarioId() == null) {
            throw new PrestamoExcepcion("No hay usuario relacionado en el prestamo");
        }

        if (prestamo.fechaDevolucion() == null || prestamo.fechaDevolucion().before(fecha)) {
            throw new PrestamoExcepcion("Fecha de solicitud no valido");
        }

        if (prestamo.monto() == null || prestamo.monto() <= 1000) {
            throw new PrestamoExcepcion("Monto minimo 1.000$");
        }

    }
}
