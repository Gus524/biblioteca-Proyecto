package com.biblioteca.controladores;


import java.io.IOException;

import com.biblioteca.App;
import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class CatalogoUserController {
    @FXML
    private JFXButton btnCatalogo;
    @FXML
    private JFXButton btnCerrarSesion;
    @FXML
    private JFXButton btnLectores;
    @FXML
    private JFXButton btnPrestamos;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableColumn<?, ?> colAutores;
    @FXML
    private TableColumn<?, ?> colCategoria;
    @FXML
    private TableColumn<?, ?> colDisponible;
    @FXML
    private TableColumn<?, ?> colEdicion;
    @FXML
    private TableColumn<?, ?> colTitulo;
    @FXML
    private ComboBox<?> comboFiltro;
    @FXML
    private TextField tfBuscar;
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
    public void initialize() {
        iconBook.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.BOOK, "1.8em"));
        iconList.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LIST, "1.8em"));
        iconUser.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.USER, "1.8em"));
        iconLock.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, "1.8em"));
    }

    @FXML
    private void cargarPrestamo(){

    }

    @FXML
    private void cargarLectores(){
        cargarContenido("lectoresUser");
    }

    @FXML  
    private void cerrarSesion() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void cargarContenido(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biblioteca/vistas/" + fxml + ".fxml"));
            Node node = loader.load();
            // Reemplazar el contenido del centro del pane
            panel.setCenter(node);
            btnLectores.setStyle("icon active icon");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
