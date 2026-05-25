package modelo;

//@autor: Brayan C

import java.time.LocalDate;


public class ProduccionDia {
    private final LocalDate fecha;
    private final Integer litrosTotales;

    public ProduccionDia(LocalDate fecha, Integer litrosTotales) {
        this.fecha = fecha;
        this.litrosTotales = litrosTotales;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getLitrosTotales() {
        return litrosTotales;
    }
}
