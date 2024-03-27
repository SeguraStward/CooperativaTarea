/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.cooperativa.classes;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author stward segura
 */
public class Json {
     public static void guardar(Cooperativa company, String archivo) {
        Gson gson = new Gson();
        
        try (FileWriter writer = new FileWriter(archivo)) {
                gson.toJson(company, writer);
                System.out.println("guardado exitoso");
        } catch (IOException e) {
          System.out.println("algo salio mal");
        }
    }
     
       public static Cooperativa cargar(String archivo) {
        Gson gson = new Gson();
        Cooperativa nueva;
        try (FileReader reader = new FileReader(archivo)) {           
             nueva = gson.fromJson(reader, Cooperativa.class);
             System.out.println("Corporativa cargada exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar");
            nueva = new Cooperativa();
        }
        return nueva;
    }
}
