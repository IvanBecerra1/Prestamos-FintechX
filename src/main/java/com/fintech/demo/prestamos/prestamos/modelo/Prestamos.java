package com.fintech.demo.prestamos.prestamos.modelo;

import com.fintech.demo.prestamos.prestamos.enumerador.EPrestamos;
import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "prestamos")
@NoArgsConstructor
@AllArgsConstructor
public class Prestamos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long monto;


    @Column(name = "fecha_solicitud",unique = true, nullable = false)
    private Date fechaSolicitud;

    @Column(name = "fecha_devolucion",unique = true, nullable = false)
    private Date fechaDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPrestamos estado;

    // Muchos prestamos pertenecen a un mismo usuario'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
