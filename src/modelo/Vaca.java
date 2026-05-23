package modelo;

//@autor: Brayan C

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Vaca {
    private String codigoInterno;
    private final String nombre;
    private final LocalDate fechaNacimiento;
    private final String razaPadre;
    private final String razaMadre;
    private String estado;
    private String descripcion;
    private String numeroIdentificador;
    private Double peso;
    private String dueño;
    private List<TratamientoVeterinario> historialTratamientos;
    private List<RegistroProduccion> registroProducciones;

    public Vaca(String nombre, LocalDate fechaNacimiento, String razaPadre, String razaMadre, String estado,String descripcion, String numeroIdentificador, Double peso, String dueño) {
        this.historialTratamientos = new ArrayList<>();
        this.registroProducciones = new ArrayList<>();
        this.codigoInterno = "0";
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.razaPadre = razaPadre;
        this.razaMadre = razaMadre;
        this.estado = estado;
        this.descripcion = descripcion;
        this.numeroIdentificador = numeroIdentificador;
        this.peso = peso;
        this.dueño = dueño;
    }

    public Vaca(String codigoInterno,String nombre, LocalDate fechaNacimiento, String razaPadre, String razaMadre, String estado,String descripcion, String numeroIdentificador, Double peso, String dueño) {
        this(nombre, fechaNacimiento, razaPadre, razaMadre, estado, descripcion, numeroIdentificador, peso, dueño);
        this.codigoInterno = codigoInterno;
        
    }
    public String getCodigoInterno() {
        return codigoInterno;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getRazaPadre() {
        return razaPadre;
    }

    public String getRazaMadre() {
        return razaMadre;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNumeroIdentificador() {
        return numeroIdentificador;
    }

    public Double getPeso() {
        return peso;
    }

    public String getDueño() {
        return dueño;
    }

    public List<TratamientoVeterinario> getHistorialTratamientos() {
        return historialTratamientos;
    }
    
    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public void setHistorialTratamientos(TratamientoVeterinario tratamiento) {
        this.historialTratamientos.add(tratamiento);
    }

    public List<RegistroProduccion> getRegistroProducciones() {
        return registroProducciones;
    }

    @Override
    public String toString() {
        return codigoInterno + " - " + nombre;
    }
    
    
}
