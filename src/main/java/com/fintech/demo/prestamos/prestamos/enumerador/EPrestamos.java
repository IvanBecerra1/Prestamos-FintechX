package com.fintech.demo.prestamos.prestamos.enumerador;

public enum EPrestamos {
    PENDIENTE("pendiente"),
    APROBADO("aprobado"),
    RECHAZADO("rechazado");

    private String valor;

    EPrestamos(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return  this.valor = valor;
    }

}
