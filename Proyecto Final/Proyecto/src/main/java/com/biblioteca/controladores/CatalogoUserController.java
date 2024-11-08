package com.biblioteca.controladores;

import java.util.function.Predicate;

import com.biblioteca.modelos.Edicion;
import com.biblioteca.modelos.Filtro;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CatalogoUserController {
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Edicion> tbLibros;
    @FXML
    private TableColumn<Edicion, String> colAutores;
    @FXML
    private TableColumn<Edicion, String> colCategoria;
    @FXML
    private TableColumn<Edicion, Integer> colDisponible;
    @FXML
    private TableColumn<Edicion, Integer> colEdicion;
    @FXML
    private TableColumn<Edicion, String> colTitulo;
    @FXML
    private JFXComboBox<String> cmbxAutor;
    @FXML
    private JFXComboBox<String> cmbxCategoria;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Label iconBook;
    @FXML
    private Label iconList;
    @FXML
    private ObservableList<Edicion> libros;

    @FXML
    public void initialize() {
        cargarTabla();
        configurarColumnas();
        configurarFiltros();
        cargarComboBox();
    }

    private void cargarTabla() {
        Edicion e = new Edicion();
        libros = FXCollections.observableArrayList(e.consultarLibros());
        tbLibros.setItems(libros);
    }

    private void configurarColumnas() {
        colTitulo.setCellValueFactory(new PropertyValueFactory<Edicion, String>("titulo"));
        colEdicion.setCellValueFactory(new PropertyValueFactory<Edicion, Integer>("no_edicion"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<Edicion, Integer>("disponibles"));
        colAutores.setCellValueFactory(new PropertyValueFactory<Edicion, String>("autores"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Edicion, String>("categorias"));
    }

    private void configurarFiltros() {
        FilteredList<Edicion> filtro = new FilteredList<>(libros, b -> true);
        tfBuscar.textProperty().addListener((observable, oldVal, newVal) -> {
            filtro.setPredicate(libros -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String titulo = newVal.toLowerCase();

                return libros.tituloProperty().getValue().toLowerCase().contains(titulo);
            });
        });
        cmbxAutor.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            aplicarFiltro(filtro, edicion -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String[] autoresSeleccionados = newVal.trim().split(",");
                String[] autoresLibro = edicion.autoresProperty().getValue().split(",");

                for (String autorSeleccionado : autoresSeleccionados) {
                    String autorSeleccionadoTrimmed = autorSeleccionado.trim().toLowerCase();
                    for (String autorLibro : autoresLibro) {
                        if (autorLibro.trim().equalsIgnoreCase(autorSeleccionadoTrimmed)) {
                            return true;
                        }
                    }
                }
                return false;
            });
        });

        cmbxCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            aplicarFiltro(filtro, edicion -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String[] categoriasSeleccionadas = newVal.trim().split(",");
                String[] categoriasLibro = edicion.categoriasProperty().getValue().split(",");

                for (String categoriaSeleccionada : categoriasSeleccionadas) {
                    String categoriaSeleccionadaTrimmed = categoriaSeleccionada.trim().toLowerCase();
                    for (String categoriaLibro : categoriasLibro) {
                        if (categoriaLibro.trim().equalsIgnoreCase(categoriaSeleccionadaTrimmed)) {
                            return true;
                        }
                    }
                }
                return false;
            });
        });
        SortedList<Edicion> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tbLibros.comparatorProperty());
        tbLibros.setItems(datos);
    }

    private void aplicarFiltro(FilteredList<Edicion> filtro, Predicate<Edicion> predicate) {
        filtro.setPredicate(libros -> {
            if (predicate == null) {
                return true;
            }
            return predicate.test(libros);
        });
    }

    private void cargarComboBox() {
        Filtro f = new Filtro();
        cmbxAutor.getItems().addAll(f.obtenerAutores());
        cmbxCategoria.getItems().addAll(f.obtenerCategorias());
    }

}
