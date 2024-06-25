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

    //Agregar Get, Set y ACTUALIZAR EL CONSTRUCTOR POR DEFECTO de todas las variables en todas las clases
    public int getId_prestamo() { return id_prestamo.get(); }
    public int getId_user() { return id_user.get(); }
    public String getFecha_prestamo() { return fecha_prestamo.get(); }
    public Long getISBN() { return ISBN.get(); }
    public String getEstado() { return estado.get(); }
    public String getFecha_devolucion() { return fecha_devolucion.get(); }
    public int getId_estado() { return id_estado.get(); }
    public int getId_prestamo_user() { return id_prestamo_user.get(); }

    public void setId_prestamo(int id_prestamo){
        this.id_prestamo.set(id_prestamo);
    }
    public void setId_user(int id_user){
        this.id_user.set(id_user);
    }
    public void setFecha_prestamo(String fecha_prestamo){
        this.fecha_prestamo.set(fecha_prestamo);
    }
    public void setISBN(Long ISBN){
        this.ISBN.set(ISBN);
    }
    public void setEstado(String estado){
        this.estado.set(estado);
    }
    public void setFecha_devolucion(String fecha_devolucion){
        this.fecha_devolucion.set(fecha_devolucion);
    }
    public void setId_estado(int id_estado){
        this.id_estado.set(id_estado);
    }
    public void setId_prestamo_user(int id_prestamo_user){
        this.id_prestamo_user.set(id_prestamo_user);
    }

    public Prestamo() {
        this.id_prestamo_user = new SimpleIntegerProperty(0);
        this.id_prestamo = new SimpleIntegerProperty(0);
        this.id_user = new SimpleIntegerProperty(0);
        this.fecha_prestamo = new SimpleStringProperty("");
        this.ISBN = new SimpleLongProperty(0);
        this.estado = new SimpleStringProperty("");
        this.fecha_devolucion = new SimpleStringProperty("");
        this.devolucion = new SimpleStringProperty("");
        this.id_estado = new SimpleIntegerProperty(0);
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
            (String) map.get("desc_estado"),
            (String) map.get("fecha_devolucion"),
            (String) map.get("devolucion"),
            (Integer) map.get("id_estado")
            );
            prestamos.add((T)prestamo);
        }
        return prestamos;
    }
    
    public int agregarPrestamo(){
        return cnn.getKey("INSERT INTO Prestamo (id_user) "+
                    "VALUES (?)",
                    getId_user());
    }

    public Boolean agregarConcentrado(){
        return (cnn.ejecutar("INSERT INTO Prestamo_Concentrado " + 
                    "(id_prestamo, ISBN, fecha_devolucion) "+
                    "VALUES (?, ?, ?)",
                    getId_prestamo(),
                    getISBN(),
                    getFecha_devolucion()) > 0);
    }

    public Boolean actualizarEstadoDevuelto(){
        return (cnn.ejecutar("UPDATE Prestamo_Concentrado SET " +
                            "id_estado = 2, devolucion = (date('now')) WHERE " +
                            "id_prestamo_user = ?", 
                            getId_prestamo_user()) > 0);
    
    }

    public List<Prestamo> obtenerPrestamo(){
        return convertirMapeo(cnn.consultar("SELECT pc.*, p.id_user, p.fecha_prestamo, e.desc_estado FROM " +
                                            "Prestamo_Concentrado pc JOIN Prestamo p ON " +
                                            "pc.id_prestamo = pc.id_prestamo JOIN Estado e ON " +
                                            "e.id_estado = pc.id_estado WHERE p.id_prestamo = ?", 
                                            getId_prestamo()));
    }
    public boolean esDevuelto() {
        List<HashMap<String, Object>> resultado = cnn.consultar(
            "SELECT id_estado FROM Prestamo_Concentrado WHERE id_prestamo_user = ? AND ISBN = ?",
            getId_prestamo_user(), getISBN()
        );
    if (!resultado.isEmpty()) {
        int idEstado = (Integer) resultado.get(0).get("id_estado");
        return idEstado == 2; 
    }
    return true; 
}

public boolean esPrestado() {
    List<HashMap<String, Object>> resultado = cnn.consultar(
        "SELECT id_estado FROM Prestamo_Concentrado WHERE id_prestamo_user = ? AND ISBN = ?",
        getId_prestamo_user(), getISBN()
    );

    if (!resultado.isEmpty()) {
        int idEstado = (Integer) resultado.get(0).get("id_estado");
        return idEstado == 1; 
    }
    return true; 
}

    public List<Prestamo> obtenerAtrasos(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Prestamo_Concentrado pc JOIN Prestamo p ON "+
                                            "p.id_prestamo = pc.id_prestamo WHERE "+
                                            "date('now') > fecha_devolucion AND id_estado = 1"));
    }
    public Boolean acutalizarCantidadEdicion(){
        return (cnn.ejecutar("UPDATE Edicion SET " +
                            "disponibles = disponibles + 1 WHERE " +
                            "ISBN = ?", getISBN()) > 0);
    }

    public Boolean quitarCantidadEdicion(){
        return (cnn.ejecutar("UPDATE Edicion SET " +
                            "disponibles = disponibles - 1 WHERE " +
                            "ISBN = ?", getISBN()) > 0);
    }
}
