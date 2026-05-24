package controlador;

//@autor: Brayan C

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.table.DefaultTableModel;
import modelo.RegistroProduccion;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelGestionProduccion;
import vista.VistaPrincipal;


public class ControladorGestionProduccion implements ActionListener, MouseListener{
    private VistaPrincipal vistaPrincipal;
    private PanelGestionProduccion panelGestionProduccion;
    private Usuario usuario;
    private VacaDAO vacaDAO;

    public ControladorGestionProduccion(VistaPrincipal vistaPrincipal, PanelGestionProduccion panelGestionProduccion, Usuario usuario, VacaDAO vacaDAO) {
        this.vistaPrincipal = vistaPrincipal;
        this.panelGestionProduccion = panelGestionProduccion;
        this.usuario = usuario;
        this.vacaDAO = vacaDAO;
        activarEventos();
        llenarComboGanado();
    }
    
    public void activarEventos() {
        panelGestionProduccion.getJcbAnimal().addActionListener(this);
        panelGestionProduccion.getTablaProduccion().addMouseListener(this);
    }
    
    public void llenarComboGanado() {
        for (Vaca vaca : vacaDAO.retornarListaVacasPorUsuario(usuario.getNombreDeUsuario())) {
            panelGestionProduccion.getJcbAnimal().addItem(vaca);
        }
    }
    
    public Vaca retornarVacaSeleccionada() {
        return (Vaca) panelGestionProduccion.getJcbAnimal().getSelectedItem();
    }
    
    public void llenarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) panelGestionProduccion.getTablaProduccion().getModel();
        
        modeloTabla.setRowCount(0);
        
        Object[] fila = new Object[5];
        
        for (RegistroProduccion produccion : retornarVacaSeleccionada().getRegistroProducciones()) {
//            fila[0] = produccion.getId();
//            fila[1] = produccion.getJornada();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelGestionProduccion.getJcbAnimal()) {
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
            
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
