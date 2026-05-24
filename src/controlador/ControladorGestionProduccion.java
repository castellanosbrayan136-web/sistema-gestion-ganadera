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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.RegistroProduccion;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelGestionProduccion;
import vista.ScreenManager;
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
        configurarTabla();
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
        tabla.getColumnModel().getColumn(0).setPreferredWidth(120);   // Id
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);  // Tratamiento
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);   // Medicamento
        tabla.getColumnModel().getColumn(3).setPreferredWidth(120);  // Dosis

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
    
    public Vaca retornarVacaSeleccionada() {
        return (Vaca) panelGestionProduccion.getJcbAnimal().getSelectedItem();
    }
    
    public void llenarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) panelGestionProduccion.getTablaProduccion().getModel();
        
        modeloTabla.setRowCount(0);
        
        Object[] fila = new Object[4];
        
        for (RegistroProduccion produccion : retornarVacaSeleccionada().getRegistroProducciones()) {
            fila[0] = produccion.getFecha();
            fila[1] = produccion.getLitrosMañana();
            fila[2] = produccion.getLitrosTarde();
            fila[3] = produccion.getLitrosMañana() + produccion.getLitrosTarde();
            modeloTabla.addRow(fila);
        }
    }
    
    public RegistroProduccion obtenerRegistroProduccionSeleccionado() {
        JTable tabla = panelGestionProduccion.getTablaProduccion();
        
        int filaSeleccionada = tabla.getSelectedRow();
        
        LocalDate fechaSeleccionada = LocalDate.parse(tabla.getValueAt(filaSeleccionada, 0).toString());
        
        return vacaDAO.retornarRegistroProduccionPorFecha((Vaca) panelGestionProduccion.getJcbAnimal().getSelectedItem(), fechaSeleccionada);
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
            ScreenManager.abrirDialogEdicionProduccion(vistaPrincipal, obtenerRegistroProduccionSeleccionado(), retornarVacaSeleccionada());
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
