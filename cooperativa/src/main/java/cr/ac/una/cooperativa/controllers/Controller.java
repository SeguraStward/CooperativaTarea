package cr.ac.una.cooperativa.controllers;

import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.classes.Json;
import cr.ac.una.cooperativa.util.AppContext;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public abstract class Controller {

    private Stage stage;
    private String accion;
    private String nombreVista;
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
}
