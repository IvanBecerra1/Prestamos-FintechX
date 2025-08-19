package com.fintech.demo.prestamos.usuario.modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( length = 50, nullable = false)
    private String nombre;

    @Column( length = 50,nullable = false)
    private String apellido;

    @Column( length = 50, unique = false)
    private String correo;

    @Column( nullable = false)
    private String clave;

    @Column( length = 50, nullable = false)
    private String rol;

}
