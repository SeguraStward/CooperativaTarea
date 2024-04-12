/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.util.AppContext;
import cr.ac.una.cooperativa.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
    private Affiliated asociado;
    private List<Account> accounts;
    @FXML
    private Label companyName;
    @FXML
    private ImageView companyImage;

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
        Label aLabel = new Label(name);
        aLabel.setMaxWidth(Double.MAX_VALUE);
        aLabel.setPrefHeight(15);
        aLabel.setAlignment(Pos.CENTER);
        allAccounts.getChildren().add(aLabel);

        aLabel.setOnDragDetected(event -> {
            Dragboard db = aLabel.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(aLabel.getText());
            db.setContent(content);
            event.consume();
        });
        aLabel.setOnDragDone(event -> {
            event.consume();
        });
    }

    private void addAccountsBox2(String name) {
        System.out.println("se agrego al box2");
        Label aLabel = new Label(name);
        aLabel.setMaxWidth(Double.MAX_VALUE);
        aLabel.setPrefHeight(15);
        aLabel.setAlignment(Pos.CENTER);
        affiliateAccounts.getChildren().add(aLabel);

        aLabel.setOnDragDetected(event -> {
            Dragboard db = aLabel.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(aLabel.getText());
            db.setContent(content);
            event.consume();
        });

        aLabel.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE && dropDone) {
                accounts.removeIf(e -> e.getName().equals(aLabel.getText()));
                affiliateAccounts.getChildren().remove(aLabel);
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
        boolean success = false;
        if (((Label) event.getGestureSource()).getParent() == affiliateAccounts) {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                success = true;
            }
        }
        dropDone = success;
        event.setDropCompleted(success);
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
                asociado.addAccount(newAccount);
                success = true;
            }

        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void searchAffiliate(ActionEvent event) {
        affiliateAccounts.getChildren().clear();
        allAccounts.getChildren().clear();
        Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
        List<Affiliated> asociados = company.getAffiliates();
        for (Affiliated aux : asociados) {
            if (aux.getFolio().equals(folioField.getText())) {
                asociado = aux;
                accounts = asociado.getAccounts();
                loadCompanyAccounts();
                loadAffiliateAccounts();
                folioField.clear();
                return;
            }
        }
        folioField.setPromptText("No existe el asociado");
    }

    private void loadCompanyAccounts() {
        Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
        List<Account> accountsComp = company.getAccounts();
        for (Account aux : accountsComp) {
            addAccountsBox1(aux.getName());
        }

    }

    private void loadAffiliateAccounts() {

        for (Account aux : accounts) {
            addAccountsBox2(aux.getName());
        }
    }

    private boolean isAlreadyIn(String name) {

        System.out.println("entro en already in");
        if (accounts == null) {
            System.out.println("accounts Null");
        } else {
            for (Account aux : accounts) {
                if (aux.getName().equals(name)) {
                    System.out.println("devolvioTrue");
                    return true;
                }
            }
        }
        System.out.println("devolvioFalse");

        return false;

    }
}
