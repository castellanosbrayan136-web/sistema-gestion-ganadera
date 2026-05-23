package modelo;

//@autor: Brayan C

import java.time.LocalDate;


public class RegistroProduccion {
    private String id;
    private int cantidadLitros;
    private String jornada;
    private LocalDate fecha;

    public RegistroProduccion(int cantidadLitros, String jornada, LocalDate fecha) {
        this.id = "0";
        this.cantidadLitros = cantidadLitros;
        this.jornada = jornada;
        this.fecha = fecha;
    }
    
    public String getId() {
        return id;
    }

    public int getCantidadLitros() {
        return cantidadLitros;
    }

    public String getJornada() {
        return jornada;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
