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
    private int age;
    private List<Account> accounts;
    private String picture;
    private float buzon;

    
    
     public Affiliated(){
        name = "";
        accounts = new ArrayList<>();
        picture = "";
        age = 0;
    }
    
    public Affiliated(String name, int age){
        this.name = name;
        this.accounts = new ArrayList<>();
        this.picture = null;
        this.age = age;
    }
    
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getBuzon() {
        return buzon;
    }

    public void setBuzon(float buzon) {
        this.buzon = buzon;
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
    
   public String getPicture(){
       return picture;
   }
   public void setPicture(String image){
       this.picture = image;
   }
   
   
}
