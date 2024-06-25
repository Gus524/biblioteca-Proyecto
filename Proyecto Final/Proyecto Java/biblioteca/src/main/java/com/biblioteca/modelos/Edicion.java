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
    private StringProperty editorial;
    private StringProperty autores;
    private StringProperty categorias;

    public StringProperty tituloProperty() { return titulo; }
    public IntegerProperty id_libroProperty() { return id_libro; }
    public LongProperty ISBNProperty() { return ISBN; }
    public IntegerProperty publicacionProperty() { return publicacion; }
    public IntegerProperty no_edicionProperty() { return no_edicion; }
    public IntegerProperty disponiblesProperty() { return disponibles; }
    public StringProperty editorialProperty() { return editorial; }
    public StringProperty autoresProperty() { return autores; }
    public StringProperty categoriasProperty() { return categorias; }

    public String getTitulo()   {return titulo.get(); }
    public int getId_libro()    { return id_libro.get(); }
    public long getISBN()       { return ISBN.get(); }
    public int getPublicacion() { return publicacion.get(); }
    public int getNo_edicion()  { return no_edicion.get(); }
    public int getDisponibles() { return disponibles.get(); }
    public String getEditorial() { return editorial.get(); }
    public String getAutores()    { return autores.get(); }
    public String getCategorias() { return categorias.get(); }

    public void setTitulo(String titulo){
        this.titulo.set(titulo); 
    }
    public void setId_libro(int id_libro){
        this.id_libro.set(id_libro);
    }
    public void setISBN(long ISBN){
        this.ISBN.set(ISBN);
    }
    public void setPublicacion(int publicacion){ 
        this.publicacion.set(publicacion);
    }
    public void setNo_edicion(int no_edicion){
        this.no_edicion.set(no_edicion);
    }
    public void setDisponibles(int disponibles){ 
        this.disponibles.set(disponibles); 
    }
    public void setEditorial(String editorial){
        this.editorial.set(editorial); 
    }
    public void setAutores(String autores){
        this.autores.set(autores);
    }
    public void setCategorias(String categorias){
        this.categorias.set(categorias); 
    }

    public Edicion() {
        this.titulo = new SimpleStringProperty("");
        this.id_libro = new SimpleIntegerProperty(0);
        this.ISBN = new SimpleLongProperty(0L);
        this.publicacion = new SimpleIntegerProperty(0);
        this.no_edicion = new SimpleIntegerProperty(0);
        this.disponibles = new SimpleIntegerProperty(0);
        this.editorial = new SimpleStringProperty("");
        this.autores = new SimpleStringProperty("");
        this.categorias = new SimpleStringProperty("");
        this.cnn = new ConnectionDB();
    }

    public Edicion(LongProperty ISBN){
        this.cnn = new ConnectionDB();
        this.ISBN = ISBN;
    }
    
    public Edicion(String titulo, Integer id_libro, Long ISBN, Integer publicacion,
            Integer no_edicion, Integer disponibles, String editorial, String autores,
            String categorias) {
        this.titulo = new SimpleStringProperty(titulo);
        this.id_libro = new SimpleIntegerProperty(id_libro);
        this.ISBN = new SimpleLongProperty(ISBN);
        this.publicacion = new SimpleIntegerProperty(publicacion);
        this.no_edicion = new SimpleIntegerProperty(no_edicion);
        this.disponibles = new SimpleIntegerProperty(disponibles);
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
                                            getISBN()));
    }

    public Boolean comprobarExisteISBNyNumero(){
        return (cnn.comprobar("SELECT * FROM Edicion WHERE ISBN = ? AND no_edicion = ?", 
                getISBN(), 
                getNo_edicion()) > 0);
    }
    public Boolean agregarEdicion(){
        return (cnn.ejecutar("INSERT INTO Edicion "
                   + "(no_edicion, id_libro, ISBN, publicacion, disponibles) VALUES "
                   + "(?, ?, ?, ?, ?)", 
                   getNo_edicion(), 
                   getId_libro(), 
                   getISBN(), 
                   getPublicacion(), 
                   getDisponibles()) > 0);
    }

    public int agregarLibro(){
        return (cnn.getKey("INSERT INTO Libro " + 
                            "(titulo, id_editorial) " + 
                            "VALUES (?, 1)" , 
                            getTitulo()));
    }

    public Boolean actualizarEdicion(){
        return (cnn.ejecutar("UPDATE Edicion SET no_edicion = ?, publicacion = ?, precio = ?, disponibles = ? WHERE ISBN = ?", 
                            getNo_edicion(),
                            getPublicacion(), 
                            getDisponibles(), 
                            getISBN()) > 0);
    }

    public Boolean actualizarPublicacion(){
        return (cnn.ejecutar("UPDATE Edicion SET publicacion = ? WHERE ISBN = ?", 
                            getPublicacion(), 
                            getISBN()) > 0);
    }

    public Boolean eliminarEdicion(){
        return (cnn.ejecutar("DELETE FROM Edicion WHERE ISBN = ?", 
                            getISBN()) > 0);
    }

    public Boolean editarNumero(){
        return (cnn.ejecutar("UPDATE Edicion SET no_edicion = ? WHERE ISBN = ?", 
                            getNo_edicion(), 
                            getISBN()) > 0);
    }
    
    public Boolean comoprobarLibro() {
        return (cnn.comprobar("SELECT * FROM Libro WHERE id_libro = ?", 
                getId_libro()) > 0);
    }

    public Boolean comprobarEdicion() {
        return (cnn.comprobar("SELECT * FROM Edicion WHERE ISBN = ?", 
                getISBN()) > 0);
    }
    
    public Boolean comprobarNumeroEdicion(){
        return (cnn.comprobar("SELECT * FROM Edicion WHERE no_edicion = ? AND ISBN = ?", 
                getNo_edicion(), 
                getISBN()) > 0);
    }

    public Boolean actualizarDisponibles() {
        return (cnn.ejecutar("UPDATE Edicion SET disponibles = ? WHERE ISBN = ?", 
                getDisponibles(), 
                getISBN()) > 0);
    }

    public Boolean actualizarTitulo() {
        return (cnn.ejecutar("UPDATE Libro SET titulo = ? WHERE id_libro = ?", 
                getTitulo(),   
                getId_libro()) > 0);
    }

    public Boolean actualizarPrecioEdicion() {
        return (cnn.ejecutar("UPDATE * FROM Edicion WHERE ISBN = ?", 
                getISBN()) > 0);
    }
}
