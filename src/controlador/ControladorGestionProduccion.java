package controlador;

//@autor: Brayan C

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.Produccion;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelGestionProduccion;
import vista.ScreenManager;
import vista.VistaPrincipal;


public class ControladorGestionProduccion implements ActionListener, MouseListener{
    private final VistaPrincipal vistaPrincipal;
    private final PanelGestionProduccion panelGestionProduccion;
    private final Usuario usuario;
    private final VacaDAO vacaDAO;

    public ControladorGestionProduccion(VistaPrincipal vistaPrincipal, PanelGestionProduccion panelGestionProduccion, Usuario usuario, VacaDAO vacaDAO) {
        this.vistaPrincipal = vistaPrincipal;
        this.panelGestionProduccion = panelGestionProduccion;
        this.usuario = usuario;
        this.vacaDAO = vacaDAO;
        activarEventos();
        cargarDatosIniciales();
    }
    
    public void activarEventos() {
        panelGestionProduccion.getJcbAnimal().addActionListener(this);
        panelGestionProduccion.getTablaProduccion().addMouseListener(this);
    }
    
    public void cargarDatosIniciales() {
        configurarTabla();
        cargarComboGanado();
    }
    
    public void cargarComboGanado() {
        panelGestionProduccion.getJcbAnimal().removeAllItems();
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {
            panelGestionProduccion.getJcbAnimal().addItem(vaca);
        }
    }
    
    public void configurarTabla() {

        JTable tabla = panelGestionProduccion.getTablaProduccion();

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
        tabla.getColumnModel().getColumn(0).setPreferredWidth(120);   
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);  
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);   
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);

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

        // Centrar encabezados
        DefaultTableCellRenderer headerRenderer =
                (DefaultTableCellRenderer) header.getDefaultRenderer();

        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Cursor tipo mano
        tabla.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Quitar borde feo de focus
        tabla.setFocusable(false);
    }
    
    public void llenarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) panelGestionProduccion.getTablaProduccion().getModel();
        Vaca vaca = panelGestionProduccion.getVaca();
        
        if (vaca == null) {
            return;
        }
        
        modeloTabla.setRowCount(0);
        
        Object[] fila = new Object[4];
        List<Produccion> producciones = new ArrayList<>(vaca.getRegistroProducciones());
        
        Collections.reverse(producciones);
        
        for (Produccion produccion : producciones) {
            int litrosManana = produccion.getLitrosMañana() != null ? produccion.getLitrosMañana() : 0;
            int litrosTarde = produccion.getLitrosTarde() != null ? produccion.getLitrosTarde() : 0;
            
            fila[0] = produccion.getFecha();
            fila[1] = litrosManana;
            fila[2] = litrosTarde;
            fila[3] = litrosManana + litrosTarde;
            modeloTabla.addRow(fila);
        }
    }
    
    public Produccion obtenerRegistroProduccionSeleccionado() {
        JTable tabla = panelGestionProduccion.getTablaProduccion();
        
        int filaSeleccionada = tabla.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            return null;
        }
        
        LocalDate fechaSeleccionada = LocalDate.parse(tabla.getValueAt(filaSeleccionada, 0).toString());
        
        return vacaDAO.getProduccionPorFecha(panelGestionProduccion.getVaca().getIdInterno(), fechaSeleccionada);
    }
    
    public void editarProduccion() {
        Produccion produccion = obtenerRegistroProduccionSeleccionado();
        
        if (produccion == null) {
            JOptionPane.showMessageDialog(vistaPrincipal, "selecciona un elemento de la tabla");
            return;
        }
        Vaca vaca = panelGestionProduccion.getVaca();
        ScreenManager.abrirDialogEdicionProduccion(vistaPrincipal, produccion, vaca);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelGestionProduccion.getJcbAnimal()) {
            llenarTabla();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
            editarProduccion();
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
