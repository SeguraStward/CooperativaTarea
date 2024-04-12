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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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

    public void load() {
        Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
        companyName.setText(company.getName());
        Image image = new Image(company.getImageFile());
        companyImage.setImage(image);
    }

    public void save() {
        Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
        Json.guardar(company, "jsonFile.json");
    }

    public Cooperativa getCoope() {
        Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
        return company;
    }

    public List<Account> getListAccounts() {
        Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
        return company.getAccounts();
    }

    public List<Affiliated> getListAffiliates() {
        Cooperativa company = (Cooperativa) AppContext.getInstance().get("Cooperativa");
        return company.getAffiliates();

    }

    public abstract void initialize();

    public void createDirectory() {
        String userHome = System.getProperty("user.home");
        File documentsDir = new File(userHome, "Documents");

        if (!documentsDir.exists() || !documentsDir.isDirectory()) {
            System.out.println("El directorio 'Documents' no existe. Usando el directorio del usuario.");
            documentsDir = new File(userHome);
        }

        File myResources = new File(documentsDir, "CoopeResources");

        if (!myResources.exists()) {
            try {
                if (myResources.mkdirs()) {
                    System.out.println("Carpeta creada");
                } else {
                    System.out.println("Carpeta no creada");
                }
            } catch (SecurityException se) {
                System.out.println("Error " + se.getMessage());
            }
        } else {
            System.out.println("La carpeta ya existe");
        }
        path = myResources.getPath();
    }

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
    
    public void saveImage(BufferedImage image, String fileName) {
    File outputFile = new File(path + File.separator + fileName);
    try {
        // Aquí especificas el formato de la imagen, por ejemplo "png", "jpg"
        ImageIO.write(image, "png", outputFile);
        System.out.println("Imagen guardada en: " + outputFile.getAbsolutePath());
    } catch (IOException e) {
        System.out.println("Error al guardar la imagen: " + e.getMessage());
    }
}
}
