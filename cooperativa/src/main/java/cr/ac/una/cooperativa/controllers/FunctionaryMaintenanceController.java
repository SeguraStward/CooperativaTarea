
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.util.AppContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class FunctionaryMaintenanceController extends Controller implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox head;
    @FXML
    private ImageView companyImage;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private Button addBtn;
    @FXML
    private ImageView userPicture;
    @FXML
    private ScrollPane scrollAffiliates;
    @FXML
    private VBox vboxAffiliates;
    String currentImg;
    
    @FXML
    private Button chooseImg;
    @FXML
    private VBox boxAffFolio;
    @FXML
    private TextField search;
    @FXML
    private Button searchBtn;
    @FXML
    private Label companyName;
    @FXML
    private Button readyBtn;
   
    /**
     * Initializes the controller class.
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
    private void addAffiliate(ActionEvent event){
          
            if(nameField.getText().length() < 4){
           nameField.setPromptText("Invalid name");
           
           }else if(Integer.parseInt(ageField.getText()) > 18){
               
           ageField.setPromptText("Invalid age");      
           }else if(userPicture != null){
               
           Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
           Affiliated newAff = new Affiliated(nameField.getText(),
                   Integer.parseInt(ageField.getText()));
           
           newAff.setPicture(currentImg);
           
           company.addAffiliated(newAff);
           company.assignFolio(newAff);
           addAffiliateToBox(newAff,vboxAffiliates);
        }    
        }
           
       
    
    public void addAffiliateToBox(Affiliated affil, VBox box){
        
        Image image = new Image(affil.getPicture());
        ImageView userPhoto = new ImageView(image);
        userPhoto.setFitWidth(50);
        userPhoto.setFitHeight(50);
        Label name = new Label(affil.getName());
        Label age = new Label(String.valueOf(affil.getAge()));
        Label folio = new Label(affil.getFolio());
        Button delete = new Button("Delete"); 
        Button editName = new Button("Editar"); 
        Button newUserPhoto = new Button("Imagen"); 
        HBox userBox = new HBox(10);
        
        userBox.setPrefHeight(15);
        userBox.setAlignment(Pos.CENTER);
        userBox.getChildren().addAll(userPhoto,name,age,folio,editName, newUserPhoto, delete);
        userBox.getStyleClass().add("accountsHbox");
        box.getChildren().add(userBox);
        
        delete.setOnAction(e -> {
             box.getChildren().remove(userBox);
             deleteAffiliate(name.getText());
        });
        
        editName.setOnAction(e -> editWindow(name));
        
        newUserPhoto.setOnAction(e -> {    
           chooseImage(affil, userPhoto);
         });
    }
    
    private void deleteAffiliate(String name){
      Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
       List<Affiliated> affiliates = company.getAffiliates();
       affiliates.removeIf(a -> a.getName().equals(name));   

    }

    private void editWindow(Label label) {
    Stage stage = new Stage();
    stage.setTitle("Editar asociado");
    Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
    List<Affiliated> affiliates = company.getAffiliates();
    VBox vbox = new VBox(10);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(10));

    TextField textField = new TextField();
    textField.setText(label.getText());

    Button confirmButton = new Button("Confirmar");
    confirmButton.setOnAction(e -> {
       
       
         for(Affiliated affiliate: affiliates){
            if(affiliate.getName().equals(label.getText())){              
                affiliate.setName(textField.getText());
               break;
            }
        }
        label.setText(textField.getText());
        stage.close();
    });
   
    Label label2 = new Label("Introduce el nuevo nombre: ");
    vbox.getChildren().addAll(label2, textField, confirmButton); 
    Scene scene = new Scene(vbox, 300, 150);
    stage.setScene(scene);
    stage.showAndWait();
    }

    
 
  private void chooseImage(Affiliated asociado,ImageView img) {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        File file = fileChooser.showOpenDialog(super.getStage());
        if (file != null) {
             try {
                 Image image = new Image(file.toURI().toString());
                 img.setImage(image);
                 asociado.setPicture(file.toURI().toString());               
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
            }
    }
      
    }
  
 

    @FXML
    private void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        File file = fileChooser.showOpenDialog(super.getStage());
        if (file != null) {
             try {
                 Image image = new Image(file.toURI().toString());
                 currentImg = file.toURI().toString();
                 userPicture.setImage(image);
                 
                            
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
            }
    }
}
    
    @FXML
    private void searchAffiliate(ActionEvent event){
        
        boxAffFolio.getChildren().clear();
        Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
        List<Affiliated> afiliados = company.getAffiliates();
        if(afiliados != null){
        for(Affiliated aux: afiliados){
            if(search.getText().equals(aux.getFolio())){
                addAffiliateToBox(aux, boxAffFolio);
                System.out.println("listooooo");
            }else{
                 System.out.println("nada se mostro");
            }
        }       
        }else{
            search.setPromptText("No hay afiliados");
        }
        
    }
  
      private void showSavedAccounts(){
        
          Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
          List<Affiliated> asociados = company.getAffiliates();
          for(Affiliated asociado: asociados){
             addAffiliateToBox(asociado, vboxAffiliates);
          }
          
    }

    @FXML
    private void readyAction(ActionEvent event) {
        super.save();
    }
}