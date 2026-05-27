package controlador;

//@autor: Brayan C

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Departamento;
import modelo.DepartamentoDAO;
import modelo.Ubicacion;
import modelo.Usuario;
import modelo.UsuarioDAO;
import vista.PanelCrearCuenta;


public class ControladorCrearCuenta implements ActionListener, MouseListener {
    private final PanelCrearCuenta panelCrearCuenta;
    private final UsuarioDAO usuarioDAO;
    private final DepartamentoDAO departamentoDAO;

    public ControladorCrearCuenta(PanelCrearCuenta panelCrearCuenta, UsuarioDAO usuarioDAO, DepartamentoDAO departamentoDAO) {
        this.panelCrearCuenta = panelCrearCuenta;
        this.usuarioDAO = usuarioDAO;
        this.departamentoDAO = departamentoDAO;
        configurarEventos();
        cargarDatosInciales();
    }
    
    public void configurarEventos() {
        panelCrearCuenta.getBtnRegistrar().addActionListener(this);
        panelCrearCuenta.getJcbDepartamento().addActionListener(this);
        
        panelCrearCuenta.getTxtNombres().addMouseListener(this);
        panelCrearCuenta.getTxtCorreo().addMouseListener(this);
        panelCrearCuenta.getTxtNombreFinca().addMouseListener(this);
        panelCrearCuenta.getTxtUsuario().addMouseListener(this);
        panelCrearCuenta.getTxtVereda().addMouseListener(this);
        panelCrearCuenta.getPwsContraseña().addMouseListener(this);
    }
    
