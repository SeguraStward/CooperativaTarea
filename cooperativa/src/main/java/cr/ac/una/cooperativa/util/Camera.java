package cr.ac.una.cooperativa.util;

import com.github.sarxos.webcam.Webcam;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;


public class Camera {
     public static BufferedImage captureImage() {
        BufferedImage image = null;
        Webcam webcam = Webcam.getDefault();
        if (webcam != null) {
            System.out.println("Webcam: " + webcam.getName());
            webcam.open();
            
            image = webcam.getImage();

          webcam.close();
        } else {
            System.out.println("No se encontro ninguna webcam");
        }
        return image;
    }
     
   public static void saveImage(String name, BufferedImage image){
          try {
                ImageIO.write(image, "PNG", new File(name));
                System.out.println("Imagen guardada");
            } catch (IOException e) {
                e.printStackTrace();
            }
   }
  
}
