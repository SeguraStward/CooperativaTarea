/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.cooperativa.util;


import cr.ac.una.cooperativa.App;
import cr.ac.una.cooperativa.controllers.Controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;

public class FlowController {

    private static FlowController INSTANCE = null;
    private static Stage mainStage;
   
    private static ResourceBundle idioma;
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();

    private FlowController() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlowController();
                }
            }
        }
    }

    public static FlowController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void InitializeFlow(Stage stage, ResourceBundle idioma) {
        getInstance();
        this.mainStage = stage;
        this.idioma = idioma;
    }

    private FXMLLoader getLoader(String name) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            synchronized (FlowController.class) {
                if (loader == null) {
                    try {
                        loader = new FXMLLoader(App.class.getResource("view/" + name + ".fxml"), this.idioma);
                        loader.load();
                        loaders.put(name, loader);
                    } catch (Exception ex) {
                        loader = null;
                        java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Creando loader [" + name + "].", ex);
                    }
                }
            }
        }
        return loader;
    }



    public void goView(String viewName) {
        goView(viewName, "Center", null);
    }

    public void goView(String viewName, String accion) {
        goView(viewName, "Center", accion);
    }

    public void goView(String viewName, String location, String accion) {
    FXMLLoader loader = getLoader(viewName);
    Controller controller = loader.getController();
    if (controller == null) {
        System.out.println("El controlador es nulo");
        return;
    }
    controller.setAccion(accion);
    controller.initialize();
    Stage stage = controller.getStage();
    if (stage == null) {
        stage = this.mainStage;
        controller.setStage(stage);
        System.out.println("stage is null");
    }

    Parent root = loader.getRoot(); // Obtener el nuevo nodo raíz
    if (root == null) {
        System.out.println("El nodo raíz es nulo");
        return;
    }

    System.out.println("Nodo raíz cargado correctamente: " + root);

    // get the current scene or we creat one
    Scene scene = stage.getScene();
   
    if (scene == null) {
        scene = new Scene(root);
        stage.setScene(scene);
          scene.getStylesheets().add(getClass().
                getResource("/styles/mainStyles.css").toExternalForm());
        stage.show(); // Mostrar la ventana si no se ha mostrado antes
        System.out.println("Nueva escena creada y ventana mostrada");
    } else {
        scene.setRoot(root);
        System.out.println("Nodo raíz actualizado en la escena existente");
        scene.getStylesheets().add(getClass().
                getResource("/styles/mainStyles.css").toExternalForm());
  }
}


    public Controller getController(String viewName) {
        return getLoader(viewName).getController();
    }
    
    public void limpiarLoader(String view){
        this.loaders.remove(view);
    }

    public static void setIdioma(ResourceBundle idioma) {
        FlowController.idioma = idioma;
    }
    
    public void initialize() {
        this.loaders.clear();
    }

    public void salir() {
        this.mainStage.close();
    }

}
