
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.util.AppContext;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.classes.Json;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 *
 * @author stwar
 */
public class ProfessorController extends Controller implements Initializable{

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox head;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private VBox vboxCompanySettings;
    @FXML
    private MFXTextField companyInputName;
    @FXML
    private Button compBtnImg;
    @FXML
    private ScrollPane accountScroll;
    @FXML
    private VBox vboxAccounts;
    @FXML
    private StackPane mainPane;
    @FXML
    private MFXTextField accountField;
    @FXML
    private MFXButton addAccountBtn;
    @FXML
    private Label companyName;
    @FXML
    private MFXButton readyBtn;
    @FXML
    private ImageView companyImage;
    String imageFile;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
         companyName.setText(company.getName());
         imageFile = company.getImageFile();
         Image image = new Image(imageFile);
         companyImage.setImage(image);
         showSavedAccounts();
    }   

    @Override
    public void initialize() {}
    
    

    @FXML
    private void chooseImage(ActionEvent event) {
        
          FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        File file = fileChooser.showOpenDialog(super.getStage());
        if (file != null) {
             try {
                Image image = new Image(file.toURI().toString());
                imageFile = file.toURI().toString();
                companyImage.setImage(image);
                AppContext.getInstance().set("companyImage", image);
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
            }
    }
      
    }
    @FXML
    private void textFieldAction(ActionEvent event){
       
        companyName.setText(companyInputName.getText());
        
    }
    @FXML
    private void ready(ActionEvent event) {
        Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
        company.setName(companyName.getText());
        company.setImageFile(imageFile);
        Json.guardar(company, "jsonFile.json");
    }
 
     @FXML
    private void addBtnAction(ActionEvent event) {
        Boolean permission = true;
        Account newAccount = new Account();
        Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa"); 
         List<Account> accounts = company.getAccounts();
          for(Account account: accounts){
            if(account.getName().equals(accountField.getText())){
              permission = false;             
            }
        }
        if(permission){
        newAccount.setName(accountField.getText());        
        company.addAccount(newAccount);
        addAccount(accountField.getText());
         accountField.clear();
         accountField.setPromptText("Done");
        }else{
            
        accountField.clear();
        accountField.setPromptText("That account already exists");
        
        }
    }
    
    private void showSavedAccounts(){
        
          Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
          List<Account> accounts = company.getAccounts();
          for(Account account : accounts){
             addAccount(account.getName());
          }
          
    }
    
    private void addAccount(String accountName){
        Label label = new Label(accountName);
        MFXButton deleteBtn = new MFXButton();
        MFXButton editBtn = new MFXButton();
        deleteBtn.setText("Delete");
        editBtn.setText("Edit");
        deleteBtn.getStyleClass().add("deleteBtn");
        editBtn.getStyleClass().add("editBtn");
        HBox accountBox = new HBox(10);
        accountBox.setPrefHeight(15);
        accountBox.setAlignment(Pos.CENTER);
        accountBox.getChildren().addAll(label,editBtn,deleteBtn);
        accountBox.getStyleClass().add("accountsHbox");

        vboxAccounts.getChildren().add(accountBox);
        deleteBtn.setOnAction(e -> {             
            vboxAccounts.getChildren().remove(accountBox);
            deleteAccount(label.getText()); 
        });
        editBtn.setOnAction(e -> editWindow(label));
    }
    
    private void deleteAccount(String name){
         Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
          List<Account> accounts = company.getAccounts();
          //lambda poderosaa encuentra el que matche y lo elimina
          accounts.removeIf(a -> a.getName().equals(name));
    }
    
    private void editWindow(Label label) {
    Stage stage = new Stage();
    stage.setTitle("Editar Cuenta");
    Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
    List<Account> accounts = company.getAccounts();
    VBox vbox = new VBox(10);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(10));

    TextField textField = new TextField();
    textField.setText(label.getText());

    Button confirmButton = new Button("Confirmar");
    confirmButton.setOnAction(e -> {
       
       
         for(Account account: accounts){
            if(account.getName().equals(label.getText())){
               Account editAccount = account;
               editAccount.setName(textField.getText());
               break;
            }
        }
        label.setText(textField.getText());
        stage.close();
    });
   
    Label label2 = new Label("Introduce el nuevo nombre de la cuenta:");
    vbox.getChildren().addAll(label2, textField, confirmButton); 
    Scene scene = new Scene(vbox, 300, 150);
    stage.setScene(scene);
    stage.showAndWait();
}

   
}