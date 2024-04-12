package cr.ac.una.cooperativa.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cooperativa {

    private String name;
    private String imageFile;
    private List<Account> accounts;
    private List<Affiliated> affiliates;

    public Cooperativa() {
        name = "C:\\Users\\stwar\\Documents\\Proyectos java\\cooperativa\\cooperativa";
        imageFile = "";
        accounts = new ArrayList<>();
        affiliates = new ArrayList<>();
    }

    public Account getAccount(int index) {
        return accounts.get(index);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Affiliated getAffiliated(int index) {
        return affiliates.get(index);
    }

    public void addAffiliated(Affiliated affiliate) {
        affiliates.add(affiliate);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String image) {
        this.imageFile = image;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Affiliated> getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(List<Affiliated> affiliates) {
        this.affiliates = affiliates;
    }

    public void assignFolio(Affiliated asociado) {
        Integer num = 0;
        Random rand = new Random(); 

        Integer randomInt = rand.nextInt(1000); 
        
        for (Affiliated affiliado : affiliates) {
            num++;
            if (affiliado.getName().equals(asociado.getName())) {
                asociado.setFolio(asociado.getName().substring(0, 1).toUpperCase() + num.toString() + randomInt.toString());
            }
        }
    }
}
