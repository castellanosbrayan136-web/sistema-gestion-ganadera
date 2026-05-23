package controlador;

//@autor: Brayan C

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Usuario;
import vista.PanelSanidad;
import vista.ScreenManager;
import vista.VistaPrincipal;


public class ControladorSanidad implements ActionListener {
    private VistaPrincipal vistaPrincipal;
    private PanelSanidad panelSanidad;
    private Usuario usuario;

    public ControladorSanidad(PanelSanidad panelSanidad, Usuario usuario, VistaPrincipal vistaPrincipal) {
        this.panelSanidad = panelSanidad;
        this.usuario = usuario;
        this.vistaPrincipal = vistaPrincipal;
        panelSanidad.getBtnRegistrarTratamiento().setBackground(new Color(93, 122, 163));
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
            ScreenManager.cambiarAPanelGestionSanidad(usuario, panelSanidad, vistaPrincipal);
        }
    }
    
    public void reiniciarColoresDeBotones() {
        Color azulGris = new Color(55, 72, 95);
        panelSanidad.getBtnRegistrarTratamiento().setBackground(azulGris);
        panelSanidad.getBtnHistorialSanitario().setBackground(azulGris);
    }
}
