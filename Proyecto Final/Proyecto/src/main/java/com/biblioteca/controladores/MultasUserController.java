package com.biblioteca.controladores;

import com.biblioteca.modelos.Multa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MultasUserController {
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
}
