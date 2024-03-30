/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.util.Camera;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class FunctionaryMaintenanceController extends Controller implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox head;
    @FXML
    private ImageView companyImage;
    @FXML
    private Label companyLabel;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private Button takePhotoBtn;
    @FXML
    private Button addBtn;
    @FXML
    private ImageView userPicture;
    @FXML
    private ScrollPane scrollAffiliates;
    @FXML
    private VBox vboxAffiliates;
    @FXML
    private Label labelWarning;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }

    @FXML
    private void takePicture(ActionEvent event) {
        Camera camara = new Camera();
        camara.captureImage("C:/Users/stwar/Documents/Proyectos java/cooperativa");
    }
    
}
