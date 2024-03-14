/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.cooperativa.classes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.ImageView;


public class Affiliated {
    private String name;
    private List<Account> accounts;
    private String information;
    private ImageView picture;
    
    public Affiliated(){
        this.name = new String();
        this.accounts = new ArrayList<>();
        this.information = new String();
        this.picture = new ImageView();
    }
    
    public Affiliated(String name, List<Account> cuentas, String information, ImageView picture){
        this.name = name;
        this.accounts = cuentas;
        this.information = information;
        this.picture = picture;
    }
    
    
     
   public String getName(){
       return this.name;
    }
   public void setName(String name){
       this.name = name;
   }
   
   public List<Account> getAccounts(){
       return this.accounts;
   }
   
   public void addAccount(Account cuenta){
        accounts.add(cuenta);   
   }
    
   public String getInformation(){
         return this.information;
   }
    
   public void setInformation(String info){
       this.information = info;
   }
    
   public ImageView getImageView(){
       return this.picture;
   }
    
}
