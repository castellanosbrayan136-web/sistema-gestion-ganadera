package controlador;

//@autor: Brayan C

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Usuario;
import vista.PanelProduccion;
import vista.ScreenManager;
import vista.VistaPrincipal;


public class ControladorProduccion implements ActionListener {
    private final PanelProduccion panelProduccion;
    private final Usuario usuario;
    private final VistaPrincipal vistaPrincipal;

    public ControladorProduccion(PanelProduccion panelProduccion, Usuario usuario, VistaPrincipal vistaPrincipal) {
        this.panelProduccion = panelProduccion;
        this.usuario = usuario;
        this.vistaPrincipal = vistaPrincipal;
        activarEventos();
        panelProduccion.getBtnRegistrarProduccion().setBackground(new Color(93, 122, 163));
        ScreenManager.cambiarAPanelRegistrarProduccion(usuario, panelProduccion);
    }
    
    public void activarEventos() {
        panelProduccion.getBtnGestionProduccion().addActionListener(this);
        panelProduccion.getBtnRegistrarProduccion().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Color botonActivo = new Color(93, 122, 163);
        if (e.getSource() == panelProduccion.getBtnRegistrarProduccion()) {
            reiniciarColoresDeBotones();
            panelProduccion.getBtnRegistrarProduccion().setBackground(botonActivo);
            ScreenManager.cambiarAPanelRegistrarProduccion(usuario, panelProduccion);
        } else if (e.getSource() == panelProduccion.getBtnGestionProduccion()) {
            reiniciarColoresDeBotones();
            panelProduccion.getBtnGestionProduccion().setBackground(botonActivo);
        }
    }
    
    public void reiniciarColoresDeBotones() {
    Color azulGris = new Color(55, 72, 95);
    panelProduccion.getBtnGestionProduccion().setBackground(azulGris);
    panelProduccion.getBtnRegistrarProduccion().setBackground(azulGris);
    }
}
