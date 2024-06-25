package com.biblioteca.controladores;

import com.biblioteca.modelos.Usuario;
import com.biblioteca.utilidades.ShowAlert;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NuevoLectorController {
    
    @FXML
    private TextField tfNombre, tfApellido;
    @FXML
    private TextField tfCorreo;

    @FXML
    private void agregarLector() {
        String nombre = tfNombre.getText().trim();
        String apellido = tfApellido.getText().trim();
        String correo = tfCorreo.getText().trim();
        try {
            validarDatos(nombre, correo, apellido);
            Usuario u = new Usuario();
            u.setNom_user(nombre);
            u.setAp_user(apellido);
            u.setCorreo(correo);
            if(u.comprobarCorreo()) {
                return;
            }
            if(u.guardarUsuario()){
                ShowAlert.show("Éxito", "Lector guardado exitosamente.");
                Stage modal = (Stage) tfNombre.getScene().getWindow();
                modal.close();
            }else{
                ShowAlert.show("Error", "No se pudo guardar el lector.");
            }
            
        } catch (Exception e) {
            ShowAlert.show("Error", e.getMessage());
        }
    }

    private void validarDatos(String nombre, String correo, String apellido) throws Exception {
        if (nombre.isEmpty() || correo.isEmpty() || apellido.isEmpty()) {
            throw new Exception("Por favor ingrese todos los datos.");
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            throw new Exception("El nombre solo puede contener letras y acentos.");
        }
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            throw new Exception("El apellido solo puede contener letras y acentos.");
        }
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z.-]+\\.[a-z]{2,}$")){
            throw new Exception("El correo es invalido");
        }
    }
}
