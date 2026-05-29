package controlador;

//@autor: Brayan C

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.Produccion;
import modelo.ProduccionDia;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelHistorialGeneral;


public class ControladorHistorialGeneral {
    private final PanelHistorialGeneral panelHistorialGeneral;
    private final VacaDAO vacaDAO;
    private final Usuario usuario;

    public ControladorHistorialGeneral(PanelHistorialGeneral panelHistorialGeneral, VacaDAO vacaDAO, Usuario usuario) {
        this.panelHistorialGeneral = panelHistorialGeneral;
        this.vacaDAO = vacaDAO;
        this.usuario = usuario;
        cargarConfiguracionesYDatosIniciales();
    }
    
    private void cargarConfiguracionesYDatosIniciales() {
        configurarTabla();
        llenarTabla();
    }
    
    private void configurarTabla() {

        JTable tabla = panelHistorialGeneral.getTblHistorialGeneral();

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
    
    private void llenarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) panelHistorialGeneral.getTblHistorialGeneral().getModel();
        
        modeloTabla.setRowCount(0);
        
        List<ProduccionDia> produccionesTotales = getProduccionesTotales();
        
        Collections.reverse(produccionesTotales);
        
        if (produccionesTotales.isEmpty()) {
            return;
        }
        
        Object[] fila = new Object[4];
        
        for (ProduccionDia produccionDia : produccionesTotales) {
            fila[0] = produccionDia.getFecha();
            fila[1] = produccionDia.getLitrosMañana();
            fila[2] = produccionDia.getLitrosTarde();
            fila[3] = produccionDia.getLitrosTotales();
            modeloTabla.addRow(fila);
        }
    }
    
    private List<ProduccionDia> getProduccionesTotales() {

        List<ProduccionDia> lista = new ArrayList<>();

        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {

            for (Produccion produccion : vaca.getRegistroProducciones()) {
                
                int litrosManana = produccion.getLitrosMañana() != null ? produccion.getLitrosMañana() : 0;
                int litrosTarde = produccion.getLitrosTarde() != null ? produccion.getLitrosTarde() : 0;

                boolean existe = false;

                for (ProduccionDia produccionDia : lista) {

                    if (produccion.getFecha().equals(produccionDia.getFecha())) {

                        produccionDia.setLitrosMañana(
                                produccionDia.getLitrosMañana()
                                + litrosManana);

                        produccionDia.setLitrosTarde(
                                produccionDia.getLitrosTarde()
                                + litrosTarde);

                        produccionDia.setLitrosTotales(
                                produccionDia.getLitrosMañana()
                                + produccionDia.getLitrosTarde());

                        existe = true;

                        break;
                    }
                }

                if (!existe) {

                    lista.add(new ProduccionDia(
                            produccion.getFecha(),
                            litrosManana,
                            litrosTarde,
                            litrosManana
                            + litrosTarde
                    ));
                }
            }
        }

        return lista;
    }
}
