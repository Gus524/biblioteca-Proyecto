package com.biblioteca.controladores;

import java.io.IOException;

import com.biblioteca.modelos.Multa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MultasAdminController {
     @FXML
    private TableView<Multa> tbMultas;
    @FXML
    private TableColumn<Multa, Integer> colIdMulta;
    @FXML
    private TableColumn<Multa, Integer> colIdCliente;
    @FXML
    private TableColumn<Multa, String> colCorreo;
    @FXML
    private TableColumn<Multa, Long> colISBN;
    @FXML
    private TableColumn<Multa, String> colTitulo;
    @FXML
    private TableColumn<Multa, Double> colCosto;
    @FXML
    private TableColumn<Multa, String> colEstado;
    @FXML
    private TableColumn<Multa, String> colPrestamo;
    @FXML
    private TableColumn<Multa, String> colDevolucion;
    @FXML
    private TextField tfBuscar;
    private ObservableList<Multa> multas;

    @FXML
    private void initialize(){
        configurarColumnas();
        cargarTabla();
        configurarBusqueda();
        configurarFilas();
    }

    private void configurarColumnas(){
        colIdMulta.setCellValueFactory(new PropertyValueFactory<Multa, Integer>("id_multa"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<Multa, Integer>("id_user"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Multa, String>("correo"));
        colISBN.setCellValueFactory(new PropertyValueFactory<Multa, Long>("ISBN"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Multa, String>("titulo"));
        colCosto.setCellValueFactory(new PropertyValueFactory<Multa, Double>("costo_multa"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Multa, String>("estatus"));
        colPrestamo.setCellValueFactory(new PropertyValueFactory<Multa, String>("fecha_prestamo"));
        colDevolucion.setCellValueFactory(new PropertyValueFactory<Multa, String>("fecha_devolucion"));

    }

    private void cargarTabla(){
        Multa m = new Multa();
        multas = FXCollections.observableArrayList(m.obtenerMultas());
        tbMultas.setItems(multas);
    }

    private void configurarBusqueda(){
        FilteredList<Multa> filtro = new FilteredList<>(multas, b -> true);
        tfBuscar.textProperty().addListener((observable, oldVal, newVal) -> {
            filtro.setPredicate(multas -> {
                if(newVal == null || newVal.isEmpty()){
                    return true;
                }
                String busqueda = newVal.toLowerCase();
                return multas.correoProperty().getValue().toLowerCase().contains(busqueda) 
                || multas.titulProperty().getValue().toLowerCase().contains(busqueda);
            });
        });
        SortedList<Multa> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tbMultas.comparatorProperty());
        tbMultas.setItems(datos);
    }

    private void configurarFilas(){
        tbMultas.setRowFactory(tv -> {
            TableRow<Multa> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                if(!fila.isEmpty()){
                    Multa multa = fila.getItem();
                    try {
                        mostrarEditarMulta(multa);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return fila;
        });
    }

    @FXML
    private void mostrarEditarMulta(Multa multa) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biblioteca/vistas/modals/editarMulta.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        EditarMultaController controlador = loader.getController();
        controlador.cargarMulta(multa);
        stage.setTitle("Editar Multa");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        stage.setScene(new Scene(root));
        stage.setOnHiding(event -> actualizarTabla());
        stage.showAndWait();
    }

    private void actualizarTabla(){
        multas.clear();
        Multa m = new Multa();
        multas.addAll(m.obtenerMultas());
        tbMultas.refresh();
    }
}
