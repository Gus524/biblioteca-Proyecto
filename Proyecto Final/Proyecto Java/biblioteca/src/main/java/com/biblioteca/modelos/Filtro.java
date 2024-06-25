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

public class Filtro {
    public class Autor implements ConvertirMapeo {
        private IntegerProperty id_autor;
        private StringProperty nom_autor;
        private ConnectionDB cnn;

        public IntegerProperty id_autorProperty() { return id_autor; }

        public StringProperty nom_autorProperty() { return nom_autor; }

        public int getId_autor() { return id_autor.get(); }

        public String getNom_autor() {
            return nom_autor.get();
        }

        public void setId_autor(int id_autor) {
            this.id_autor.set(id_autor);
        }

        public void setNom_autor(String nom_autor) {
            this.nom_autor.set(nom_autor);
        }

        public Autor (int id_autor, String nom_autor) {
            this.id_autor = new SimpleIntegerProperty(id_autor);
            this.nom_autor = new SimpleStringProperty(nom_autor);
        }

        public Autor() {
            this.id_autor = new SimpleIntegerProperty(0);
            this.nom_autor = new SimpleStringProperty("");
            this.cnn = new ConnectionDB();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
            List<T> autores = new LinkedList<>();
            for (var map : lista) {
                Autor autor = new Autor(
                        (Integer) map.get("id_autor"),
                        (String) map.get("nom_autor"));
                autores.add((T) autor);
            }
            return autores;
        }

        @Override
        public String toString() {
            return getNom_autor();
        }

        public List<String> obtenerAutores() {
            List<String> autores = new LinkedList<>();
            for (var autor : convertirMapeo(cnn.consultar("SELECT * FROM Autor"))) {
                autores.add(autor.toString());
            }
            return autores;
        }

        public List<Autor> obtenerAutoresObj() {
            return convertirMapeo(cnn.consultar("SELECT * FROM Autor"));
        }

        public Boolean agregarAutor(){
            return (cnn.ejecutar("INSERT INTO Autor (nom_autor) VALUES (?)", 
                                getNom_autor()) > 0);
        }

        public Boolean agregarLibro(int id_libro){
            return (cnn.ejecutar("INSERT INTO Libro_Autor (id_libro, id_autor) VALUES (?, ?)",
                                id_libro,
                                getId_autor()) > 0);
        }

        public Boolean autorExiste(){
            return (cnn.comprobar("SELECT * FROM Autor WHERE nom_autor = ?", getNom_autor()) > 0);
        }

        public List<Autor> comprobarAutor(){
            return convertirMapeo(cnn.consultar("SELECT * FROM Autor WHERE nom_autor = ?", getNom_autor()));
        }
    }

    public class Categoria implements ConvertirMapeo{
        private IntegerProperty id_categoria;
        private StringProperty descripcion;
        private ConnectionDB cnn;

        public IntegerProperty id_categoriaProperty() { return id_categoria; }
        public StringProperty descripcionProperty() { return descripcion; }

        public int getId_categoria() { return id_categoria.get(); }
        public String getDescripcion() { return descripcion.get(); }

        public void setId_categoria(int id_categoria) { this.id_categoria.set(id_categoria); }
        public void setDescripcion(String descripcion) { this.descripcion.set(descripcion); }

        public Categoria(int id_categoria, String descripcion) {
            this.id_categoria = new SimpleIntegerProperty(id_categoria);
            this.descripcion = new SimpleStringProperty(descripcion);
        }

        public Categoria() {
            this.id_categoria = new SimpleIntegerProperty(0);
            this.descripcion = new SimpleStringProperty("");
            this.cnn = new ConnectionDB();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
            List<T> categorias = new LinkedList<>();
            for (var map : lista) {
                Categoria categoria = new Categoria(
                        (Integer) map.get("id_categoria"),
                        (String) map.get("descripcion"));
                categorias.add((T) categoria);
            }
            return categorias;
        }

        @Override
        public String toString() {
            return getDescripcion();
        }

        public List<String> obtenerCategorias() {
            List<String> categorias = new LinkedList<>();
            for (var categoria : convertirMapeo(cnn.consultar("SELECT * FROM Categoria"))) {
                categorias.add(categoria.toString());
            }
            return categorias;
        }

        public List<Categoria> obtenerCategoriasObj() {
            return convertirMapeo(cnn.consultar("SELECT * FROM Categoria"));
        }

        public Boolean agregarLibro(int id_libro){
            return (cnn.ejecutar("INSERT INTO Libro_Categoria (id_libro, id_categoria) VALUES (?, ?)",
                                id_libro,
                                getId_categoria()) > 0);
        }

        public List<Categoria> comprobarCategoria(){
            return convertirMapeo(cnn.consultar("SELECT * FROM Categoria WHERE descripcion = ?", 
                                        getDescripcion()));
        }
    }

    public class Editorial implements ConvertirMapeo {
        private IntegerProperty id_editorial;
        private StringProperty nom_editorial;
        private ConnectionDB cnn;

        public IntegerProperty id_editorialProperty() { return id_editorial; }
        public StringProperty nom_editorialProperty() { return nom_editorial; }

        public int getId_editorial() { return id_editorial.get(); }
        public String getNom_editorial() { return nom_editorial.get(); }

        public void setId_editorial(int id_editorial) { this.id_editorial.set(id_editorial); }
        public void setNom_editorial(String nom_editorial) { this.nom_editorial.set(nom_editorial); }

        public Editorial(int id_editorial, String nom_editorial) {
            this.id_editorial = new SimpleIntegerProperty(id_editorial);
            this.nom_editorial = new SimpleStringProperty(nom_editorial);
        }

        public Editorial() {
            this.id_editorial = new SimpleIntegerProperty(0);
            this.nom_editorial = new SimpleStringProperty("");
            this.cnn = new ConnectionDB();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
            List<T> editoriales = new LinkedList<>();
            for (var map : lista) {
                Editorial editorial = new Editorial(
                        (Integer) map.get("id_editorial"),
                        (String) map.get("nom_editorial"));
                editoriales.add((T) editorial);
            }
            return editoriales;
        }

        @Override
        public String toString() {
            return getNom_editorial();
        }

        public List<String> obtenerEditoriales() {
            List<String> editoriales = new LinkedList<>();
            for (var editorial : convertirMapeo(cnn.consultar("SELECT * FROM Editorial"))) {
                editoriales.add(editorial.toString());
            }
            return editoriales;
        }

        public List<Editorial> obtenerEditorialesObj() {
            return convertirMapeo(cnn.consultar("SELECT * FROM Editorial"));
        }

        public Boolean agregarEditorial(){
            return (cnn.ejecutar("INSERT INTO Editorial (nom_editorial) VALUES (?)", 
                                getNom_editorial()) > 0);
        }
    }
    public List<String> obtenerAutores() {
        return new Autor().obtenerAutores();
    }

    public List<Autor> obtenerAutoresObj() {
        return new Autor().obtenerAutoresObj();
    }

    public List<String> obtenerCategorias() {
        return new Categoria().obtenerCategorias();
    }

    public List<Categoria> obtenerCategoriasObj() {
        return new Categoria().obtenerCategoriasObj();
    }

    public List<String> obtenerEditoriales() {
        return new Editorial().obtenerEditoriales();
    }

    public List<Editorial> obtenerEditorialesObj() {
        return new Editorial().obtenerEditorialesObj();
    }
}
