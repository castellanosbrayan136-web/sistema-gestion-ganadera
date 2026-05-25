package modelo;

//@autor: Brayan C

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class VacaDAO {
    private final Gson gson;
    private final String ruta;
    private final List<Vaca> listaVacas;
    private final List<String> listaRazas;
    private final String rutaRazas;

    public VacaDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.ruta = "ganado.json";
        this.listaVacas = cargarDatos();
        this.rutaRazas = "razas.json";
        this.listaRazas = cargarListadoDeRazas();
    }
    
    private void guardarDatos() {
        try (Writer writer = new FileWriter(ruta)) {
            gson.toJson(listaVacas,writer);
        } catch (IOException ex) {
            System.err.println("Error al guardar datos: " + ex.getMessage());
        }
    }
    
    private List<Vaca> cargarDatos() {
        File archivo = new File(ruta);
        
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<List<Vaca>>(){}.getType();
            List<Vaca> lista = gson.fromJson(reader, tipoLista);
            
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }
    
    private List<String> cargarListadoDeRazas() {
        File archivo = new File(rutaRazas);
        
        try (Reader lector = new FileReader(archivo)) {
            Type tipo = new TypeToken<List<String>>(){}.getType();
            List<String> lista = gson.fromJson(lector, tipo);
            return lista;
        } catch (IOException ex) {
            System.err.println("Error al cargar listado de razas: " + ex.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean addVaca(Vaca bovino) {
        if (bovino == null) {
            return false;
        }
        listaVacas.add(bovino);
        guardarDatos();
        return true;
    }
    
    public List<Vaca> getVacasPorIdPropietario(UUID idUsuario) {
        List<Vaca> lista = new ArrayList<>();
        
        for (Vaca vaca : listaVacas) {
            if (vaca.getIdPropietario().equals(idUsuario)) {
                lista.add(vaca);
            }
        }
        return lista;
    }
    
    public int getCantidadDeAnimalesPorIdPropietario(UUID idUsuario) {
        List<Vaca> lista = getVacasPorIdPropietario(idUsuario);
        
        return lista.size() + 1;
    }
    
    public List<String> getListaRazas() {
        return listaRazas;
    }
    
    public List<Vaca> filtrarVacasPorCoincidencia(UUID idUsuario, String nombreVaca) {
        List<Vaca> listaFiltrada = new ArrayList<>();
        
        for (Vaca vaca : getVacasPorIdPropietario(idUsuario)) {
            if (vaca.getNombre().startsWith(nombreVaca)) {
                listaFiltrada.add(vaca);
            }
        }
        return listaFiltrada;
    }
    
    public Vaca getVacaPorId(UUID idVaca, UUID idUsuario) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getIdInterno().equals(idVaca) && vaca.getIdPropietario().equals(idUsuario)) {
                return vaca;
            }
        }
        return null;
    }
    
    public boolean addTratamiento(Vaca vacaTratada, TratamientoVeterinario tratamientoHecho) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getIdInterno().equals(vacaTratada.getIdInterno())) {
                vaca.getHistorialTratamientos().add(tratamientoHecho);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    public boolean updateTratamiento(Vaca vacaAEditar, TratamientoVeterinario tratamientoAEditar) {
        if (vacaAEditar == null || tratamientoAEditar == null) return false;
        
        for (Vaca vaca : listaVacas) {
            if (vaca.getIdInterno().equals(vacaAEditar.getIdInterno())) {
                for (int i = 0 ; i < vaca.getHistorialTratamientos().size() ; i++) {
                    if (vaca.getHistorialTratamientos().get(i).getIdInterno().equals(tratamientoAEditar.getIdInterno())) {
                        vaca.getHistorialTratamientos().set(i, tratamientoAEditar);
                        guardarDatos();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public TratamientoVeterinario getTratamientoDeVacaPorId(UUID idVaca, UUID idTratamiento) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getIdInterno().equals(idVaca)) {
                for (TratamientoVeterinario tratamiento : vaca.getHistorialTratamientos()) {
                    if (tratamiento.getIdInterno().equals(idTratamiento)) {
                        return tratamiento;
                    }
                }
            }
        }
        return null;
    }
    
    public boolean updateVaca(Vaca vaca) {
        if (vaca == null) return false; 
        
        for (int i = 0 ; i < listaVacas.size() ; i++) {
            if (listaVacas.get(i).getIdInterno().equals(vaca.getIdInterno())) {
                listaVacas.set(i, vaca);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteTratamientoPorId(UUID idVaca, UUID IdTratamiento) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getIdInterno().equals(idVaca)) {
                boolean eliminado = vaca.getHistorialTratamientos().removeIf(
                        tratamiento -> tratamiento.getIdInterno().equals(IdTratamiento));
                guardarDatos();
                return eliminado;
            }
        }
        return false;
    }
    
    public boolean addProduccion(Produccion produccion, UUID Idvaca) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getIdInterno().equals(Idvaca)) {
                vaca.getRegistroProducciones().add(produccion);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    public boolean updateProduccion(UUID idVaca, Produccion produccion) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getIdInterno().equals(idVaca)) {
                for (int i = 0 ; i < vaca.getRegistroProducciones().size() ; i++) {
                    if (vaca.getRegistroProducciones().get(i).getFecha().equals(produccion.getFecha())) {
                        vaca.getRegistroProducciones().set(i, produccion);
                        guardarDatos();
                        return true;
                    }
                }
            }
        }
        return false;
    }
        
    public Produccion getProduccionPorFecha(UUID IdVaca, LocalDate fecha) {
        for (Vaca vaca :listaVacas) {
            if (vaca.getIdInterno().equals(IdVaca)) {
                for (Produccion produccion : vaca.getRegistroProducciones()) {
                    if (produccion.getFecha().equals(fecha)) {
                        return produccion;
                    }
                }
            }
        }
        return null;
    }
}
