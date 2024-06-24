package com.biblioteca.controladores;

import java.io.IOException;

import com.biblioteca.modelos.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LectoresAdminController {
        @FXML
    private TableView<Usuario> tbLectores;
    @FXML
    private TableColumn<Usuario, Integer> colCliente;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colApellido;
    @FXML
    private TableColumn<Usuario, String> colCorreo;
    @FXML
    private TextField tfBuscar;
    private ObservableList<Usuario> usuarios;
    
    @FXML
    private void initialize(){
        configurarColumnas();
        cargarTabla();
        configurarBusqueda();
        configurarFilas();
    }

    private void configurarFilas(){
        tbLectores.setRowFactory(t -> {
            TableRow<Usuario> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                if(!fila.isEmpty()){
                    Usuario u = fila.getItem();
                    try {
                        mostrarVerLector(u);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return fila;
        });
    }

    @FXML
    private void mostrarVerLector(Usuario usuario) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biblioteca/vistas/modals/verLector.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        AdministrarLectoresController controlador = loader.getController();
        controlador.cargarLector(usuario);
        stage.setTitle("Ver Lector");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        stage.setScene(new Scene(root));
        stage.setOnHiding(event -> actualizarTabla());
        stage.showAndWait();
    }

    private void configurarColumnas(){
        colCliente.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id_user"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nom_user"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("ap_user"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Usuario, String>("correo"));
    }

    private void cargarTabla(){
        Usuario u = new Usuario();
        usuarios = FXCollections.observableArrayList(u.obtenerUsuarios());
        tbLectores.setItems(usuarios);
    }

    private void configurarBusqueda(){
        FilteredList<Usuario> filtro = new FilteredList<>(usuarios, b -> true);
        tfBuscar.textProperty().addListener((obs, oldVal, newVal) -> {
            filtro.setPredicate(usuarios -> {
                if(newVal == null || newVal.isEmpty()){
                    return true;
                }
                return usuarios.correoProperty().getValue().toLowerCase().contains(newVal.toLowerCase());
            });
        });
        SortedList<Usuario> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tbLectores.comparatorProperty());
        tbLectores.setItems(datos);
    }

    private void actualizarTabla(){
        usuarios.clear();
        Usuario u = new Usuario();
        usuarios.addAll(u.obtenerUsuarios());
        tbLectores.refresh();
    }

    @FXML
    private void mostrarModal() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biblioteca/vistas/modals/nuevoLector.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();        
        stage.setTitle("Nuevo Lector");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(null);
        stage.setScene(new Scene(root));
        stage.setOnHiding(event -> actualizarTabla());
        stage.showAndWait();
    }
}
