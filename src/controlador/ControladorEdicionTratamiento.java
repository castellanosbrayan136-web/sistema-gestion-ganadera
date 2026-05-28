package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.TratamientoVeterinario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.DialogEdicionTratamiento;


public class ControladorEdicionTratamiento implements ActionListener {
    private final DialogEdicionTratamiento dialogEdicionTratamiento;
    private final TratamientoVeterinario tratamiento;
    private final Vaca vaca;
    private final VacaDAO vacaDAO;
    

    public ControladorEdicionTratamiento(DialogEdicionTratamiento dialogEdicionTratamiento, TratamientoVeterinario tratamiento, VacaDAO vacaDAO, Vaca vaca) {
        this.dialogEdicionTratamiento = dialogEdicionTratamiento;
        this.tratamiento = tratamiento;
        this.vacaDAO = vacaDAO;
        this.vaca = vaca;
        llenarDatos();
        configurarEventos();
    }
    
    public void configurarEventos() {
        dialogEdicionTratamiento.getBtnGuardar().addActionListener(this);
        dialogEdicionTratamiento.getBtnEliminar().addActionListener(this);
        dialogEdicionTratamiento.getBtnCancelar().addActionListener(this);
    }
    
    public void editarTratamiento() {
        TratamientoVeterinario tratamientoActualizado = construirTratamientoVeterinario();
        
        if (tratamientoActualizado == null) {
            return;
        }
        
        if (vacaDAO.updateTratamiento(tratamientoActualizado, vaca.getIdInterno())) {
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
        
        if (vacaDAO.deleteTratamientoPorId(vaca.getIdInterno(), tratamiento.getIdInterno())) {
            JOptionPane.showMessageDialog(dialogEdicionTratamiento, "Registro eliminado.");
            dialogEdicionTratamiento.dispose();
        } else {
            JOptionPane.showMessageDialog(dialogEdicionTratamiento, "Error al eliminar");
            dialogEdicionTratamiento.dispose();
        }
    }
    
    public void llenarDatos() {
        dialogEdicionTratamiento.setLblId(tratamiento.getIdentificador());
        dialogEdicionTratamiento.setLblFechaTratamiento(tratamiento.getFecha().toString());
        dialogEdicionTratamiento.setTxtMedicamento(tratamiento.getMedicamento());
        dialogEdicionTratamiento.setTxtDosis(tratamiento.getDosis());
        dialogEdicionTratamiento.setTxtObservaciones(tratamiento.getObservaciones());
        dialogEdicionTratamiento.getJcbTratamiento().addItem(tratamiento.getTipo());
        llenarComboTratamiento();
    }
    
    public void llenarComboTratamiento() {
        List<String> tratamientos = new ArrayList<>(
            Arrays.asList(
                "Vacunación",
                "Desparasitación",
                "Desgarrapatización",
                "Vitaminización",
                "Antibiótico",
                "Tratamiento respiratorio",
                "Tratamiento digestivo",
                "Curación de heridas",
                "Control de fiebre",
                "Control de mastitis",
                "Suplementación mineral",
                "Control de parásitos",
                "Inseminación",
                "Control reproductivo",
                "Revisión veterinaria",
                "Cirugía"
            )
        );
        
        for (String tipoTratamiento : tratamientos) {
            if (tipoTratamiento.equals(tratamiento.getTipo())) {
                continue;
            }
            dialogEdicionTratamiento.getJcbTratamiento().addItem(tipoTratamiento);
        }
    }
    
    public TratamientoVeterinario construirTratamientoVeterinario() {

        String tipo = dialogEdicionTratamiento.getTratamiento();
        String medicamento = dialogEdicionTratamiento.getMedicamento();
        String dosis = dialogEdicionTratamiento.getDosis();
        String observaciones = dialogEdicionTratamiento.getObservaciones();

        LocalDate fecha = tratamiento.getFecha();
        String identificador = tratamiento.getIdentificador();

        if (tipo == null || medicamento == null
                || dosis == null || observaciones == null) {

            JOptionPane.showMessageDialog(
                    dialogEdicionTratamiento,
                    "Revise y complete correctamente los datos."
            );

            return null;
        }

        return new TratamientoVeterinario(
                tipo,
                medicamento,
                dosis,
                fecha,
                observaciones,
                identificador
        );
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
