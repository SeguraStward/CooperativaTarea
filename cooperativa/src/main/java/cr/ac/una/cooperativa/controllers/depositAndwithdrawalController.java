
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class depositAndwithdrawalController extends Controller implements Initializable {

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
    private Affiliated affiliated;
    @FXML
    private Label buzonAmount;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        accountCbox.getItems().add("Nada seleccionado");
    }    

    @FXML
    private void buscarAction(ActionEvent event) {
        Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
        List<Affiliated> asociados = company.getAffiliates();
        for(Affiliated aux: asociados){
            if(aux.getFolio().equals(folioField.getText())){
                affiliated = aux;
                userLabel.setText(affiliated.getName());
                folioField.clear();
                insertAccountsInCBox();
                buzonAmount.setText(String.valueOf(affiliated.getBuzon()));
                return;
            }          
        }
        folioField.setPromptText("No existe el asociado");
       
    }

    @FXML
    private void depostiAction(ActionEvent event) {
       if(affiliated != null){
        List<Account> asociateAccounts= affiliated.getAccounts();
         folioField.clear();
          userLabel.setText(affiliated.getName());

           for(Account aux: asociateAccounts){
              if(aux.getName().equals(accountCbox.getValue())){                 
                depositFromBuzon(aux);
                
              }else if(accountCbox.getValue().equals("Buzon")){
                  int deposit = Integer.parseInt(amountField.getText());
                  int newAmountBuzon = affiliated.getBuzon() + deposit;
                  affiliated.setBuzon(newAmountBuzon);
                  buzonAmount.setText(String.valueOf(affiliated.getBuzon()));
                 labelWarning.setText("Deposito hecho en el buzon");

           }
           }
       }else{
           labelWarning.setText("No existe el asociado");
       }
    }

    @FXML
    private void withdrawalAction(ActionEvent event) {
        //checking this to work good within the if
       if(affiliated != null && affiliated.getAccounts() != null){
        List<Account> asociateAccounts= affiliated.getAccounts();
          folioField.clear();
            userLabel.setText(affiliated.getName());
           for(Account aux: asociateAccounts){//looking for the right account
              if(aux.getName().equals(accountCbox.getValue())){                 
                  withdrawalFromAccount(aux);//doing the withdrawal
                
              }else if(accountCbox.getValue().equals("Buzon")){// if we want to withdrawal money from the buzon
                  try{//getting the amount of the withdrawal
                      int withdrawal = Integer.parseInt(amountField.getText());
                      if(affiliated.getBuzon() >= withdrawal){// the withdrawal has to be less than or equal the buzon
                      int newAmountBuzon = affiliated.getBuzon() - withdrawal;//updating the new
                      affiliated.setBuzon(newAmountBuzon);// amount of the buzon
                      buzonAmount.setText(String.valueOf(affiliated.getBuzon()));//updating the window buzon
                      labelWarning.setText("Deposito hecho");//confirmation label future creation
                      }else{
                      labelWarning.setText("Fondos insuficientes");
                      }
                  } catch (Exception e) {
                     labelWarning.setText("Ingresa numeros!!");
                  }
                }   
           }
       }else{
           labelWarning.setText("No existe el asociado");
       }
    } 

    @FXML
    private void getBackAction(ActionEvent event) {
        labelWarning.setText("");
        accountCbox.getItems().clear();
        FlowController.getInstance().goView("functionaryMaintenance");
        
    }
    private void insertAccountsInCBox(){
        if(affiliated.getAccounts() != null) {
            List<Account> affAccounts = affiliated.getAccounts();
            for (Account aux : affAccounts) {
                accountCbox.getItems().add(aux.getName());
            }
            labelWarning.setText("");
            accountCbox.getItems().add("Buzon");
        }else{
            labelWarning.setText("No existe el asociado");
        }
    }
    //we deposit from the buzon to the account
    private void depositFromBuzon(Account account){
        try {//getting the amount of the deposit
            int deposit = Integer.parseInt(amountField.getText());
            if(affiliated.getBuzon()>= deposit){//the buzon has to be greater than or equal the deposit
                //we update the amount of money in the buzon
                int newAmountBuzon = affiliated.getBuzon() - deposit;
                int newAmountAccount = account.getMoney() + deposit;
                account.setMoney(newAmountAccount);// we add the money to the account
                affiliated.setBuzon(newAmountBuzon);//updating affiliated's buzon
                buzonAmount.setText(String.valueOf(affiliated.getBuzon()));//updating window's buzon
                amountField.setPromptText("Deposito hecho");//add confirmation label future advice
                labelWarning.setText("");

            }else{
                labelWarning.setText("Fondos insuficientes");
            }
            
        } catch (NumberFormatException e) {
           labelWarning.setText("Ingresa numeros!!");
        }
    }
    
    private void withdrawalFromAccount(Account account){
         try {//we get the amount
            int withdrawal = Integer.parseInt(amountField.getText());
            //the withdrawal has to be equal or less than the account's money
            if(account.getMoney()>= withdrawal){
               //account's new amount of money
                int newAmountAccount = account.getMoney() - withdrawal;
                account.setMoney(newAmountAccount);//updating account's money
                amountField.setPromptText("Retiro hecho");//create confirmation label
            }else{
                labelWarning.setText("Fondos insuficientes");
            }
            
        } catch (Exception e) {
            labelWarning.setText("Ingresa numeros!!");
        }
    }

    @Override
    public void initialize() {

    }
}
