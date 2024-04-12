
package cr.ac.una.cooperativa.classes;


public class Account {
    private String name;
    private int money;
    
    
    public Account(){}
    
    public Account(String name, int money){
    this.name = name;
    this.money = money; 
    
    }
    
   public String getName(){
       return this.name;
    }
   public void setName(String name){
       this.name = name;
   }
   
   public int getMoney(){
       return this.money;
   }
   
   public void setMoney(int money){
       this.money = money;   
   }
      
}
