package com.biblioteca.controladores;

import com.biblioteca.modelos.Edicion;
import com.biblioteca.modelos.Prestamo;
import com.biblioteca.modelos.PrestamoUsuario;
import com.biblioteca.utilidades.ShowAlert;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdministrarPrestamoController {
    @FXML
    private TextField tfLibro;
    @FXML
    private TextField tfLector;
    @FXML
    private TextField tfFechaPrestamo;
    @FXML
    private JFXButton btnActualizar;
    private int id_prestamo;
    private int id_estado;
    private Long ISBN;
    public void cargarPrestamo(PrestamoUsuario prestamo){
        Edicion e = new Edicion();
        Prestamo p = new Prestamo();
        id_estado = (prestamo.getEstado() == "Prestado") ? 1 : 2;
        id_prestamo = prestamo.getId_prestamo_user();
        p.setId_prestamo_user(prestamo.getId_prestamo_user());
        p.setId_prestamo(prestamo.getId_prestamo());
        ISBN = p.obtenerPrestamo().get(0).getISBN();
        e.setISBN(ISBN);
        tfLibro.setText(prestamo.getTitulo() + " " + e.buscarEdicion().get(0).getNo_edicion() + "ed");
        tfLector.setText(prestamo.getEmail());
        tfFechaPrestamo.setText("prestamo: " + prestamo.getFecha_prestamo() + " fecha devolucion: " + prestamo.getFecha_devolucion());
        if(p.comprobarPrestamoDevuelto(id_estado)){
            btnActualizar.disableProperty().set(true);
        }
    }

    @FXML
    private void actualizarPrestamo(){
        Prestamo p = new Prestamo();
        p.setId_prestamo_user(id_prestamo);
        p.setISBN(ISBN);
        try {
            if (p.esDevuelto()) {
                ShowAlert.show("Error", "El libro ya ha sido devuelto. No se puede devolver otra vez.");
            } else if (p.esPrestado()) {
                if (p.actualizarEstadoDevuelto()) {
                    p.acutalizarCantidadEdicion();
                    ShowAlert.show("Éxito", "El libro ha sido devuelto exitosamente.");
                    Stage modal = (Stage) tfLibro.getScene().getWindow();
                    modal.close();
                } else {
                    ShowAlert.show("Error al actualizar", "Ocurrió un error al actualizar el estado del libro.");
                }
            } else {
                ShowAlert.show("Error", "El libro no está actualmente prestado.");
            }
        } catch (Exception e) {
            ShowAlert.show("Error", "Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
