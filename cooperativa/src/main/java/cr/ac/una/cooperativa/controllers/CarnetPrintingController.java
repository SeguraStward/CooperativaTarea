package cr.ac.una.cooperativa.controllers;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import cr.ac.una.cooperativa.classes.Affiliated;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.cooperativa.util.FlowController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author stward segura
 */
public class CarnetPrintingController extends Controller implements Initializable {

    @FXML
    private TextField folioField;
    @FXML
    private Label confirmationLabel;
    @FXML
    private Button backBtn;
    private  Affiliated affiliated;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       super.createDirectory();
       super.load();
    }    

    @Override
    public void initialize() {
    }
    
    @FXML
    private void printingCarnet(ActionEvent event){
     if(isAffiliatedToPrint()){
      try {
       
        String pdfPathAndName = super.path + File.separator + affiliated.getName()+".pdf";
 // Creating the pdf
        PdfWriter writer = new PdfWriter(pdfPathAndName);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
     //to add the image into the pdf and to make changes in the image
        ImageData imageData = ImageDataFactory.create(affiliated.getPicture());
        Image image = new Image(imageData);
        Div div = new Div();//just experimenting with this i just added a border

        float widthInInches = 100;
        float heightInInches = 130;
        //new size for the image
        image.scaleToFit(widthInInches , heightInInches);
        //adding the image first


        //adding the information
        Paragraph p1 = new Paragraph("Nombre: "+ affiliated.getName()+ "\n Apellido: "+ affiliated.getLastName()+ "\n Edad: "+ String.valueOf(affiliated.getAge())+ "\n Folio: " + affiliated.getFolio()+"\n ");
          Border border = new SolidBorder(ColorConstants.BLACK,3);
          div.add(image);
          div.add(p1);
          div.setBorder(border);
          div.setWidth(120);
          document.add(div);
          document.close();
          System.out.println("pdf created");
          confirmationLabel.setText("Pdf creado!");
    } catch (Exception e) {
          confirmationLabel.setText("Fallo al crear el pdf");
        System.out.println("something bad happened:"+e.getMessage());
    }
     }else {
         System.out.println("no affiliates");
         confirmationLabel.setText("Fallo al crear el pdf");
     }
    }
//this get the affiliate and return true
    private boolean isAffiliatedToPrint() {
                if(getCoope().getAffiliates() != null) {
                    List<Affiliated> affiliates = getCoope().getAffiliates();

                    for (Affiliated aux : affiliates) {
                        if (folioField.getText().equals(aux.getFolio())) {
                            folioField.clear();
                            affiliated = aux;
                            return true;
                        }
                    }
                }
                return false;
    }
    @FXML
    private void goBack(ActionEvent event) {
        FlowController.getInstance().goView("FunctionaryWindow");
    }
}
