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
import java.util.ArrayList;
import java.util.List;


public class VacaDAO {
    private final Gson gson;
    private final String ruta;
    private final List<Vaca> listaVacas;
    private List<String> listaRazas;
    private String rutaRazas;

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
    
    public List<String> cargarListadoDeRazas() {
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
    
    private String generarCodigoInterno(int codigoCorrespondiente) {
        
        if (codigoCorrespondiente < 10) {
            return "00" + String.valueOf(codigoCorrespondiente);
        } else if (codigoCorrespondiente < 100) {
            return "0" + String.valueOf(codigoCorrespondiente);
        } else {
            return String.valueOf(codigoCorrespondiente);
        }
    }
    
    public boolean registrar(Vaca bovino) {
        if (bovino == null) {
            return false;
        }
        
        bovino.setCodigoInterno(generarCodigoInterno(retornarCantidadDeAnimalesPorUsuario(bovino.getDueño())));
        
        listaVacas.add(bovino);
        guardarDatos();
        return true;
    }
    
    public List<Vaca> retornarListaVacasPorUsuario(String usuario) {
        List<Vaca> lista = new ArrayList<>();
        for (Vaca vaca : listaVacas) {
            if (vaca.getDueño().equals(usuario)) {
                lista.add(vaca);
            }
        }
        return lista;
    }
    
    public int retornarCantidadDeAnimalesPorUsuario(String nombreUsuario) {
        List<Vaca> lista = retornarListaVacasPorUsuario(nombreUsuario);
        
        return lista.size() + 1;
    }
    
    public List<String> retornarListadoDeRazas() {
        return listaRazas;
    }
    
    public List<Vaca> filtrarVacasPorCoincidencia(String usuario, String nombreVaca) {
        List<Vaca> listaPorDueño = retornarListaVacasPorUsuario(usuario);
        
        List<Vaca> listaFiltrada = new ArrayList<>();
        
        for (Vaca vaca : listaPorDueño) {
            if (vaca.getNombre().startsWith(nombreVaca)) {
                listaFiltrada.add(vaca);
            }
        }
        return listaFiltrada;
    }
    
    public Vaca buscarVacaPorCodigoYpropietario(String codigo, String nombrePropietario) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getCodigoInterno().equals(codigo) && vaca.getDueño().equals(nombrePropietario)) {
                return vaca;
            }
        }
        return null;
    }
    
    public boolean registrarTratamiento(Vaca vacaTratada,TratamientoVeterinario tratamiento) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getCodigoInterno().equals(vacaTratada.getCodigoInterno()) && vaca.getNombre().equals(vacaTratada.getNombre())) {
                vaca.setHistorialTratamientos(tratamiento);
                guardarDatos();
                return true;
            }
        }
        return false;
    }
    
    public boolean editarTratamiento(Vaca vacaAEditarTratamiento, TratamientoVeterinario tratamientoAEditar) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getCodigoInterno().equals(vacaAEditarTratamiento.getCodigoInterno()) && vaca.getDueño().equals(vacaAEditarTratamiento.getDueño())) {
                for (int i = 0; i < vaca.getHistorialTratamientos().size();i++) {
                    if (vaca.getHistorialTratamientos().get(i).getId().equals(tratamientoAEditar.getId())) {
                        vaca.getHistorialTratamientos().set(i, tratamientoAEditar);
                        guardarDatos();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public TratamientoVeterinario retorntarTratamientoPorIdYVaca(Vaca vacaAEditar, String IdTratamiento) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getCodigoInterno().equals(vacaAEditar.getCodigoInterno()) && vaca.getDueño().equals(vacaAEditar.getDueño())) {
                for (TratamientoVeterinario tratamiento : vaca.getHistorialTratamientos()) {
                    if (tratamiento.getId().equals(IdTratamiento)) {
                        return tratamiento;
                    }
                }
            }
        }
        return null;
    }
    
    public boolean editarGanado(Vaca vaca) {
        if (vaca != null) {
            for (int i = 0; i < listaVacas.size();i++) {
                if (listaVacas.get(i).getCodigoInterno().equals(vaca.getCodigoInterno()) && listaVacas.get(i).getDueño().equals(vaca.getDueño())) {
                    listaVacas.set(i, vaca);
                    guardarDatos();
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean eliminarRegistroTratamiento(Vaca vacaAEditar, String IdTratamiento) {
        for (Vaca vaca : listaVacas) {
            if (vaca.getCodigoInterno().equals(vacaAEditar.getCodigoInterno()) && vaca.getDueño().equals(vacaAEditar.getDueño())) {
                boolean eliminado = vaca.getHistorialTratamientos().removeIf(tratamiento -> tratamiento.getId().equals(IdTratamiento));
                guardarDatos();
                return eliminado;
            }
        }
        return false;
    }
}
