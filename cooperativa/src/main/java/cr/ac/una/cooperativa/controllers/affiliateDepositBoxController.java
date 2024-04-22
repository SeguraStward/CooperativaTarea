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
    private ChoiceBox<Integer> billetsCBox;
    @FXML
    private ChoiceBox<Integer> coinsCBox;
    @FXML
    private TextField numOfBillets;
    @FXML
    private TextField numOfCoins;
    @FXML
    private Button exitBtn;
    @FXML
    private Label userLabel;
    @FXML
    private Label buzonLabel;
    @Override
    public void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        billetsCBox.getItems().addAll(1000, 2000, 5000, 10000, 20000, 50000,0);
        coinsCBox.getItems().addAll(5, 10, 25, 50, 100, 500,0);
        billetsCBox.setValue(0);
        coinsCBox.setValue(0);
    }

    @FXML
    private void depositAction(ActionEvent event) {
        String numCoins = numOfCoins.getText();
        String numbBillets = numOfBillets.getText();

            if (!numCoins.isEmpty()) {
              depositCoins(numCoins);
              buzonLabel.setText(String.valueOf(affiliated.getBuzon()));

            }
            if (!numbBillets.isEmpty()) {

              depositBillets(numbBillets);
              buzonLabel.setText(String.valueOf(affiliated.getBuzon()));
            }


    }

    @FXML
    private void searchAccount(ActionEvent event) {

        for (Affiliated aux : super.getListAffiliates()) {
            if (aux.getFolio().equals(folioField.getText())) {
                affiliated = aux;
                userLabel.setText(affiliated.getName());
                buzonLabel.setText(String.valueOf(affiliated.getBuzon()));
                labelWarning.setText("Usuario encontrado");
                return;
            }
        }
        labelWarning.setText("Usuario no encontrado");
    }

    private void depositCoins(String quantity) {
        try {
            
            int amount = Integer.parseInt(quantity);
            int buzon = affiliated.getBuzon();
            affiliated.setBuzon(buzon + coinsCBox.getValue() * amount);
            formating();
        } catch (Exception e) {
            labelWarning.setText("Error al depositar monedas");
            System.out.println("Error monedas: " + e.getMessage());

        }
    }
    
    private void depositBillets(String quantity){
          try {

            int amount = Integer.parseInt(quantity);
            int buzon = affiliated.getBuzon();
            affiliated.setBuzon(buzon + billetsCBox.getValue() * amount);
            formating();
        } catch (Exception e) {
              labelWarning.setText("Error al depositar monedas");
              System.out.println("Error billetes : " + e.getMessage());
        }
    }

    @FXML
    private void exitAction(ActionEvent event) {

        userLabel.setText("Usuario");
        buzonLabel.setText("Monto del Buzon");
        folioField.clear();
        folioField.setPromptText("Inserte Folio");
        super.save();
        formating();
        FlowController.getInstance().goView("affiliatedWindow");
    }

    private void formating(){

        numOfBillets.clear();
        numOfCoins.clear();
        billetsCBox.setValue(0);
        coinsCBox.setValue(0);
    }
}
