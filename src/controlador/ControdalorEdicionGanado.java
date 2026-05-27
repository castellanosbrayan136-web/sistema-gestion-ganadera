package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.DialogEdicionGanado;
import vista.VistaPrincipal;


public class ControdalorEdicionGanado implements ActionListener {
    private final VistaPrincipal vistaPrincipal;
    private final DialogEdicionGanado dialogEdicionGanado;
    private final VacaDAO vacaDAO;
    private final Vaca vaca;

    public ControdalorEdicionGanado(VistaPrincipal vistaPrincipal, DialogEdicionGanado dialogEdicionGanado, VacaDAO vacaDAO, Vaca vaca) {
        this.vistaPrincipal = vistaPrincipal;
        this.dialogEdicionGanado = dialogEdicionGanado;
        this.vacaDAO = vacaDAO;
        this.vaca = vaca;
        activarEventos();
        llenarDatos();
        llenarCombos();
    }
    
    public void activarEventos() {
        dialogEdicionGanado.getBtnCancelar().addActionListener(this);
        dialogEdicionGanado.getBtnConfirmar().addActionListener(this);
    }
    
    public void llenarDatos() {
        
        dialogEdicionGanado.setLblCodigoInterno(vaca.getCodigoInterno());
        dialogEdicionGanado.setLblFechaNacimiento(vaca.getFechaNacimiento().toString());
        dialogEdicionGanado.setTxtNombre(vaca.getNombre());
        dialogEdicionGanado.getJcbEstado().addItem(vaca.getEstado());
        dialogEdicionGanado.getJcbRazaMadre().addItem(vaca.getRazaMadre());
        dialogEdicionGanado.getJcbRazaPadre().addItem(vaca.getRazaPadre());
        dialogEdicionGanado.setTxtNumeroIdentificador(vaca.getNumeroIdentificador());
        dialogEdicionGanado.setTxtPeso(vaca.getPeso());
        dialogEdicionGanado.setTxtDescripcion(vaca.getDescripcion());
    }
    
    public void llenarCombos() {
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
            dialogEdicionGanado.getJcbEstado().addItem(estado);
        }
        
        for (String raza : vacaDAO.retornarListadoDeRazas()) {
            if (raza.equals(vaca.getRazaMadre())) {
                continue;
            }
            dialogEdicionGanado.getJcbRazaMadre().addItem(raza);
        }
        
        for (String raza : vacaDAO.retornarListadoDeRazas()) {
            if (raza.equals(vaca.getRazaPadre())) {
                continue;
            }
            dialogEdicionGanado.getJcbRazaPadre().addItem(raza);
        }
    }
    
    public Vaca leerDatosNuevos() {
        return new Vaca(vaca.getCodigoInterno(),dialogEdicionGanado.getNombre(), vaca.getFechaNacimiento(), dialogEdicionGanado.getJcbRazaPadre().getSelectedItem().toString(), dialogEdicionGanado.getJcbRazaMadre().getSelectedItem().toString(), dialogEdicionGanado.getJcbEstado().getSelectedItem().toString(), dialogEdicionGanado.getDescripcion(), dialogEdicionGanado.getNumeroIdentificador(), Double.valueOf(dialogEdicionGanado.getPeso()), vaca.getDueño());
    }
    
    public void editarDatos() {
        if (vacaDAO.editarGanado(leerDatosNuevos())) {
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
