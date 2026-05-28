package controlador;

//@autor: Brayan C

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import vista.PanelGestionarGanado;
import vista.ScreenManager;
import vista.VistaPrincipal;


public class ControladorGestionarGanado implements KeyListener, ActionListener {
    private final PanelGestionarGanado panelListaGanado;
    private final VistaPrincipal vistaPrincipal;
    private final VacaDAO vacaDAO;
    private final Usuario usuario;

    public ControladorGestionarGanado(PanelGestionarGanado panelListaGanado, VacaDAO vacaDAO, Usuario usuario, VistaPrincipal vistaPrincipal) {
        this.panelListaGanado = panelListaGanado;
        this.vacaDAO = vacaDAO;
        this.usuario = usuario;
        this.vistaPrincipal = vistaPrincipal;
        cargarDatosIniciales();
        configurarEventos();
    }
    
    public void configurarEventos() {
        panelListaGanado.getTxtFiltroPorNombre().addKeyListener(this);
        
        panelListaGanado.getBtnBuscar().addActionListener(this);
        panelListaGanado.getBtnEditar().addActionListener(this);
        panelListaGanado.getBtnVerInformacion().addActionListener(this);
    }
    
    public void cargarDatosIniciales() {
        configurarTabla();
        llenarTabla();
    }
    
    public void configurarTabla() {

        JTable tabla = panelListaGanado.getTablaGanado();

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
        tabla.getColumnModel().getColumn(0).setPreferredWidth(60);   // Codigo
        tabla.getColumnModel().getColumn(1).setPreferredWidth(180);  // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(70);   // Edad
        tabla.getColumnModel().getColumn(3).setPreferredWidth(260);  // Raza
        tabla.getColumnModel().getColumn(4).setPreferredWidth(140);  // Estado
        
        // UUID oculto
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
    
    public void llenarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) panelListaGanado.getTablaGanado().getModel();
        
        modeloTabla.setRowCount(0);
        
        Object[] fila = new Object[6];
        
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {
            if (vaca.getEstado().equals("Enfermo") || vaca.getEstado().equals("Vendido")) {
                continue;
            }
            
            Period periodo = Period.between(vaca.getFechaNacimiento(), LocalDate.now());

            String edad = periodo.getYears() + " años y " + periodo.getMonths() + " meses";
            
            
            String raza = vaca.getRazaMadre() + " " + vaca.getRazaPadre();
            fila[0] = vaca.getIdentificador();
            fila[1] = vaca.getNombre();
            fila[2] = edad;
            fila[3] = raza;
            fila[4] = vaca.getEstado();
            fila[5] = vaca.getIdInterno();
            modeloTabla.addRow(fila);
        }
    }
    
    public void filtrarTablaPorNombreVaca() {
        String nombreVaca = panelListaGanado.getNombreAFiltrar();
        
        if (nombreVaca == null || nombreVaca.trim().isEmpty()) {
            llenarTabla();
            return;
        }
        
        DefaultTableModel modeloTabla = (DefaultTableModel) panelListaGanado.getTablaGanado().getModel();
        
        modeloTabla.setRowCount(0);
        
        Object[] fila = new Object[6];
        
        for (Vaca vaca : vacaDAO.filtrarVacasPorCoincidencia(usuario.getIdInterno(), nombreVaca)) {
            int edad = LocalDate.now().getYear() - vaca.getFechaNacimiento().getYear();
            String raza = vaca.getRazaMadre() + " " + vaca.getRazaPadre();
            fila[0] = vaca.getIdentificador();
            fila[1] = vaca.getNombre();
            fila[2] = edad;
            fila[3] = raza;
            fila[4] = vaca.getEstado();
            fila[5] = vaca.getIdInterno();
            modeloTabla.addRow(fila);
        }
    }
    
    public void mostrarInformacion() {
        JOptionPane.showMessageDialog(vistaPrincipal, llenarInformacion());
    }
    
    public Vaca retornarVacaSeleccionada() {
        DefaultTableModel modeloTabla = (DefaultTableModel) panelListaGanado.getTablaGanado().getModel();
        
        int row = panelListaGanado.getTablaGanado().getSelectedRow();
        
        if (row == -1) {
            return null;
        }
        
        UUID idVaca = UUID.fromString(modeloTabla.getValueAt(row, 5).toString());
        
        return vacaDAO.getVacaPorId(idVaca, usuario.getIdInterno());
        
    }
    
    public String llenarInformacion() {
        Vaca vaca = retornarVacaSeleccionada();
        
        if (vaca == null) {
            return "No hay fila seleccionada";
        }

        String informacion = "=======INFORMACIÓN==========";

        Double peso = vaca.getPeso();
        String pesoString = (peso == null) ? "No se registro peso." : String.valueOf(peso);
        
        informacion += "\nId: " + vaca.getIdentificador()+ 
                "\nNombre: " + vaca.getNombre() +
                "\nFecha de nacimiento: " +
                vaca.getFechaNacimiento().getDayOfMonth() + 
                "/" + vaca.getFechaNacimiento().getMonthValue() +
                "/" + vaca.getFechaNacimiento().getYear() +
                "\nRaza: " + vaca.getRazaMadre() + " x " + vaca.getRazaPadre() + 
                "\nEstado: " + vaca.getEstado() +
                "\nPeso(Kg): " + pesoString +
                "\nDescripcion: \n" + vaca.getDescripcion();
        return informacion;
        
    }
    
    public void editarGanado() {
        Vaca vaca = retornarVacaSeleccionada();
        
        if (vaca == null) {
            JOptionPane.showMessageDialog(panelListaGanado, "Selecciona una fila.");
            return;
        }
        
        ScreenManager.abrirDialogEdicionGanado(vistaPrincipal, vaca);
        llenarTabla();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarTablaPorNombreVaca();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelListaGanado.getBtnBuscar()) {
            filtrarTablaPorNombreVaca();
        } else if (e.getSource() == panelListaGanado.getBtnVerInformacion()) {
            mostrarInformacion();
        } else if (e.getSource() == panelListaGanado.getBtnEditar()) {
            editarGanado();
        }
    }
}
