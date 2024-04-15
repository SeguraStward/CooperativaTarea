package cr.ac.una.cooperativa.controllers;

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
public class affiliateDepositBoxController extends Controller implements Initializable {

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
    private TextField folioField;
    @FXML
    private Label label;
    @FXML
    private Button depositBtn;
    @FXML
    private Label labelWarning;
    @FXML
    private Button search;
    private Affiliated affiliated;
    @FXML
    private ChoiceBox<Integer> billeteCBox;
    @FXML
    private ChoiceBox<Integer> monedaCBox;
    @FXML
    private TextField numOfBillete;
    @FXML
    private TextField numOfMoneda;
    @FXML
    private Button exitBtn;
    @FXML
    private Label userLabel;
    @Override
    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        billeteCBox.getItems().addAll(1000, 2000, 5000, 10000, 20000, 50000,0);
        monedaCBox.getItems().addAll(5, 10, 25, 50, 100, 500,0);
        billeteCBox.setValue(0);
        monedaCBox.setValue(0);
    }

    @FXML
    private void depositAction(ActionEvent event) {
        String numMonedas = numOfMoneda.getText();
        String numBilletes = numOfBillete.getText();

            if (!numMonedas.isEmpty()) {
              depositMonedas(numMonedas);
            } else if (!numBilletes.isEmpty()) {
              depositBilletes(numBilletes);
            }
            billeteCBox.setValue(0);
            monedaCBox.setValue(0);
            numOfBillete.clear();
            numOfMoneda.clear();
    }

    @FXML
    private void searchAccount(ActionEvent event) {
        billeteCBox.setValue(0);
        monedaCBox.setValue(0);
        numOfBillete.clear();
        numOfMoneda.clear();
        for (Affiliated aux : super.getListAffiliates()) {
            if (aux.getFolio().equals(folioField.getText())) {
                affiliated = aux;
                userLabel.setText(affiliated.getName());
            }
        }
    }

    private void depositMonedas(String quantity) {
        try {
            
            int amount = Integer.parseInt(quantity);
            int buzon = affiliated.getBuzon();
            affiliated.setBuzon(buzon + monedaCBox.getValue() * amount);
            
        } catch (NumberFormatException e) {
            labelWarning.setText("Error al depositar monedas");
            System.out.println("Error monedas: " + e.getMessage());

        }
    }
    
    private void depositBilletes(String quantity){
          try {
            
            int amount = Integer.parseInt(quantity);
            int buzon = affiliated.getBuzon();
            affiliated.setBuzon(buzon + billeteCBox.getValue() * amount);

        } catch (NumberFormatException e) {
              labelWarning.setText("Error al depositar monedas");
              System.out.println("Error billetes : " + e.getMessage());
        }
    }

    @FXML
    private void exitAction(ActionEvent event) {
        FlowController.getInstance().goView("affiliatedWindow");
    }
}
