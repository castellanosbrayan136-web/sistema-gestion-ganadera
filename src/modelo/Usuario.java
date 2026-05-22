package modelo;

//@autor: Brayan C

public class Usuario {
    private String nombresYApellidos;
    private String nombreDeUsuario;
    private String correoElectronico;
    private String nombreDeLafinca;
    private Ubicacion ubicacion;
    private String contraseña;

    public Usuario(String nombresYApellidos, String nombreDeUsuario, String correoElectronico, String nombreDeLafinca, Ubicacion ubicacion, String contraseña) {
        this.nombresYApellidos = nombresYApellidos;
        this.nombreDeUsuario = nombreDeUsuario;
        this.correoElectronico = correoElectronico;
        this.nombreDeLafinca = nombreDeLafinca;
        this.ubicacion = ubicacion;
        this.contraseña = contraseña;
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
