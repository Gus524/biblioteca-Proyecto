package com.biblioteca.modelos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.biblioteca.dao.ConnectionDB;
import com.biblioteca.interfaces.ConvertirMapeo;

import javafx.beans.property.DoubleProperty;

public class Multa implements ConvertirMapeo{
    private IntegerProperty id_multa;
    private IntegerProperty id_prestamo_user;
    private IntegerProperty id_historial;
    private IntegerProperty total_multa;
    private StringProperty estado;
    private DoubleProperty costo_multa;
    private StringProperty fecha_multa;
    private IntegerProperty id_estado;
    private ConnectionDB cnn;

    public StringProperty getFecha_multa() { return fecha_multa; }
    public IntegerProperty getId_multa() { return id_multa; }
    public IntegerProperty getId_prestamo_user() { return id_prestamo_user; }
    public IntegerProperty getId_historial() { return id_historial; }
    public IntegerProperty getTotal_multa() { return total_multa; }
    public StringProperty getEstado() { return estado; }
    public DoubleProperty getCosto_multa() { return costo_multa; }
    public IntegerProperty getId_estado() { return id_estado; }

    public Multa() {
        this.cnn = new ConnectionDB();
    }   

    public Multa(IntegerProperty id_multa){
        this.cnn = new ConnectionDB();
        this.id_multa = id_multa;
    }
    
    public Multa(Integer id_multa, Integer id_prestamo_user, Integer id_historial, Integer total_multa, String estado, Double costo_multa, String fecha_multa, Integer id_estado) {
        this.id_multa = new SimpleIntegerProperty(id_multa);
        this.id_prestamo_user = new SimpleIntegerProperty(id_prestamo_user);
        this.id_historial = new SimpleIntegerProperty(id_historial);
        this.total_multa = new SimpleIntegerProperty(total_multa);
        this.estado = new SimpleStringProperty(estado);
        this.costo_multa = new SimpleDoubleProperty(costo_multa);
        this.fecha_multa = new SimpleStringProperty(fecha_multa);
        this.id_estado = new SimpleIntegerProperty(id_estado);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> multas = new LinkedList<>();
        for (var map : lista) {
            Multa multa = new Multa(
            (Integer) map.get("id_multa"), 
            (Integer) map.get("id_prestamo_user"),
            (Integer) map.get("id_historial"), 
            (Integer) map.get("total_multa"),
            (String) map.get("estado"), 
            (Double) map.get("costo_multa"),
            (String) map.get("fecha_multa"),
            (Integer) map.get("id_estado"));
            multas.add((T)multa);
        }
        return multas;
    }

    public List<Multa> obtenerMultas(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Multa"));
    }

    public List<Multa> obtenerMulta(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Multa WHERE id_multa = ?", 
                                            this.id_multa.get()));
    }

    public List<Multa> obtenerHistorial(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Multa_Historial WHERE id_historial = ?",
                                            this.id_historial.get()));
    }

    public List<Multa> obtenerMultaUsuario(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Multa WHERE id_prestamo_user = ?",
                                            this.id_prestamo_user.get()));
    }

    public Boolean agregarHistorial(int id_user){
        return (cnn.ejecutar("INSERT INTO Multa_Historial" +
                            "(id_user, total_multa)" + 
                            "VALUES (?,?)", 
                            id_user, 
                            this.total_multa.get()) > 0);
    }

    public Boolean agregarMulta(){
       return (cnn.ejecutar("INSERT INTO Multa" + 
                            "(id_prestamo_user, id_historial, id_estado, costo_multa, fecha_multa)" +
                            "VALUES (?, ?, ?, ?, ?, ?)", 
                            this.id_prestamo_user.get(), 
                            this.id_historial.get(),
                            this.id_estado.get(),
                            this.costo_multa.get(),
                            this.fecha_multa.get()) > 0);
    }

    public Boolean actualizarCosto(){
        return (cnn.ejecutar("UPDATE Multa SET costo_multa = ? WHERE id_multa = ?", 
                            this.costo_multa.get(), 
                            this.id_multa.get()) > 0);
    }

    public Boolean actualizarEstado(){
        return (cnn.ejecutar("UPDATE Multa SET id_estado = ? WHERE id_multa = ?", 
                            this.id_estado.get(), 
                            this.id_multa.get()) > 0);
    }

}
