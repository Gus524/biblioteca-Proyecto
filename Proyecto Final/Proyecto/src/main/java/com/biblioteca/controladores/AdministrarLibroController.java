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
    private int id_libro;
    private Long originalISBN; 
    private Boolean isEditMode = false;

    public void cargarLibro(Edicion edicion) {
        tfTitulo.setText(edicion.getTitulo());
        tfISBN.setText(String.valueOf(edicion.getISBN()));
        spnEdicion.getValueFactory().setValue(edicion.getNo_edicion());
        spnCantidad.getValueFactory().setValue(edicion.getDisponibles());
        spnPublicacion.getValueFactory().setValue(edicion.getPublicacion());
        id_libro = edicion.getId_libro();
    }

    @FXML
    public void initialize() {
        // Inicialización de SpinnerValueFactory para cada Spinner
        SpinnerValueFactory<Integer> valueFactoryEdicion = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                Integer.MAX_VALUE, 1);
        spnEdicion.setValueFactory(valueFactoryEdicion);

        SpinnerValueFactory<Integer> valueFactoryCantidad = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                Integer.MAX_VALUE, 1);
        spnCantidad.setValueFactory(valueFactoryCantidad);

        SpinnerValueFactory<Integer> valueFactoryPublicacion = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900,
                2024, 2024);
        spnPublicacion.setValueFactory(valueFactoryPublicacion);
    }

    @FXML
    private void guardarCambios() {
        try {
            Long newISBN = Long.parseLong(tfISBN.getText());

            if (isEditMode) {
              
                if (!newISBN.equals(originalISBN)) {
                    ShowAlert.show("Error", "No se puede cambiar el ISBN en modo edición");
                    return;
                }
            } else {
                Edicion existingEdicion = new Edicion();
                existingEdicion.setISBN(newISBN);
                existingEdicion.setNo_edicion(spnEdicion.getValue());
                if (existingEdicion.comprobarEdicion()) {
                    ShowAlert.show("Error", "El ISBN ya existen");
                    return;
                }
                if(existingEdicion.comprobarExisteISBNyNumero()){
                    ShowAlert.show("Error", "El número de edición ya existe");
                    return;
                }
            }

            if(tfTitulo.getText().isEmpty() || tfISBN.getText().isEmpty()) {
                ShowAlert.show("Error", "Por favor, llene todos los campos");
                return;
            }
            if(spnCantidad.getValue() < 0) {
                ShowAlert.show("Error", "La cantidad no puede ser negativa");
                return;
            }
            if(spnPublicacion.getValue() < 1900 || spnPublicacion.getValue() > 2024) {
                ShowAlert.show("Error", "El año de publicación debe estar entre 1900 y 2024");
                return;
            }
            if(tfISBN.getText().length() != 13) {
                ShowAlert.show("Error", "El ISBN debe tener 13 dígitos");
                return;
            }
            Edicion e = new Edicion();
            e.setTitulo(tfTitulo.getText());
            e.setISBN(newISBN);
            e.setNo_edicion(spnEdicion.getValue());
            e.setDisponibles(spnCantidad.getValue());
            e.setPublicacion(spnPublicacion.getValue());
            e.setId_libro(id_libro);
            if(e.comprobarExisteISBNyNumero()){
                ShowAlert.show("Error", "El número de edición ya existe");
                return;
            }
            if(isEditMode){
                if(e.actualizarEdicion()){
                    ShowAlert.show("Éxito", "La edición se ha actualizado correctamente");
                } else {
                    ShowAlert.show("Error", "No se pudo actualizar la edición");
                }
            } else {
                if(e.agregarEdicion()){
                    ShowAlert.show("Éxito", "La edición se ha agregado correctamente");
                } else {
                    ShowAlert.show("Error", "No se pudo agregar la edición");
                }
            }
        } catch (NumberFormatException ex) {
            ShowAlert.show("Error", "El ISBN debe ser un número válido");
        }
    }

    @FXML
    private void agregarEdicion() {
        isEditMode = false;
        tfISBN.setDisable(false); 
        tfTitulo.setDisable(true); 
        spnEdicion.setDisable(false); 
        spnCantidad.setDisable(false); 
        spnPublicacion.setDisable(false); 

        tfISBN.clear();
        spnEdicion.getValueFactory().setValue(1);
        spnCantidad.getValueFactory().setValue(1);
        spnPublicacion.getValueFactory().setValue(2020);
    }

    @FXML
    private void editarCampos() {
        spnCantidad.setDisable(false); 
        spnPublicacion.setDisable(false); 
    }
}
