package cr.ac.una.cooperativa;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("affiliatedWindow"), 640, 480);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().
                getResource("/styles/mainWindow.css").toExternalForm());
                
        stage.show();
     

    }
   public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
   
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void setCss(String css){
        
    }
    
    public static void main(String[] args) {
        launch();
    }


}