package cr.ac.una.cooperativa;


import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.classes.Json;
import cr.ac.una.cooperativa.util.AppContext;
import cr.ac.una.cooperativa.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {


    
    @Override
    public void start(Stage stage) throws IOException {
       
       Cooperativa company = Json.cargar("jsonFile.json");     
       AppContext.getInstance().set("Cooperativa",company);
       FlowController.getInstance().InitializeFlow(stage, null);
       String vista = (String)AppContext.getInstance().get("Vista");
       if(vista !=null){
          FlowController.getInstance().goView(vista);
       }else{
          FlowController.getInstance().goView("mainWindow");
       }
    }
    
    public static void main(String[] args) {
        launch();
    }

}