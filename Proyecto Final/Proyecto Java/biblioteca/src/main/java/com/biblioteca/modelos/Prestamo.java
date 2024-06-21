package com.biblioteca.modelos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.biblioteca.dao.ConnectionDB;
import com.biblioteca.interfaces.ConvertirMapeo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Prestamo implements ConvertirMapeo{
    private ConnectionDB cnn;
    private IntegerProperty id_prestamo;
    private IntegerProperty id_user;
    private StringProperty fecha_prestamo;
    private LongProperty ISBN;
    private StringProperty estado;
    private StringProperty fecha_devolucion;
    private StringProperty devolucion;
    private IntegerProperty id_estado;
    private IntegerProperty id_prestamo_user;

    public IntegerProperty id_prestamoProperty() { return id_prestamo; }
    public IntegerProperty id_userProperty() { return id_user; }
    public StringProperty fecha_prestamoProperty() { return fecha_prestamo; }
    public LongProperty ISBNProperty() { return ISBN; }
    public StringProperty estadoProperty() { return estado; }
    public StringProperty fecha_devolucionProperty() { return fecha_devolucion; }
    public StringProperty devolucionProperty() { return devolucion; }
    public IntegerProperty id_estadoProperty() { return id_estado; }
    public IntegerProperty id_prestamo_userProperty() { return id_prestamo_user; }

    public Prestamo(){
        this.cnn = new ConnectionDB();
    }

    public Prestamo(Integer id_prestamo_user, Integer id_prestamo, Integer id_user, String fecha_prestamo, Long ISBN, String estado, String fecha_devolucion, String devolucion, Integer id_estado) {
        this.id_prestamo_user = new SimpleIntegerProperty(id_prestamo_user);
        this.id_prestamo = new SimpleIntegerProperty(id_prestamo);
        this.id_user = new SimpleIntegerProperty(id_user);
        this.fecha_prestamo = new SimpleStringProperty(fecha_prestamo);
        this.ISBN = new SimpleLongProperty(ISBN);
        this.estado = new SimpleStringProperty(estado);
        this.fecha_devolucion = new SimpleStringProperty(fecha_devolucion);
        this.devolucion = new SimpleStringProperty(devolucion);
        this.id_estado = new SimpleIntegerProperty(id_estado);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> prestamos = new LinkedList<>();
        for (var map : lista) {
            Prestamo prestamo = new Prestamo(
            (Integer) map.get("id_prestamo_user"),
            (Integer) map.get("id_prestamo"),
            (Integer) map.get("id_user"),
            (String) map.get("fecha_prestamo"),
            (Long) map.get("ISBN"),
            (String) map.get("estado"),
            (String) map.get("fecha_devolucion"),
            (String) map.get("devolucion"),
            (Integer) map.get("id_estado")
            );
            prestamos.add((T)prestamo);
        }
        return prestamos;
    }
    
    public Boolean agregarPrestamo(){
        return (cnn.ejecutar("INSERT INTO Prestamo (id_user) "+
                    "VALUES (?)",
                    this.id_user) > 0);
    }

    public Boolean agregarConcentrado(){
        return (cnn.ejecutar("INSERT INTO Prestamo_Concentrado " + 
                    "(id_prestamo, ISBN, id_estado, fecha_devolucion, devolucion) "+
                    "VALUES (?, ?, ?, ?, ?)",
                    this.id_prestamo,
                    this.ISBN,
                    this.id_estado,
                    this.fecha_devolucion,
                    this.devolucion) > 0);
    }

    public Boolean actualizarEstadoDevuelto(){
        return (cnn.ejecutar("UPDATE Prestamo_Concentrado SET id_estado = 4 AND devolucion = ? WHERE id_prestamo_user = ?", 
                    this.id_estado, 
                    this.id_prestamo_user) > 0);
    
    }

    public List<Prestamo> obtenerPrestamo(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Prestamo WHERE id_prestamo = ?", 
                                            this.id_prestamo));
    }
}
