package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelRegistrarGanado;


public class ControladorRegistrarGanado implements ActionListener {
    private final Usuario usuario;
    private final PanelRegistrarGanado panelRegistrarGanado;
    private final VacaDAO vacaDAO;

    public ControladorRegistrarGanado(PanelRegistrarGanado panelRegistrarGanado, VacaDAO vacaDAO, Usuario usuario) {
        this.panelRegistrarGanado = panelRegistrarGanado;
        this.vacaDAO = vacaDAO;
        this.usuario = usuario;
        cargarDatosIniciales();
        configurarEventos();
    }
    
    private void cargarDatosIniciales() {
        llenarCmbRazas();
        llenarCmbAños();
        llenarCmbMeses();
        llenarCmbDias();
    }
    
    private void configurarEventos() {
        panelRegistrarGanado.getJcbAño().addActionListener(this);
        panelRegistrarGanado.getJcbMes().addActionListener(this);
        panelRegistrarGanado.getBtnRegistrar().addActionListener(this);
    }
    
    private void llenarCmbRazas() {
        configCmb(panelRegistrarGanado.getJcbRazaMadre(), "Raza madre");
        configCmb(panelRegistrarGanado.getJcbRazaPadre(), "Raza padre");
        
        for (String raza : vacaDAO.getListaRazas()) {
            panelRegistrarGanado.getJcbRazaMadre().addItem(raza);
            panelRegistrarGanado.getJcbRazaPadre().addItem(raza);
        }
    }
    
    private void llenarCmbMeses() {
        for (Month mes : Month.values()) {
            String mesEnEspañol = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            panelRegistrarGanado.getJcbMes().addItem(mesEnEspañol);
        }
    }
    
    public void llenarCmbAños() {
        int añoActual = LocalDate.now().getYear();
        
        for (int i = añoActual ; i >= 1990 ; i--) {
            panelRegistrarGanado.getJcbAño().addItem(String.valueOf(i));
        }
    }
    
    public void llenarCmbDias() {
        configCmb(panelRegistrarGanado.getJcbDia(), "Dia");
        LocalDate fecha = panelRegistrarGanado.getFecha();
        
        if (fecha == null) {
            return;
        }
        
        int año = fecha.getYear();
        int mes = fecha.getMonthValue();
        
        YearMonth yearMonth = YearMonth.of(año, mes);
        int diasMes = yearMonth.lengthOfMonth();
        
        for (int i = 1; i <= diasMes; i++) {
            panelRegistrarGanado.getJcbDia().addItem(String.valueOf(i));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelRegistrarGanado.getJcbAño() || e.getSource() == panelRegistrarGanado.getJcbMes()) {
            llenarCmbDias();
        }  else if (e.getSource() == panelRegistrarGanado.getBtnRegistrar()) {
            registrar();
        }
    }
    
    public void registrar() {
        if (vacaDAO.addVaca(construirVaca())) {
            JOptionPane.showMessageDialog(panelRegistrarGanado, "Registro completado.");
            reiniciarFormulario();
        } else {
            JOptionPane.showMessageDialog(panelRegistrarGanado, "Error al registrar, completa los datos.");
        }
    }
    
    public Vaca construirVaca() {
        String identificador = panelRegistrarGanado.getIdentificador();
        String nombre = panelRegistrarGanado.getNombre();
        LocalDate fecha = panelRegistrarGanado.getFecha();
        String razaPadre = panelRegistrarGanado.getRazaPadre();
        String razaMadre = panelRegistrarGanado.getRazaMadre();
        String estado = panelRegistrarGanado.getEstado();
        Double peso = panelRegistrarGanado.getPeso();
        String descripcion = panelRegistrarGanado.getDescripcion();
        UUID idPropietario = usuario.getIdInterno();
        
        int contador = 0;
        
        if (identificador == null) {
            panelRegistrarGanado.setLblMensajeIdentificador("Ingresa un identificador.");
            contador++;
        } else {
            panelRegistrarGanado.setLblMensajeIdentificador("");
        }
        
        if (nombre == null) {
            panelRegistrarGanado.setLblMensajeNombre("Ingresa un nombre");
            contador++;
        } else {
            panelRegistrarGanado.setLblMensajeNombre("");
        }
        
        if (fecha == null) {
            panelRegistrarGanado.setLblMensajeFecha("Completa la fecha");
            contador++;
        } else {
            panelRegistrarGanado.setLblMensajeFecha("");
        }
        
        if (razaMadre == null) {
            panelRegistrarGanado.setLblMensajeRazaMadre("Ingresa un raza");
            contador++;
        } else {
            panelRegistrarGanado.setLblMensajeRazaMadre("");
        }
        
        if (razaPadre == null) {
            panelRegistrarGanado.setLblMensajeRazaPadre("Ingresa un raza");
            contador++;
        } else {
            panelRegistrarGanado.setLblMensajeRazaPadre("");
        }
        
        if (estado == null) {
            panelRegistrarGanado.setLblMensajeEstado("Ingresa un estado");
            contador++;
        } else {
            panelRegistrarGanado.setLblMensajeEstado("");
        }
        
        if (contador == 0) {
            return new Vaca(identificador, nombre, fecha, razaPadre, razaMadre, estado, peso, descripcion, idPropietario);
        }
        
        return null;
    }
    
    
    private void configCmb(JComboBox cmb, String item) {
        cmb.removeAllItems();
        cmb.addItem(item);
    }
    
    private void reiniciarFormulario() {
        panelRegistrarGanado.reiniciarFormulario();
        llenarCmbDias();
    }
}
