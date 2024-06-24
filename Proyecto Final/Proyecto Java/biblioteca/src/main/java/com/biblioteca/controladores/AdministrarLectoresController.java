package com.biblioteca.controladores;

import com.biblioteca.modelos.Usuario;
import com.biblioteca.utilidades.ShowAlert;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class AdministrarLectoresController {
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private TextField tfCorreo;
    private int id_user;
    
    public void cargarLector(Usuario usuario){
        tfNombre.setText(usuario.getNom_user());
        tfApellido.setText(usuario.getAp_user());
        tfCorreo.setText(usuario.getCorreo());
        id_user = usuario.getId_user();
    }

    @FXML
    private void activarEdicion(){
        tfNombre.setEditable(true);
        tfApellido.setEditable(true);
        tfCorreo.setEditable(true);
        tfNombre.disableProperty().set(false);
        tfApellido.disableProperty().set(false);
        tfCorreo.disableProperty().set(false);
    }

    @FXML
    private void actualizarLector(){
        Usuario u = new Usuario();
        u.setNom_user(tfNombre.getText());
        u.setAp_user(tfApellido.getText());
        u.setCorreo(tfCorreo.getText());
        u.setId_user(id_user);
        if(u.actualizarUsuario()){
            ShowAlert.show("Actualizado correctamente", "Se actualizaron correctamente los datos");
            Stage modal = (Stage) tfNombre.getScene().getWindow();
            modal.close();
        }else{
            ShowAlert.show("Error al actualizar", "Ocurrió un error al actualizar los datos");
        }
    }
}
