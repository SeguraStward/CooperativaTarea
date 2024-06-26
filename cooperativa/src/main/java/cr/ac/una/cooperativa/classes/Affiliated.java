package cr.ac.una.cooperativa.classes;

import java.util.ArrayList;
import java.util.List;

public class Affiliated {

    private String name;
    private String lastName;
    private int age;
    private List<Account> accounts;
    private String picture;
    private int buzon;
    private String folio;

    public Affiliated() {
        name = "";
        accounts = new ArrayList<>();
        picture = "";
        age = 0;
        lastName = "";
        buzon = 0;
    }

    public Affiliated(String name,String lastName, int age) {
        this.name = name;
        this.accounts = new ArrayList<>();
        this.picture = null;
        this.age = age;
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBuzon() {
        return buzon;
    }

    public void setBuzon(int buzon) {
        this.buzon = buzon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String image) {
        this.picture = image;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }


}
