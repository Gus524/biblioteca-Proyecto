package com.biblioteca.modelos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.biblioteca.dao.ConnectionDB;
import com.biblioteca.interfaces.ConvertirMapeo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Libro implements ConvertirMapeo {
    private IntegerProperty id_libro;
    private IntegerProperty id_editorial;
    private StringProperty titulo;
    private StringProperty nom_editorial;
    private StringProperty autores;
    private StringProperty categorias;
    private ConnectionDB cnn;

    public IntegerProperty id_libroProperty() { return id_libro; }
    public IntegerProperty id_editorialProperty() { return id_editorial; }
    public StringProperty tituloProperty() { return titulo; }
    public StringProperty nom_editorialProperty() { return nom_editorial; }
    public StringProperty autoresProperty() { return autores; }
    public StringProperty categoriasProperty() { return categorias; }

    public int getId_libro() { return id_libro.get(); }
    public int getId_editorial() { return id_editorial.get(); }
    public String getTitulo() { return titulo.get(); }
    public String getNom_editorial() { return nom_editorial.get(); }
    public String getAutores() { return autores.get(); }
    public String getCategorias() { return categorias.get(); }

    public void setId_libro(int id_libro){
        this.id_libro.set(id_libro); 
    }
    public void setId_editorial(int id_editorial){
        this.id_editorial.set(id_editorial); 
    }
    public void setTitulo(String titulo){
        this.titulo.set(titulo);
    }
    public void setNom_editorial(String nom_editorial){
        this.nom_editorial.set(nom_editorial);
    }
    public void setAutores(String autores){
        this.autores.set(autores);
    }
    public void setCategorias(String categorias){
        this.categorias.set(categorias);
    }

    public Libro(){
        this.id_libro = new SimpleIntegerProperty(0);
        this.id_editorial = new SimpleIntegerProperty(0);
        this.titulo = new SimpleStringProperty("");
        this.nom_editorial = new SimpleStringProperty("");
        this.autores = new SimpleStringProperty("");
        this.categorias = new SimpleStringProperty("");
        this.cnn = new ConnectionDB();
    }

    public Libro(Integer id_libro, Integer id_editorial, String titulo, String nom_editorial, String autores, String categorias){
        this.id_libro = new SimpleIntegerProperty(id_libro);
        this.id_editorial = new SimpleIntegerProperty(id_editorial);
        this.titulo = new SimpleStringProperty(titulo);
        this.nom_editorial = new SimpleStringProperty(nom_editorial);
        this.autores = new SimpleStringProperty(autores);
        this.categorias = new SimpleStringProperty(categorias);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> libros = new LinkedList<>();
        for(var map : lista){
            Libro libro = new Libro(
            (Integer) map.get("id_libro"),
            (Integer) map.get("id_editorial"),
            (String) map.get("titulo"),
            (String) map.get("nom_editorial"),
            (String) map.get("autores"),
            (String) map.get("categorias")
            );
            libros.add((T) libro);
        }
        return libros;
    }

    public List<Libro> obtenerLibros(){
        return convertirMapeo(cnn.consultar("SELECT 	l.*, e.nom_editorial, " +
	                        "(SELECT GROUP_CONCAT(a.nom_autor, ',') FROM " + 
                            "(SELECT DISTINCT a.nom_autor FROM Autor a " +
                            "JOIN Libro_Autor la ON a.id_autor = la.id_autor " +
                            "WHERE la.id_libro = l.id_libro) a) AS autores, " +
                            "(SELECT GROUP_CONCAT(c.descripcion, ',') FROM " +
                            "(SELECT DISTINCT c.descripcion FROM Categoria c " +
                            "JOIN Libro_Categoria lc ON c.id_categoria = lc.id_categoria " +
                            "WHERE lc.id_libro = l.id_libro) c) AS categorias " +
                            "FROM Libro l JOIN Editorial e ON e.id_editorial = l.id_editorial"));
    }

    public Boolean agregarLibro() {
        return cnn.ejecutar("INSERT INTO Libro (titulo, autores, categorias ) VALUES (?, ?, ?)", getTitulo(), getAutores(), getCategorias()) > 0;
    }
}
