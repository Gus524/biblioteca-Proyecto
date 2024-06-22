import javafx.fxml.FXML;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrestamosUserController {

    @FXML
    private Button btnBuscar;

    @FXML
    private JFXButton btnCatalogo;

    @FXML
    private JFXButton btnCerrarSesion;

    @FXML
    private JFXButton btnLectores;

    @FXML
    private Button btnNuevo;

    @FXML
    private JFXButton btnPrestamos;

    @FXML
    private TableColumn<?, ?> colCantidad;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colFechaDev;

    @FXML
    private TableColumn<?, ?> colFechaInicio;

    @FXML
    private TableColumn<?, ?> colLector;

    @FXML
    private TableColumn<?, ?> colLibro;

    @FXML
    private TextField tfBuscar;

    @FXML
    private void initialize() {
        btnNuevo.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevoPrestamo.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Nuevo Préstamo");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    

}
