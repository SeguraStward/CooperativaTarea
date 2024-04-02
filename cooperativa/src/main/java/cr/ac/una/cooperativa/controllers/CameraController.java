/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.cooperativa.controllers;

import com.github.sarxos.webcam.Webcam;
import cr.ac.una.cooperativa.util.AppContext;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class CameraController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    private Webcam webcam;
    private volatile boolean stopCamera = false;
    private BufferedImage takenPhoto;
    @FXML
    private ImageView preview;
    @FXML
    private Button takePhotobtn;
    @FXML
    private Button deletePic;
    @FXML
    private Button readyBtn;
    private boolean stopAll;
    private Stage cameraStage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        start();
    }    
    
     public void start() {
        webcam = Webcam.getDefault();
        webcam.open();
        stopCamera = false;
        stopAll = false;
        // Hilo para actualizar el ImageView con la vista previa de la cámara
        new Thread(() -> {
            while (!stopCamera) {
                BufferedImage bImage = webcam.getImage();
                Image image = SwingFXUtils.toFXImage(bImage, null);
                Platform.runLater(() -> preview.setImage(image));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Algo occurrio en el hilo");
                    break;
                }
            }
        }).start();

        
        takePhotobtn.setOnAction(event -> { 
            stopCamera = true;
            
            takenPhoto = webcam.getImage();
            Image image = SwingFXUtils.toFXImage(takenPhoto, null);
            preview.setImage(image);
           
        });
       
       readyBtn.setOnAction(event -> {
           if(takenPhoto != null){
           stopCamera();
           
           }
       });
       
        deletePic.setOnAction(event -> {
            takenPhoto = null;
            stopCamera = false;
       });
        

    }
     
    public void stopCamera() {
        stopCamera = true;     
        webcam.close();
        cameraStage.close();
    }
    public BufferedImage getImage(){
        return takenPhoto;
    }
    
    public void setStage(Stage stage){ 
        cameraStage = stage;            
    }
    @Override
    public void initialize() {
    }
    public void saveImage(String directoryAndName, BufferedImage image){
             try {
                ImageIO.write(image, "PNG", new File(directoryAndName));
                System.out.println("Imagen guardada");
            } catch (IOException e) {
                e.printStackTrace();
            }
   }
}
