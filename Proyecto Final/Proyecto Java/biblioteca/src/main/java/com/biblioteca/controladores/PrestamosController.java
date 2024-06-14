package com.biblioteca.controladores;

import java.io.IOException;

import com.biblioteca.App;

import javafx.fxml.FXML;

public class PrestamosController {
    @FXML
    public void btnVolver_click() throws IOException{
        App.setRoot("main");
    }
}
