package controlador;

//@autor: Brayan C

import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.DialogInformacionUsuario;


public class ControladorInformacionUsuario {
    DialogInformacionUsuario dialogInformacionUsuario;
    Usuario usuario;
    VacaDAO vacaDAO;

    public ControladorInformacionUsuario(DialogInformacionUsuario dialogInformacionUsuario, Usuario usuario, VacaDAO vacaDAO) {
        this.dialogInformacionUsuario = dialogInformacionUsuario;
        this.usuario = usuario;
        this.vacaDAO = vacaDAO;
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
        dialogInformacionUsuario.setLblCabezasDeGanado(String.valueOf(numeroDeCabezasDeGanado()));
    }
    
    public int numeroDeCabezasDeGanado() {
        int contador = 0;
    
        for (Vaca vaca : vacaDAO.retornarListaVacasPorUsuario(usuario.getNombreDeUsuario())) {
            contador++;
        }
        return contador;
    }
}
