package modelo;

//@autor: Brayan C

import java.time.LocalDate;


public class TratamientoVeterinario {
    private String id;
    private String tipo;
    private String medicamento;
    private String dosis;
    private LocalDate fecha;
    private String observaciones;

    public TratamientoVeterinario(String tipo, String medicamento, String dosis, LocalDate fecha, String observaciones, String id) {
        this.id = id;
        this.tipo = tipo;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public String getId() {
        return id;
    }
    
    public String getTipo() {
        return tipo;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }
}
