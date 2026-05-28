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
import modelo.TratamientoVeterinario;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelRegistrarTratamiento;


public class ControladorRegistrarTratamiento implements ActionListener {
    
    private final PanelRegistrarTratamiento panelRegistrarTratamiento;
    private final VacaDAO vacaDAO;
    private final Usuario usuario;

    public ControladorRegistrarTratamiento(PanelRegistrarTratamiento panelRegistrarTratamiento, VacaDAO vacaDAO, Usuario usuario) {
        this.panelRegistrarTratamiento = panelRegistrarTratamiento;
        this.vacaDAO = vacaDAO;
        this.usuario = usuario;
        cargarDatosIniciales();
        configurarEventos();
    }
     
    public void configurarEventos() {
        panelRegistrarTratamiento.getBtnRegistrar().addActionListener(this);
        panelRegistrarTratamiento.getCmbMes().addActionListener(this);
        panelRegistrarTratamiento.getCmbAño().addActionListener(this);
    }
    
    public void cargarDatosIniciales() {
        cargarCmbAños();
        cargarCmbDias();
        cargarCmbMeses(); 
        cargarComboVacas();
    }

    private void cargarCmbMeses() {
        for (Month mes : Month.values()) {
            String mesEnEspañol = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            panelRegistrarTratamiento.getCmbMes().addItem(mesEnEspañol);
        }
    }
    
    public void cargarCmbAños() {
        int añoActual = LocalDate.now().getYear();
        
        for (int i = añoActual ; i >= 2020 ; i--) {
            panelRegistrarTratamiento.getCmbAño().addItem(String.valueOf(i));
        }
    }
    
    public void cargarCmbDias() {
        configCmb(panelRegistrarTratamiento.getCmbDia(), "Dia");
        LocalDate fecha = panelRegistrarTratamiento.getFecha();
        
        if (fecha == null) {
            return;
        }
        
        int año = fecha.getYear();
        int mes = fecha.getMonthValue();
        
        YearMonth yearMonth = YearMonth.of(año, mes);
        int diasMes = yearMonth.lengthOfMonth();
        
        for (int i = 1; i <= diasMes; i++) {
            panelRegistrarTratamiento.getCmbDia().addItem(String.valueOf(i));
        }
    }
    
    private void cargarComboVacas() {
        panelRegistrarTratamiento.getCmbVacas().removeAllItems();
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {
            panelRegistrarTratamiento.getCmbVacas().addItem(vaca);
        }
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelRegistrarTratamiento.getBtnRegistrar()) {
            registrarTratamiento();
        } else if (e.getSource() == panelRegistrarTratamiento.getCmbAño() || e.getSource() == panelRegistrarTratamiento.getCmbMes()) {
            cargarCmbDias();
        } 
    }
    
    public void registrarTratamiento() {
        if (vacaDAO.addTratamiento(panelRegistrarTratamiento.getVaca(), construirTratamientoVeterinario())) {
            JOptionPane.showMessageDialog(panelRegistrarTratamiento, "Registro exitoso.");
        } 
    }
    
    
    public TratamientoVeterinario construirTratamientoVeterinario() {

        String tratamiento = panelRegistrarTratamiento.getTratamiento();
        String medicamento = panelRegistrarTratamiento.getMedicamento();
        String dosis = panelRegistrarTratamiento.getDosis();
        LocalDate fecha = panelRegistrarTratamiento.getFecha();
        String observaciones = panelRegistrarTratamiento.getObservaciones(); // opcional
        String identificador = panelRegistrarTratamiento.getIdentificador();

        int contador = 0;

        if (tratamiento == null) {
            panelRegistrarTratamiento.setLblMensajeTratamientos("Ingresa un tratamiento");
            contador++;
        } else {
            panelRegistrarTratamiento.setLblMensajeTratamientos("");
        }

        if (medicamento == null) {
            panelRegistrarTratamiento.setLblMensajeMedicamento("Ingresa un medicamento");
            contador++;
        } else {
            panelRegistrarTratamiento.setLblMensajeMedicamento("");
        }

        if (dosis == null) {
            panelRegistrarTratamiento.setLblMensajeDosis("Ingresa la dosis");
            contador++;
        } else {
            panelRegistrarTratamiento.setLblMensajeDosis("");
        }

        if (fecha == null) {
            panelRegistrarTratamiento.setLblMensajeFecha("Ingresa la fecha");
            contador++;
        } else {
            panelRegistrarTratamiento.setLblMensajeFecha("");
        }

        if (identificador == null) {
            panelRegistrarTratamiento.setLblMensajeIdTratamiento("Ingresa el identificador");
            contador++;
        } else {
            panelRegistrarTratamiento.setLblMensajeIdTratamiento("");
        }

        if (contador == 0) {
            return new TratamientoVeterinario(
                tratamiento,
                medicamento,
                dosis,
                fecha,
                observaciones,
                identificador
            );
        }

        return null;
    }
    
    private void configCmb(JComboBox cmb, String item) {
        cmb.removeAllItems();
        cmb.addItem(item);
    }
}
