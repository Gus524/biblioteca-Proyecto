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
        String nombre = tfNombre.getText().trim();
        String apellido = tfApellido.getText().trim();
        String correo = tfCorreo.getText().trim();

        try {
            validarCampos(nombre, apellido, correo);

            Usuario u = new Usuario();
            u.setNom_user(nombre);
            u.setAp_user(apellido);
            u.setCorreo(correo);
            u.setId_user(id_user);

            if (u.actualizarUsuario()) {
                ShowAlert.show("Actualizado correctamente", "Se actualizaron correctamente los datos");
                Stage modal = (Stage) tfNombre.getScene().getWindow();
                modal.close();
            } else {
                ShowAlert.show("Error al actualizar", "Ocurrió un error al actualizar los datos");
            }
        } catch (Exception e) {
            ShowAlert.show("Error de Validación", e.getMessage());
        }
    }

    private void validarCampos(String nombre, String apellido, String correo) throws Exception {
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {
            throw new Exception("Por favor ingrese todos los campos.");
        }

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            throw new Exception("El nombre solo puede contener letras y acentos.");
        }
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            throw new Exception("El nombre solo puede contener letras y acentos.");
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z.-]+\\.[a-z]{2,}$")) {
            throw new Exception("El correo electrónico no es válido.");
        }
    }
}
