package controlador;

//@autor: Brayan C

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Usuario;
import vista.PanelSanidad;
import vista.ScreenManager;


public class ControladorSanidad implements ActionListener {
    private PanelSanidad panelSanidad;
    private Usuario usuario;

    public ControladorSanidad(PanelSanidad panelSanidad, Usuario usuario) {
        this.panelSanidad = panelSanidad;
        this.usuario = usuario;
        ScreenManager.cambiarAPanelRegistrarTratamiento(panelSanidad, usuario);
        activarEventos();
    }
    
    public void activarEventos() {
        panelSanidad.getBtnHistorialSanitario().addActionListener(this);
        panelSanidad.getBtnRegistrarTratamiento().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Color botonActivo = new Color(93, 122, 163);
        if (e.getSource() == panelSanidad.getBtnRegistrarTratamiento()) {
            reiniciarColoresDeBotones();
            panelSanidad.getBtnRegistrarTratamiento().setBackground(botonActivo);
            ScreenManager.cambiarAPanelRegistrarTratamiento(panelSanidad, usuario);
        } else if (e.getSource() == panelSanidad.getBtnHistorialSanitario()) {
            reiniciarColoresDeBotones();
            panelSanidad.getBtnHistorialSanitario().setBackground(botonActivo);
        }
    }
    
    public void reiniciarColoresDeBotones() {
        Color azulGris = new Color(55, 72, 95);
        panelSanidad.getBtnRegistrarTratamiento().setBackground(azulGris);
        panelSanidad.getBtnHistorialSanitario().setBackground(azulGris);
    }
}
