package modelo;

//@autor: Brayan C

import java.time.LocalDate;
import java.util.UUID;


public class TratamientoVeterinario {
    private final UUID idInterno;
    private String identificador;
    private String tipo;
    private String medicamento;
    private String dosis;
    private LocalDate fecha;
    private String observaciones;

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

    public void setIdentificador(String identificador) {
        if (verificarTexto(identificador)) {
            this.identificador = identificador;
        }
    }

    public void setTipo(String tipo) {
        if (verificarTexto(tipo)) {
            this.tipo = tipo;
        }
    }

    public void setMedicamento(String medicamento) {
        if (verificarTexto(medicamento)) {
            this.medicamento = medicamento;
        }
    }

    public void setDosis(String dosis) {
        if (verificarTexto(dosis)) {
            this.dosis = dosis;
        }
    }

    public void setFecha(LocalDate fecha) {
        if (fecha != null) {
            this.fecha = fecha;
        }
    }

    public void setObservaciones(String observaciones) {
        if (verificarTexto(observaciones)) {
            this.observaciones = observaciones;
        }
    }

    private boolean verificarTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}
