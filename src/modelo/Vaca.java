package modelo;

//@autor: Brayan C

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Vaca {
    private final UUID idInterno;
    private String identificador;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String razaPadre;
    private String razaMadre;
    private String estado;
    private Double peso;
    private String descripcion;
    private UUID idPropietario;
    private final List<TratamientoVeterinario> historialTratamientos;
    private final List<Produccion> registroProducciones;

    public Vaca(String identificador, String nombre, LocalDate fechaNacimiento, String razaPadre, String razaMadre, String estado, Double peso, String descripcion, UUID idPropietario) {
        this.idInterno = UUID.randomUUID();
        this.identificador = identificador;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.razaPadre = razaPadre;
        this.razaMadre = razaMadre;
        this.estado = estado;
        this.peso = peso;
        this.descripcion = descripcion;
        this.idPropietario = idPropietario;
        this.historialTratamientos = new ArrayList<>();
        this.registroProducciones = new ArrayList<>();
    }
    
    
    public UUID getIdInterno() {
        return idInterno;
    }

    public String getIdentificador() {
        return identificador;
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
    
    public Double getPeso() {
        return peso;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public UUID getIdPropietario() {
        return idPropietario;
    }

    public List<TratamientoVeterinario> getHistorialTratamientos() {
        return historialTratamientos;
    }
    
    public List<Produccion> getRegistroProducciones() {
        return registroProducciones;
    }

    public void setIdentificador(String identificador) {
        if (verificarTexto(identificador)) {
            this.identificador = identificador;
        }
    }

    public void setNombre(String nombre) {
        if (verificarTexto(nombre)) {
            this.nombre = nombre;
        }
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento != null) {
            this.fechaNacimiento = fechaNacimiento;
        }
    }

    public void setRazaPadre(String razaPadre) {
        if (verificarTexto(razaPadre)) {
            this.razaPadre = razaPadre;
        }
    }

    public void setRazaMadre(String razaMadre) {
        if (verificarTexto(razaMadre)) {
            this.razaMadre = razaMadre;
        }
    }

    public void setEstado(String estado) {
        if (verificarTexto(estado)) {
            this.estado = estado;
        }
    }

    public void setPeso(Double peso) {
        if (peso != null && peso > 0) {
           this.peso = peso; 
        }
    }

    public void setDescripcion(String descripcion) {
        if (verificarTexto(descripcion)) {
            this.descripcion = descripcion;
        }
    }

    public void setPropietario(UUID idPropietario) {
        if (idPropietario != null) {
            this.idPropietario = idPropietario;
        }
    }
    
    @Override
    public String toString() {
        return identificador + " - " + nombre;
    }
    
    private boolean verificarTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}
