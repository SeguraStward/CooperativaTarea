/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class OpenAccountsController extends Controller implements Initializable {

  
    @FXML
    private MFXTextField folioField; 
    @FXML
    private VBox affiliateAccounts;
    @FXML
    private VBox allAccounts;
    private boolean dropDone = false;
    private Affiliated affiliated;
    private List<Account> accounts;
    @FXML
    private Label companyName;
    @FXML
    private ImageView companyImage;
    @FXML
    private Label warningLabel;
    @FXML
    private Label userLabel;
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

    @Override
    public void initialize() {
    }

    @FXML
    private void goBack(ActionEvent event) {

        FlowController.getInstance().goView("functionaryWindow");
    }

    private void addAccountsBox1(String name) {
        Label label = new Label(name);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setPrefHeight(15);
        label.setAlignment(Pos.CENTER);
        allAccounts.getChildren().add(label);

        label.setOnDragDetected(event -> {
            Dragboard db = label.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());
            db.setContent(content);
            event.consume();
        });
        label.setOnDragDone(event -> {
            event.consume();
        });
    }

    private void addAccountsBox2(String name) {
        System.out.println("it is added in box2");
        Label label = new Label(name);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setPrefHeight(15);
        label.setAlignment(Pos.CENTER);
        affiliateAccounts.getChildren().add(label);

        label.setOnDragDetected(event -> {
            Dragboard db = label.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());
            db.setContent(content);
            event.consume();
        });

        label.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE && dropDone) {
                if( isDeletable(label.getText())) {
                    accounts.removeIf(e -> e.getName().equals(label.getText()));
                    affiliateAccounts.getChildren().remove(label);
                }else{
                    warningLabel.setText("La cuenta tiene dinero\nno puede ser eliminada");
                }
                dropDone = false;

            }
            event.consume();
        });
    }

    @FXML
    private void dragOver1(DragEvent event) {
        if (((Label) event.getGestureSource()).getParent() != allAccounts && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void dragDropped1(DragEvent event) {
        dropDone = false;
        if (((Label) event.getGestureSource()).getParent() == affiliateAccounts) {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                dropDone = true;
            }
        }
        event.setDropCompleted(dropDone);
        event.consume();
    }

    @FXML
    private void dragOver2(DragEvent event) {
        if (((Label) event.getGestureSource()).getParent() != affiliateAccounts && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void dragDropped2(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            System.out.println("entro en el dropped2");

            if (((Label) event.getGestureSource()).getParent() == allAccounts && !isAlreadyIn(db.getString())) {
                System.out.println("is alreadyin falso");
                addAccountsBox2(db.getString());
                Account newAccount = new Account();
                newAccount.setName(db.getString());
                affiliated.addAccount(newAccount);
                success = true;
            }

        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void searchAffiliate(ActionEvent event) {

       try {
           affiliateAccounts.getChildren().clear();
           allAccounts.getChildren().clear();
           List<Affiliated> associates = getCoope().getAffiliates();
           for (Affiliated aux : associates) {
               if (aux.getFolio().equals(folioField.getText())) {
                   affiliated = aux;
                   accounts = affiliated.getAccounts();
                   userLabel.setText(affiliated.getName());
                   loadCompanyAccounts();
                   loadAffiliateAccounts();
                   folioField.clear();
                   warningLabel.setText("Encontrado");
                   return;
               }
           }
       }catch (Exception e){
           System.out.println("error buscando afiliado: " + e.getMessage());
           warningLabel.setText("Error buscando el asociado");
           return;
       }
        warningLabel.setText("Asociado no encontrado!");
        folioField.clear();
    }

    private void loadCompanyAccounts() {
        try {

            List<Account> accountsComp = getCoope().getAccounts();
            for (Account aux : accountsComp) {
                addAccountsBox1(aux.getName());
            }
            warningLabel.setText("");
        }catch (Exception e){
            System.out.println("error obteniendo cuentas: " + e.getMessage());
            warningLabel.setText("No existen cuentas en la cooperativa");
        }
    }

    private void loadAffiliateAccounts() {
       try{
            for (Account aux : accounts) {
                addAccountsBox2(aux.getName());
            }
        }catch(Exception e){
           warningLabel.setText("No existe el asociado\n o no tiene cuentas");
           System.out.println("No existe el asociado\n o no tiene cuentas");
       }
    }

    private boolean isAlreadyIn(String name) {

        System.out.println("entro en already in");
        if (accounts == null) {
            warningLabel.setText("El asociado no tiene cuentas");
            System.out.println("accounts Null");
        } else {
            for (Account aux : accounts) {
                if (aux.getName().equals(name)) {
                    System.out.println("devolviotrue");
                    warningLabel.setText("");
                    return true;
                }
            }
        }
        System.out.println("devolviofalse");

        return false;

    }

    private void cleaning(){
        affiliateAccounts.getChildren().clear();
        allAccounts.getChildren().clear();
        folioField.clear();
        userLabel.setText("Usuario");
    }

    private boolean isDeletable(String name){
        for(Account accountsAux : accounts){
            if(accountsAux.getName().equals(name)){
                return accountsAux.getMoney() == 0;
            }
        }
        return false;
    }
}
