
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.util.AppContext;
import cr.ac.una.cooperativa.util.FlowController;
import java.net.URL;
import java.util.List;
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
public class depositAndwithdrawalController implements Initializable {

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
    private Label labelWarning;
    @FXML
    private TextField folioField;
    @FXML
    private Button enterBtn;
    @FXML
    private Label buzonInfo;
    @FXML
    private ChoiceBox<String> accountCbox;
    @FXML
    private TextField amountField;
    @FXML
    private Button depositBtn;
    @FXML
    private Button retirarBtn;
    @FXML
    private Label userLabel;
    @FXML
    private Button getBackBtn;
    private Affiliated asociado;
    @FXML
    private Label buzonAmount;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarAction(ActionEvent event) {
        Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
        List<Affiliated> asociados = company.getAffiliates();
        for(Affiliated aux: asociados){
            if(aux.getFolio().equals(folioField.getText())){
                asociado = aux;
                userLabel.setText(asociado.getName());
                folioField.clear();
                insertAccountsInCBox();
                buzonAmount.setText(String.valueOf(asociado.getBuzon()));
                return;
            }          
        }
        folioField.setPromptText("No existe el asociado");
       
    }

    @FXML
    private void depostiAction(ActionEvent event) {
       if(asociado != null){
        List<Account> asociateAccounts= asociado.getAccounts();
         folioField.clear();
          userLabel.setText(asociado.getName());

           for(Account aux: asociateAccounts){
              if(aux.getName().equals(accountCbox.getValue())){                 
                depositFromBuzon(aux);
                
              }else if(accountCbox.getValue().equals("Buzon")){
                  int deposit = Integer.parseInt(amountField.getText());
                  int newAmountBuzon = asociado.getBuzon() + deposit;
                  asociado.setBuzon(newAmountBuzon);
                  buzonAmount.setText(String.valueOf(asociado.getBuzon()));
                  amountField.setPromptText("Deposito hecho");

           }
           }
       }else{
           folioField.setPromptText("Ingresa Primero!!!");
       }
    }

    @FXML
    private void withdrawalAction(ActionEvent event) {
       if(asociado != null){
        List<Account> asociateAccounts= asociado.getAccounts();
          folioField.clear();
            userLabel.setText(asociado.getName());
           for(Account aux: asociateAccounts){
              if(aux.getName().equals(accountCbox.getValue())){                 
                  withdrawalFromAccount(aux);
                
              }else if(accountCbox.getValue().equals("Buzon")){
                  try{
                      int withdrawal = Integer.parseInt(amountField.getText());
                      if(asociado.getBuzon() >= withdrawal){
                      int newAmountBuzon = asociado.getBuzon() - withdrawal;
                      asociado.setBuzon(newAmountBuzon);
                      buzonAmount.setText(String.valueOf(asociado.getBuzon()));
                      amountField.setPromptText("Deposito hecho");
                      }else{
                      amountField.setPromptText("Fondos insuficientes");
                      }
                  } catch (NumberFormatException e) {
                      amountField.setPromptText("Numeros!!!!");
                  }
                }   
           }
       }else{
           folioField.setPromptText("Ingresa Primero!!!");
       }
    } 

    @FXML
    private void getBackAction(ActionEvent event) {
        FlowController.getInstance().goView("functionaryMaintenance");
        
    }
    private void insertAccountsInCBox(){
       List<Account> asociateAccounts= asociado.getAccounts();
       
       for(Account aux: asociateAccounts){
           accountCbox.getItems().add(aux.getName());
       }
       
       accountCbox.getItems().add("Buzon");
    }
    
    private void depositFromBuzon(Account account){
        try {
            int deposit = Integer.parseInt(amountField.getText());
            if(asociado.getBuzon()>= deposit){
                int newAmountBuzon = asociado.getBuzon() - deposit;
                int newAmountAccount = account.getMoney() + deposit;
                account.setMoney(newAmountAccount);
                asociado.setBuzon(newAmountBuzon);
                buzonAmount.setText(String.valueOf(asociado.getBuzon()));
                amountField.setPromptText("Deposito hecho");
            }else{
                amountField.setPromptText("no tiene fondos");
            }
            
        } catch (NumberFormatException e) {
            amountField.setPromptText("Numeros!!!!");
        }
    }
    
    private void withdrawalFromAccount(Account account){
         try {
            int withdrawal = Integer.parseInt(amountField.getText());
            if(account.getMoney()>= withdrawal){
               
                int newAmountAccount = account.getMoney() - withdrawal;
                account.setMoney(newAmountAccount);
               
             
                amountField.setPromptText("Retiro hecho");
            }else{
                amountField.setPromptText("no tiene fondos");
            }
            
        } catch (NumberFormatException e) {
            amountField.setPromptText("Numeros!!!!");
        }
    }
 
}
