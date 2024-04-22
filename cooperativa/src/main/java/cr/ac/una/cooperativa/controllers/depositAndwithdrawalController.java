
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
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
    @FXML
    private Label goodLabel;
    @FXML
    private Label accountLabel;
    @FXML
    private Label accountMoney;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      super.load();
    }    

    @FXML
    private void searchAffiliated(ActionEvent event) {
        accountCbox.getItems().clear();
        accountCbox.getItems().add("Nada seleccionado");
        accountCbox.setValue("Nada Seleccionado");
        try {
            List<Affiliated> affiliates = getCoope().getAffiliates();
            for (Affiliated aux : affiliates) {
                if (aux.getFolio().equals(folioField.getText())) {
                    affiliated = aux;
                    userLabel.setText(affiliated.getName());
                    folioField.clear();
                    insertAccountsInCBox();
                    buzonAmount.setText(String.valueOf(affiliated.getBuzon()));
                    return;
                }
            }
        }catch (Exception e){
            System.out.println("Error buscando asociado en depositandwithdrawal");

        }
       labelWarning.setText("Asociado no encontrado");
       userLabel.setText("Usuario");
       accountCbox.setValue("Nada seleccionado");
       buzonAmount.setText("");
    }

    @FXML
    private void depositAction(ActionEvent event) {
       if(affiliated != null){
        List<Account> accounts= affiliated.getAccounts();
         folioField.clear();
          userLabel.setText(affiliated.getName());
           if(accountCbox.getValue().equals("Buzon")){
               depositToBuzon();
               return;
           }
           for(Account aux: accounts){
              if(aux.getName().equals(accountCbox.getValue())){                 
                depositFromBuzon(aux);
                return;
              }else{
                  labelWarning.setText("Elige una cuenta!!");
              }
           }
       }else if(folioField.getText().isEmpty()){
           labelWarning.setText("Ingresa el folio!!");

       }else{
           labelWarning.setText("Asociado no encontrado");
       }
    }

    @FXML
    private void withdrawalAction(ActionEvent event) {
        //checking this to work good within the condition
       if(affiliated != null){// just in case
        List<Account> accounts= affiliated.getAccounts();
          folioField.clear();
            userLabel.setText(affiliated.getName());

           if (accountCbox.getValue().equals("Buzon")) {// if we want to withdrawal money from the buzon
               withdrawalFromBuzon();
               return;
           }
           for(Account aux: accounts) {//looking for the right account
               if (aux.getName().equals(accountCbox.getValue())) {
                   withdrawalFromAccount(aux);//doing the withdrawal
                  return;
               }else{
                   labelWarning.setText("Elige una cuenta!!");
               }
           }
       }else if(folioField.getText().isEmpty()){
             labelWarning.setText("Ingresa el folio!");
        }else{
           labelWarning.setText("Asociado no encontrado");
       }
    } 

    @FXML
    private void getBackAction(ActionEvent event) {
        initialize();
        super.save();
        FlowController.getInstance().goView("functionaryWindow");
        
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
                accountLabel.setText(account.getName());
                accountMoney.setText(String.valueOf(account.getMoney()));
                buzonAmount.setText(String.valueOf(affiliated.getBuzon()));//updating window's buzon
                goodLabel.setText("Deposito hecho en la cuenta");//add confirmation label future advice
                labelWarning.setText("");

            }else{
                labelWarning.setText("Fondos insuficientes");
                goodLabel.setText("");
            }
            
        } catch (NumberFormatException e) {
           labelWarning.setText("Ingresa numeros!!");
           goodLabel.setText("");
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
                accountLabel.setText(account.getName());
                accountMoney.setText(String.valueOf(account.getMoney()));
                goodLabel.setText("Retiro hecho");
                labelWarning.setText("");
            }else{
                labelWarning.setText("Fondos insuficientes");
                goodLabel.setText("");
            }
            
        } catch (Exception e) {
            labelWarning.setText("Ingresa numeros!!");
            goodLabel.setText("");
        }
    }
    private void depositToBuzon(){
        try {
            int deposit = Integer.parseInt(amountField.getText());
            int newAmountBuzon = affiliated.getBuzon() + deposit;
            affiliated.setBuzon(newAmountBuzon);
            buzonAmount.setText(String.valueOf(affiliated.getBuzon()));
            goodLabel.setText("Deposito hecho en el buzon");
            labelWarning.setText("");
            accountLabel.setText("Cuenta");
            accountMoney.setText("");
        }catch (Exception e){
            System.out.println("Error depositandwithdrawal");
            labelWarning.setText("Ingrese numeross!");
            goodLabel.setText("");
        }
    }

    @Override
    public void initialize() {
        affiliated = null;
        accountMoney.setText("");
        accountLabel.setText("Cuenta");
        labelWarning.setText("");
        goodLabel.setText("");
        userLabel.setText("Usuario");
        buzonAmount.setText("");
        folioField.clear();
        amountField.clear();
        accountCbox.getItems().clear();
        accountCbox.getItems().add("Nada seleccionado");
        accountCbox.getSelectionModel().selectFirst();
    }

    private void withdrawalFromBuzon(){
        try {//getting the amount of the withdrawal
            int withdrawal = Integer.parseInt(amountField.getText());
            System.out.println("here");
            if (affiliated.getBuzon() >= withdrawal) {// the withdrawal has to be less than or equal the buzon
                int newAmountBuzon = affiliated.getBuzon() - withdrawal;//updating the new
                affiliated.setBuzon(newAmountBuzon);// amount of the buzon
                buzonAmount.setText(String.valueOf(affiliated.getBuzon()));//updating the window buzon
                goodLabel.setText("Retiro hecho");//confirmation label future creation
                accountLabel.setText("Cuenta");
                accountMoney.setText("");
                labelWarning.setText("");

            } else {
                labelWarning.setText("Fondos insuficientes");
                goodLabel.setText("");
            }
        } catch (Exception e) {
            labelWarning.setText("Ingresa numeros!!");
            goodLabel.setText("");
        }
    }
}
