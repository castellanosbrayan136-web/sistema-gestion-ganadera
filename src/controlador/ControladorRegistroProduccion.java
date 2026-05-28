package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.Produccion;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelRegistrarProduccion;


public class ControladorRegistroProduccion implements ActionListener {
    private final PanelRegistrarProduccion panelRegistroProduccion;
    private final VacaDAO vacaDAO;
    private final Usuario usuario;

    public ControladorRegistroProduccion(PanelRegistrarProduccion panelRegistroProduccion, VacaDAO vacaDAO, Usuario usuario) {
        this.panelRegistroProduccion = panelRegistroProduccion;
        this.vacaDAO = vacaDAO;
        this.usuario = usuario;
        configurarEventos();
        cargarDatosIniciales();
    }

    private void configurarEventos() {
        panelRegistroProduccion.getBtnRegistrar().addActionListener(this);
        panelRegistroProduccion.getCmbAño().addActionListener(this);
        panelRegistroProduccion.getCmbMes().addActionListener(this);
    }
    
    private void cargarDatosIniciales() {
        cargarCmbAños();
        cargarCmbDias();
        cargarCmbMeses();
        cargarComboVacas();
    }
    
    private void cargarComboVacas() {
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {
            panelRegistroProduccion.getCmbVacas().addItem(vaca);
        }
    }
    
    private void cargarCmbMeses() {
        for (Month mes : Month.values()) {
            String mesEnEspañol = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            panelRegistroProduccion.getCmbMes().addItem(mesEnEspañol);
        }
    }
    
    public void cargarCmbAños() {
        int añoActual = LocalDate.now().getYear();
        
        for (int i = añoActual ; i >= 2020 ; i--) {
            panelRegistroProduccion.getCmbAño().addItem(String.valueOf(i));
        }
    }
    
    public void cargarCmbDias() {
        configCmb(panelRegistroProduccion.getCmbDia(), "Dia");
        LocalDate fecha = panelRegistroProduccion.getFecha();
        
        if (fecha == null) {
            return;
        }
        
        int año = fecha.getYear();
        int mes = fecha.getMonthValue();
        
        YearMonth yearMonth = YearMonth.of(año, mes);
        int diasMes = yearMonth.lengthOfMonth();
        
        for (int i = 1; i <= diasMes; i++) {
            panelRegistroProduccion.getCmbDia().addItem(String.valueOf(i));
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelRegistroProduccion.getBtnRegistrar()) {
            registrarProduccion();
        } else if (e.getSource() == panelRegistroProduccion.getCmbAño() || e.getSource() == panelRegistroProduccion.getCmbMes()) {
            cargarCmbDias();
        } 
    }
    
    private Produccion verificarProduccion() {
        LocalDate fecha = panelRegistroProduccion.getFecha();
        
        Produccion produccion = vacaDAO.getProduccionPorFecha(panelRegistroProduccion.getVaca().getIdInterno(), fecha);
        
        if (produccion == null) {
            return new Produccion(fecha);
        } else {
            return produccion;
        }
    }
        
    private void mostrarMensaje() {
            JOptionPane.showMessageDialog(panelRegistroProduccion, "Registro exitoso.");
        }
        
    private void registrarProduccion() {
        Vaca vaca = panelRegistroProduccion.getVaca();
        String jornada = panelRegistroProduccion.getJornada();
        Integer litros = panelRegistroProduccion.getLitros();
        
        Produccion produccion = verificarProduccion();
        
        if (jornada.equals("Mañana")) {
           if (produccion.getLitrosTarde() == null && produccion.getLitrosMañana() == null) {
                produccion.setLitrosMañana(litros);
                vacaDAO.addProduccion(produccion, vaca.getIdInterno());
                mostrarMensaje();
            } else if (produccion.getLitrosTarde() != null && produccion.getLitrosMañana() == null) {
               produccion.setLitrosMañana(litros);
               vacaDAO.updateProduccion(vaca.getIdInterno(), produccion);
               mostrarMensaje();
           } else {
                JOptionPane.showMessageDialog(panelRegistroProduccion, "Ya hiciste registro para la manana de este dia.");
            }
        }
        
        if (jornada.equals("Tarde")) {
            if (produccion.getLitrosTarde() == null && produccion.getLitrosMañana() == null) {
                produccion.setLitrosTarde(litros);
                vacaDAO.addProduccion(produccion, vaca.getIdInterno());
                mostrarMensaje();
            } else if (produccion.getLitrosTarde() == null && produccion.getLitrosMañana() != null) {
                produccion.setLitrosTarde(litros);
                vacaDAO.updateProduccion(vaca.getIdInterno(), produccion);
                mostrarMensaje();
            } else {
                JOptionPane.showMessageDialog(panelRegistroProduccion, "Ya hiciste registro para la tarde de este dia.");
            }
        }
    }
    
    private void configCmb(JComboBox cmb, String item) {
        cmb.removeAllItems();
        cmb.addItem(item);
    }
}
