import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML
            Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
            
            // Crear la escena con el root
            Scene scene = new Scene(root);
            
            // Establecer la escena en el escenario principal (primaryStage)
            primaryStage.setScene(scene);
            
            // Establecer el título del escenario
            primaryStage.setTitle("BiblioSys");
            
            // Mostrar el escenario
            primaryStage.show();

            // Centramos la ventana
            primaryStage.setOnShowing(event -> {
                primaryStage.centerOnScreen();
            });

        primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
