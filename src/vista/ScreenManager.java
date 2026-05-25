package vista;

//@autor: Brayan C

import controlador.ControladorAutenticacion;
import controlador.ControladorCrearCuenta;
import controlador.ControladorEdicionTratamiento;
import controlador.ControladorEditarProduccion;
import controlador.ControladorGanado;
import controlador.ControladorGestionProduccion;
import controlador.ControladorGestionSanidad;
import controlador.ControladorIniciarSesion;
import controlador.ControladorGestionarGanado;
import controlador.ControladorInformacionUsuario;
import controlador.ControladorInicio;
import controlador.ControladorPrincipal;
import controlador.ControladorProduccion;
import controlador.ControladorRegistrarGanado;
import controlador.ControladorRegistrarTratamiento;
import controlador.ControladorRegistroProduccion;
import controlador.ControladorSanidad;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import modelo.DepartamentoDAO;
import modelo.RegistroProduccion;
import modelo.TratamientoVeterinario;
import modelo.Usuario;
import modelo.UsuarioDAO;
import modelo.Vaca;
import modelo.VacaDAO;


public class ScreenManager {
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    private static VacaDAO vacaDAO = new VacaDAO();
    
    public static void abrirVistaAutenticacion() {
        VistaAutenticacion vistaAutenticacion = new VistaAutenticacion();
        vistaAutenticacion.setSize(1440,900);
        vistaAutenticacion.setLocationRelativeTo(null);
        new ControladorAutenticacion(vistaAutenticacion);
        
        vistaAutenticacion.setVisible(true);
    }
    
    public static void cerrarVistaAutenticacion(VistaAutenticacion vistaAutenticacion) {
        vistaAutenticacion.dispose();
    }
    public static void cambiarAPanelCrearCuenta(VistaAutenticacion vistaAutenticacion) {
        PanelCrearCuenta panelCrearCuenta = new PanelCrearCuenta();
        
        new ControladorCrearCuenta(panelCrearCuenta, usuarioDAO, departamentoDAO);
        
        cambiarPaneles(vistaAutenticacion.getPanelIntercambiable(), panelCrearCuenta, 881, 1128);
    }
    
    public static void cambiarAPanelIniciarSesion(VistaAutenticacion vistaAutenticacion) {
        PanelIniciarSesion panelIniciarSesion = new PanelIniciarSesion();
        
        new ControladorIniciarSesion(usuarioDAO, panelIniciarSesion, vistaAutenticacion);
        
        cambiarPaneles(vistaAutenticacion.getPanelIntercambiable(), panelIniciarSesion, 881, 1128);
    }
    
    public static void abrirVistaPrincipal(Usuario usuario, VistaAutenticacion vistaAutenticacion) {
        cerrarVistaAutenticacion(vistaAutenticacion);
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        
        vistaPrincipal.setSize(1440,900);
        vistaPrincipal.setTitle("Sistema de gestion ganadera");
        vistaPrincipal.setLocationRelativeTo(null);
        
        new ControladorPrincipal(vistaPrincipal, usuario);
        
        vistaPrincipal.setVisible(true);
    }
    
    public static void cerrarVistaPrincipal(VistaPrincipal vistaPrincipal) {
        vistaPrincipal.dispose();
    }
    
    public static void cambiarAPanelInicio(VistaPrincipal vistaPrincipal, Usuario usuario) {
        PanelInicio panelInicio = new PanelInicio();
        
        new ControladorInicio(panelInicio, usuario, vacaDAO);
        
        cambiarPaneles(vistaPrincipal.getPanelVacio(), panelInicio, 1128, 778);
    }
    
    public static void cambiarAPanelGanado(VistaPrincipal vistaPrincipal, Usuario usuario) {
        PanelGanado panelGanado = new PanelGanado();
        
        new ControladorGanado(panelGanado, usuario, vistaPrincipal);
        
        cambiarPaneles(vistaPrincipal.getPanelVacio(), panelGanado, 1128, 778);
    }
    
    public static void cambiarAPanelRegistrarGanado(PanelGanado panelGanado, Usuario usuario) {
        PanelRegistrarGanado panelRegistrarGanado = new PanelRegistrarGanado();
        
        new ControladorRegistrarGanado(panelRegistrarGanado, vacaDAO, usuario);
        
        cambiarPaneles(panelGanado.getPanelVacio(), panelRegistrarGanado, 860, 764);
    }
    
    public static void cambiarAPanelListaGanado(PanelGanado panelGanado, Usuario usuario, VistaPrincipal vistaPrincipal) {
        PanelGestionarGanado panelListaGanado = new PanelGestionarGanado();
        
        new ControladorGestionarGanado(panelListaGanado, vacaDAO, usuario, vistaPrincipal);
        
        cambiarPaneles(panelGanado.getPanelVacio(), panelListaGanado, 860, 764);
    }
    
    public static void cambiarAPanelSanidad(VistaPrincipal vistaPrincipal, Usuario usuario) {
        PanelSanidad panelSanidad = new PanelSanidad();
        
        new ControladorSanidad(panelSanidad, usuario, vistaPrincipal);
        
        cambiarPaneles(vistaPrincipal.getPanelVacio(), panelSanidad, 1128, 778);
    }
    
