package modelo;

//@autor: Brayan C

import java.time.LocalDate;
import java.util.UUID;


public class TratamientoVeterinario {
    private final UUID idInterno;
    private final String identificador;
    private final String tipo;
    private final String medicamento;
    private final String dosis;
    private final LocalDate fecha;
    private final String observaciones;

    public TratamientoVeterinario(String tipo, String medicamento, String dosis, LocalDate fecha, String observaciones, String identificador) {
        this.idInterno = UUID.randomUUID();
        this.identificador = identificador;
        this.tipo = tipo;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public UUID getIdInterno() {
        return idInterno;
    }

    public String getIdentificador() {
        return identificador;
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
