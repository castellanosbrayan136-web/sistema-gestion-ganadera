package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.TratamientoVeterinario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.DialogEdicionGanado;
import vista.DialogEdicionTratamiento;


public class ControladorEdicionTratamiento implements ActionListener {
    private DialogEdicionTratamiento dialogEdicionTratamiento;
    private TratamientoVeterinario tratamiento;
    private Vaca vacaAEditarTratamiento;
    private VacaDAO vacaDAO;
    

    public ControladorEdicionTratamiento(DialogEdicionTratamiento dialogEdicionTratamiento, TratamientoVeterinario tratamiento, VacaDAO vacaDAO, Vaca vacaAEditarTratamiento) {
        this.dialogEdicionTratamiento = dialogEdicionTratamiento;
        this.tratamiento = tratamiento;
        this.vacaDAO = vacaDAO;
        this.vacaAEditarTratamiento = vacaAEditarTratamiento;
        llenarDatos();
        activarEventos();
    }
    
    public void activarEventos() {
        dialogEdicionTratamiento.getBtnGuardar().addActionListener(this);
        dialogEdicionTratamiento.getBtnEliminar().addActionListener(this);
        dialogEdicionTratamiento.getBtnCancelar().addActionListener(this);
    }
    
    public void editarTratamiento() {
        if (vacaDAO.editarTratamiento(vacaAEditarTratamiento, leerDatos())) {
            JOptionPane.showMessageDialog(dialogEdicionTratamiento, "Editado correctamente.");
            dialogEdicionTratamiento.dispose();
        } else {
            JOptionPane.showMessageDialog(dialogEdicionTratamiento, "Error al editar, completa los datos.");
        }
    }
    
    public void eliminarTratamiento() {
        int confirmacion = JOptionPane.showConfirmDialog(dialogEdicionTratamiento, "Estas seguro de eliminar este registro?");
        
        switch (confirmacion) {
            case JOptionPane.NO_OPTION -> {
                return;
            }
            case JOptionPane.CANCEL_OPTION -> {
                return;
            }
            case JOptionPane.CLOSED_OPTION -> {
                return;
            }
            default -> {
            }
        }
        
        if (vacaDAO.eliminarRegistroTratamiento(vacaAEditarTratamiento, tratamiento.getId())) {
            JOptionPane.showMessageDialog(dialogEdicionTratamiento, "Registro eliminado.");
            dialogEdicionTratamiento.dispose();
        } else {
            JOptionPane.showMessageDialog(dialogEdicionTratamiento, "Error al eliminar");
            dialogEdicionTratamiento.dispose();
        }
    }
    
    public void llenarDatos() {
        dialogEdicionTratamiento.setJblId(tratamiento.getId());
        dialogEdicionTratamiento.setJblFechaTratamiento(tratamiento.getFecha().toString());
        dialogEdicionTratamiento.setTxtMedicamento(tratamiento.getMedicamento());
        dialogEdicionTratamiento.setTxtDosis(tratamiento.getDosis());
        dialogEdicionTratamiento.setTxtObservaciones(tratamiento.getObservaciones());
        dialogEdicionTratamiento.getJcbTratamiento().addItem(tratamiento.getTipo());
        llenarComboTratamiento();
    }
    
    public void llenarComboTratamiento() {
        List<String> tratamientos = new ArrayList<>(
    Arrays.asList(
        "VACUNACION",
        "DESPARASITACION",
        "DESGARRAPATIZACION",
        "VITAMINIZACION",
        "ANTIBIOTICO",
        "TRATAMIENTO RESPIRATORIO",
        "TRATAMIENTO DIGESTIVO",
        "CURACION HERIDAS",
        "CONTROL FIEBRE",
        "CONTROL MASTITIS",
        "SUPLEMENTACION MINERAL",
        "CONTROL PARASITOS",
        "INSEMINACION",
        "CONTROL REPRODUCTIVO",
        "REVISION VETERINARIA",
        "CIRUGIA"
    )
);
        for (String tipoTratamiento : tratamientos) {
            if (tipoTratamiento.equals(tratamiento.getTipo())) {
                continue;
            }
            dialogEdicionTratamiento.getJcbTratamiento().addItem(tipoTratamiento);
        }
    }
    
    public TratamientoVeterinario leerDatos() {
        return new TratamientoVeterinario((String) dialogEdicionTratamiento.getJcbTratamiento().getSelectedItem(), dialogEdicionTratamiento.getMedicamento(), dialogEdicionTratamiento.getDosis(), tratamiento.getFecha(), dialogEdicionTratamiento.getObservaciones(), tratamiento.getId());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dialogEdicionTratamiento.getBtnGuardar()) {
            editarTratamiento();
        } else if (e.getSource() == dialogEdicionTratamiento.getBtnEliminar()) {
            eliminarTratamiento();
        } else if (e.getSource() == dialogEdicionTratamiento.getBtnCancelar()) {
            dialogEdicionTratamiento.dispose();
        }
    }
    
}
