package cr.ac.una.cooperativa;


import cr.ac.una.cooperativa.util.AppContext;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.classes.Json;
import cr.ac.una.cooperativa.util.FlowController;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws IOException {
       
       Cooperativa company = Json.cargar("jsonFile.json");     
       AppContext.getInstance().set("Cooperativa",company);
       FlowController.getInstance().InitializeFlow(stage, null);
       FlowController.getInstance().goView("functionaryMaintenance");
     
    }
    
    public static void main(String[] args) {
        launch();
    }

}