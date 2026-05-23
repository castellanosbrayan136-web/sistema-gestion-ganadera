package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import modelo.RegistroProduccion;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelRegistrarProduccion;


public class ControladorRegistroProduccion implements ActionListener {
    private PanelRegistrarProduccion panelRegistroProduccion;
    private VacaDAO vacaDAO;
    private Usuario usuario;

    public ControladorRegistroProduccion(PanelRegistrarProduccion panelRegistroProduccion, VacaDAO vacaDAO, Usuario usuario) {
        this.panelRegistroProduccion = panelRegistroProduccion;
        this.vacaDAO = vacaDAO;
        this.usuario = usuario;
        activarEventos();
    }

    public void activarEventos() {
        panelRegistroProduccion.getBtnRegistrar().addActionListener(this);
    }
    
    private RegistroProduccion leerDatos() {
        if (panelRegistroProduccion.getJcbMes().getSelectedIndex() == 0) {
            return null;
        }
        
        LocalDate fecha = LocalDate.of((int) panelRegistroProduccion.getJcbAño().getSelectedItem(), panelRegistroProduccion.getJcbMes().getSelectedIndex(),(int) panelRegistroProduccion.getJcbDia().getSelectedItem());
        
        return new RegistroProduccion((int) panelRegistroProduccion.getSpinLitros().getValue(),(String) panelRegistroProduccion.getJcbJornada().getSelectedItem(), fecha);
    }
    
    public void registrarProduccion() {
        if (leerDatos() == null) {
            JOptionPane.showMessageDialog(panelRegistroProduccion, "Completa los datos.");
        }
        
        if (vacaDAO.registrarProduccion(leerDatos(), vaca)) {
            JOptionPane.showMessageDialog(panelRegistroProduccion, "Registro exitoso.");
        } else {
            JOptionPane.showMessageDialog(panelRegistroProduccion, "Error al registrar.");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelRegistroProduccion.getBtnRegistrar()) {
            registrarProduccion();
        }
    }
}
