/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.AppContext;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author stwar
 */
public class FunctionaryController extends Controller implements Initializable{

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
    private VBox selectionVbox;
    @FXML
    private MFXButton mantenimientoBtn;
    @FXML
    private MFXButton carnetBtn;
    @FXML
    private MFXButton aperturaBtn;
    @FXML
    private MFXButton moneyBtn;

    @Override
    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
               companyImage.setImage((Image)AppContext.getInstance().get("companyImage"));
    }
    
}
