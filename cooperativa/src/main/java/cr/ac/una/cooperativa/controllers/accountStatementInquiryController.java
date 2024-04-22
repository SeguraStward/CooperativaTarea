package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.cooperativa.util.FlowController;
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
    private Affiliated affiliated;
    @FXML
    private Label userLabel;
    @Override
    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accountChoiceBox.getItems().add("Selecciona");
        accountChoiceBox.setValue("Selecciona");
        super.load();

    }

    @FXML
    private void searchAffiliate(ActionEvent event) {

        userLabel.setText("");
        infoField.clear();
        accountChoiceBox.getItems().clear();
        accountChoiceBox.getItems().add("Selecciona");
        accountChoiceBox.setValue("Selecciona");
        for (Affiliated aux : super.getListAffiliates()) {
            if (aux.getFolio().equals(folioField.getText())) {
                affiliated = aux;
                userLabel.setText(affiliated.getName());
                fillChoiceBox(aux);
                labelWarning.setText("");
                return;
            }
        }
        folioField.clear();
        labelWarning.setText("No se encontro el afiliado");
    }

    private void fillChoiceBox(Affiliated aux) {

        for (Account aux2 : aux.getAccounts()) {
            accountChoiceBox.getItems().add(aux2.getName());
        }

    }
    @FXML //showing the account i was going to create just one method to avoid two similar methods but i don't have enough time to think
    private void showOneAction(ActionEvent event) {
        infoField.clear();
        if (affiliated != null ) {
            if(!affiliated.getAccounts().isEmpty()) {
                for (Account account : affiliated.getAccounts()) {
                    if (accountChoiceBox.getValue().equals(account.getName())) {
                        infoField.setText("Cuenta: " + account.getName() + " Saldo: " + String.valueOf(account.getMoney()) +"\n");
                        return;
                    }
                }
            }else{
                labelWarning.setText("Asociado sin cuentas");
            }
        }else {
            labelWarning.setText("Asociado incorrecto");
        }
    }
    @FXML
    private void showAllAction(ActionEvent event) {
        infoField.clear();
        if (affiliated != null ) {
            if(!affiliated.getAccounts().isEmpty()) {
                System.out.println("mostrar todo");

                for (Account account : affiliated.getAccounts()) {
                    infoField.setText("Cuenta: " + account.getName() + " Saldo: " + String.valueOf(account.getMoney()) + "\n");
                }
            }else{
                labelWarning.setText("Asociado sin cuentas\n para mostrar");
            }
        }else {
            labelWarning.setText("El asociado no existe!");
        }
    }
    @FXML
    private void exitBtnAction(ActionEvent event){
        infoField.clear();
        affiliated = null;
        userLabel.setText("");
        folioField.setText("");
        accountChoiceBox.getItems().clear();
        accountChoiceBox.getItems().add("Selecciona");
        accountChoiceBox.setValue("Selecciona");
        save();
        FlowController.getInstance().goView("affiliatedWindow");
    }

}
