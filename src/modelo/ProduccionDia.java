package modelo;

//@autor: Brayan C

import java.time.LocalDate;


public class ProduccionDia {
    private final LocalDate fecha;
    private Integer litrosMañana;
    private Integer litrosTarde;
    private Integer litrosTotales;
    

    public ProduccionDia(LocalDate fecha, Integer litrosTotales) {
        this.fecha = fecha;
        this.litrosTotales = litrosTotales;
    }

    public ProduccionDia(LocalDate fecha, Integer litrosMañana, Integer litrosTarde, Integer litrosTotales) {
        this.fecha = fecha;
        this.litrosMañana = litrosMañana;
        this.litrosTarde = litrosTarde;
        this.litrosTotales = litrosTotales;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }

    public Integer getLitrosTotales() {
        return litrosTotales;
    }

    public Integer getLitrosMañana() {
        return litrosMañana;
    }

    public Integer getLitrosTarde() {
        return litrosTarde;
    }

    public void setLitrosMañana(Integer litrosMañana) {
        this.litrosMañana = litrosMañana;
    }

    public void setLitrosTarde(Integer litrosTarde) {
        this.litrosTarde = litrosTarde;
    }

    public void setLitrosTotales(Integer litrosTotales) {
        this.litrosTotales = litrosTotales;
    }
    
    
}
