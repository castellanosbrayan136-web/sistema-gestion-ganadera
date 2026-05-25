package controlador;

//@autor: Brayan C

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.ScreenManager;
import vista.VistaPrincipal;


public class ControladorPrincipal implements ActionListener {
    private VistaPrincipal vistaPrincipal;
    private Usuario usuario;

    public ControladorPrincipal(VistaPrincipal vistaPrincipal, Usuario usuario) {
        this.vistaPrincipal = vistaPrincipal;
        this.usuario = usuario;
        
        activarEventos();
        cargarInformacionDeInicio();
    }
    
    public void activarEventos() {
        eventoBotonX();
        activarBotones();
    }
    
    public void eventoBotonX() {
        vistaPrincipal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ScreenManager.cerrarVistaPrincipal(vistaPrincipal);
                ScreenManager.abrirVistaAutenticacion();
            }
        });
    }
    
    public void activarBotones() {
        vistaPrincipal.getBtnInicio().addActionListener(this);
        vistaPrincipal.getBtnGanado().addActionListener(this);
        vistaPrincipal.getBtnSanidad().addActionListener(this);
        vistaPrincipal.getBtnProduccion().addActionListener(this);
        vistaPrincipal.getBtnClientes().addActionListener(this);
        vistaPrincipal.getBtnFinanzas().addActionListener(this);
        vistaPrincipal.getBtnReportes().addActionListener(this);
        vistaPrincipal.getBtnSalir().addActionListener(this);
        vistaPrincipal.getLblUsuario().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String mensajeVersion = "Disponible en futuras versiones.";
        Color botonActivo = new Color(70, 90, 120);
        if (e.getSource() == vistaPrincipal.getBtnInicio()) {
            reiniciarColorDeBoton();
            vistaPrincipal.getBtnInicio().setBackground(botonActivo);
            ScreenManager.cambiarAPanelInicio(vistaPrincipal, usuario);
            
        } else if (e.getSource() == vistaPrincipal.getBtnGanado()) {
            reiniciarColorDeBoton();
            vistaPrincipal.getBtnGanado().setBackground(botonActivo);
            ScreenManager.cambiarAPanelGanado(vistaPrincipal, usuario);
        }else if (e.getSource() == vistaPrincipal.getBtnSanidad()) {
            reiniciarColorDeBoton();
            vistaPrincipal.getBtnSanidad().setBackground(botonActivo);
            ScreenManager.cambiarAPanelSanidad(vistaPrincipal, usuario);
        }else if (e.getSource() == vistaPrincipal.getBtnProduccion()) {
            reiniciarColorDeBoton();
            vistaPrincipal.getBtnProduccion().setBackground(botonActivo);
            ScreenManager.cambiarAPanelProduccion(vistaPrincipal, usuario);
        } else if (e.getSource() == vistaPrincipal.getBtnFinanzas()) {
            JOptionPane.showMessageDialog(vistaPrincipal, mensajeVersion);
        } else if (e.getSource() == vistaPrincipal.getBtnClientes()) {
            JOptionPane.showMessageDialog(vistaPrincipal, mensajeVersion);
        } else if (e.getSource() == vistaPrincipal.getBtnReportes()) {
            JOptionPane.showMessageDialog(vistaPrincipal, mensajeVersion);
        } else if (e.getSource() == vistaPrincipal.getLblUsuario()) {
            ScreenManager.abrirDialogInformacionUsuario(vistaPrincipal, usuario);
        } else if (e.getSource() == vistaPrincipal.getBtnSalir()) {
            System.exit(0);
        } 
    }
    
    public void reiniciarColorDeBoton() {
        Color azulGris = new Color(39, 51, 69);
        vistaPrincipal.getBtnInicio().setBackground(azulGris);
        vistaPrincipal.getBtnGanado().setBackground(azulGris);
        vistaPrincipal.getBtnSanidad().setBackground(azulGris);
        vistaPrincipal.getBtnProduccion().setBackground(azulGris);
    }
    
    public void cargarInformacionDeInicio() {
        ScreenManager.cambiarAPanelInicio(vistaPrincipal, usuario);
        vistaPrincipal.getBtnInicio().setBackground(new Color(70, 90, 120));
        
        String diaDeLaSemana = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        String diaDelMes = String.valueOf(LocalDate.now().getDayOfMonth());
        String mes = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        String año = String.valueOf(LocalDate.now().getYear());
        
        vistaPrincipal.setLblFecha(diaDeLaSemana, diaDelMes, mes, año);
        vistaPrincipal.setLblUsuario(usuario.getNombreDeUsuario());
    }
    
}
