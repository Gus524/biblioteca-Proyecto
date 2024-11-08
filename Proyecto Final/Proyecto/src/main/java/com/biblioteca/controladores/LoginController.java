package com.biblioteca.controladores;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import com.biblioteca.App;
import com.biblioteca.modelos.Login;
import com.biblioteca.utilidades.Fecha;
import com.biblioteca.utilidades.ShowAlert;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController implements Initializable{
    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfPassword;
    @FXML
    private Label iconLock;
    @FXML
    private Label iconUser;
    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle rsc){
        Image image = new Image(App.class.getResource("images/logo.png").toExternalForm());
        System.out.println("Ruta:" + image.getUrl());
        imageView.setImage(image);
        cargarIconos();
    }
    @FXML
    private void btnEntrar_click() throws IOException{
        Login login = new Login(tfUsuario.getText(), tfPassword.getText());
        Fecha.comprobarMulta();
        if(login.comprobarUser()){
            switch (tfUsuario.getText()) {
                case "admin":
                    App.setRoot("mainAdmin");
                    break;
                case "usuario":
                    App.setRoot("mainUser");
                    break;
                default:
                    break;
            }
        }
        else{
            ShowAlert.show("Error", "Usuario o contraseña incorrectos");
        }
    }

    private void cargarIconos(){
        iconLock.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, "1.8em"));
        iconUser.setGraphic((GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.8em")));
    }
}
