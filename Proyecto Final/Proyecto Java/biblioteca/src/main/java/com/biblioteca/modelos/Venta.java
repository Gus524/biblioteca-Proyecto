package com.biblioteca.modelos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.biblioteca.dao.ConnectionDB;
import com.biblioteca.interfaces.ConvertirMapeo;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Venta implements ConvertirMapeo{
    private ConnectionDB cnn;
    private IntegerProperty id_user;
    private StringProperty fecha_venta;
    private DoubleProperty costo_total;
    private IntegerProperty id_venta;
    private LongProperty ISBN;
    private DoubleProperty costo_parcial;
    private IntegerProperty id_detalle;

    public IntegerProperty id_userProperty() { return id_user; }
    public StringProperty fecha_ventaProperty() { return fecha_venta; }
    public DoubleProperty costo_totalProperty() { return costo_total; }
    public IntegerProperty id_ventaProperty() { return id_venta; }
    public LongProperty ISBNProperty() { return ISBN; }
    public DoubleProperty costo_parcialProperty() { return costo_parcial; }
    public IntegerProperty id_detalleProperty() { return id_detalle; }

    public Venta(){
        this.cnn = new ConnectionDB();
    }

    public Venta(Integer id_user, String fecha_venta, Double costo_total, Integer id_venta, Long ISBN, Double costo_parcial, Integer id_detalle){
        this.id_user = new SimpleIntegerProperty(id_user);
        this.fecha_venta = new SimpleStringProperty(fecha_venta);
        this.costo_total = new SimpleDoubleProperty(costo_total);
        this.id_venta = new SimpleIntegerProperty(id_venta);
        this.ISBN = new SimpleLongProperty(ISBN);
        this.costo_parcial = new SimpleDoubleProperty(costo_parcial);
        this.id_detalle = new SimpleIntegerProperty(id_detalle);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> ventas = new LinkedList<>();
        for (var map : lista) {
            Venta venta = new Venta(
            (Integer) map.get("id_user"),
            (String) map.get("fecha_venta"),
            (Double) map.get("costo_total"),
            (Integer) map.get("id_venta"),
            (Long) map.get("ISBN"),
            (Double) map.get("costo_parcial"),
            (Integer) map.get("id_detalle")
            );
            ventas.add((T)venta);
        }
        return ventas;
    }

    public List<Venta> obtenerVenta(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Venta WHERE id_venta = ?", 
                                            this.id_venta.get()));
    }
    
}
