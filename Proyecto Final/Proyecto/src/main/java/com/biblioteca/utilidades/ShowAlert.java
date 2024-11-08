package com.biblioteca.utilidades;

import javafx.scene.control.Alert;

public class ShowAlert {
    public static void show(String title, String message){
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
