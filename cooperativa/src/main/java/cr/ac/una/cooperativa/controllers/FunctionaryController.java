package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.util.FlowController;
import cr.ac.una.cooperativa.util.AppContext;
import cr.ac.una.cooperativa.classes.Cooperativa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author stwar
 */
public class FunctionaryController extends Controller implements Initializable{

    @FXML
    private ImageView companyImage;
    @FXML
    private Label companyName;

    @Override
    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
         companyName.setText(company.getName());
         String imageFile = company.getImageFile();
         Image image = new Image(imageFile);
         companyImage.setImage(image);
    }

    @FXML
    private void goMainteinance(ActionEvent event) {
        FlowController.getInstance().goView("functionaryMaintenance");
    }

    @FXML
    private void goCarnetPrinting(ActionEvent event) {
         FlowController.getInstance().goView("carnetPrinting");
    }

    @FXML
    private void goDepositAndWithdrawal(ActionEvent event) {
         FlowController.getInstance().goView("depositAndWithdrawalWindow");
    }

    @FXML
    private void goOpenAccounts(ActionEvent event) {
         FlowController.getInstance().goView("openAccountsWindow");
    }
    
}
