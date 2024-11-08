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

public class Usuario implements ConvertirMapeo{
    private ConnectionDB cnn;
    private IntegerProperty id_user;
    private StringProperty nom_user;
    private StringProperty ap_user;
    private StringProperty correo;

    public IntegerProperty id_userProperty() { return id_user; }
    public StringProperty nom_userProperty() { return nom_user; }
    public StringProperty ap_userProperty() { return ap_user; }
    public StringProperty correoProperty() { return correo; }

    public int getId_user() { return id_user.get(); }
    public String getNom_user() { return nom_user.get(); }
    public String getAp_user() { return ap_user.get(); }
    public String getCorreo() { return correo.get(); }

    public void setId_user(int id_user){
        this.id_user.set(id_user); 
    }
    public void setNom_user(String nom_user){
        this.nom_user.set(nom_user);
    }
    public void setAp_user(String ap_user){ 
        this.ap_user.set(ap_user);
    }
    public void setCorreo(String correo){
        this.correo.set(correo);
    }

    public Usuario(){
        this.id_user = new SimpleIntegerProperty(0);
        this.nom_user = new SimpleStringProperty("");
        this.ap_user = new SimpleStringProperty("");
        this.correo = new SimpleStringProperty("");
        this.cnn = new ConnectionDB();
    }

    public Usuario(Integer id_user, String nom_user, String ap_user, String correo){
        this.id_user = new SimpleIntegerProperty(id_user);
        this.nom_user = new SimpleStringProperty(nom_user);
        this.ap_user = new SimpleStringProperty(ap_user);
        this.correo = new SimpleStringProperty(correo);
    }

    @Override
    public String toString(){
        return correoProperty().getValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> usuarios = new LinkedList<>();
        for(var map : lista){
            Usuario usuario = new Usuario(
            (Integer) map.get("id_user"),
            (String) map.get("nom_user"),
            (String) map.get("ap_user"),
            (String) map.get("email")
            );
            usuarios.add((T)usuario);
        }
        return usuarios;
    }

    public Boolean guardarUsuario(){
        return (cnn.ejecutar("INSERT INTO Usuario(nom_user, ap_user, email) VALUES(?, ?, ?)",
                getNom_user(), getAp_user(), getCorreo()) > 0);
    }

    public Boolean comprobarCorreo(){
        return (cnn.comprobar("SELECT * FROM Usuario WHERE email = ?",
                getCorreo()) > 0);
    }

    public List<Usuario> obtenerUsuarios(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Usuario"));
    }
    
    public List<Usuario> obtenerUsuario(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Usuario WHERE id_user = ?", 
                                            getId_user()));
    }

    public List<Usuario> obtenerUsuarioCorreo(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Usuario WHERE email = ?", getCorreo()));
    }

    public Boolean actualizarUsuario(){
        return (cnn.ejecutar("UPDATE Usuario SET nom_user = ?, ap_user = ?, email = ? WHERE id_user = ?",
                getNom_user(), getAp_user(), getCorreo(), getId_user()) > 0);
    }
}
