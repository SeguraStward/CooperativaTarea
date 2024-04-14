package cr.ac.una.cooperativa.controllers;

import com.github.sarxos.webcam.Webcam;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.util.FlowController;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import io.github.palexdev.mfxeffects.utils.NumberUtils;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private Label userFolio;
    @FXML
    private Button captureImgBtn;
    @FXML
    private Button deleteImgBtn;
    private Webcam webcam;
    private boolean run;
    private BufferedImage bufImage;
    @FXML
    private Button exitBtn;
    @FXML
    private Label guideLabel;

    @FXML// me falta verificar que no ingrese un nombre ya registrado
    private void saveAffiliateInfo(ActionEvent event) {
        if (nameField.getText().length() <= 3) {
            guideLabel.setText("Nombre no valido");
        } else if (lastNameField.getText().length() <= 3) {
            guideLabel.setText("Edad no valida");

        } else if (bufImage == null) {
            guideLabel.setText("Captura tu foto!!!");

        }else if(!isInteger(ageField.getText())){
            guideLabel.setText("No es un numero");
        }else if(Integer.parseInt(ageField.getText()) >= 18){
            guideLabel.setText("Muy viejo vaya trabaje");
        }else{
            Affiliated asociado = new Affiliated();
            asociado.setName(nameField.getText());
            super.saveImage(bufImage, asociado.getName());
            String imgPath = super.path + File.separator + asociado.getName();
            asociado.setPicture(imgPath);
            asociado.setAge(Integer.parseInt(ageField.getText()));
            asociado.setLastName(lastNameField.getText());
            super.getCoope().addAffiliated(asociado);
            super.getCoope().assignFolio(asociado);
            userFolio.setText(asociado.getFolio());
            guideLabel.setText("Registrado");
            super.save();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.createDirectory();
        super.load();

    }

    @Override
    public void initialize() { //to make sure that the camera won't be open when the window gets closed
        if (super.stage != null) {
            super.stage.setOnCloseRequest(event -> {
                System.out.println("closing camera");
                stopCamera();
            });
        }
    }

    public void start() {
        initialize();
        webcam = Webcam.getDefault();
        webcam.open();
        run = true;

        //Why do i use this thread? because we need this to get executed at the same time of the main thread
        // so the UI will keep its fluidity, I did not know about threads
        new Thread(() -> {
            while (run) {
                BufferedImage bImage = webcam.getImage();
                Image image = SwingFXUtils.toFXImage(bImage, null);
                Platform.runLater(() -> userPicture.setImage(image));// we uptade the userPicture in the main thread

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Something went wrong");
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
        try { // i use this try to use the button with two purposes to start the camera and capture a photo
            bufImage = webcam.getImage();
            Image image = SwingFXUtils.toFXImage(bufImage, null);//converting buffered img to Image
            userPicture.setImage(image);
            stopCamera();
        } catch (RuntimeException e) {
            System.out.println("the Camera started");
            start();
        }

    }
    //this button action does not delete anything; it just activates the camera again if the camera was paused by captureImgAction
    //i should change both the button and method names
    @FXML
    private void deleteImgAction(ActionEvent event) {
        bufImage = null;
        start();
    }

    @FXML
    private void exitBtnAction(ActionEvent event) {
    FlowController.getInstance().goView("affiliatedWindow");
    }
}
