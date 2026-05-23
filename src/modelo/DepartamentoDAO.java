package modelo;

//@autor: Brayan C

import com.formdev.flatlaf.json.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DepartamentoDAO {
    private final String ruta;
    private final Gson gson;
    private final List<Departamento> departamentosColombia;

    public DepartamentoDAO() {
        this.ruta = "colombia.json";
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.departamentosColombia = cargarDepartamentos();
    }
    
    public List<Departamento> cargarDepartamentos() {
        File archivo = new File(ruta);
        
        try(Reader lector = new FileReader(archivo)) {
            Type tipo = new TypeToken<List<Departamento>>(){}.getType();
            List<Departamento> lista = gson.fromJson(lector, tipo);
            return lista;
        } catch (IOException ex) {
            System.err.println("Error al cargar departamentos: " + ex.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<Departamento> retornarDepartamentos() {
        return departamentosColombia;
    }
    
    public void imprimir() {
        for (Departamento departamento : departamentosColombia) {
            System.out.println(departamento);
        }
    }
}
