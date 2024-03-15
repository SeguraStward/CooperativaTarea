/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.cooperativa.classes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;



public class Cooperativa {
    private String name;
    private Image image;
    private List<Account> accounts;
    private List<Affiliated> affiliates;
    
    
    public Cooperativa(){
        name = "";
        image = null;
        accounts = new ArrayList<>();
        affiliates = new ArrayList<>();

    }
    
    public Account getAccount(int index){
        return accounts.get(index);
    }
    
    public void addAccount(Account account){
           accounts.add(account);
    }
    
    public Affiliated getAffiliated(int index){
        return affiliates.get(index);
    }
    
    public void addAccount(Affiliated affiliate){
           affiliates.add(affiliate);
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
 }
