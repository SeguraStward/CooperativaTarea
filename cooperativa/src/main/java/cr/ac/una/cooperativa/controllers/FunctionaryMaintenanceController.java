package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Affiliated;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
    private ImageView companyImage;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private ImageView userPicture;
    @FXML
    private ScrollPane scrollAffiliates;
    @FXML
    private VBox vboxAffiliates;
    private String currentImg;

    @FXML
    private VBox boxAffFolio;
    @FXML
    private TextField search;
    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox head;
    @FXML
    private Label companyName;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private Button chooseImg;
    @FXML
    private Button addBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button readyBtn;
    @FXML
    private TextField lastNameField;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.load();
        showSavedAccounts();
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void addAffiliate(ActionEvent event) {

        if (nameField.getText().length() < 4) {
            nameField.setPromptText("Invalid name");

        } else if (Integer.parseInt(ageField.getText()) > 18) {

            ageField.setPromptText("Invalid age");
        } else if (userPicture == null) {

        }else if(lastNameField.getText().length() < 4){
            lastNameField.setPromptText("Invalid lastName");
        }else {

            Affiliated newAff = new Affiliated(nameField.getText(),Integer.parseInt(ageField.getText()));
            newAff.setPicture(currentImg);
            super.getCoope().addAffiliated(newAff);
            super.getCoope().assignFolio(newAff);
            addAffiliateToBox(newAff, vboxAffiliates);
        }
    }

    public void addAffiliateToBox(Affiliated affil, VBox box) {

        Image image = new Image(affil.getPicture());
        ImageView userPhoto = new ImageView(image);
        userPhoto.setFitWidth(50);
        userPhoto.setFitHeight(50);
        Label name = new Label(affil.getName());
        Label lastName = new Label(affil.getLastName());
        Label age = new Label(String.valueOf(affil.getAge()));
        Label folio = new Label(affil.getFolio());
        Button delete = new Button("Delete");
        Button editName = new Button("Editar nombre");
        Button editLastName = new Button("Editar apellido");
        Button newUserPhoto = new Button("Imagen");
        HBox userBox = new HBox(10);

        userBox.setPrefHeight(15);
        userBox.setAlignment(Pos.CENTER);
        userBox.getChildren().addAll(userPhoto, name,lastName, age, folio, editName, newUserPhoto, delete);
        userBox.getStyleClass().add("accountsHbox");
        box.getChildren().add(userBox);

        delete.setOnAction(e -> {
            deleteAffiliate(name.getText());
            box.getChildren().remove(userBox);
            
        });

        editName.setOnAction(e -> editWindow(name));
        
        editLastName.setOnAction(e -> editWindow(lastName));
        
        newUserPhoto.setOnAction(e -> {
            chooseImage(affil, userPhoto);
        });
        
        
        
    }

    private void deleteAffiliate(String name) {
        List<Affiliated> affiliates = super.getCoope().getAffiliates();
        affiliates.removeIf(a -> a.getName().equals(name));

    }

    private void editWindow(Label label) {
        Stage stage = new Stage();
        stage.setTitle("Editar "+ label.getText());
        
        List<Affiliated> affiliates = super.getCoope().getAffiliates();
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        TextField textField = new TextField();
        textField.setText(label.getText());

        Button confirmButton = new Button("Confirmar");
       
        Label label2 = new Label("Edita tu apellido o nombre aqui:  ");
        vbox.getChildren().addAll(label2, textField, confirmButton);
        Scene scene = new Scene(vbox, 300, 150);
        stage.setScene(scene);
        stage.showAndWait();
        
         confirmButton.setOnAction(e -> {
            for (Affiliated affiliate : affiliates) {
                if (affiliate.getName().equals(label.getText())) {
                    affiliate.setName(textField.getText());
                    break;
                }
            }
            label.setText(textField.getText());
            stage.close();
        });
    }

    private void chooseImage(Affiliated asociado, ImageView img) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona la imagen");
        File file = fileChooser.showOpenDialog(super.getStage());
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                img.setImage(image);
                if(asociado != null){
                asociado.setPicture(file.toURI().toString());
                }else if(asociado == null){
                    currentImg = file.toURI().toString();
                }
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
            }
        }

    }

    @FXML
    private void chooseImage(ActionEvent event) {
       chooseImage(null, userPicture);
    }

    @FXML
    private void searchAffiliate(ActionEvent event) {

        boxAffFolio.getChildren().clear();
       
        List<Affiliated> afiliados = super.getCoope().getAffiliates();
        if (afiliados != null) {
            for (Affiliated aux : afiliados) {
                if (search.getText().equals(aux.getFolio())) {
                    addAffiliateToBox(aux, boxAffFolio);
                    System.out.println("listooooo");
                } else {
                    System.out.println("nada se mostro");
                }
            }
        } else {
            search.setPromptText("No hay afiliados");
        }

    }

    private void showSavedAccounts() {

       
        List<Affiliated> asociados = super.getCoope().getAffiliates();
        for (Affiliated asociado : asociados) {
            addAffiliateToBox(asociado, vboxAffiliates);
        }

    }

    @FXML
    private void readyAction(ActionEvent event) {
        super.save();
    }
}
