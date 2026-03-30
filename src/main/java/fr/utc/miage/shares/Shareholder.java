/*
 * Copyright 2024 David Navarre &lt;David.Navarre at irit.fr&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.utc.miage.shares;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a shareholder in the application. It extends the User class and adds functionality for managing a portfolio of actions.
 * @author Walaedine Sekoub &lt;
 */
public class Shareholder extends User {

    private final Map<Action, Integer> portefeuille;

    private static final ActionsList actionsList = new ActionsList();

    public Shareholder(String email, String password, String name, String firstname) {
        super(email, password, name, firstname);
        this.portefeuille = new HashMap<>();
    }

    // Method to add an action to the shareholder's portfolio. If the action already exists in the portfolio, 
    // we update the quantity and the total price of the action. If the action does not exist in the portfolio, 
    // we add it with the given quantity and price. We also check that the price is not negative and that the quantity is positive.
    //This implementation is not optimal
    public void addAction(Action action, Jour j, int quantite){
        if(action==null){
            throw new IllegalArgumentException("L'action est null");
        }

        if(action.valeur(j)<=0){
            throw new IllegalArgumentException("Le prix est nul ou négatif");
        }

        if(quantite<=0){
            throw new IllegalArgumentException("La quantité est négative ou égale à 0");
        }
        
        if (!actionsList.isActionDispo(action)) {
            throw new IllegalArgumentException("L'action n'est pas disponible");
        }
        Integer listAction = portefeuille.get(action);

        if(listAction!=null){
            listAction+=quantite;

            portefeuille.put(action, listAction);
            
        }else{
            portefeuille.put(action, quantite);
        }
    } 

    // Method to sell an action from the shareholder's portfolio. If the action does not exist, 
    // we throw an exception. If the quantity to sell is greater than the quantity owned, we throw an exception. 
    // Otherwise, we update the quantity of the action in the portfolio and if the quantity becomes 0, 
    // we remove the action from the portfolio
    public void sellAction(Action action, int quantite){
        if(action==null){
            throw new IllegalArgumentException("L'action est null");
        }

        if(quantite<=0){
            throw new IllegalArgumentException("La quantité est négative ou égale à 0");
        }

        if (!actionsList.getAllActions().contains(action)) {
            throw new IllegalArgumentException("L'action n'existe pas");
        }
        Integer listAction = portefeuille.get(action);

        if(listAction==null){
            throw new IllegalArgumentException("Cette action n'est pas présente dans ce portefeuille");
        }else{
            if(listAction>=quantite){
                listAction-=quantite;
                portefeuille.put(action, listAction);
                if(listAction == 0){
                    portefeuille.remove(action);
                }
            }else{
                throw new IllegalArgumentException("La quantite à vendre est supèrieur à la quantité possedée");
            }
        }
    }

    public Map<Action, Integer> getPortefeuille(){
        return portefeuille;
    }

    // Method to get the total value of the portfolio by multiplying the quantity of each action by its current price and summing the results. We also check that the price of each action is not negative.
    public double getValeurPortefeuilleActuel(){
        double valeur = 0;
        for(Map.Entry<Action, Integer> entry : portefeuille.entrySet()){
            Action action = entry.getKey();
            Integer quantite = entry.getValue();
            double prixActuel = action.valeur(new Jour());
            valeur += quantite * prixActuel;
        }
        return valeur;
    }
}
