package com.biblioteca.modelos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
    private IntegerProperty id_user;
    private IntegerProperty id_prestamo_user;
    private DoubleProperty costo_multa;
    private StringProperty correo;
    private StringProperty estatus;
    private LongProperty ISBN;
    private StringProperty titulo;
    private StringProperty fecha_prestamo;
    private StringProperty fecha_devolucion;

    private ConnectionDB cnn;

    public IntegerProperty id_mulptaProperty() { return id_multa; }
    public IntegerProperty id_userProperty() { return id_user; }
    public IntegerProperty id_prestamo_userProperty() { return id_prestamo_user; }
    public DoubleProperty  costo_multaProperty() { return costo_multa; }
    public StringProperty correoProperty() { return correo; }
    public StringProperty estatusProperty() { return estatus; }
    public LongProperty ISBNProperty() { return ISBN; }
    public StringProperty titulProperty() { return titulo; }
    public StringProperty fecha_prestamoProperty() { return fecha_prestamo; }
    public StringProperty fecha_devolucionProperty() { return fecha_devolucion; }

    public int getId_multa() { return id_multa.get(); }
    public int getId_user() { return id_user.get(); }
    public int getId_prestamo_user() { return id_prestamo_user.get(); }
    public double getCosto_multa() { return costo_multa.get(); }
    public String getCorreo() { return correo.get(); }
    public String getEstatus() { return estatus.get(); }
    public long getISBN() { return ISBN.get(); }
    public String getTitulo() { return titulo.get(); }
    public String getFecha_prestamo() { return fecha_prestamo.get(); }
    public String getFecha_devolucion() { return fecha_devolucion.get(); }

    public void setId_multa(int id_multa){
        this.id_multa.set(id_multa);
    }
    public void setId_user(int id_user){
        this.id_user.set(id_user); 
    }
    public void setId_prestamo_user(int id_prestamo_user){ 
        this.id_prestamo_user.set(id_prestamo_user); 
    }
    public void setCosto_multa(double costo_multa){ 
        this.costo_multa.set(costo_multa); 
    }
    public void setCorreo(String correo){ 
        this.correo.set(correo); 
    }
    public void setEstatus(String estatus){ 
        this.estatus.set(estatus); 
    }
    public void setISBN(long ISBN){ 
        this.ISBN.set(ISBN); 
    }
    public void setTitulo(String titulo){ 
        this.titulo.set(titulo); 
    }
    public void setFecha_prestamo(String fecha_prestamo){ 
        this.fecha_prestamo.set(fecha_prestamo); 
    }
    public void setFecha_devolucion(String fecha_devolucion){ 
        this.fecha_devolucion.set(fecha_devolucion); 
    }

    public Multa() {
        this.id_multa = new SimpleIntegerProperty(0);
        this.id_user = new SimpleIntegerProperty(0);
        this.id_prestamo_user = new SimpleIntegerProperty(0);
        this.costo_multa = new SimpleDoubleProperty(0.0);
        this.correo = new SimpleStringProperty("");
        this.estatus = new SimpleStringProperty("");
        this.ISBN = new SimpleLongProperty(0);
        this.titulo = new SimpleStringProperty("");
        this.fecha_prestamo = new SimpleStringProperty("");
        this.fecha_devolucion = new SimpleStringProperty("");
        this.cnn = new ConnectionDB();
    }   

    public Multa(IntegerProperty id_multa){
        this.cnn = new ConnectionDB();
        this.id_multa = id_multa;
    }
    
    public Multa(Integer id_multa, Integer id_user, Integer id_prestamo_user, Double costo_multa, String estatus, String correo, Long ISBN, String titulo, String fecha_prestamo, String fecha_devolucion) {
        this.id_multa = new SimpleIntegerProperty(id_multa);
        this.id_user = new SimpleIntegerProperty(id_user);
        this.id_prestamo_user = new SimpleIntegerProperty(id_prestamo_user);
        this.costo_multa = new SimpleDoubleProperty(costo_multa);
        this.correo = new SimpleStringProperty(correo);
        this.estatus = new SimpleStringProperty(estatus);
        this.ISBN = new SimpleLongProperty(ISBN);
        this.titulo = new SimpleStringProperty(titulo);
        this.fecha_prestamo = new SimpleStringProperty(fecha_prestamo);
        this.fecha_devolucion = new SimpleStringProperty(fecha_devolucion);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> multas = new LinkedList<>();
        for (var map : lista) {
            Multa multa = new Multa(
            (Integer) map.get("id_multa"), 
            (Integer) map.get("id_user"),
            (Integer) map.get("id_prestamo_user"),
            (Double) map.get("costo_multa"),
            (String) map.get("estatus"),
            (String) map.get("email"),
            (Long) map.get("ISBN"),
            (String) map.get("titulo"),
            (String) map.get("fecha_prestamo"),
            (String) map.get("fecha_devolucion")
            );
            multas.add((T)multa);
        }
        return multas;
    }

    public List<Multa> obtenerMultas(){
        return convertirMapeo(cnn.consultar("SELECT m.*, l.titulo, u.email, e.ISBN, p.fecha_prestamo, pc.fecha_devolucion " + 
                                            "FROM Multa m JOIN Prestamo_Concentrado pc ON " +
                                            "m.id_prestamo_user = pc.id_prestamo_user JOIN Prestamo p ON " +
                                            "pc.id_prestamo = p.id_prestamo JOIN Edicion e ON " +
                                            "e.ISBN = pc.ISBN JOIN Libro l ON " + 
                                            "l.id_libro = e.id_libro JOIN Usuario u ON " +
                                            "u.id_user = m.id_user "));
    }

    public List<Multa> obtenerMulta(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Multa WHERE id_multa = ?", 
                                            getId_multa()));
    }

    public List<Multa> obtenerMultaUsuario(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Multa WHERE id_prestamo_user = ?",
                                            getId_prestamo_user()));
    }

    public Boolean comprobarMultaPrestamo(){
        return (cnn.comprobar("SELECT * FROM Multa m JOIN Prestamo_Concentrado pc ON " +
                            "m.id_prestamo_user = pc.id_prestamo_user WHERE "+
                            "pc.id_prestamo_user = ? AND id_estado = 1", 
                            getId_prestamo_user()) > 0);
    }


    public Boolean agregarMulta(){
       return (cnn.ejecutar("INSERT INTO Multa" + 
                            "(id_user, id_prestamo_user, costo_multa, estatus)" +
                            "VALUES (?, ?, ?, ?)", 
                            getId_user(),
                            getId_prestamo_user(), 
                            getCosto_multa(),
                            getEstatus()) > 0);
    }

    public Boolean actualizarCosto(){
        return (cnn.ejecutar("UPDATE Multa SET costo_multa = ? WHERE id_multa = ?", 
                            this.costo_multa.get(), 
                            this.id_multa.get()) > 0);
    }

    public Boolean actualizarCostoPrestamo(){
        return (cnn.ejecutar("UPDATE Multa SET costo_multa = ? WHERE id_prestamo_user = ?",
                                getCosto_multa(),
                                getId_prestamo_user()) > 0);
    }

    public Boolean actualizarPagar(){
        return (cnn.ejecutar("UPDATE Multa SET estatus = 'Pagada' WHERE id_prestamo_user = ?", 
                            getId_prestamo_user()) > 0);
    }

    public Boolean actualizarPrestamoMulta(){
        return (cnn.ejecutar("UPDATE Prestamo_Concentrado SET " +
                             "id_estado = 2, devolucion = (date('now')) " +
                             "WHERE id_prestamo_user = ?", getId_prestamo_user()) > 0);
    }

    public Boolean acutalizarCantidadEdicion(){
        return (cnn.ejecutar("UPDATE Edicion SET " +
                            "disponibles = disponibles + 1 WHERE " +
                            "ISBN = ?", getISBN()) > 0);
    }

}
