package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
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
        activarEventos();
        llenarCombos();
    }

    private void activarEventos() {
        panelRegistroProduccion.getBtnRegistrar().addActionListener(this);
        panelRegistroProduccion.getJcbAño().addActionListener(this);
        panelRegistroProduccion.getJcbMes().addActionListener(this);
    }
    
    private void llenarCombos() {
        llenarAñosYMeses();
        llenarComboVacas();
    }
    
    private Produccion verificarProduccion() {
        LocalDate fecha = leerFechaSeleccionada();
        
        Produccion produccion = vacaDAO.retornarRegistroProduccionPorFecha(leerVacaSeleccionada(), fecha);
        
        if (produccion == null) {
            return new Produccion(fecha);
        } else {
            return produccion;
        }
    }
    
    private LocalDate leerFechaSeleccionada() {
        return LocalDate.of(
    Integer.parseInt(panelRegistroProduccion.getJcbAño().getSelectedItem().toString()),
    panelRegistroProduccion.getJcbMes().getSelectedIndex(),
    Integer.parseInt(panelRegistroProduccion.getJcbDia().getSelectedItem().toString())
);
    }
    
    private String leerJornadaSeleccionada() {
        return (String) panelRegistroProduccion.getJcbJornada().getSelectedItem();
    }
    
    private Integer leerLitrosIngresados() {
        return panelRegistroProduccion.getLitros();
    }
    
    private void llenarComboVacas() {
        for (Vaca vaca : vacaDAO.retornarListaVacasPorUsuario(usuario.getNombreDeUsuario())) {
            panelRegistroProduccion.getJcbAnimal().addItem(vaca);
        }
    }
    
    private void llenarAñosYMeses() {
        for (Month mes : Month.values()) {
            String mesEspañol = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            panelRegistroProduccion.getJcbMes().addItem(mesEspañol);
        }
        
        int añoActual = LocalDate.now().getYear();
        
        for (int i = añoActual ; i >= 2025 ; i--) {
            panelRegistroProduccion.getJcbAño().addItem(String.valueOf(i));
        }
    }
        
    private Vaca leerVacaSeleccionada() {
        return (Vaca) panelRegistroProduccion.getJcbAnimal().getSelectedItem();
    }
    
        private void llenarDiasDelMes() {
        panelRegistroProduccion.getJcbDia().removeAllItems();
        panelRegistroProduccion.getJcbDia().addItem("Día");
        
        int año;
        
        try {
            año = Integer.parseInt((String) panelRegistroProduccion.getJcbAño().getSelectedItem());
        } catch (NumberFormatException e) {
            return;
        }
        int mes = panelRegistroProduccion.getJcbMes().getSelectedIndex();
        
        if (mes == 0) {
            panelRegistroProduccion.getJcbDia().removeAllItems();
            panelRegistroProduccion.getJcbDia().addItem("Día");
            return;
        }
        
        YearMonth yearMonth = YearMonth.of(año, mes);
        
        int cantidadDias = yearMonth.lengthOfMonth();
        
        for (int i = 1; i <= cantidadDias; i++) {
            panelRegistroProduccion.getJcbDia().addItem(String.valueOf(i));
        }
    }
        
        private void mostrarMensaje() {
            JOptionPane.showMessageDialog(panelRegistroProduccion, "Registro exitoso.");
        }
        
    private void registrarProduccion() {
        Vaca vacaARealizarRegistro = leerVacaSeleccionada();
        LocalDate fecha = leerFechaSeleccionada();
        String jornada = leerJornadaSeleccionada();
        Integer litros = leerLitrosIngresados();
        
        Produccion produccion = verificarProduccion();
        
        if (jornada.equals("Mañana")) {
           if (produccion.getLitrosTarde() == null && produccion.getLitrosMañana() == null) {
                produccion.setLitrosMañana(litros);
                vacaDAO.registrarProduccion(produccion, vacaARealizarRegistro);
                mostrarMensaje();
            } else if (produccion.getLitrosTarde() != null && produccion.getLitrosMañana() == null) {
               produccion.setLitrosMañana(litros);
               vacaDAO.editarProduccion(vacaARealizarRegistro, produccion);
               mostrarMensaje();
           } else {
                JOptionPane.showMessageDialog(panelRegistroProduccion, "Ya hiciste registro para la manana de este dia.");
            }
        }
        
        if (jornada.equals("Tarde")) {
            if (produccion.getLitrosTarde() == null && produccion.getLitrosMañana() == null) {
                produccion.setLitrosTarde(litros);
                vacaDAO.registrarProduccion(produccion, vacaARealizarRegistro);
                mostrarMensaje();
            } else if (produccion.getLitrosTarde() == null && produccion.getLitrosMañana() != null) {
                produccion.setLitrosTarde(litros);
                vacaDAO.editarProduccion(vacaARealizarRegistro, produccion);
                mostrarMensaje();
            } else {
                JOptionPane.showMessageDialog(panelRegistroProduccion, "Ya hiciste registro para la tarde de este dia.");
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelRegistroProduccion.getBtnRegistrar()) {
            registrarProduccion();
        } else if (e.getSource() == panelRegistroProduccion.getJcbAño()) {
            llenarDiasDelMes();
        } else if (e.getSource() == panelRegistroProduccion.getJcbMes()) {
            llenarDiasDelMes();
        }
    }
}
