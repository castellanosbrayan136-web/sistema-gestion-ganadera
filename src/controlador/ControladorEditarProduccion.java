package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import modelo.Produccion;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.DialogEdicionProduccion;
import vista.VistaPrincipal;


public class ControladorEditarProduccion implements ActionListener {
    private final DialogEdicionProduccion dialogEdicionProduccion;
    private final VistaPrincipal vistaPrincipal;
    private final VacaDAO vacaDAO;
    private final Produccion produccion;
    private final Vaca vaca;

    public ControladorEditarProduccion(DialogEdicionProduccion dialogEdicionProduccion, VistaPrincipal vistaPrincipal, VacaDAO vacaDAO, Produccion produccion, Vaca vaca) {
        this.dialogEdicionProduccion = dialogEdicionProduccion;
        this.vistaPrincipal = vistaPrincipal;
        this.vacaDAO = vacaDAO;
        this.produccion = produccion;
        this.vaca = vaca;
        configurarEventos();
        llenarDatos();
    }
    
    public void configurarEventos() {
        dialogEdicionProduccion.getBtnGuardar().addActionListener(this);
        dialogEdicionProduccion.getBtnCancelar().addActionListener(this);
    }
    
    public void editarDatos() {
        Produccion produccionActualizada = construirProduccion();
        
        if (produccionActualizada == null) {
            return;
        }
        
        if (vacaDAO.updateProduccion(vaca.getIdInterno(), produccionActualizada)) {
            JOptionPane.showMessageDialog(vistaPrincipal, "Registro editado correctamente.");
            dialogEdicionProduccion.dispose();
        } else {
            JOptionPane.showMessageDialog(vistaPrincipal, "Revise y complete correctamente los datos.");
        }
    }

    public Produccion construirProduccion() {

    LocalDate fecha = produccion.getFecha();

    Integer litrosManana = dialogEdicionProduccion.getLitrosMañana();
    Integer litrosTarde = dialogEdicionProduccion.getLitrosTarde();

    if (litrosManana == null || litrosTarde == null) {

        JOptionPane.showMessageDialog(
                dialogEdicionProduccion,
                "Revise y complete correctamente los datos."
        );

        return null;
    }

    return new Produccion(
            fecha,
            litrosManana,
            litrosTarde
    );
}
    
    public void llenarDatos() {
        int litrosManana = produccion.getLitrosMañana() != null ? produccion.getLitrosMañana() : 0;
        int litrosTarde = produccion.getLitrosTarde() != null ? produccion.getLitrosTarde() : 0;
        
        dialogEdicionProduccion.setLblFechaRegistro(produccion.getFecha());
        dialogEdicionProduccion.setLblVaca(vaca);
        dialogEdicionProduccion.setSpnMañana(litrosManana);
        dialogEdicionProduccion.setSpnTarde(litrosTarde);
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
