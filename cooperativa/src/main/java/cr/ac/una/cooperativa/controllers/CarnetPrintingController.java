package cr.ac.una.cooperativa.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class CarnetPrintingController extends Controller implements Initializable {

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
    private TextField folioField;
    @FXML
    private Button printBtn;
    @FXML
    private Label labelWarning;
    @FXML
    private Label confirmationLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }
    
}