    public void cargarDatosInciales() {
        cargarCbmDepartamentos();
        CargarCbmMunicipios();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) { 
        if (e.getSource() == panelCrearCuenta.getBtnRegistrar()) {
            registrar();
        } else if (e.getSource() == panelCrearCuenta.getJcbDepartamento()) {
            CargarCbmMunicipios();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (source == panelCrearCuenta.getTxtNombres()) {
            reestablecerPlaceHolder();
            if (panelCrearCuenta.getNombres() != null) return;
            configTextField(panelCrearCuenta.getTxtNombres());
            
        } else if (source == panelCrearCuenta.getTxtCorreo()) {
            reestablecerPlaceHolder();
            if (panelCrearCuenta.getCorreo ()!= null) return;
            configTextField(panelCrearCuenta.getTxtCorreo());
            
        } else if (source == panelCrearCuenta.getTxtUsuario()) {
                reestablecerPlaceHolder();
                if (panelCrearCuenta.getUsuario() != null) return;
                configTextField(panelCrearCuenta.getTxtUsuario());
        } else if (source == panelCrearCuenta.getPwsContraseña()) {
            reestablecerPlaceHolder();
            if (panelCrearCuenta.getContraseña() != null) return;
            configTextField(panelCrearCuenta.getPwsContraseña());
            
        } else if (source == panelCrearCuenta.getTxtVereda()) {
            reestablecerPlaceHolder();
            if (panelCrearCuenta.getVereda() != null) return;
            configTextField(panelCrearCuenta.getTxtVereda());
            
        } else if (source == panelCrearCuenta.getTxtNombreFinca()) {
            reestablecerPlaceHolder();
            if (panelCrearCuenta.getNombreFinca() != null) return;
            configTextField(panelCrearCuenta.getTxtNombreFinca());
        }
    }
    public void registrar() {
        Usuario nuevoUsuario = verificarYRetornarDatos();
        
        if (usuarioDAO.registrar(nuevoUsuario)) {
            JOptionPane.showMessageDialog(panelCrearCuenta, "Usuario creado correctamente.");
            reiniciarFormulario();
        }
    }
    
    public void reestablecerPlaceHolder() {
        Color grisClaro = new Color(204,204,204);
        
        if (panelCrearCuenta.getCorreo() == null) {
            panelCrearCuenta.setTxtCorreo("Correo electrónico");
            panelCrearCuenta.getTxtCorreo().setForeground(grisClaro);
        }
        
        if (panelCrearCuenta.getNombreFinca() == null) {
            panelCrearCuenta.setTxtNombreFinca("Nombre de la finca");
            panelCrearCuenta.getTxtNombreFinca().setForeground(grisClaro);
        }
        
        if (panelCrearCuenta.getNombres() == null) {
            panelCrearCuenta.setTxtNombres("Nombres y apellidos");
            panelCrearCuenta.getTxtNombres().setForeground(grisClaro);
        }
        
        if (panelCrearCuenta.getUsuario() == null) {
            panelCrearCuenta.setTxtUsuario("Nombre de usuario");
            panelCrearCuenta.getTxtUsuario().setForeground(grisClaro);
        }
        
        if (panelCrearCuenta.getVereda() == null) {
            panelCrearCuenta.setTxtVereda("Vereda");
            panelCrearCuenta.getTxtVereda().setForeground(grisClaro);
        }
        
        if (panelCrearCuenta.getContraseña() == null) {
            panelCrearCuenta.setPwsContraseña("**************");
            panelCrearCuenta.getPwsContraseña().setForeground(grisClaro);
        }
    }
    
    public void reiniciarFormulario() {
        Color grisClaro = new Color(204, 204, 204);
        cargarDatosInciales();

        panelCrearCuenta.setTxtCorreo("Correo electrónico");
        panelCrearCuenta.getTxtCorreo().setForeground(grisClaro);

        panelCrearCuenta.setTxtNombreFinca("Nombre de la finca");
        panelCrearCuenta.getTxtNombreFinca().setForeground(grisClaro);

        panelCrearCuenta.setTxtNombres("Nombres y apellidos");
        panelCrearCuenta.getTxtNombres().setForeground(grisClaro);

        panelCrearCuenta.setTxtUsuario("Nombre de usuario");
        panelCrearCuenta.getTxtUsuario().setForeground(grisClaro);

        panelCrearCuenta.setTxtVereda("Vereda");
        panelCrearCuenta.getTxtVereda().setForeground(grisClaro);

        panelCrearCuenta.setPwsContraseña("**************");
        panelCrearCuenta.getPwsContraseña().setForeground(grisClaro);
    }
    
    public Usuario verificarYRetornarDatos() {
        String nombresYApellidos = panelCrearCuenta.getNombres();
        String nombreDeUsuario = panelCrearCuenta.getUsuario();
        String correoElectronico = panelCrearCuenta.getCorreo();
        String nombreDeLaFinca = panelCrearCuenta.getNombreFinca();
        Ubicacion ubicacion = panelCrearCuenta.getUbicacion();
        String contraseña = panelCrearCuenta.getContraseña();
        
        int contador = 0;
        
        if (nombresYApellidos == null) {
            panelCrearCuenta.setMensajeNombres("Ingresa nombres y apellidos");
            contador++;
        } else {
            panelCrearCuenta.setMensajeNombres("");
        }
        
        if (nombreDeUsuario == null) {
            panelCrearCuenta.setMensajeUsuario("Ingresa un usuario");
            contador++;
        } else {
            panelCrearCuenta.setMensajeUsuario("");
        }
        
        if (usuarioDAO.verificarNombreEnUso(nombreDeUsuario)) {
            panelCrearCuenta.setMensajeUsuario("Este nombre ya esta en uso");
            contador++;
        } else {
            panelCrearCuenta.setMensajeUsuario("");
        }
        
        if (correoElectronico == null) {
            panelCrearCuenta.setMensajeCorreo("Ingresa un correo electrónico");
            contador++;
        } else {
            panelCrearCuenta.setMensajeCorreo("");
        }
        
        if (nombreDeLaFinca == null) {
            panelCrearCuenta.setMensajeNombreDeLaFinca("Ingresa el nombre de la finca");
            contador++;
        } else {
            panelCrearCuenta.setMensajeNombreDeLaFinca("");
        }
        
        if (ubicacion == null) {
            panelCrearCuenta.setMensajeUbicacion("Completa la ubicación");
            contador++;
        } else {
            panelCrearCuenta.setMensajeUbicacion("");
        }
        
        if (contraseña == null) {
            panelCrearCuenta.setMensajeContraseña("Ingresa una contraseña");
            contador++;
        } else {
            panelCrearCuenta.setMensajeContraseña("");
        }
        
        if (contador == 0) {
            return new Usuario(nombresYApellidos, nombreDeUsuario, correoElectronico, nombreDeLaFinca, ubicacion, contraseña);
        }
        
        return null;
    }
    
    public void cargarCbmDepartamentos() {
        JComboBox cmbDepartamentos = panelCrearCuenta.getJcbDepartamento();
        configCmb(cmbDepartamentos, "Departamento");
        for (Departamento departamento : departamentoDAO.retornarDepartamentos() ) {
            panelCrearCuenta.getJcbDepartamento().addItem(departamento.getDepartamento());
        }
    }
    
    public void CargarCbmMunicipios() {
        String departamentoSeleccionado = panelCrearCuenta.getJcbDepartamento().getSelectedItem().toString();
        
        for (Departamento departamento : departamentoDAO.retornarDepartamentos()) {
            if (departamento.getDepartamento().equals(departamentoSeleccionado)) {
                configCmb(panelCrearCuenta.getJcbMunicipio(), "Municipio");
                for (String municipio : departamento.getMunicipios()) {
                    panelCrearCuenta.getJcbMunicipio().addItem(municipio);
                }
            }
        }
    }
    
    private void configTextField(JTextField txt) {
        txt.setText("");
        txt.setForeground(Color.BLACK);
    }
    
    private void configCmb(JComboBox cmb, String item) {
        cmb.removeAllItems();
        cmb.addItem(item);
    }
    
    //Murio aprox 5:10pm - 5:20pm del dia jueves 14 de mayo del año 2026
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
