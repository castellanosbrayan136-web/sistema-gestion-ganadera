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


public class UsuarioDAO {
    private final String ruta;
    private final Gson gson;
    private final List<Usuario> usuarios;

    public UsuarioDAO() {
        this.ruta = "usuarios.json";
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.usuarios = cargarDatos();
    }
    
    private List<Usuario> cargarDatos() {
        File archivo = new File(ruta);
        
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (Reader lector = new FileReader(archivo)) {
            Type tipoDeLista = new TypeToken<List<Usuario>>(){}.getType();
            List<Usuario> listaUsuarios = gson.fromJson(lector, tipoDeLista);
            
            return (listaUsuarios != null) ? listaUsuarios : new ArrayList<>();
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }
    
    private void guardarDatos() {
        File archivo = new File(ruta);
        try (Writer escritor = new FileWriter(archivo)) {
            gson.toJson(usuarios , escritor);
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
    
    public Usuario verificarUsuario(String nombreUsuario, String contraseña) {
        for (Usuario usuario : usuarios) {
            if (usuario.getContraseña().equals(contraseña) && usuario.getNombreDeUsuario().equals(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }
    
}
