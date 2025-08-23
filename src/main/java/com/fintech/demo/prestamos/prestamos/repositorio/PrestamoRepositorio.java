package com.fintech.demo.prestamos.prestamos.repositorio;

import com.fintech.demo.prestamos.prestamos.modelo.Prestamos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamos, Integer> {

}

