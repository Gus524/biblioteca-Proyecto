package com.biblioteca.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.modelos.Edicion;
import com.biblioteca.modelos.Filtro;
import com.biblioteca.modelos.Filtro.Autor;
import com.biblioteca.modelos.Filtro.Categoria;
import com.biblioteca.utilidades.ShowAlert;
import com.jfoenix.controls.JFXComboBox;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NuevoLibroController {
    @FXML
    private Label plusIcon;
    @FXML
    private Label minusIcon;
    @FXML
    private Label categoriaIconMinus;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfPublicacion;
    @FXML
    private TextField tfISBN;
    @FXML
    private TextArea txtAutor;
    @FXML
    private TextArea txtCategoria;
    @FXML
    private Button btnEliminarCategoria;
    @FXML
    private Button btnEliminarAutor;
    @FXML
    private Spinner<Integer> spnCantidad = new Spinner<Integer>();
    @FXML
    private JFXComboBox<String> cmbAutor;
    @FXML
    private JFXComboBox<String> cmbCategoria;
    @FXML
    private Spinner<Integer> spnEdicion = new Spinner<Integer>();
    private List<String> autores = new ArrayList<>();
    private List<String> categorias = new ArrayList<>();


    @FXML
    private void initialize() {
        cargarIconos();
        cargarComboBox();
        inicarSpinner();
    }

    private void inicarSpinner(){
        SpinnerValueFactory<Integer> valueFactoryCantidad = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1);
        spnCantidad.setValueFactory(valueFactoryCantidad);
        SpinnerValueFactory<Integer> valueFactoryEdicion = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 1);
        spnEdicion.setValueFactory(valueFactoryEdicion);
    }

    @FXML
    private void cargarIconos() {
        plusIcon.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
        minusIcon.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
        categoriaIconMinus.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MINUS));
    }

    @FXML
    private void agregarLibro() {
        Edicion e = new Edicion();
        int id_libro, year = LocalDate.now().getYear();
        if(tfTitulo.getText().trim() == "" || tfPublicacion.getText().trim() == "" || tfISBN.getText().trim() == "" || txtAutor.getText().trim() == "" || txtCategoria.getText().trim() == ""){
            ShowAlert.show("Error", "Debe llenar todos los campos");
            return;
        }
        if(!tfISBN.getText().trim().matches("[0-9]+") || tfISBN.getText().trim().length() != 13){
            ShowAlert.show("Error", "El ISBN solo puede contener números y deben ser 13 dígitos");
            return;
        }
        if(!tfPublicacion.getText().trim().matches("[0-9]+") || tfPublicacion.getText().trim().length() != 4){
            ShowAlert.show("Error", "El año de publicación solo puede contener números y deben ser 4 dígitos");
            return;
        }
        if(Integer.parseInt(tfPublicacion.getText().trim()) > year){
            ShowAlert.show("Error", "El año de publicación no puede ser mayor al año actual");
            return;
        }
        e.setTitulo(tfTitulo.getText().trim());
        e.setISBN(Long.parseLong(tfISBN.getText().trim()));
        e.setNo_edicion(spnEdicion.getValue());
        e.setDisponibles(spnCantidad.getValue());
        e.setPublicacion(Integer.parseInt(tfPublicacion.getText().trim()));
        if(e.comprobarEdicion()) {
            ShowAlert.show("Error", "El libro ya existe");
            return;
        }
        id_libro = e.agregarLibro();

        System.out.println(id_libro);
        if(id_libro > 0) {
            e.setId_libro(id_libro);
            if(e.agregarEdicion()){
            for(String autor : autores) {
                Filtro f = new Filtro();
                Autor a = f.new Autor();
                a.setNom_autor(autor);
                a.setId_autor(a.comprobarAutor().get(0).getId_autor());
                a.agregarLibro(id_libro);
            }
            for(String categoria : categorias) {
                Filtro f = new Filtro();
                Categoria c = f.new Categoria();
                c.setDescripcion(categoria);
                c.setId_categoria(c.comprobarCategoria().get(0).getId_categoria());
                System.out.println(c.getId_categoria() + c.getDescripcion());
                c.agregarLibro(id_libro);
            }
            ShowAlert.show("Libro agregado correctamente", "El libro se ha agregado correctamente");
        }
        } else {
            ShowAlert.show("Error", "No se pudo agregar el libro");
        }
    }

    @FXML
    private void agregarAutor() {
        String autor = cmbAutor.getValue();
        if(txtAutor.getText().contains(autor))
            return;
        autores.add(autor);
        btnEliminarAutor.setDisable(false);
        txtAutor.appendText("\n" + autor);
    }

    @FXML
    private void agregarCategoria() {
        String categoria = cmbCategoria.getValue();
        if(txtCategoria.getText().contains(categoria))
            return;
        categorias.add(categoria.trim());
        btnEliminarCategoria.setDisable(false);
        txtCategoria.appendText("\n" + categoria);
    }

    @FXML
    private void eliminarAutor() {
        if(txtAutor.getText().length() > 0 ) {
            txtAutor.deleteText(obtenerIndiceSaltoDeLinea(txtAutor.getText()), txtAutor.getText().length());
            autores.remove(autores.size() - 1);
            btnEliminarAutor.setDisable(autores.size() == 0);
        }
    }

    private int obtenerIndiceSaltoDeLinea(String texto) {
        for(int i = texto.length() - 1; i > 0; i--) {
            if(texto.charAt(i) == '\n') {
                return i;
            }
        }
        return 0; 
    }

    @FXML
    private void eliminarCategoria() {
        if(txtCategoria.getText().length() > 0) {
            txtCategoria.deleteText(obtenerIndiceSaltoDeLinea(txtCategoria.getText()), txtCategoria.getText().length());
            categorias.remove(categorias.size() - 1);
            btnEliminarCategoria.setDisable(categorias.size() == 0);
        }
    }

    private void cargarComboBox() {
        Filtro f = new Filtro();
        cmbAutor.getItems().addAll(f.obtenerAutores());
        cmbCategoria.getItems().addAll(f.obtenerCategorias());
    }

    private void reloadComboBox(String autor){
        cmbAutor.getItems().add(autor);
    }

    @FXML
    private void cancelar(){
        Stage modal = (Stage) tfTitulo.getScene().getWindow();
        modal.close();
    }

    @FXML
    private void nuevoAutor(){
        TextField tfAutor = new TextField();
        tfAutor.setStyle("-fx-font-size: 14px; -fx-pref-width: 150px; -fx-text-fill: black; -fx-background-color: white;");
        Button btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(e -> {
            guardarAutor(tfAutor);
        });
        btnAgregar.setStyle("-fx-font-size: 12px; -fx-min-width: 80px; -fx-text-fill: white; -fx-background-color: #4CAF50;");
        VBox vbox = new VBox(10, tfAutor, btnAgregar);
        Scene scene = new Scene(vbox, 200, 100);
        Stage modal = new Stage();
        modal.setScene(scene);
        modal.setTitle("Nuevo Autor");
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(null);
        modal.showAndWait();
    }

    private void guardarAutor(TextField newAutor) {
        Filtro f = new Filtro();
        Autor a = f.new Autor();
        String autor = newAutor.getText();
        if (autor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\\\s .]+") && !(autor == "")){
            a.setNom_autor(autor.toUpperCase());
            if (!a.autorExiste()) {
                if (a.agregarAutor()) {
                    ShowAlert.show("Autor agregado", "El autor se ha agregado correctamente");
                    reloadComboBox(autor);
                } else {
                    ShowAlert.show("Error", "No se pudo agregar el autor");
                }
            } else {
                ShowAlert.show("Error", "El autor ya existe");
            }
        } else {
            ShowAlert.show("Error", "Debe ingresar un nombre de autor valido");
        }
    }
}
