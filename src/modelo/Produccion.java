package modelo;

//@autor: Brayan C

import java.time.LocalDate;


public class Produccion {
    private final LocalDate fecha;
    private Integer litrosMañana;
    private Integer litrosTarde;
    
    public Produccion(LocalDate fecha) {
        this.litrosMañana = null;
        this.litrosTarde = null;
        this.fecha = fecha;
    }

    public Produccion(LocalDate fecha, Integer litrosMañana, Integer litrosTarde) {
        this.fecha = fecha;
        this.litrosMañana = litrosMañana;
        this.litrosTarde = litrosTarde;
    }
    
    public Integer getLitrosMañana() {
        return litrosMañana;
    }

    public Integer getLitrosTarde() {
        return litrosTarde;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }

    public void setLitrosMañana(int litrosMañana) {
        if (litrosMañana >= 0) {
            this.litrosMañana = litrosMañana;
        }
    }

    public void setLitrosTarde(int litrosTarde) {
        if (litrosTarde >= 0) {
            this.litrosTarde = litrosTarde;
        }
    }
}
