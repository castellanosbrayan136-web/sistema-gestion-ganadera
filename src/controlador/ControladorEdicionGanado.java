package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.DialogEdicionGanado;
import vista.VistaPrincipal;


public class ControladorEdicionGanado implements ActionListener {
    private final VistaPrincipal vistaPrincipal;
    private final DialogEdicionGanado dialogEdicionGanado;
    private final VacaDAO vacaDAO;
    private final Vaca vaca;

    public ControladorEdicionGanado(VistaPrincipal vistaPrincipal, DialogEdicionGanado dialogEdicionGanado, VacaDAO vacaDAO, Vaca vaca) {
        this.vistaPrincipal = vistaPrincipal;
        this.dialogEdicionGanado = dialogEdicionGanado;
        this.vacaDAO = vacaDAO;
        this.vaca = vaca;
        configurarEventos();
        cargarDatosIniciales();
    }
    
    public void configurarEventos() {
        dialogEdicionGanado.getBtnCancelar().addActionListener(this);
        dialogEdicionGanado.getBtnConfirmar().addActionListener(this);
    }
    
    public void cargarDatosIniciales() {
        dialogEdicionGanado.setLblIdentificador(vaca.getIdentificador());
        dialogEdicionGanado.setLblFechaNacimiento(vaca.getFechaNacimiento().toString());
        dialogEdicionGanado.setTxtNombre(vaca.getNombre());
        dialogEdicionGanado.getCmbEstado().addItem(vaca.getEstado());
        dialogEdicionGanado.getCmbRazaMadre().addItem(vaca.getRazaMadre());
        dialogEdicionGanado.getCmbRazaPadre().addItem(vaca.getRazaPadre());
        dialogEdicionGanado.setTxtPeso(vaca.getPeso());
        dialogEdicionGanado.setTxtDescripcion(vaca.getDescripcion());
        cargarCombos();
    }
    
    public void cargarCombos() {
        List<String> estados = new ArrayList<>(
            Arrays.asList(
                "Activo",
                "Produciendo",
                "Gestación",
                "Enfermo",
                "Vendido",
                "Muerto"
            )
        );
        
        for (String estado : estados) {
            if (estado.equals(vaca.getEstado())) {
                continue;
            }
            dialogEdicionGanado.getCmbEstado().addItem(estado);
        }
        
        for (String raza : vacaDAO.getListaRazas()) {
            if (raza.equals(vaca.getRazaMadre())) {
                continue;
            }
            dialogEdicionGanado.getCmbRazaMadre().addItem(raza);
        }
        
        for (String raza : vacaDAO.getListaRazas()) {
            if (raza.equals(vaca.getRazaPadre())) {
                continue;
            }
            dialogEdicionGanado.getCmbRazaPadre().addItem(raza);
        }
    }
    
    public Vaca construirNuevaVaca() {
        String identificador = vaca.getIdentificador();
        LocalDate fecha = vaca.getFechaNacimiento();
        String nombre = dialogEdicionGanado.getNombre();
        String razaPadre = dialogEdicionGanado.getRazaPadre();
        String razaMadre = dialogEdicionGanado.getRazaMadre();
        String estado = dialogEdicionGanado.getEstado();
        Double peso = dialogEdicionGanado.getPeso();
        String descripcion = dialogEdicionGanado.getDescripcion();

        if (nombre == null || razaPadre == null || razaMadre == null
                || estado == null) {

            JOptionPane.showMessageDialog(dialogEdicionGanado,
                    "Revise y complete correctamente los datos.");
            return null;
        }
        
        return new Vaca(identificador, nombre, fecha, razaPadre, razaMadre, estado, peso, descripcion, vaca.getIdPropietario());
    }
    
    public void editarDatos() {
        Vaca nuevaVaca = construirNuevaVaca();
        
        if (nuevaVaca == null) {
            return;
        }
        
        if (vacaDAO.updateVaca(nuevaVaca, vaca.getIdInterno())) {
            JOptionPane.showMessageDialog(vistaPrincipal, "Se ha editado correctamente.");
            dialogEdicionGanado.dispose();
        } else {
            JOptionPane.showMessageDialog(vistaPrincipal, "Error, completa los datos.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dialogEdicionGanado.getBtnConfirmar()) {
            editarDatos();
        } else if (e.getSource() == dialogEdicionGanado.getBtnCancelar()) {
            dialogEdicionGanado.dispose();
        }
    }
}
