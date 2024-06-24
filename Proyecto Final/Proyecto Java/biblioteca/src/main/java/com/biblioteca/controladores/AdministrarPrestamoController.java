package com.biblioteca.controladores;

import com.biblioteca.modelos.Edicion;
import com.biblioteca.modelos.Prestamo;
import com.biblioteca.modelos.PrestamoUsuario;
import com.biblioteca.utilidades.ShowAlert;

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
    private int id_prestamo;
    private Long ISBN;
    public void cargarPrestamo(PrestamoUsuario prestamo){
        Edicion e = new Edicion();
        Prestamo p = new Prestamo();
        id_prestamo = prestamo.getId_prestamo_user();
        p.setId_prestamo(prestamo.getId_prestamo());
        ISBN = p.obtenerPrestamo().get(0).getISBN();
        e.setISBN(ISBN);
        tfLibro.setText(prestamo.getTitulo() + " " + e.buscarEdicion().get(0).getNo_edicion() + "ed");
        tfLector.setText(prestamo.getEmail());
        tfFechaPrestamo.setText("prestamo: " + prestamo.getFecha_prestamo() + " fecha devolucion: " + prestamo.getFecha_devolucion());
    }

    @FXML
    private void actualizarPrestamo(){
        Prestamo p = new Prestamo();
        p.setId_prestamo_user(id_prestamo);
        p.setISBN(ISBN);
        if(p.actualizarEstadoDevuelto()){
            p.acutalizarCantidadEdicion();
            ShowAlert.show("Estado actualizado", "El libro ha sido devuelto");
            Stage modal = (Stage) tfLibro.getScene().getWindow();
            modal.close();
        }
        else{
            ShowAlert.show("Error al actualizar", "Ocurrió un error al actualizar el estado del libro");
        }
    }
}
