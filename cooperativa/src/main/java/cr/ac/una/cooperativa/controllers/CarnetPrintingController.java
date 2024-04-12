package cr.ac.una.cooperativa.controllers;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import cr.ac.una.cooperativa.classes.Account;
import cr.ac.una.cooperativa.classes.Affiliated;
import cr.ac.una.cooperativa.classes.Cooperativa;
import cr.ac.una.cooperativa.util.AppContext;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class CarnetPrintingController extends Controller implements Initializable {

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
    private TextField folioField;
    @FXML
    private Button printBtn;
    @FXML
    private Label confirmationLabel;
    private  Affiliated asociado;
    @FXML
    private Label compayLabel;
    @FXML
    private Button backBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @Override
    public void initialize() {
    }
    
    @FXML
    private void printingCarnet(ActionEvent event){
     if(getAccountToPrint()){
      try {
       
        String destino = super.path + asociado.getName()+".pdf";
        // Inicializar PDF writer y document
        PdfWriter writer = new PdfWriter(destino);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        ImageData imageData = ImageDataFactory.create(asociado.getPicture());
        Image image = new Image(imageData);

        float widthInInches = 2 + (15f / 72);
        float heightInInches = 2.5f + (15f / 72);
        // Convertir a puntos para iText
        image.scaleToFit(widthInInches * 72, heightInInches * 72);
        
        document.add(image);

        Paragraph p1 = new Paragraph("Nombre: "+asociado.getName()+"\n Edad: "+ String.valueOf(asociado.getAge())+ "\nFolio: " + asociado.getFolio()+"\n");
        List<Account> accounts = asociado.getAccounts();
        String listAccounts = "";
        for(Account account: accounts){
            listAccounts +="Cuenta: "+ account.getName() + " "+ "Efectivo: "+ String.valueOf(account.getMoney() +"\n");
        
        }
        Paragraph p2 = new Paragraph(listAccounts);
        document.add(p1);
        document.add(p2);
       
        document.close();
        
        System.out.println("pdf creado");
    } catch (Exception e) {
        e.printStackTrace();
    }
     }
    }

    private boolean getAccountToPrint() {
                Cooperativa company = (Cooperativa)AppContext.getInstance().get("Cooperativa");
                List<Affiliated> asociates = company.getAffiliates();
                
                for(Affiliated aux: asociates){
                    if(folioField.getText().equals(aux.getFolio())){
                        asociado = aux;
                        return true;
                    }
                }
                return false;
    }
}
