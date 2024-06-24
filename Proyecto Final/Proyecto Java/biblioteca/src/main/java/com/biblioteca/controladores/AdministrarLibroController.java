package com.biblioteca.controladores;

import com.biblioteca.modelos.Edicion;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import com.biblioteca.utilidades.ShowAlert;

public class AdministrarLibroController {
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfISBN;
    @FXML
    private Spinner<Integer> spnEdicion = new Spinner<Integer>();
    @FXML
    private Spinner<Integer> spnCantidad = new Spinner<Integer>();
    @FXML
    private Spinner<Integer> spnPublicacion = new Spinner<Integer>();

    public void cargarLibro(Edicion edicion) {
        tfTitulo.setText(edicion.getTitulo());
        tfISBN.setText(String.valueOf(edicion.getISBN()));
        spnEdicion.getValueFactory().setValue(edicion.getNo_edicion());
        spnCantidad.getValueFactory().setValue(edicion.getDisponibles());
        spnPublicacion.getValueFactory().setValue(edicion.getPublicacion());
    }

    @FXML
    public void initialize() {
        // Inicialización de SpinnerValueFactory para cada Spinner
        SpinnerValueFactory<Integer> valueFactoryEdicion = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                Integer.MAX_VALUE, 1);
        spnEdicion.setValueFactory(valueFactoryEdicion);

        SpinnerValueFactory<Integer> valueFactoryCantidad = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,
                Integer.MAX_VALUE, 0);
        spnCantidad.setValueFactory(valueFactoryCantidad);

        SpinnerValueFactory<Integer> valueFactoryPublicacion = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900,
                2100, 2024);
        spnPublicacion.setValueFactory(valueFactoryPublicacion);
    }

    @FXML
    private void guardarCambios() {
        Edicion e = new Edicion();
        e.setTitulo(tfTitulo.getText());
        e.setISBN(Long.parseLong(tfISBN.getText()));
        e.setNo_edicion(spnEdicion.getValue());
        e.setDisponibles(spnCantidad.getValue());
        e.setPublicacion(spnPublicacion.getValue());
        if (e.actualizarEdicion()) {
            ShowAlert.show("Actualización exitosa", "La edición se ha actualizado correctamente");
        } else {
            ShowAlert.show("Error", "No se pudo actualizar la edición");
        }
    }

    @FXML
    private void agregarEdicion() {
        Edicion e = new Edicion();
        e.setTitulo(tfTitulo.getText());
        e.setISBN(Long.parseLong(tfISBN.getText()));
        e.setNo_edicion(spnEdicion.getValue());
        e.setDisponibles(spnCantidad.getValue());
        e.setPublicacion(spnPublicacion.getValue());
        if (e.agregarEdicion()) {
            ShowAlert.show("Agregada correctamente", "La edición se ha agregado correctamente");
        } else {
            ShowAlert.show("Error", "No se pudo agregar la edición");
        }
    }
}
