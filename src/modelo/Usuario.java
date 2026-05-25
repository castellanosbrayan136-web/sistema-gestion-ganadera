package modelo;

//@autor: Brayan C

import java.util.UUID;


public class Usuario {
    private final UUID idInterno;
    private final String nombresYApellidos;
    private final String nombreDeUsuario;
    private final String correoElectronico;
    private final String nombreDeLafinca;
    private final Ubicacion ubicacion;
    private final String contraseña;

    public Usuario(String nombresYApellidos, String nombreDeUsuario, String correoElectronico, String nombreDeLafinca, Ubicacion ubicacion, String contraseña) {
        this.idInterno = UUID.randomUUID();
        this.nombresYApellidos = nombresYApellidos;
        this.nombreDeUsuario = nombreDeUsuario;
        this.correoElectronico = correoElectronico;
        this.nombreDeLafinca = nombreDeLafinca;
        this.ubicacion = ubicacion;
        this.contraseña = contraseña;
    }

    public UUID getIdInterno() {
        return idInterno;
    }
    
    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getNombresYApellidos() {
        return nombresYApellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombreDeLafinca() {
        return nombreDeLafinca;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }
}
