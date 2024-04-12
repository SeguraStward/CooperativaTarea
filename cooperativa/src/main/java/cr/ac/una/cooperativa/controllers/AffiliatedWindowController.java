package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
public class AffiliatedWindowController extends Controller implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox head;
    @FXML
    private ImageView companyImage;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private HBox selectionHbox;
    @FXML
    private MFXButton registrarseBtn;
    @FXML
    private MFXButton consultaBtn;
    @FXML
    private MFXButton buzonBtn;
    @FXML
    private Label companyName;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.load();
    }

    @FXML
    private void goRegister(ActionEvent event) {
        FlowController.getInstance().goView("signInAffiliate");
    }

    @FXML
    private void goStatementInquiry(ActionEvent event) {
        FlowController.getInstance().goView("accountStatementInquiry");
    }

    @FXML
    private void goBuzon(ActionEvent event) {
        FlowController.getInstance().goView("affiliateDepositBox");
    }

    @Override
    public void initialize() {
    }

}
