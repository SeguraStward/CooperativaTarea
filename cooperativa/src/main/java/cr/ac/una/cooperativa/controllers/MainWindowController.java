/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.util.FlowController;
import cr.ac.una.cooperativa.util.AppContext;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
public class MainWindowController extends Controller implements Initializable {
    

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ImageView companyImage;
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
    @FXML
    private HBox selectionHbox;
   
    @FXML
    private Label companyName;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       companyImage.setImage((Image)AppContext.getInstance().get("companyImage"));
       companyName.setText((String) AppContext.getInstance().get("companyName"));
    }    
    
    @Override
    public void initialize() {
        
    }
    
    public ImageView getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(ImageView companyImage) {
        this.companyImage = companyImage;
    }

    public Label getCompayLabel() {
        return companyName;
    }

    public void setCompayLabel(Label compayLabel) {
        this.companyName = compayLabel;
    }

   
     @FXML
    private void onActionBtnFunctionary(ActionEvent event){
         FlowController.getInstance().goView("functionaryWindow");
    }
}
