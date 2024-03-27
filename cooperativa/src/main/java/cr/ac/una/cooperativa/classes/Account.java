/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.cooperativa.classes;


public class Account {
    private String name;
    private float money;
    private String description;
    
    public Account(){}
    
    public Account(String name, float money,String description){
    this.name = name;
    this.money = money; 
    this.description = description;
    }
    
   public String getName(){
       return this.name;
    }
   public void setName(String name){
       this.name = name;
   }
   
   public float getMoney(){
       return this.money;
   }
   
   public void setmoney(float money){
       this.money = money;   
   }
      
}
