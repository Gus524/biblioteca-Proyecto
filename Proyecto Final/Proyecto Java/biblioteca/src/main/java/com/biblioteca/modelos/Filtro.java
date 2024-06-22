package com.biblioteca.modelos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.biblioteca.dao.ConnectionDB;
import com.biblioteca.interfaces.ConvertirMapeo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Filtro implements ConvertirMapeo{
    private StringProperty autores;
    private StringProperty categorias;
    private ConnectionDB cnn;
    public static List<String> listaEstado;
    public static List<String> listaAutor;
    public static List<String> listaCategoria;
    static {
        listaAutor = new ArrayList<>();
        listaCategoria = new ArrayList<>();
        listaEstado = new ArrayList<>();
    }

    public StringProperty getAutores() { return autores; }
    public StringProperty getCategorias() { return categorias; }

    public Filtro (String autores, String categorias) {
        this.autores = new SimpleStringProperty(autores);
        this.categorias = new SimpleStringProperty(categorias);
    }

    public Filtro() {
        this.cnn = new ConnectionDB();
        obtenerFiltros();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List <T> filtros = new LinkedList<>();
        for(var map : lista) {
            Filtro filtro = new Filtro(
            (String) map.get("autores"), 
            (String) map.get("categorias"));
            filtros.add((T)filtro);
        }
        return filtros;
    }

    private static void obtenerListas(List<Filtro> filtros){
        if(listaAutor.size() > 0) listaAutor.clear();
        if(listaCategoria.size() > 0) listaCategoria.clear();
        
        for(Filtro filtro : filtros){
            listaAutor.addAll(Arrays.asList(filtro.getAutores().get().split(",")));
            listaCategoria.addAll(Arrays.asList(filtro.getCategorias().get().split(",")));
        }
    }

    public void obtenerFiltros() {
        List<Filtro> filtros = convertirMapeo(cnn.consultar("SELECT " +
                        "(SELECT GROUP_CONCAT(descripcion, ', ') FROM " +
                        "(SELECT DISTINCT descripcion FROM Categoria)) AS categorias, " +
                        "(SELECT GROUP_CONCAT(nom_autor, ', ') FROM " +
                        "(SELECT DISTINCT nom_autor FROM Autor)) AS autores" ));
        obtenerListas(filtros);
    }
}
