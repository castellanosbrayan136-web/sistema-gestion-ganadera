package modelo;

//@autor: Brayan C

import com.formdev.flatlaf.json.Json;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO {
    private final String ruta;
    private final Gson gson;
    private final List<Usuario> usuarios;

    public UsuarioDAO() {
        this.ruta = "usuarios.json";
        this.gson = new Gson().newBuilder().setPrettyPrinting().create();
        this.usuarios = cargarDatos();
    }
    
    private List<Usuario> cargarDatos() {
        File file = new File(ruta);
        
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader reader = new FileReader(file)) {
            Type tipoLista = new TypeToken<List<Usuario>>(){}.getType();
            List<Usuario> lista = gson.fromJson(reader, tipoLista);
            
            return (lista != null) ? lista : new ArrayList<>();
            
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }
    
    private void guardarDatos() {
        try (Writer writer = new FileWriter(ruta)) {
            gson.toJson(usuarios, writer);
        } catch (IOException ex) {
            System.err.println("Error al guardar los datos: " + ex.getMessage());
        }
    }
    
    
    
    
    public boolean registrar(Usuario usuario) {
        if (usuario == null) return false;
        
        usuarios.add(usuario);
        guardarDatos();
        return true;
    }
    
    public boolean verificarNombreEnUso(String nombreUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreDeUsuario().equals(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }
    
    public Usuario verificarUsuario(String nombreUsuario, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.getContraseña().equals(contraseña) && usuario.getNombreDeUsuario().equals(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }
    
}
