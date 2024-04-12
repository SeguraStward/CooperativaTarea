package cr.ac.una.cooperativa.controllers;

import com.github.sarxos.webcam.Webcam;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 *
 * @author stward segura
 */
public class signInAffiliateController extends Controller implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private HBox head;
    @FXML
    private ImageView companyImage;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private Button saveBtn;
    @FXML
    private ImageView userPicture;
    @FXML
    private Label companyName;
    @FXML
    private TextField lastNameField;
    @FXML
    private Button showFolio;
    @FXML
    private Label userFolio;
    @FXML
    private Button captureImgBtn;
    @FXML
    private Button deleteImgBtn;
    private Webcam webcam;
    private boolean run;
    private BufferedImage bufImage;

    @FXML
    private void saveAffiliateInfo(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
    }

    public void start() {
        webcam = Webcam.getDefault();
        webcam.open();
        run = true;

        // Hilo para actualizar el ImageView con la vista previa de la cámara
        new Thread(() -> {
            while (run) {
                BufferedImage bImage = webcam.getImage();
                Image image = SwingFXUtils.toFXImage(bImage, null);
                Platform.runLater(() -> userPicture.setImage(image));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Algo occurrio en el hilo");
                    break;
                }
            }
        }).start();

    }

    public void stopCamera() {
        run = false;
        webcam.close();

    }

    @FXML
    private void captureImgAction(ActionEvent event) {
      
      
        bufImage = webcam.getImage();
        Image image = SwingFXUtils.toFXImage(bufImage, null);
        userPicture.setImage(image);
        stopCamera();
    }

    @FXML
    private void deleteImgAction(ActionEvent event) {
        start();
        
    }

    @FXML
    private void showFolioAction(ActionEvent event) {
    }
}
