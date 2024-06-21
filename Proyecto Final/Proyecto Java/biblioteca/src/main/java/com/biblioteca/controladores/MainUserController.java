package com.biblioteca.controladores;

import java.io.IOException;

import com.biblioteca.App;
import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MainUserController{
    @FXML
    private JFXButton btnCatalogo;
    @FXML
    private JFXButton btnCerrarSesion;
    @FXML
    private JFXButton btnLectores;
    @FXML
    private JFXButton btnPrestamos;
    @FXML
    private Label iconBook;
    @FXML
    private Label iconList;
    @FXML
    private Label iconUser;
    @FXML
    private Label iconLock;
    @FXML
    private BorderPane panel;

    @FXML
    public void initialize() throws IOException{
        cargarCatalogo();
        cargarIconos();
    }

    public void cargarIconos(){
        iconBook.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.BOOK, "1.8em"));
        iconList.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LIST, "1.8em"));
        iconUser.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.8em"));
        iconLock.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, "1.8em"));
    }

    @FXML
    private void cargarCatalogo() throws IOException{
        activarBoton(btnCatalogo, iconBook);
        cargarContenido("catalogoUser");
    }

    @FXML
    private void cargarLectores() throws IOException{
        activarBoton(btnLectores, iconUser);
        cargarContenido("lectoresUser");
    }

    @FXML
    private void cargarPrestamos() throws IOException{
        activarBoton(btnPrestamos, iconLock);
        cargarContenido("prestamosUser");
    }
    
    @FXML
    private void cargarContenido(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biblioteca/vistas/" + fxml + ".fxml"));
        Node node = loader.load();
        panel.setCenter(node);
    }

    private void activarBoton(JFXButton btn, Label icon){
        desactivarBotones();

        btn.getStyleClass().add("active-button");
        icon.getStyleClass().add("active-icon");
    }

    private void desactivarBotones(){
        btnCatalogo.getStyleClass().remove("active-button");
        btnLectores.getStyleClass().remove("active-button");
        btnPrestamos.getStyleClass().remove("active-button");

        iconBook.getStyleClass().remove("active-icon");
        iconList.getStyleClass().remove("active-icon");
        iconUser.getStyleClass().remove("active-icon");
    }

    @FXML
    private void cerrarSesion() throws IOException{
        App.setRoot("login");
    }
}
