package org.tudai.entregable3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarreraDTO {
    private String nombre;

    public CarreraDTO(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Carrera: " +
                "nombre: " + nombre;
    }
}
