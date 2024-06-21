import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CatalogoUserController {

    @FXML
    private Button btnBuscar;

    @FXML
    private JFXButton btnCatalogo;

    @FXML
    private JFXButton btnCerrarSesion;

    @FXML
    private JFXButton btnLectores;

    @FXML
    private JFXButton btnPrestamos;

    @FXML
    private TableColumn<?, ?> colAutores;

    @FXML
    private TableColumn<?, ?> colCategoria;

    @FXML
    private TableColumn<?, ?> colDisponible;

    @FXML
    private TableColumn<?, ?> colEdicion;

    @FXML
    private TableColumn<?, ?> colTitulo;

    @FXML
    private ComboBox<?> comboFiltro;

    @FXML
    private TextField tfBuscar;


}
