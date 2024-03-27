/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.cooperativa.classes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Affiliated {
    private String name;
    private List<Account> accounts;
    private String information;
    private String picture;
    private float buzon;
    
    public Affiliated(){
        name = "";
        accounts = new ArrayList<>();
        information = "";
        picture = "";
        
    }
    
    public Affiliated(String name, String information){
        this.name = name;
        this.accounts = new ArrayList<>();
        this.information = information;
        this.picture = null;
    }
    
    
     
   public String getName(){
       return name;
    }
   public void setName(String name){
       this.name = name;
   }
   
   public List<Account> getAccounts(){
       return accounts;
   }
   
   public void addAccount(Account cuenta){
        accounts.add(cuenta);   
   }
    
   public String getInformation(){
         return information;
   }
    
   public void setInformation(String info){
       this.information = info;
   }
    
   public String getImage(){
       return picture;
   }
   public void setImage(String image){
       this.picture = image;
   }
}
