package cr.ac.una.cooperativa.util;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Camera {
     public void captureImage() {
         String userHome = System.getProperty("user.home");
         String separator = System.getProperty("file.separator");
         String directoryPath = userHome + separator + "misRecursos";
         File directory = new File(directoryPath);
         if (!directory.exists()) {
              directory.mkdirs(); // Crea el directorio si no existe
         }
        
        // Obtiene la cámara web predeterminada
        Webcam webcam = Webcam.getDefault();
        if (webcam != null) {
            System.out.println("Webcam: " + webcam.getName());
            webcam.open();

            // Captura la imagen
            BufferedImage image = webcam.getImage();

            // Guarda la imagen en el disco
            try {
                ImageIO.write(image, "PNG", new File(directoryPath));
                System.out.println("Imagen guardada");
            } catch (IOException e) {
                e.printStackTrace();
            }

            webcam.close();
        } else {
            System.out.println("No se encontro ninguna webcam");
        }
    }
}
