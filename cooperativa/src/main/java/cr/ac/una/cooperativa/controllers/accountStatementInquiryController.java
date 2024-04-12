package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 *
 * @author stward segura
 */
public class accountStatementInquiryController extends Controller implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox head;
    @FXML
    private ImageView companyImage;
    @FXML
    private Label companyName;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private Label label;
    @FXML
    private ChoiceBox<String> accountChoiceBox;
    @FXML
    private TextField folioField;
    @FXML
    private Button showInfoBtn;
    @FXML
    private Label labelWarning;
    @FXML
    private TextArea infoField;
    @FXML
    private Button searchBtn;
    private Affiliated asociado;

    @Override
    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.load();
    }

    @FXML
    private void searchAffiliate(ActionEvent event) {
        infoField.clear();
        for (Affiliated aux : super.getListAffiliates()) {
            if (aux.getFolio().equals(folioField.getText())) {
                asociado = aux;
                fillChoiceBox(aux);
            }
        }
    }

    private void fillChoiceBox(Affiliated aux) {
        accountChoiceBox.getItems().clear();
        for (Account aux2 : aux.getAccounts()) {
            accountChoiceBox.getItems().add(aux2.getName());
        }

    }

    @FXML
    private void showInfoAction(ActionEvent event) {
        if (asociado != null) {
            for (Account account : asociado.getAccounts()) {
                if (accountChoiceBox.getValue().equals(account.getName())) {
                    infoField.setText("Cuenta: " + account.getName() + "\n Saldo: " + String.valueOf(account.getMoney()));
                }
            }
        }
    }

}
