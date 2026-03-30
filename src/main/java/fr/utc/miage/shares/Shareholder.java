package fr.utc.miage.shares;

import java.util.HashMap;
import java.util.Map;

public class Shareholder extends User {

    private final Map<Action, Double[]> portefeuille;

    public Shareholder(String email, String password, String name, String firstname) {
        super(email, password, name, firstname);
        this.portefeuille = new HashMap<>();
    }

    
    public void addAction(Action action, double prixAchat, int quantite){

        if(prixAchat<0){
            throw new IllegalArgumentException("Le prix est négatif");
        }

        if(quantite<=0){
            throw new IllegalArgumentException("La quantité est négative ou égale à 0");
        }

        if(action==null){
            throw new IllegalArgumentException("L'action est null");
        }
        Double[] listAction = portefeuille.get(action);

        if(listAction!=null){
            listAction[0]+=prixAchat;
            listAction[1]+=quantite;

            portefeuille.put(action, listAction);
            
        }else{
            Double[] newListAction = new Double[2];
            newListAction[0] =prixAchat;
            newListAction[1]= (double) quantite;
            portefeuille.put(action, newListAction);
        }
    } 

    public void sellAction(Action action, int quantite){
        if(quantite<=0){
            throw new IllegalArgumentException("La quantité est négative ou égale à 0");
        }

        if(action==null){
            throw new IllegalArgumentException("L'action est null");
        }
        Double[] listAction = portefeuille.get(action);

        if(listAction==null){
            throw new IllegalArgumentException("Cette action n'est pas présente dans ce portefeuille");
        }else{
            if(listAction[1]>=quantite){
                listAction[1]-=quantite;
                portefeuille.put(action, listAction);
            }else{
                throw new IllegalArgumentException("La quantite à vendre est supèrieur à la quantité posseder");
            }
        }
    }

    public Map<Action, Double[]> getPortefeuille(){
        return portefeuille;
    }

    
    
}
