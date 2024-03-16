/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.cooperativa.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author stwar
 */
public class MainWindowController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ImageView companyImage;
    @FXML
    private Label compayLabel;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private MFXButton professorBtn;
    @FXML
    private MFXButton functionaryBtn;
    @FXML
    private MFXButton AffiliateBtn;
    @FXML
    private StackPane mainPane;
    @FXML
    private HBox head;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public ImageView getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(ImageView companyImage) {
        this.companyImage = companyImage;
    }

    public Label getCompayLabel() {
        return compayLabel;
    }

    public void setCompayLabel(Label compayLabel) {
        this.compayLabel = compayLabel;
    }
    
}
