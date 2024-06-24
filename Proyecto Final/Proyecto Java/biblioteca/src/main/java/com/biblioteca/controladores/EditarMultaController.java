package com.biblioteca.controladores;

import com.biblioteca.modelos.Multa;
import com.biblioteca.modelos.Usuario;
import com.biblioteca.utilidades.ShowAlert;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarMultaController {
    @FXML
    private TextField tfLibro;
    @FXML
    private TextField tfLector;
    @FXML
    private TextField tfCosto;
    private int id_prestamo;
    private Long ISBN;
    
    public void cargarMulta(Multa multa){
        Usuario u = new Usuario();
        id_prestamo = multa.getId_prestamo_user();
        ISBN = multa.getISBN();
        u.setId_user(multa.getId_user());
        tfLibro.setText(multa.getTitulo());
        tfLector.setText(u.obtenerUsuario().get(0).getNom_user() + " " + u.obtenerUsuario().get(0).getAp_user());
        tfCosto.setText(String.valueOf(multa.getCosto_multa()));
    }

    @FXML
    private void actualizarMulta(){
        Multa m = new Multa();
        m.setId_prestamo_user(id_prestamo);
        if(m.actualizarPagar() && m.actualizarPrestamoMulta()){
            m.setISBN(ISBN);
            m.acutalizarCantidadEdicion();
            ShowAlert.show("Multa actualizada", "Se actualizó correctamente la multa y se actualizó el prestamo del libro");
            Stage modal = (Stage) tfLibro.getScene().getWindow();
            modal.close();
        }else{
            ShowAlert.show("Error al actualizar", "Ocurrió un error al actualizar la multa");
        }
    }
}
