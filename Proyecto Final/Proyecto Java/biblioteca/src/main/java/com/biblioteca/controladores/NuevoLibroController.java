package com.biblioteca.controladores;

import com.biblioteca.modelos.Filtro;
import com.biblioteca.modelos.Libro;
import com.biblioteca.utilidades.ShowAlert;
import com.jfoenix.controls.JFXComboBox;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NuevoLibroController {
    @FXML
    private Label plusIcon;
    @FXML
    private Label minusIcon;
    @FXML
    private Label categoriaIconPlus;
    @FXML
    private Label categoriaIconMinus;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfAutor;
    @FXML
    private TextField tfCategoria;
    @FXML
    private TextArea txtAreaAutor;
    @FXML
    private TextArea txtAreaCategoria;
    @FXML
    private Spinner<Integer> spnCantidad;
    @FXML
    private JFXComboBox<String> cmbAutor;
    @FXML
    private JFXComboBox<String> cmbCategoria;


    @FXML
    private void initialize() {
        cargarIconos();
        cargarComboBox();
        SpinnerValueFactory<Integer> valueFactoryCantidad = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        spnCantidad.setValueFactory(valueFactoryCantidad);
    }

    @FXML
    private void cargarIconos() {
        plusIcon.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
        minusIcon.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
        categoriaIconPlus.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
        categoriaIconMinus.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
    }

    @FXML
    private void agregarLibro() {
        Libro libro = new Libro();
        libro.setTitulo(tfTitulo.getText());
        libro.setAutores(txtAreaAutor.getText());
        libro.setCategorias(txtAreaCategoria.getText());
        if(libro.agregarLibro()) {
            ShowAlert.show("Libro agregado correctamente", "El libro se ha agregado correctamente");
        } else {
            ShowAlert.show("Error", "No se pudo agregar el libro");
        }
    }

    @FXML
    private void agregarAutor() {
        String autor = cmbAutor.getValue();
        if(txtAreaAutor.getText().contains(autor))
            return;
        txtAreaAutor.appendText("\n" + autor);
    }

    @FXML
    private void eliminarAutor() {
        if(txtAreaAutor.getText().length() > 0) {
            txtAreaAutor.deleteText(txtAreaAutor.getText().length() - 1, txtAreaAutor.getText().length());
        }
    }

    @FXML
    private void agregarCategoria() {
        String categoria = cmbCategoria.getValue();
        if(txtAreaCategoria.getText().contains(categoria))
            return;
        txtAreaCategoria.appendText("\n" + categoria);
    }

    @FXML
    private void eliminarCategoria() {
        if(txtAreaAutor.getText().length() > 0) {
            txtAreaAutor.deleteText(txtAreaAutor.getText().length() - 1, txtAreaAutor.getText().length());
        }
    }

    private void cargarComboBox() {
        cmbAutor.getItems().addAll(Filtro.listaAutor);
        cmbCategoria.getItems().addAll(Filtro.listaCategoria);
    }
}
