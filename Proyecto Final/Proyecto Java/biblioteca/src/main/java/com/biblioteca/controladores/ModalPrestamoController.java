package com.biblioteca.controladores;

import java.util.ArrayList;
import java.util.List;

import com.biblioteca.App;
import com.biblioteca.modelos.Edicion;
import com.biblioteca.modelos.Prestamo;
import com.biblioteca.modelos.Usuario;
import com.biblioteca.utilidades.Fecha;
import com.biblioteca.utilidades.ShowAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ModalPrestamoController {
    @FXML
    private TextField tfLector;
    @FXML
    private TextField tfLibro;
    @FXML
    private ComboBox<Usuario> cmbxLector;
    @FXML
    private ComboBox<Edicion> cmbxLibro;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextArea txtLibros;
    private ObservableList<Edicion> libros;
    private ObservableList<Usuario> lectores;
    private List<Edicion> listaLibros = new ArrayList<Edicion>();
    private static String text;

    @FXML
    private void initialize(){
        cargarListas();
        configurarFiltroLector();
        configurarFiltroLibros();
    }

    private void cargarListas(){
        Usuario u = new Usuario();
        Edicion e = new Edicion();
        lectores = FXCollections.observableArrayList(u.obtenerUsuarios());
        libros = FXCollections.observableArrayList(e.consultarLibros());
    }

    private void configurarFiltroLibros() {
        ObservableList<Edicion> filtro = FXCollections.observableArrayList();
        tfLibro.textProperty().addListener((observable, oldVal, newVal) -> {
            cmbxLibro.getItems().clear();
            cmbxLibro.setVisibleRowCount(10);
            for (Edicion titulo : libros) {
                if (titulo.tituloProperty().getValue().toLowerCase().contains(newVal.toLowerCase())) {
                    filtro.add(titulo);
                }
            }
            cmbxLibro.setItems(filtro);
            cmbxLibro.show();
        });
        
    }
    
    private void configurarFiltroLector(){
        ObservableList<Usuario> filtro = FXCollections.observableArrayList();
        tfLector.textProperty().addListener((observable, oldVal, newVal) -> {
            cmbxLector.getItems().clear();
            cmbxLector.setVisibleRowCount(10);
            for(Usuario correo : lectores){
                if(correo.correoProperty().getValue().toLowerCase().contains(newVal.toLowerCase())){
                    filtro.add(correo);
                }
            }
            cmbxLector.setItems(filtro);
            cmbxLector.show();
        });
    }

    @FXML
    private void agregarLibro(){
        listaLibros.add(cmbxLibro.getSelectionModel().getSelectedItem());
        cambiarLista();
    }

    @FXML
    private void quitarLibro(){
        listaLibros.remove(listaLibros.size() - 1);
        cambiarLista();
    }
    private void cambiarLista(){
        text = "Libros: \n";
        for(Edicion e : listaLibros){
            text += e.toString() + "\n";
        }
        txtLibros.setText(text);
    }

    @FXML
    private void agregarPrestamo(){
        Prestamo p = new Prestamo(); 
        System.out.println(cmbxLector.getSelectionModel().getSelectedItem());
        p.setId_user(cmbxLector.getSelectionModel().getSelectedItem().id_userProperty().getValue());
        System.out.println(cmbxLector.getSelectionModel().getSelectedItem().id_userProperty().getValue());
        try{
            p.setId_prestamo(p.agregarPrestamo());
            System.out.println(p.id_prestamoProperty().getValue());
            p.setFecha_devolucion(Fecha.convertirFormatoSQL(dpFecha.getEditor().getText()));
            for(Edicion e : listaLibros){
                p.setISBN(e.ISBNProperty().getValue());
                System.out.println(p.ISBNProperty().getValue());
                p.agregarConcentrado();
            }
            ShowAlert.show("Prueba", "Si se pudo");
        } catch (Exception e) {
            ShowAlert.show("Error", "Valió verga carnal");
        }
    }

    @FXML
    private void cancelar(){
        
    }

    @FXML
    private void agregarLector(){
        
    }
}
