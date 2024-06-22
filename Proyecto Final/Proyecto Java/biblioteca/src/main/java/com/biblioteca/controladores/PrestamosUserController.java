package com.biblioteca.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import java.io.IOException;

import com.biblioteca.modelos.PrestamoUsuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrestamosUserController {
    @FXML
    private Button btnNuevo;
    @FXML
    private TableView <PrestamoUsuario> tbPrestamos;
    @FXML
    private TableColumn<PrestamoUsuario, String> colTitulo;
    @FXML
    private TableColumn<PrestamoUsuario, String> colEmail;
    @FXML
    private TableColumn<PrestamoUsuario, String> colFechaDev;
    @FXML
    private TableColumn<PrestamoUsuario, String> colFechaPrestamo;
    @FXML
    private TableColumn<PrestamoUsuario, Integer> colCantidad;
    @FXML
    private TableColumn<PrestamoUsuario, Integer> colEstado;
    @FXML
    private TextField tfBuscar;
    @FXML
    private ObservableList<PrestamoUsuario> prestamos;

    @FXML
    private void initialize() {
        cargarTabla();
        configurarColumnas();
        configurarFiltros();
    }

    private void cargarTabla(){
        PrestamoUsuario p = new PrestamoUsuario();
        prestamos = FXCollections.observableArrayList(p.obtenerPrestamos());
        tbPrestamos.setItems(prestamos);
    }

    private void configurarColumnas(){
        colTitulo.setCellValueFactory(new PropertyValueFactory<PrestamoUsuario, String>("titulo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<PrestamoUsuario, String>("email"));
        colFechaDev.setCellValueFactory(new PropertyValueFactory<PrestamoUsuario, String>("fecha_devolucion"));
        colFechaPrestamo.setCellValueFactory(new PropertyValueFactory<PrestamoUsuario, String>("fecha_prestamo"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<PrestamoUsuario, Integer>("cantidad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<PrestamoUsuario, Integer>("estado"));
    }

    private void configurarFiltros(){
        FilteredList<PrestamoUsuario> filtro = new FilteredList<>(prestamos);
        tfBuscar.textProperty().addListener((observable, oldVal, newVal) -> {
            filtro.setPredicate(prestamos -> {
                if(newVal == null || newVal.isEmpty()){
                    return true;
                }
                String busqueda = newVal.toLowerCase();
                return prestamos.emailProperty().getValue().toLowerCase().contains(busqueda) 
                || prestamos.tituloProperty().getValue().toLowerCase().contains(busqueda);
            });
        });

        SortedList<PrestamoUsuario> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tbPrestamos.comparatorProperty());
        tbPrestamos.setItems(datos);
    }

    @FXML
    private void mostrarModal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biblioteca/vistas/nuevoPrestamo.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();        
        stage.setTitle("Nuevo Prestamo");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    private void guardarPresatmo() { 

    }
}
