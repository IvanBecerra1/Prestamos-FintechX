package com.fintech.demo.prestamos.util;

import com.fintech.demo.prestamos.usuario.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    private Boolean exito;
    private String mensaje;
    private T datos;
    private Instant fecha;
    private List<String> errores;
    private String token;

    public ApiResponse(Boolean exito, String mensaje) {
        this(exito, mensaje, null); // llamo al otro constructor
    }
    public ApiResponse(Boolean exito, String mensaje, T datos) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.datos = datos;
        this.fecha = Instant.now();
        this.errores = new ArrayList<>();
    }
    public ApiResponse(Boolean exito, String mensaje, T datos, String token) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.datos = datos;
        this.fecha = Instant.now();
        this.errores = new ArrayList<>();
        this.token = token;
    }


    public ApiResponse(Boolean exito, String mensaje, T data, List<String> errores) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.datos = data;
        this.errores = errores != null ? errores : new ArrayList<>();
        this.fecha = Instant.now();
    }

    // Estaticos, Factory Metods
    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<T>(true, "Operacion exitosa", data);
    }
    public static <T> ApiResponse<T> success(T data, String mensaje){
        return new ApiResponse<T>(true, mensaje, data);
    }
    public static <T> ApiResponse<T> error(String mensaje){
        return new ApiResponse<T>(false, mensaje, null);
    }
    public static <T> ApiResponse<T> error(String mensaje, List<String> errors){
        return new ApiResponse<T>(false, mensaje,null, errors);
    }

}
