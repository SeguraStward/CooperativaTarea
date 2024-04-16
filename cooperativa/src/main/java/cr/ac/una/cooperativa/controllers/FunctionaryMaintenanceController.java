package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Affiliated;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.cooperativa.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class FunctionaryMaintenanceController extends Controller implements Initializable {


    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private ImageView userPicture;
    @FXML
    private VBox vboxAffiliates;

    @FXML
    private VBox boxAffFolio;
    @FXML
    private TextField search;
   @FXML
    private TextField lastNameField;
    @FXML
    private Label warningName;
    @FXML
    private Label warningLast;
    @FXML
    private Label warningImg;
    @FXML
    private Label warningAdd;
    @FXML
    private Label message;
    @FXML
    private Label warningSearch;
    @FXML
    private Label warningAge;
    private String currentImg;
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.load();
        vboxAffiliates.getChildren().clear();
        showSavedAccounts();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void addAffiliate(ActionEvent event) {

        if (verifyInputs() && super.verifyingAuthenticity(nameField,lastNameField)) {//finally we add the affiliated
            warningAdd.setText("Usuario Agregado correctamente");
            Affiliated newAff = new Affiliated(nameField.getText(), lastNameField.getText(), Integer.parseInt(ageField.getText()));
            newAff.setPicture(currentImg);//assigning the imgpath to the affiliated
            super.getCoope().addAffiliated(newAff);//adding the affiliated in the cooperativa
            super.getCoope().assignFolio(newAff);//we assign it a folio
            addAffiliateToBox(newAff, vboxAffiliates); //we create its vbox
            super.save();
            cleanWindow();
        }
    }

    public void addAffiliateToBox(Affiliated affil, VBox box) {
        // all that user's hbox needs
        Image image = new Image(affil.getPicture());
        ImageView userPhoto = new ImageView(image);
        userPhoto.setFitWidth(50);
        userPhoto.setFitHeight(50);
        Label name = new Label(affil.getName());
        Label lastName = new Label(affil.getLastName());
        Label age = new Label(String.valueOf(affil.getAge()));
        Label folio = new Label(affil.getFolio());
        Button delete = new Button("Delete");
        Button editName = new Button("Editar\nnombre");
        Button editLastName = new Button("Editar\napellido");
        Button newUserPhoto = new Button("Imagen");
        //setting the hbox with some specifications
        HBox userBox = new HBox(10);
        userBox.setPrefHeight(15);
        userBox.setAlignment(Pos.CENTER);
        //adding the content in the hbox
        userBox.getChildren().addAll(userPhoto, name, lastName, age, folio, editName, editLastName, newUserPhoto, delete);
        userBox.getStyleClass().add("accountsHbox");
        box.getChildren().add(userBox);
        //we assign each button the actions that will delete or edit the userBox
        //and the affiliated attributes
        delete.setOnAction(e -> {
            deleteAffiliate(name.getText());
            box.getChildren().remove(userBox);

        });
        editName.setOnAction(e -> editWindow(name));//edit name

        editLastName.setOnAction(e -> editWindow(lastName));//edit lastname

        newUserPhoto.setOnAction(e -> {//selecciona una nueva foto
            chooseImage(affil, userPhoto);
        });
    }

    private void deleteAffiliate(String name) {

        List<Affiliated> affiliates = super.getCoope().getAffiliates();
        affiliates.removeIf(a -> a.getName().equals(name));

    }

    //small window to edit the labels of the user
    private void editWindow(Label label) {
        Stage stage = new Stage();
        stage.setTitle("Editar " + label.getText());
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        TextField textField = new TextField();
        textField.setText(label.getText());

        Button confirmButton = new Button("Confirmar");

        Label label2 = new Label("Edita tu apellido o nombre aqui:  ");
        vbox.getChildren().addAll(label2, textField, confirmButton);
        Scene scene = new Scene(vbox, 300, 150);
        List<Affiliated> affiliates = super.getCoope().getAffiliates();
        confirmButton.setOnAction(e -> {//changing the affiliate's label
            for (Affiliated affiliate : affiliates) {//looking for the affiliate we have to change
                if (affiliate.getName().equals(label.getText())) {
                    affiliate.setName(textField.getText());// setting its new name
                    break;
                } else if (affiliate.getLastName().equals(label.getText()) || affiliate.getLastName().isEmpty()) {
                    affiliate.setLastName(textField.getText());//setting its lastname
                    break;
                }
            }
            label.setText(textField.getText());
            stage.close();
        });
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void chooseImage(Affiliated associated, ImageView img) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(super.getStage());
        if (file != null) {
            try {//
                Image image = new Image(file.toURI().toString());
                img.setImage(image);
                if (associated != null) {//this if to use the method twice
                    associated.setPicture(file.toURI().toString());
                } else {
                    currentImg = file.toURI().toString();
                    userPicture.setImage(image);
                }
            } catch (Exception e) {
                System.err.println("Error loading the image: " + e.getMessage());
            }
        }

    }

    @FXML
    private void chooseImage(ActionEvent event) {
        chooseImage(null, userPicture);
    }

    @FXML
    private void searchAffiliate(ActionEvent event) {
        //cleaning before showing the searched affiliate box
        boxAffFolio.getChildren().clear();

        List<Affiliated> affiliates = super.getCoope().getAffiliates();
        if (affiliates != null) {
            for (Affiliated aux : affiliates) {//going through the list
                // to find the affiliate with the same folio
                if (search.getText().equals(aux.getFolio())) {
                    //add the searched affiliate to a different Vbox
                    addAffiliateToBox(aux, boxAffFolio);
                    System.out.println("listooooo");
                } else {
                    System.out.println("nada se mostro");
                }
            }
        } else {
            warningSearch.setText("Asociado no encontrado");
        }

    }

    private void showSavedAccounts() {

        if (super.getCoope().getAffiliates() != null) {
            List<Affiliated> affiliates = super.getCoope().getAffiliates();
            for (Affiliated affiliated : affiliates) {
                addAffiliateToBox(affiliated, vboxAffiliates);
            }
        }
    }

    @FXML
    private void exitAction(ActionEvent event) {
        cleanWindow();
        boxAffFolio.getChildren().clear();
        FlowController.getInstance().goView("functionaryWindow");
    }


    private void cleanWindow() {
        userPicture.setImage(null);
        warningName.setText("");
        warningLast.setText("");
        warningAdd.setText("");
        warningAge.setText("");
        warningImg.setText("");
        message.setText("");
        warningSearch.setText("");
        nameField.setText("");
        lastNameField.setText("");
        ageField.setText("");

    }

    private boolean verifyInputs() {
        boolean condition = true;
        try {
            if (nameField.getText().length() < 4) {// return false if the name has less than 4 characteres
                cleanWindow();
                warningName.setText("Nombre invalido");
                condition = false;
            }
           else if (lastNameField.getText().length() < 4) {//same but with lastname
                condition = false;
                cleanWindow();
                warningLast.setText("Apellido invalido");
            }
            else if (Integer.parseInt(ageField.getText()) > 18) {//its for childs sooo
                condition = false;
                cleanWindow();
                warningAge.setText("Edad invalida");
            }
            else if (userPicture.getImage() == null) {//if there is no picture
                condition = false;
                cleanWindow();
                warningImg.setText("Foto de usuario Invalida");
            }

        }catch (Exception e) {
            condition = false;
         System.err.println("Error: " + e.getMessage());
        }
        return condition;
    }


}