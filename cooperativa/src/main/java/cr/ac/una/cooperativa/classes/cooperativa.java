/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.cooperativa.classes;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class Cooperativa {
    private String name;
    private Image image;
    private List<Account> accounts;
    private List<Affiliated> affiliates;
    
    
    public Cooperativa(){
        this.name = "";
        this.image = null;
        this.accounts = new ArrayList<>();
        this.affiliates = new ArrayList<>();

    }
    
 }
