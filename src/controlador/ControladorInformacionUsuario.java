package controlador;

//@autor: Brayan C

import modelo.Usuario;
import vista.DialogInformacionUsuario;


public class ControladorInformacionUsuario {
    DialogInformacionUsuario dialogInformacionUsuario;
    Usuario usuario;

    public ControladorInformacionUsuario(DialogInformacionUsuario dialogInformacionUsuario, Usuario usuario) {
        this.dialogInformacionUsuario = dialogInformacionUsuario;
        this.usuario = usuario;
        llenarDatos();
    }
    
    public void llenarDatos() {
        dialogInformacionUsuario.setLblNombresApellidos(usuario.getNombresYApellidos());
        dialogInformacionUsuario.setLblUsuario(usuario.getNombreDeUsuario());
        dialogInformacionUsuario.setLblCorreo(usuario.getCorreoElectronico());
        dialogInformacionUsuario.setLblFinca(usuario.getNombreDeLafinca());
        dialogInformacionUsuario.setLblDepartamento(usuario.getUbicacion().getDepartamento());
        dialogInformacionUsuario.setLblMunicipio(usuario.getUbicacion().getMunicipio());
        dialogInformacionUsuario.setLblVereda(usuario.getUbicacion().getVereda());
    }
}
