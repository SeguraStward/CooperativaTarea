package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.classes.Json;
import cr.ac.una.cooperativa.util.AppContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.bridj.CRuntime;

import javax.imageio.ImageIO;

public abstract class Controller {

    protected Stage stage;
    private String accion;
    private String nombreVista;
    protected String path;
    @FXML
    protected ImageView companyImage;
    @FXML
    protected Label companyName;

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public String getNombreVista() {
        return nombreVista;
    }

    public void setNombreVista(String nombreVista) {
        this.nombreVista = nombreVista;
    }
    // this load the company's name and image
    public void load() {
        try {
            Cooperativa company = getCoope();
            companyName.setText(company.getName());
            String imgFile = company.getImageFile();
            Image image = new Image(imgFile);
            companyImage.setImage(image);


        }catch(Exception e){
            companyImage.setImage(null);
            companyName.setText("Cooperativa");
            save();
            System.out.println("Error loading: " + e.getMessage());
        }
    }

    public void save() {

        Json.guardar(getCoope(),"jsonFile.json");
    }

    public Cooperativa getCoope() {
        return (Cooperativa) AppContext.getInstance().get("Cooperativa");
    }

    public List<Account> getListAccounts() {
        return getCoope().getAccounts();
    }

    public List<Affiliated> getListAffiliates() {

        return getCoope().getAffiliates();

    }

    public abstract void initialize();

    public void createDirectory() {
        String userHome = System.getProperty("user.home");//we get the userHome of the computer
        File documentsDir = new File(userHome, "Documents");//finding the Documents so we can create a folder there
         //testing if documents exist and it is a directory
        if (!documentsDir.exists() || !documentsDir.isDirectory()) {
            System.out.println("Directory does not exist: " + documentsDir.getAbsolutePath());
            documentsDir = new File(userHome);// we would use userHome to create there a folder and save things
        }
        //creating the path in which we will create a folder 
        File myResources = new File(documentsDir, "CoopeResources");
        //in case it exists or not we manage the different situations
        if (!myResources.exists()) {
            try {
                if (myResources.mkdirs()) {
                    System.out.println("Folder Created");
                } else {
                    System.out.println("Folder was not created");
                }
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        } else {
            System.out.println("The Folder already exists");
        }
        path = myResources.getPath();
        System.out.println(path);
    }
    // it could be in an interface but, I did it here 
    public boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // as well, it could be in an interface 
    public void saveImage(BufferedImage image, String fileName) {
        String imgPng = fileName + ".png";
        File imgPath = new File(path + File.separator + imgPng);

    try {
        // here we specify the format of the image, and we write it in the specified path that as well includes the image's name
        ImageIO.write(image, "png", imgPath);
        System.out.println("image saved" + imgPath.getAbsolutePath());
    } catch (IOException e) {
        System.out.println("error given by trying to save img" + e.getMessage());
    }
}
    public boolean verifyingAuthenticity(TextField name, TextField lastName){
        List<Affiliated> affiliates = getListAffiliates();
        for (Affiliated affiliated : affiliates){
            if(affiliated.getName().equals(name.getText()) && affiliated.getLastName().equals(lastName.getText())){
                return false;
            }
        }
        return true;
    }
}
