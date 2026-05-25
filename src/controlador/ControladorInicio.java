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
import modelo.ProduccionDia;
import modelo.RegistroProduccion;
import modelo.Usuario;
import modelo.Vaca;
import modelo.VacaDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import vista.PanelInicio;


public class ControladorInicio {
    PanelInicio panelInicio;
    Usuario usuario;
    VacaDAO vacaDAO;

    public ControladorInicio(PanelInicio panelInicio, Usuario usuario, VacaDAO vacaDAO) {
        this.panelInicio = panelInicio;
        this.usuario = usuario;
        this.vacaDAO = vacaDAO;
        generarGrafica();
    }
    
    public List<ProduccionDia> produccionesUltimos15Dias() {
        
        

        Map<LocalDate, Integer> produccionPorDia = new HashMap<>();

        for (Vaca vaca : vacaDAO.retornarListaVacasPorUsuario(usuario.getNombreDeUsuario())) {

            for (RegistroProduccion produccion : vaca.getRegistroProducciones()) {
                
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
            datos.setValue(produccionDia.getLitrosTotales(), "Produccion", produccionDia.getFecha());
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
}
