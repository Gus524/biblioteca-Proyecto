package com.biblioteca.modelos;

import javafx.beans.property.*;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

import com.biblioteca.dao.ConnectionDB;
import com.biblioteca.interfaces.ConvertirMapeo;

public class Edicion implements ConvertirMapeo {
    private ConnectionDB cnn;
    private StringProperty titulo;
    private IntegerProperty id_libro;
    private LongProperty ISBN;
    private IntegerProperty publicacion;
    private IntegerProperty no_edicion;
    private IntegerProperty disponibles;
    private DoubleProperty precio;
    private StringProperty editorial;
    private StringProperty autores;
    private StringProperty categorias;

    public StringProperty tituloProperty() { return titulo; }
    public IntegerProperty id_libroProperty() { return id_libro; }
    public LongProperty ISBNProperty() { return ISBN; }
    public IntegerProperty publicacionProperty() { return publicacion; }
    public IntegerProperty no_edicionProperty() { return no_edicion; }
    public IntegerProperty disponiblesProperty() { return disponibles; }
    public DoubleProperty precioProperty() { return precio; }
    public StringProperty editorialProperty() { return editorial; }
    public StringProperty autoresProperty() { return autores; }
    public StringProperty categoriasProperty() { return categorias; }

    public Edicion() {
        this.cnn = new ConnectionDB();
    }

    public Edicion(LongProperty ISBN){
        this.cnn = new ConnectionDB();
        this.ISBN = ISBN;
    }
    
    public Edicion(String titulo, Integer id_libro, Long ISBN, Integer publicacion,
            Integer no_edicion, Integer disponibles, Double precio, String editorial, String autores,
            String categorias) {
        this.titulo = new SimpleStringProperty(titulo);
        this.id_libro = new SimpleIntegerProperty(id_libro);
        this.ISBN = new SimpleLongProperty(ISBN);
        this.publicacion = new SimpleIntegerProperty(publicacion);
        this.no_edicion = new SimpleIntegerProperty(no_edicion);
        this.disponibles = new SimpleIntegerProperty(disponibles);
        this.precio = new SimpleDoubleProperty(precio);
        this.editorial = new SimpleStringProperty(editorial);
        this.autores = new SimpleStringProperty(autores);
        this.categorias = new SimpleStringProperty(categorias);
    }

    @Override
    public String toString(){
        return titulo.getValue() + " " + no_edicion.getValue() + "e" + " (" + disponiblesProperty().getValue() + ")";
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> ediciones = new LinkedList<>();
        for (var map : lista) {
            Edicion edicion = new Edicion(
            (String) map.get("titulo"),
            (Integer) map.get("id_libro"),
            (Long) map.get("ISBN"),
            (Integer) map.get("publicacion"),
            (Integer) map.get("no_edicion"),
            (Integer) map.get("disponibles"),
            (Double) map.get("precio"),
            (String) map.get("nom_editorial"),
            (String) map.get("autores"),
            (String) map.get("categorias")
        );
            ediciones.add((T) edicion);
        }
        return ediciones;
    }

    public List<Edicion> consultarLibros() {
        return convertirMapeo(cnn.consultar("SELECT \n" +
                        "    l.titulo,\n" + 
                        "    e.*,\n" +
                        "    ed.nom_editorial,\n" +
                        "    (SELECT GROUP_CONCAT(a.nom_autor, ',') FROM (\n" +
                        "        SELECT DISTINCT a.nom_autor FROM Autor a\n" +
                        "        JOIN Libro_Autor la ON a.id_autor = la.id_autor \n" +
                        "        WHERE la.id_libro = l.id_libro\n" +
                        "    ) a) AS autores,\n" +
                        "    (SELECT GROUP_CONCAT(c.descripcion, ',') FROM (\n" +
                        "        SELECT DISTINCT c.descripcion FROM Categoria c\n" +
                        "        JOIN Libro_Categoria lc ON c.id_categoria = lc.id_categoria \n" +
                        "        WHERE lc.id_libro = l.id_libro\n" +
                        "    ) c) AS categorias\n" +
                        "FROM \n" +
                        "    Libro l\n" +
                        "    JOIN Edicion e ON l.id_libro = e.id_libro\n" +
                        "    JOIN Editorial ed ON ed.id_editorial = l.id_editorial;"));
    }

    public List<Edicion> buscarEdicion(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Edicion e " + 
                                            "JOIN Libro l ON e.id_libro = l.id_libro WHERE e.ISBN = ?", 
                                            this.ISBN.get()));
    }

    public Boolean agregarEdicion(){
        return (cnn.ejecutar("INSERT INTO Edicion "
                   + "(no_edicion, id_libro, ISBN, publicacion, precio, disponibles) VALUES "
                   + "(?, ?, ?, ?, ?, ?)", 
                   this.no_edicion.get(), 
                   this.id_libro.get(), 
                   this.ISBN.get(), 
                   this.publicacion.get(), 
                   this.precio.get(), 
                   this.disponibles.get()) > 0);
    }

    public Boolean actualizarPublicacion(){
        return (cnn.ejecutar("UPDATE Edicion SET publicacion = ? WHERE ISBN = ?", 
                            this.publicacion.get(), 
                            this.ISBN.get()) > 0);
    }

    public Boolean eliminarEdicion(){
        return (cnn.ejecutar("DELETE FROM Edicion WHERE ISBN = ?", 
                            this.ISBN.get()) > 0);
    }

    public Boolean editarNumero(){
        return (cnn.ejecutar("UPDATE Edicion SET no_edicion = ? WHERE ISBN = ?", 
                            this.no_edicion.get(), 
                            this.ISBN.get()) > 0);
    }
    
    public Boolean comoprobarLibro() {
        return (cnn.comprobar("SELECT * FROM Libro WHERE id_libro = ?", 
                this.id_libro.get()) > 0);
    }

    public Boolean comprobarEdicion() {
        return (cnn.comprobar("SELECT * FROM Edicion WHERE ISBN = ?", 
                this.ISBN.get()) > 0);
    }

    public Boolean actualizarPrecio(){
        return (cnn.ejecutar("UPDATE Edicion SET precio = ? WHERE ISBN = ?", 
                this.precio.get(), 
                this.ISBN.get()) > 0);
    }

    public Boolean actualizarDisponibles() {
        return (cnn.ejecutar("UPDATE Edicion SET disponibles = ? WHERE ISBN = ?", 
                this.disponibles.get(), 
                this.ISBN.get()) > 0);
    }

    public Boolean actualizarTitulo() {
        return (cnn.ejecutar("UPDATE Libro SET titulo = ? WHERE id_libro = ?", 
                this.titulo.get(),   
                this.id_libro.get()) > 0);
    }

    public Boolean actualizarPrecioEdicion() {
        return (cnn.ejecutar("UPDATE * FROM Edicion WHERE ISBN = ?", 
                this.ISBN.get()) > 0);
    }
}
