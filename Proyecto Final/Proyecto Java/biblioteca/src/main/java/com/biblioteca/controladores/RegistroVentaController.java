package com.biblioteca.controladores;

import java.net.URL;
import java.util.ResourceBundle;

import com.biblioteca.modelos.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegistroVentaController implements Initializable{
    @FXML
    private TableView<Usuario> tbClientes;
    @FXML
    private TableColumn<Usuario, String> idColumn;
    @FXML
    private TableColumn<Usuario, String> emaiColumn;
    @FXML
    private TableColumn<Usuario, String> nomColumn;
    @FXML
    private TableColumn<Usuario, String> telColumn;
    @FXML
    private TextArea txtVenta;
    @FXML
    private TextField txtCorreo;
    private ObservableList<Usuario> usuarios;
    
    @Override
    public void initialize(URL location, ResourceBundle resource){
        cargarTabla();
        configurarColumnas();
    }

    private void cargarTabla(){
        Usuario u = new Usuario();
        usuarios = FXCollections.observableArrayList(u.obtenerUsuarios());
        tbClientes.getItems().addAll(usuarios);
    }

    private void configurarColumnas(){
        idColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("id_user"));
        emaiColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nom_user"));
        telColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("tel_user"));
    }
}
