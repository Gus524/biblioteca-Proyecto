package com.biblioteca.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.biblioteca.App;
import com.biblioteca.modelos.Edicion;
import com.biblioteca.modelos.Filtro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibrosController implements Initializable{
    @FXML 
    private ComboBox<String> cmbxAutor;
    @FXML
    private ComboBox<String> cmbxCategoria;
    @FXML
    private TableView<Edicion> tbLibros;
    @FXML 
    private ObservableList<Edicion> libros;
    @FXML
    private TableColumn<Edicion, String> tituloColumn;
    @FXML 
    private TableColumn<Edicion, Integer> ISBNColumn;
    @FXML
    private TableColumn<Edicion, Integer> publColumn;
    @FXML
    private TableColumn<Edicion, Double> precioColumn;
    @FXML
    private TableColumn<Edicion, String> autorColumn;
    @FXML
    private TableColumn<Edicion, String> categoriaColumn;
    @FXML
    private TableColumn<Edicion, Integer> dispColumn;
    @FXML
    private TableColumn<Edicion, Integer> edicionColumn;
    @FXML
    private TextField txtTitulo;

    Filtro f = new Filtro(); 

    @Override
    public void initialize(URL location, ResourceBundle rsc){
        setCmbx();
        configurarColumnas();
        cargarTabla();
        configurarFiltros();
    }

    private void setCmbx(){
        cmbxAutor.getItems().addAll(Filtro.listaAutor);
        cmbxCategoria.getItems().addAll(Filtro.listaCategoria);
    }

    private void configurarColumnas(){
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        publColumn.setCellValueFactory(new PropertyValueFactory<>("publicacion"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("autores"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categorias"));
        dispColumn.setCellValueFactory(new PropertyValueFactory<>("disponibles"));
        edicionColumn.setCellValueFactory(new PropertyValueFactory<>("no_edicion"));
    }
    
    private void cargarTabla(){
        Edicion e = new Edicion();
        libros = FXCollections.observableArrayList(e.consultarLibros());
        tbLibros.setItems(libros);
    }

    private void configurarFiltros(){
        FilteredList<Edicion> filtro = new FilteredList<>(libros, b -> true);
        txtTitulo.textProperty().addListener((observable, oldVal, newVal) -> {
            filtro.setPredicate(libros -> {
                if(newVal ==  null || newVal.isEmpty()){
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

    @FXML
    private void btnVolver_click() throws IOException{
        App.setRoot("main");
    }
}
