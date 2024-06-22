import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NuevoPrestamoController {

    @FXML
    private TextField tfLibro;

    @FXML
    private TextField tfLector;

    @FXML
    private TextField tfFechaInicio;

    @FXML
    private TextField tfFechaDev;

    @FXML
    private TextField tfCantidad;

    @FXML
    private TextField tfEstado;

    @FXML
    private void guardarPrestamo() {
        // Aquí puedes manejar el guardado del nuevo préstamo
        System.out.println("Guardando préstamo...");
        // Cierra el modal después de guardar
        cerrar();
    }

    @FXML
    private void cerrar() {
        Stage stage = (Stage) tfLibro.getScene().getWindow();
        stage.close();
    }
}
