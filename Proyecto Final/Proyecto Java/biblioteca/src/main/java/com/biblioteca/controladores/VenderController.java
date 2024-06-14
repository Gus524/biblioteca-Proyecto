package com.biblioteca.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.biblioteca.App;
import com.biblioteca.modelos.Edicion;

import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VenderController implements Initializable{
    @FXML
    private ObservableList<Edicion> libros;
    @FXML
    private TableView<Edicion> tbLibros;
    @FXML
    private TableColumn<Edicion, String> tituloColumn;
    @FXML
    private TableColumn<Edicion, String> edicionColumn;
    @FXML  
    private TableColumn<Edicion, String> precioColumn;
    @FXML  
    private TableColumn<Edicion, String> dispColumn;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextArea txtLibros;
    @FXML
    private TextField txtTitulo;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnQuitar;
    private static List<Edicion> listaVenta;
    private static LongProperty isbn;
    private static Double total;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        listaVenta = new ArrayList<>();
        configurarColumnas();
        cargarTabla();
        configurarFiltro();
        configurarFila();
    }

    private void cargarTabla(){
        Edicion e = new Edicion();
        libros = FXCollections.observableArrayList(e.consultarLibros());
        tbLibros.getItems().addAll(libros);
    }

    private void configurarColumnas(){
        tituloColumn.setCellValueFactory(new PropertyValueFactory<Edicion, String>("titulo"));
        edicionColumn.setCellValueFactory(new PropertyValueFactory<Edicion, String>("no_edicion"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<Edicion, String>("precio"));
        dispColumn.setCellValueFactory(new PropertyValueFactory<Edicion, String>("disponibles"));
    }

    private void configurarFila(){
        tbLibros.setRowFactory(tb -> {
            TableRow<Edicion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(!row.isEmpty()) {
                    isbn = row.getItem().ISBNProperty();
                    btnAgregar.setDisable(false);
                }
                else{
                    btnAgregar.setDisable(true);
                }
            });
            return row;
        });
    }

    private void configurarFiltro(){
        FilteredList<Edicion> filteredData = new FilteredList<>(libros, p -> true);
        txtTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(libro -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String titulo = newValue.toLowerCase();
                if (libro.tituloProperty().getValue().toLowerCase().contains(titulo)){
                    return true;
                }
                return false;
            });
        });
        SortedList<Edicion> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbLibros.comparatorProperty());
        tbLibros.setItems(sortedData);
    }
    
    @FXML
    private void btnAgregar_click(){
        Edicion e = new Edicion(isbn);
        System.out.println(isbn.getValue());
        String lista = txtLibros.getText();
        txtLibros.setText(lista + e.buscarEdicion().get(0).tituloProperty().get() + " $" + e.buscarEdicion().get(0).precioProperty().get() + "\n");
        if(txtLibros.getText() != ""){
            btnQuitar.setDisable(false);
        }
        listaVenta.add(e.buscarEdicion().get(0));
    }

    @FXML
    private void btnQuitar_click(){
        txtLibros.setText("");
        btnAgregar.setDisable(true);
    }

    @FXML
    private void btnVender_clic(){
        
    }

    @FXML
    public void btnVolver_click() throws IOException{
        App.setRoot("main");
    }
}
