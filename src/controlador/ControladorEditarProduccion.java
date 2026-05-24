package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.RegistroProduccion;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.DialogEdicionProduccion;
import vista.VistaPrincipal;


public class ControladorEditarProduccion implements ActionListener {
    private final DialogEdicionProduccion dialogEdicionProduccion;
    private final VistaPrincipal vistaPrincipal;
    private final VacaDAO vacaDAO;
    private final RegistroProduccion registroProduccion;
    private final Vaca vaca;

    public ControladorEditarProduccion(DialogEdicionProduccion dialogEdicionProduccion, VistaPrincipal vistaPrincipal, VacaDAO vacaDAO, RegistroProduccion registroProduccion, Vaca vaca) {
        this.dialogEdicionProduccion = dialogEdicionProduccion;
        this.vistaPrincipal = vistaPrincipal;
        this.vacaDAO = vacaDAO;
        this.registroProduccion = registroProduccion;
        this.vaca = vaca;
        activarEventos();
        llenarDatos();
    }
    
    public void activarEventos() {
        dialogEdicionProduccion.getBtnGuardar().addActionListener(this);
        dialogEdicionProduccion.getBtnCancelar().addActionListener(this);
    }
    
    public void editarDatos() {
        if (vacaDAO.editarProduccion(vaca, leerNuevosDatos())) {
            JOptionPane.showMessageDialog(vistaPrincipal, "Registro editado correctamente.");
            dialogEdicionProduccion.dispose();
        } else {
            JOptionPane.showMessageDialog(vistaPrincipal, "Completa los datos.");
        }
    }

    public RegistroProduccion leerNuevosDatos() {
        return new RegistroProduccion(registroProduccion.getFecha(), dialogEdicionProduccion.getLitrosMañana(), dialogEdicionProduccion.getLitrosTarde());
    }
    
    public void llenarDatos() {
        dialogEdicionProduccion.setJblFechaRegistro(registroProduccion.getFecha());
        dialogEdicionProduccion.setJblVaca(vaca);
        dialogEdicionProduccion.setSpinMañana(registroProduccion.getLitrosMañana());
        dialogEdicionProduccion.setSpinTarde(registroProduccion.getLitrosTarde());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dialogEdicionProduccion.getBtnGuardar()) {
            editarDatos();
        } else if (e.getSource() == dialogEdicionProduccion.getBtnCancelar()) {
            dialogEdicionProduccion.dispose();
        }
    }
    
}
