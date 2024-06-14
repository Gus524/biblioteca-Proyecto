package com.biblioteca.controladores;

import java.io.IOException;

import com.biblioteca.App;

import javafx.fxml.FXML;

public class ClientesController {
    @FXML
    public void btnVolver_click() throws IOException{
        App.setRoot("main");
    }

    @FXML
    public void btnCliente_click() throws IOException{
        App.setRoot("registrarCliente");
    }
}
