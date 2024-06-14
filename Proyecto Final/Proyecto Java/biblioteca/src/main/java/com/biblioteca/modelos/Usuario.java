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
    private IntegerProperty tel_user;

    public IntegerProperty id_userProperty() { return id_user; }
    public StringProperty nom_userProperty() { return nom_user; }
    public StringProperty ap_userProperty() { return ap_user; }
    public StringProperty correoProperty() { return correo; }
    public IntegerProperty tel_userProperty() { return tel_user; }

    public Usuario(){
        cnn = new ConnectionDB();
    }

    public Usuario(Integer id_user, String nom_user, String ap_user, String correo, Integer tel_user){
        this.id_user = new SimpleIntegerProperty(id_user);
        this.nom_user = new SimpleStringProperty(nom_user);
        this.ap_user = new SimpleStringProperty(ap_user);
        this.correo = new SimpleStringProperty(correo);
        this.tel_user = new SimpleIntegerProperty(tel_user);
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
            (String) map.get("email"),
            (Integer) map.get("tel_user")
            );
            usuarios.add((T)usuario);
        }
        return usuarios;
    }

    public List<Usuario> obtenerUsuarios(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Usuario"));
    }
    
    public List<Usuario> obtenerUsuario(){
        return convertirMapeo(cnn.consultar("SELECT * FROM Usuario WHERE id_user = ?", 
                                            this.id_user.get()));
    }
}
