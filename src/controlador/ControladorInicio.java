package controlador;

//@autor: Brayan C

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import modelo.ProduccionDia;
import modelo.Produccion;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import vista.PanelInicio;


public class ControladorInicio {
    private final PanelInicio panelInicio;
    private final Usuario usuario;
    private final VacaDAO vacaDAO;
    private final List<String> alertas;

    public ControladorInicio(PanelInicio panelInicio, Usuario usuario, VacaDAO vacaDAO) {
        this.alertas = new ArrayList<>();
        this.panelInicio = panelInicio;
        this.usuario = usuario;
        this.vacaDAO = vacaDAO;
        cargarDatos();
    }
    
    public void cargarDatos() {
        generarGrafica();
        vacaConMenorProduccionHoy();
        produccionPromedioUltimos15Dias();
        promedioProduccionPorVacaHoy();
        totalLitrosProducidosHoy();
        alertasRegistrosHoy();
        llenarAlertas();
    }
    public List<ProduccionDia> produccionesUltimos15Dias() {
        Map<LocalDate, Integer> produccionPorDia = new HashMap<>();

        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {

            for (Produccion produccion : vaca.getRegistroProducciones()) {
                
                int litrosManana = produccion.getLitrosMañana() != null ? produccion.getLitrosMañana() : 0;
                int litrosTarde = produccion.getLitrosTarde() != null ? produccion.getLitrosTarde() : 0;
                
                LocalDate fecha = produccion.getFecha();

                int litrosTotales =
                        litrosManana
                        + litrosTarde;

                // Si ya existe esa fecha, suma
                produccionPorDia.put(
                        fecha,
                        produccionPorDia.getOrDefault(fecha, 0) + litrosTotales
                );
            }
        }

        List<ProduccionDia> lista = new ArrayList<>();

        // Convertir Map a lista
        for (Map.Entry<LocalDate, Integer> entry : produccionPorDia.entrySet()) {

            lista.add(
                    new ProduccionDia(
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        }

        // Ordenar por fecha
        lista.sort(Comparator.comparing(ProduccionDia::getFecha));

        // Obtener últimos 15
        if (lista.size() > 15) {
            return lista.subList(lista.size() - 15, lista.size());
        }

        return lista;
    }
    
    public void generarGrafica() {
        
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        
        for (ProduccionDia produccionDia : produccionesUltimos15Dias()) {
            String dia = String.valueOf(produccionDia.getFecha().getDayOfMonth());
            datos.setValue(produccionDia.getLitrosTotales(), "Produccion", dia);
        }
        
        JFreeChart grafica = ChartFactory.createLineChart(
        "Producción últimos 15 días",
        "Fecha",
        "Litros",
        datos
);
        
        ChartPanel panelGrafica = new ChartPanel(grafica);
        panelGrafica.setPreferredSize(new Dimension(1090, 400));
        
        panelInicio.getPanelGrafico().removeAll();
        panelInicio.getPanelGrafico().setLayout(new BorderLayout());
        panelInicio.getPanelGrafico().add(panelGrafica, BorderLayout.CENTER);
        panelInicio.getPanelGrafico().revalidate();
        panelInicio.getPanelGrafico().repaint();
    }
    
    public void vacaConMenorProduccionHoy() {
        LocalDate fechaHoy = LocalDate.now();
        
        Vaca vacaMenorProduccion = null;
        int menorProduccion = Integer.MAX_VALUE;
        
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {

        Produccion produccionHoy = vacaDAO.getProduccionPorFecha(vaca.getIdInterno(), fechaHoy);

        if (produccionHoy == null) {
            continue;
        }

        int litrosManana = produccionHoy.getLitrosMañana() != null ? produccionHoy.getLitrosMañana() : 0;
        int litrosTarde = produccionHoy.getLitrosTarde() != null ? produccionHoy.getLitrosTarde() : 0;

        int total = litrosManana + litrosTarde;

        if (total < menorProduccion) {
            menorProduccion = total;
            vacaMenorProduccion = vaca;
            }
        }
        if (vacaMenorProduccion == null) {
            panelInicio.setJblVacaMenorProduccion("Sin registros hoy.");
            return;
        }
        panelInicio.setJblVacaMenorProduccion(String.valueOf(vacaMenorProduccion));
    }
    
    public void produccionPromedioUltimos15Dias() {
        List<ProduccionDia> produccionUltimos15Dias = produccionesUltimos15Dias();
        
        int acumuladorProducciones = 0;
        
        for (ProduccionDia produccioDia : produccionUltimos15Dias) {
            acumuladorProducciones += produccioDia.getLitrosTotales();
        }
        
        double promedio15Dias = (double) acumuladorProducciones / produccionUltimos15Dias.size();
        String promedioString = String.format("%.2f", promedio15Dias);
        
        panelInicio.setJblPromedio15Dias(promedioString);
    }
    
    public void promedioProduccionPorVacaHoy() {
        LocalDate fechaHoy = LocalDate.now();
        List<Integer> produccionPorVacaHoy = new ArrayList<>();
        
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {
            Produccion produccionHoy = vacaDAO.getProduccionPorFecha(vaca.getIdInterno(), fechaHoy);
            
            if (produccionHoy == null) {
                continue;
            }
            
            int litrosManana = produccionHoy.getLitrosMañana() != null ? produccionHoy.getLitrosMañana() : 0;
            int litrosTarde = produccionHoy.getLitrosTarde() != null ? produccionHoy.getLitrosTarde() : 0;
            
            produccionPorVacaHoy.add(litrosManana + litrosTarde);
        }
        
        int acumulador = 0;
        
        for (int litros : produccionPorVacaHoy) {
            acumulador += litros;
        }
        
        if (produccionPorVacaHoy.isEmpty()) {
            panelInicio.setJblPromedioVacaHoy("Sin registros hoy.");
            return;
        }
        
        double promedio = (double) acumulador / produccionPorVacaHoy.size();
        String promedioString = String.format("%.2f", promedio);
        
        panelInicio.setJblPromedioVacaHoy(promedioString);
    }
    
    public void totalLitrosProducidosHoy() {
        LocalDate fechaHoy = LocalDate.now();
        int acumulador = 0;
        
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {
            Produccion produccionHoy = vacaDAO.getProduccionPorFecha(vaca.getIdInterno(), fechaHoy);
            
            if (produccionHoy == null) {
                continue;
            }
            
            int litrosManana = produccionHoy.getLitrosMañana() != null ? produccionHoy.getLitrosMañana() : 0;
            int litrosTarde = produccionHoy.getLitrosTarde() != null ? produccionHoy.getLitrosTarde() : 0;
            
            acumulador += (litrosManana + litrosTarde);
        }
        
        panelInicio.setJblRegistroTotalHoy(String.valueOf(acumulador));
    }
    
    public void llenarAlertas() {
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        
        for (String alerta : alertas) {
            modeloLista.addElement(alerta);
        }
        
        panelInicio.getListAlertas().setModel(modeloLista);
    }
    
    public void alertasRegistrosHoy() {
        LocalDate fechaHoy = LocalDate.now();
        for (Vaca vaca : vacaDAO.getVacasPorIdPropietario(usuario.getIdInterno())) {
            Produccion produccionHoy = vacaDAO.getProduccionPorFecha(vaca.getIdInterno(), fechaHoy);
            if (produccionHoy == null) {
                String alerta = "No has hecho registro hoy para: " + vaca;
                alertas.add(alerta);
                continue;
            }
            
            if (produccionHoy.getLitrosMañana() == null || produccionHoy.getLitrosTarde() == null) {
                String alerta = "No has completado el registro de produccion hoy para: " + vaca;
                alertas.add(alerta);
            }
        }
    }
}
