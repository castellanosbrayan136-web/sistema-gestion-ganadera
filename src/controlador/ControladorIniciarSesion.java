package controlador;

//@autor: Brayan C

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.Usuario;
import modelo.UsuarioDAO;
import vista.PanelIniciarSesion;
import vista.ScreenManager;
import vista.VistaAutenticacion;


public class ControladorIniciarSesion implements ActionListener {
    private final UsuarioDAO usuarioDAO;
    private final PanelIniciarSesion panelIniciarSesion;
    private final VistaAutenticacion vistaAutenticacion;

    public ControladorIniciarSesion(UsuarioDAO usuarioDAO, PanelIniciarSesion panelIniciarSesion, VistaAutenticacion vistaAutenticacion) {
        this.usuarioDAO = usuarioDAO;
        this.panelIniciarSesion = panelIniciarSesion;
        this.vistaAutenticacion = vistaAutenticacion;
        
        configurarEventos();
    }
    
    public void configurarEventos() {
        panelIniciarSesion.getBtnEntrar().addActionListener(this);
        eventoTxtNombreUsurio();
        eventoTxtContraseña();
        eventoEnter();
    }
    
    public void eventoEnter() {
        panelIniciarSesion.getJpfContraseña().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   iniciarSesion();
               } 
            }
        });
    }
    
    public void eventoTxtNombreUsurio() {
        panelIniciarSesion.getTxtUsuario().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                reiniciarCampos();
                if (panelIniciarSesion.getUsuario() == null) {
                    panelIniciarSesion.setTxtUsuario("");
                    panelIniciarSesion.getTxtUsuario().setForeground(Color.black);
                }
            }
        });
    }
    
    public void eventoTxtContraseña() {
        panelIniciarSesion.getJpfContraseña().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                reiniciarCampos();
                if (panelIniciarSesion.getContraseña()== null) {
                    panelIniciarSesion.setJpfContraseña("");
                    panelIniciarSesion.getJpfContraseña().setForeground(Color.black);
                }
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelIniciarSesion.getBtnEntrar()) {
            iniciarSesion();
        }
    }
    
    public void iniciarSesion() {
        String nombreUsuario = leerNombreUsuario();
        String contraseña = leerContraseña();
        
        if (nombreUsuario == null || contraseña == null) {
            return;
        }
        Usuario usuario = usuarioDAO.verificarUsuario(nombreUsuario, contraseña);
        
        if (usuario != null) {
            ScreenManager.abrirVistaPrincipal(usuario, vistaAutenticacion);
        } else {
            panelIniciarSesion.setMensajeContraseña("Usuario o contraseña incorrecta!");
        }
    }
    
    public String leerNombreUsuario() {
        String usuario = panelIniciarSesion.getUsuario();
        if (usuario == null) {
            panelIniciarSesion.setMensajeUsuario("Ingresa un usuario");
        }
        return usuario;
    }
    
    public String leerContraseña() {
        String contraseña = panelIniciarSesion.getContraseña();
        if (contraseña == null) {
            panelIniciarSesion.setMensajeContraseña("Ingresa una contraseña");
        }
        return contraseña;
    }
    
    public void reiniciarCampos() {
        int c = 204;
        Color grisClaro = new Color(c,c,c);
        
        if (panelIniciarSesion.getUsuario() == null) {
            panelIniciarSesion.setTxtUsuario("Ingrese su nombre de usuario");
            panelIniciarSesion.getTxtUsuario().setForeground(grisClaro);
        }
        
        if (panelIniciarSesion.getContraseña() == null) {
            panelIniciarSesion.setJpfContraseña("**************");
            panelIniciarSesion.getJpfContraseña().setForeground(grisClaro);
        }
    }
}
