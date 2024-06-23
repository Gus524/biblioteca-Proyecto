package com.biblioteca.modelos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.biblioteca.dao.ConnectionDB;
import com.biblioteca.interfaces.ConvertirMapeo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Estado implements ConvertirMapeo{
    private ConnectionDB cnn;
    private StringProperty desc_estado;

    public StringProperty desc_estadoProperty() { return desc_estado; }

    public Estado(){
        this.cnn = new ConnectionDB();
    }

    public Estado(String desc_estado){
        this.desc_estado = new SimpleStringProperty(desc_estado);
        this.cnn = new ConnectionDB();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista) {
        List<T> estados = new LinkedList<>();
        for (var map : lista) {
            Estado estado = new Estado(
            (String) map.get("desc_estado"));
            estados.add((T)estado.desc_estadoProperty().getValue());
        }
        return estados;
    }

    public List<String> obtenerEstados(){
        return convertirMapeo(cnn.consultar("SELECT desc_estado FROM Estado"));
    }
}