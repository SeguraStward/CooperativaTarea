package cr.ac.una.cooperativa.controllers;

import com.github.sarxos.webcam.Webcam;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.util.FlowController;
import io.github.palexdev.materialfx.utils.SwingFXUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;
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

/**
 *
 * @author stward segura
 */
public class signInAffiliateController extends Controller implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;

    @FXML
    private ImageView userPicture;

    @FXML
    private TextField lastNameField;
    @FXML
    private Label userFolio;

    @FXML
    private Button deleteImgBtn;
    private Webcam webcam;
    private boolean run;
    private BufferedImage bufImage;
    @FXML
    private Label guideLabel;

    @FXML
    private void saveAffiliateInfo(ActionEvent event) {
       if(verifyingInputs() && verifyingAuthenticity(nameField, lastNameField)){
            Affiliated affiliated = new Affiliated();
            affiliated.setName(nameField.getText());
            super.saveImage(bufImage, affiliated.getName());
            String imgPath = super.path + File.separator + affiliated.getName();
            affiliated.setPicture(imgPath);
            affiliated.setAge(Integer.parseInt(ageField.getText()));
            affiliated.setLastName(lastNameField.getText());
            super.getCoope().addAffiliated(affiliated);
            super.getCoope().assignFolio(affiliated);
            userFolio.setText(affiliated.getFolio());
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
                Platform.runLater(() -> userPicture.setImage(image));// we update the userPicture in the main thread

                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.out.println("Something went wrong" + e.getMessage());
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
        } catch (Exception e) {
            System.out.println("the Camera started" + e.getMessage());
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
        nameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        userPicture.setImage(null);

        FlowController.getInstance().goView("affiliatedWindow");
    }

    private boolean verifyingInputs(){
        boolean condition = true;
        try {
            if (nameField.getText().length() <= 3) {
                condition = false;
                guideLabel.setText("Nombre no valido");
            } else if (lastNameField.getText().length() <= 3) {
                condition = false;
                guideLabel.setText("Apellido no valido");
            } else if (!isInteger(ageField.getText())) {
                condition = false;
                guideLabel.setText("No es un numero");
            } else if (Integer.parseInt(ageField.getText()) > 18) {
                condition = false;
                guideLabel.setText("Muy viejo vaya trabaje");
            } else if (bufImage == null) {
                condition = false;
                guideLabel.setText("Captura tu foto!!!");
            }
        }catch(Exception e){
            condition = false;
            System.err.println("Error " + e.getMessage());
        }
        return condition;
    }



}
