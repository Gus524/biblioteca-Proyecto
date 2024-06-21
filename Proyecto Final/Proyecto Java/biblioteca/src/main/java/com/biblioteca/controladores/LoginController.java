package com.biblioteca.controladores;
import java.io.IOException;

import com.biblioteca.App;
import com.biblioteca.modelos.Login;
import com.biblioteca.utilidades.ShowAlert;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfPassword;

    @FXML
    private void btnEntrar_click() throws IOException{
        Login login = new Login(tfUsuario.getText(), tfPassword.getText());
        if(login.comprobarUser()){
            App.setRoot("main");
        }
        else{
            ShowAlert.show("Error", "Usuario o contraseña incorrectos");
        }
    }
}
