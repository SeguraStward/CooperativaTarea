/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.util.AppContext;
import cr.ac.una.cooperativa.util.Camera;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.stage.Stage;

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
    private Label companyLabel;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private Button takePhotoBtn;
    @FXML
    private Button addBtn;
    @FXML
    private ImageView userPicture;
    @FXML
    private ScrollPane scrollAffiliates;
    @FXML
    private VBox vboxAffiliates;
    @FXML
    private Label labelWarning;
    String imagesDirectory;
    BufferedImage img;
    CameraController camera;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }
   public void openCamera() throws IOException{
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/cameraView.fxml"));
        Parent root = loader.load();
        
        camera = loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        camera.setStage(stage);     
        stage.showAndWait();
   }
   
   public void creatingDirectory(){
        String userHome = System.getProperty("user.home");
         String separator = System.getProperty("file.separator");
         String directoryPath = userHome + separator + "myResources";
         File directory = new File(directoryPath);
         if (!directory.exists()) {
              directory.mkdirs(); 
          }
        String locatingImg = directoryPath + separator;
        imagesDirectory = locatingImg;
   }
    @FXML
    private void takePicture(ActionEvent event){
        try{
            openCamera();
        }catch(IOException e){
        }
     
     creatingDirectory();
        
        img = camera.getImage();
        Image image = SwingFXUtils.toFXImage(img, null);//covierte la buffered image a image
        userPicture.setImage(image);
       
       
          addBtn.setOnAction(e-> {
          
            if(nameField.getText().length() < 4){
           nameField.setPromptText("Invalid name");
           
           }else if(Integer.parseInt(ageField.getText()) > 18){
               
           ageField.setPromptText("Invalid age");      
           }else{
               
           Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
           Affiliated newAff = new Affiliated(nameField.getText(),
                   Integer.parseInt(ageField.getText()));
           
           newAff.setPicture(nameField.getText()+".png");
           camera.saveImage(imagesDirectory + newAff.getPicture(), img);
           company.addAffiliated(newAff);
           addAffiliateToBox(newAff);
        }    
        });
           
       }
    
    public void addAffiliateToBox(Affiliated affil){
        
        Image image = new Image(imagesDirectory + affil.getPicture());
        ImageView userPhoto = new ImageView(image);
        
        Label name = new Label(affil.getName());
        Label age = new Label(String.valueOf(affil.getAge()));
        Button delete = new Button("Delete"); 
        Button editName = new Button("Change name"); 
        Button newUserPhoto = new Button("Change photo"); 
        HBox userBox = new HBox(10);
        
        userBox.setPrefHeight(15);
        userBox.setAlignment(Pos.CENTER);
        userBox.getChildren().addAll(userPhoto,name,age,editName, newUserPhoto, delete);
        userBox.getStyleClass().add("accountsHbox");
        vboxAffiliates.getChildren().add(userBox);
        
        delete.setOnAction(e -> {
             vboxAffiliates.getChildren().remove(userBox);
             deleteAffiliate(name.getText());
        });
        
        editName.setOnAction(e -> editWindow(name));
        
        newUserPhoto.setOnAction(e -> {    
            try {
               openCamera();
               img = camera.getImage();
               Image image2 = SwingFXUtils.toFXImage(img, null);
               userPhoto.setImage(image2);
               deleteFromFile(affil.getPicture());
               camera.saveImage(imagesDirectory + affil.getPicture(), img);
               
            } catch (IOException ex) {
                
            }
         });
    }
    public void deleteFromFile(String imagen){
        File file = new File(imagesDirectory + imagen);
      
        if (file.delete()) {
            System.out.println("imagen eliminada");
        } else {
            System.out.println("no se elimino la imagen");
        }
    }
    private void deleteAffiliate(String name){
      Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
       List<Affiliated> affiliates = company.getAffiliates();
       affiliates.removeIf(a -> a.getName().equals(name));   
    }
    
    private void editWindow(Label label) {
    Stage stage = new Stage();
    stage.setTitle("Editar Cuenta");
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
   
  
    vbox.getChildren().addAll(new Label("Introduce el nuevo nombre de la cuenta:"), textField, confirmButton); 
    Scene scene = new Scene(vbox, 300, 150);
    stage.setScene(scene);
    stage.showAndWait();
    }

    }

