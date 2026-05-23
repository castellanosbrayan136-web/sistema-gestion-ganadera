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
import modelo.TratamientoVeterinario;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelRegistrarTratamiento;


public class ControladorRegistrarTratamiento implements ActionListener {
    
    private PanelRegistrarTratamiento panelRegistrarTratamiento;
    private VacaDAO vacaDAO;
    private Usuario usuario;

    public ControladorRegistrarTratamiento(PanelRegistrarTratamiento panelRegistrarTratamiento, VacaDAO vacaDAO, Usuario usuario) {
        this.panelRegistrarTratamiento = panelRegistrarTratamiento;
        this.vacaDAO = vacaDAO;
        this.usuario = usuario;
        llenarCombos();
        activarEventos();
    }
     
    public void registrarTratamiento() {
        System.out.println(panelRegistrarTratamiento.getAnimalAAplicar());
        if (vacaDAO.registrarTratamiento(panelRegistrarTratamiento.getAnimalAAplicar() , leerDatos())) {
            JOptionPane.showMessageDialog(panelRegistrarTratamiento, "Registro exitoso.");
        } else {
            JOptionPane.showMessageDialog(panelRegistrarTratamiento, "Error al registrar.");
        }
    }
    
    public void activarEventos() {
        panelRegistrarTratamiento.getBtnRegistrar().addActionListener(this);
        panelRegistrarTratamiento.getJcbMes().addActionListener(this);
        panelRegistrarTratamiento.getJcbAño().addActionListener(this);
    }
    
    public void llenarCombos() {
        llenarAñosYMeses();
        llenarAnimalAAplicar();
    }
    public TratamientoVeterinario leerDatos() {
        int year = Integer.parseInt(panelRegistrarTratamiento.getJcbAño().getSelectedItem().toString());
        int mes =  panelRegistrarTratamiento.getJcbMes().getSelectedIndex();
        int dia = Integer.parseInt(panelRegistrarTratamiento.getJcbDia().getSelectedItem().toString());
        
        LocalDate fecha = LocalDate.of(year, mes, dia);
        
        return new TratamientoVeterinario(panelRegistrarTratamiento.getTipoTratamiento(), panelRegistrarTratamiento.getMedicamento(), panelRegistrarTratamiento.getDosis(), fecha, panelRegistrarTratamiento.getObservaciones(), panelRegistrarTratamiento.getId());
    }

    public void llenarAñosYMeses() {
        for (Month mes : Month.values()) {
            String mesEspañol = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            panelRegistrarTratamiento.getJcbMes().addItem(mesEspañol);
        }
        
        int añoActual = LocalDate.now().getYear();
        
        for (int i = añoActual ; i >= 1950 ; i--) {
            panelRegistrarTratamiento.getJcbAño().addItem(String.valueOf(i));
        }
    }
    
    public void llenarDiasDelMes() {
        panelRegistrarTratamiento.getJcbDia().removeAllItems();
        panelRegistrarTratamiento.getJcbDia().addItem("Día");
        
        int año;
        
        try {
            año = Integer.parseInt(String.valueOf(panelRegistrarTratamiento.getJcbAño().getSelectedItem()));
        } catch (NumberFormatException e) {
            return;
        }
        int mes = panelRegistrarTratamiento.getJcbMes().getSelectedIndex();
        
        if (mes == 0) {
            panelRegistrarTratamiento.getJcbDia().removeAllItems();
            panelRegistrarTratamiento.getJcbDia().addItem("Día");
            return;
        }
        
        YearMonth yearMonth = YearMonth.of(año, mes);
        
        int cantidadDias = yearMonth.lengthOfMonth();
        
        for (int i = 1; i <= cantidadDias; i++) {
            panelRegistrarTratamiento.getJcbDia().addItem(String.valueOf(i));
        }
    }
    
    public void llenarAnimalAAplicar() {
        panelRegistrarTratamiento.getJcbAnimalAAplicar().removeAllItems();
        
        for (Vaca vaca : vacaDAO.retornarListaVacasPorUsuario(usuario.getNombreDeUsuario())) {
            panelRegistrarTratamiento.getJcbAnimalAAplicar().addItem(vaca);
        }
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelRegistrarTratamiento.getBtnRegistrar()) {
            registrarTratamiento();
        } else if (e.getSource() == panelRegistrarTratamiento.getJcbAño() || e.getSource() == panelRegistrarTratamiento.getJcbMes()) {
            llenarDiasDelMes();
        } 
    }
    
}
