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

public class PrestamoUsuario implements ConvertirMapeo {
    private IntegerProperty id_prestamo;
    private IntegerProperty id_user;
    private StringProperty email;
    private StringProperty titulo;
    private StringProperty estado;
    private StringProperty fecha_prestamo;
    private StringProperty fecha_devolucion;
    private IntegerProperty id_prestamo_user;
    private ConnectionDB cnn;

    public IntegerProperty id_prestamoProperty() { return id_prestamo; }
    public IntegerProperty id_userProperty() { return id_user; }
    public StringProperty emailProperty() { return email; }
    public StringProperty tituloProperty() { return titulo; }
    public StringProperty estadoProperty() { return estado; }
    public StringProperty fecha_prestamoProperty() { return fecha_prestamo; }
    public StringProperty fecha_devolucionProperty() { return fecha_devolucion; }
    public IntegerProperty id_prestamo_userProperty() { return id_prestamo_user; }
    

    public PrestamoUsuario(){
        this.cnn = new ConnectionDB();
    }
    public PrestamoUsuario(Integer id_prestamo, Integer id_user, String email, String titulo, String estado, String fecha_prestamo, String fecha_devolucion, Integer id_prestamo_user){
        this.id_prestamo = new SimpleIntegerProperty(id_prestamo);
        this.id_user = new SimpleIntegerProperty(id_user);
        this.email = new SimpleStringProperty(email);
        this.titulo = new SimpleStringProperty(titulo);
        this.estado = new SimpleStringProperty(estado);
        this.fecha_prestamo = new SimpleStringProperty(fecha_prestamo);
        this.fecha_devolucion = new SimpleStringProperty(fecha_devolucion);
        this.id_prestamo_user = new SimpleIntegerProperty(id_prestamo_user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> prestamos = new LinkedList<>();
        for(var map : lista){
            PrestamoUsuario prestamo = new PrestamoUsuario(
                (Integer) map.get("id_prestamo"),
                (Integer) map.get("id_user"),
                (String) map.get("email"),
                (String) map.get("titulo"),
                (String) map.get("desc_estado"),
                (String) map.get("fecha_prestamo"),
                (String) map.get("fecha_devolucion"),
                (Integer) map.get("id_prestamo_user")
            );
            prestamos.add((T)prestamo);
        }
        return prestamos;
    }
    public List<PrestamoUsuario> obtenerPrestamos(){
        return convertirMapeo(cnn.consultar("SELECT p.id_prestamo, p.id_user, u.email, l.titulo, es.desc_estado, p.fecha_prestamo, pc.fecha_devolucion, pc.id_prestamo_user " +
                                            "FROM Usuario u JOIN Prestamo p ON u.id_user = p.id_user " + 
                                            "JOIN Prestamo_Concentrado pc ON pc.id_prestamo = p.id_prestamo " +
                                            "JOIN Edicion e ON e.ISBN = pc.ISBN " +
                                            "JOIN Libro l ON l.id_libro = e.id_libro " +
                                            "JOIN Estado es ON es.id_estado = pc.id_estado"));
    }
    
}