    public static void cambiarAPanelRegistrarTratamiento(PanelSanidad panelSanidad, Usuario usuario) {
        PanelRegistrarTratamiento panelRegistrarTratamiento = new PanelRegistrarTratamiento();
        
        new ControladorRegistrarTratamiento(panelRegistrarTratamiento, vacaDAO, usuario);
        
        cambiarPaneles(panelSanidad.getPanelVacio(), panelRegistrarTratamiento, 860, 764);
    }
    
    public static void abrirDialogEdicionGanado(VistaPrincipal vistaPrincipal, Vaca vaca) {
        DialogEdicionGanado dialogEdicionGanado = new DialogEdicionGanado(vistaPrincipal, true);
        dialogEdicionGanado.setLocationRelativeTo(vistaPrincipal);
        dialogEdicionGanado.setSize(590,775);
        
        new controlador.ControdalorEdicionGanado(vistaPrincipal, dialogEdicionGanado, vacaDAO, vaca);
        
        dialogEdicionGanado.setVisible(true);
    }
    
    public static void abrirDialogInformacionUsuario(VistaPrincipal vistaPrincipal, Usuario usuario) {
        DialogInformacionUsuario dialogInformacionUsuario = new DialogInformacionUsuario(vistaPrincipal, true);
        dialogInformacionUsuario.setLocationRelativeTo(vistaPrincipal);
        dialogInformacionUsuario.setTitle("Información de usuario");
        
        new ControladorInformacionUsuario(dialogInformacionUsuario, usuario, vacaDAO);
        
        dialogInformacionUsuario.setVisible(true);
    }
    
    public static void cambiarAPanelGestionSanidad(Usuario usuario, PanelSanidad panelSanidad, VistaPrincipal vistaPrincipal ) {
        PanelGestionSanidad panelGestionSanidad = new PanelGestionSanidad();
        
        new ControladorGestionSanidad(panelGestionSanidad, usuario, vacaDAO, vistaPrincipal);
        
        cambiarPaneles(panelSanidad.getPanelVacio(), panelGestionSanidad, 860, 764);
    }
    
    public static void abrirDialogEdicionTratamiento(VistaPrincipal vistaPrincipal, Vaca vaca, TratamientoVeterinario tratamiento) {
        DialogEdicionTratamiento dialogEdicionTratamiento = new DialogEdicionTratamiento(vistaPrincipal, true);
        dialogEdicionTratamiento.setLocationRelativeTo(vistaPrincipal);
        
        new ControladorEdicionTratamiento(dialogEdicionTratamiento, tratamiento, vacaDAO, vaca);
        
        dialogEdicionTratamiento.setVisible(true);
    }
    
    public static void cambiarAPanelProduccion(VistaPrincipal vistaPrincipal, Usuario usuario) {
        PanelProduccion panelProduccion = new PanelProduccion();
        
        new ControladorProduccion(panelProduccion, usuario, vistaPrincipal);
        
        cambiarPaneles(vistaPrincipal.getPanelVacio(), panelProduccion, 1128, 778);
    }
    
    public static void cambiarAPanelRegistrarProduccion(Usuario usuario, PanelProduccion panelProduccion) {
        PanelRegistrarProduccion panelRegistrarProduccion = new PanelRegistrarProduccion();
        
        new ControladorRegistroProduccion(panelRegistrarProduccion, vacaDAO, usuario);
        
        cambiarPaneles(panelProduccion.getPanelVacio(), panelRegistrarProduccion, 860, 764);
    }
    
    public static void cambiarAPanelGestionProduccion(VistaPrincipal vistaPrincipal, Usuario usuario, PanelProduccion panelProduccion) {
        PanelGestionProduccion panelGestionProduccion = new PanelGestionProduccion();
        
        new ControladorGestionProduccion(vistaPrincipal, panelGestionProduccion, usuario, vacaDAO);
        
        cambiarPaneles(panelProduccion.getPanelVacio(), panelGestionProduccion, 860, 764);
    }
    
    public static void abrirDialogEdicionProduccion(VistaPrincipal vistaPrincipal, RegistroProduccion registroProduccion, Vaca vaca) {
        DialogEdicionProduccion dialogEdicionProduccion = new DialogEdicionProduccion(vistaPrincipal, true);
        dialogEdicionProduccion.setLocationRelativeTo(vistaPrincipal);
        dialogEdicionProduccion.setTitle("Editar registro.");
        
        new ControladorEditarProduccion(dialogEdicionProduccion, vistaPrincipal, vacaDAO, registroProduccion, vaca);
        
        dialogEdicionProduccion.setVisible(true);
    }
    
    public static void cambiarPaneles(JPanel panelVacio, JPanel panelAIntercambiar, int ancho, int largo) {
        panelAIntercambiar.setSize(ancho,largo);
        panelAIntercambiar.setLocation(0, 0);
        
        panelVacio.removeAll();
        panelVacio.add(panelAIntercambiar, BorderLayout.CENTER);
        panelVacio.revalidate();
        panelVacio.repaint();
    }
}
