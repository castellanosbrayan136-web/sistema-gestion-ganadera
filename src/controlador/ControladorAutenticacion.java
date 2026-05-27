package controlador;

//@autor: Brayan C

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ScreenManager;
import vista.VistaAutenticacion;


public class ControladorAutenticacion implements ActionListener{
    private final VistaAutenticacion vistaAutenticacion;

    public ControladorAutenticacion(VistaAutenticacion vistaAutenticacion) {
        this.vistaAutenticacion = vistaAutenticacion;
        ScreenManager.cambiarAPanelIniciarSesion(vistaAutenticacion);
        vistaAutenticacion.getBtnIniciarSesion().setBackground(new Color(70, 90, 120));
        activarBotones();
    }
    
    public void activarBotones() {
        vistaAutenticacion.getBtnIniciarSesion().addActionListener(this);
        vistaAutenticacion.getBtnCrearCuenta().addActionListener(this);
        vistaAutenticacion.getBtnSalir().addActionListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaAutenticacion.getBtnIniciarSesion()) {
            reiniciarColorDeBoton();
            vistaAutenticacion.getBtnIniciarSesion().setBackground(new Color(70, 90, 120));
            ScreenManager.cambiarAPanelIniciarSesion(vistaAutenticacion);
        } else if (e.getSource() == vistaAutenticacion.getBtnCrearCuenta()) {
            reiniciarColorDeBoton();
            vistaAutenticacion.getBtnCrearCuenta().setBackground(new Color(70, 90, 120));
            ScreenManager.cambiarAPanelCrearCuenta(vistaAutenticacion);
        } else if (e.getSource() == vistaAutenticacion.getBtnSalir()) {
            System.exit(0);
        }
    }
    
    public void reiniciarColorDeBoton() {
        Color azulGris = new Color(39, 51, 69);
        vistaAutenticacion.getBtnCrearCuenta().setBackground(azulGris);
        vistaAutenticacion.getBtnIniciarSesion().setBackground(azulGris);
    }
}
