package controlador;

//@autor: Brayan C

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.TratamientoVeterinario;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelGestionSanidad;
import vista.ScreenManager;
import vista.VistaPrincipal;


public class ControladorGestionSanidad implements ActionListener, MouseListener {
    private final VistaPrincipal vistaPrincipal;
    private final PanelGestionSanidad panelGestionSanidad;
    private final Usuario usuario;
    private final VacaDAO vacaDAO;

    public ControladorGestionSanidad(PanelGestionSanidad panelGestionSanidad,Usuario usuario, VacaDAO vacaDAO, VistaPrincipal vistaPrincipal) {
        this.panelGestionSanidad = panelGestionSanidad;
        this.usuario = usuario;
        this.vacaDAO = vacaDAO;
        this.vistaPrincipal = vistaPrincipal;
        cargarDatosIniciales();
        configurarEventos();
    }
    
    public TratamientoVeterinario getTratamientoSeleccionado() {
        DefaultTableModel modeloTabla = (DefaultTableModel) panelGestionSanidad.getTablaSanidad().getModel();
        
        int filaSeleccionada = panelGestionSanidad.getTablaSanidad().getSelectedRow();
        
        if (filaSeleccionada == -1) {
            return null;
        }
        
        UUID idTratamiento = UUID.fromString(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
        
        return vacaDAO.getTratamientoDeVacaPorId(panelGestionSanidad.getVaca().getIdInterno(), idTratamiento);
    }
    
    public void configurarEventos() {
        panelGestionSanidad.getJcbAnimal().addActionListener(this);
        panelGestionSanidad.getTablaSanidad().addMouseListener(this);
    }
    
    public void cargarDatosIniciales() {
        configurarTabla();
        llenarTablaTratamientos();
        llenarComboGanado();
    }
    
        public void configurarTabla() {

        JTable tabla = panelGestionSanidad.getTablaSanidad();

        // Altura de filas
        tabla.setRowHeight(32);

        // Seleccionar fila completa
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(false);

        // NO permitir editar
        tabla.setDefaultEditor(Object.class, null);

        // Diseño limpio
        tabla.setShowVerticalLines(true);
        tabla.setShowHorizontalLines(true);

        // Espaciado elegante
        tabla.setIntercellSpacing(new Dimension(0, 1));

        // Que ocupe todo el ancho automaticamente
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Distribucion proporcional columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);   // Id
        tabla.getColumnModel().getColumn(1).setPreferredWidth(180);  // Tratamiento
        tabla.getColumnModel().getColumn(2).setPreferredWidth(180);   // Medicamento
        tabla.getColumnModel().getColumn(3).setPreferredWidth(140);  // Dosis
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);  // Fecha
        
        tabla.getColumnModel().getColumn(5).setMinWidth(0);
        tabla.getColumnModel().getColumn(5).setMaxWidth(0);
        tabla.getColumnModel().getColumn(5).setWidth(0);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(0);
        tabla.getColumnModel().getColumn(5).setResizable(false);

        // Fuente
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Header
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        // Renderer centrado
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);

        // Centrar columnas
        tabla.getColumnModel().getColumn(0).setCellRenderer(centro);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centro);
        tabla.getColumnModel().getColumn(2).setCellRenderer(centro);
        tabla.getColumnModel().getColumn(3).setCellRenderer(centro);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centro);

        // Centrar encabezados
        DefaultTableCellRenderer headerRenderer =
                (DefaultTableCellRenderer) header.getDefaultRenderer();

        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Cursor tipo mano
        tabla.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Quitar borde feo de focus
        tabla.setFocusable(false);
    }
        
    public void llenarTablaTratamientos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) panelGestionSanidad.getTablaSanidad().getModel();
        
        modeloTabla.setRowCount(0);
        
        Object[] fila = new Object[6];
        
        Vaca vaca = panelGestionSanidad.getVaca();
        
        if (vaca == null) {
            return;
        }
        
        List<TratamientoVeterinario> lista =  new ArrayList<>(vaca.getHistorialTratamientos());
        
        Collections.reverse(lista);
        
        for (TratamientoVeterinario tratamiento : lista) {
            fila[0] = tratamiento.getIdentificador();
            fila[1] = tratamiento.getTipo();
            fila[2] = tratamiento.getMedicamento();
            fila[3] = tratamiento.getDosis();
            fila[4] = tratamiento.getFecha();
            fila[5] = tratamiento.getIdInterno();
            modeloTabla.addRow(fila);
        }
    }
    
    public void llenarComboGanado() {
        panelGestionSanidad.getJcbAnimal().removeAllItems();
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {
            panelGestionSanidad.getJcbAnimal().addItem(vaca);
        }
    }
    
    public void editarTratamiento() {
        TratamientoVeterinario tratamiento = getTratamientoSeleccionado();
        
        if (tratamiento == null) {
            JOptionPane.showMessageDialog(panelGestionSanidad, "Selecciona un tratamiento en la tabla");
            return;
        }
        
        ScreenManager.abrirDialogEdicionTratamiento(vistaPrincipal, panelGestionSanidad.getVaca(), tratamiento);
        llenarTablaTratamientos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelGestionSanidad.getJcbAnimal()) {
            llenarTablaTratamientos();
        } 
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
            editarTratamiento();
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
