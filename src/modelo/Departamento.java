package modelo;

//@autor: Brayan C

import java.util.List;


public class Departamento {
    private String id; 
    private String departamento;
    private List<String> ciudades;

    public String getId() {
        return id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public List<String> getMunicipios() {
        return ciudades;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setMunicipios(List<String> ciudades) {
        this.ciudades = ciudades;
    }
}
