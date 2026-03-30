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

    private final Map<Action, Double[]> portefeuille;

    public Shareholder(String email, String password, String name, String firstname) {
        super(email, password, name, firstname);
        this.portefeuille = new HashMap<>();
    }

    // Méthode pour ajouter une action au portefeuille du shareholder. Si l'action existe déjà, on met à jour le prix d'achat et la quantité. Sinon, on ajoute une nouvelle entrée dans le portefeuille
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

    // Méthode pour vendre une action du portefeuille du shareholder. Si l'action n'existe pas, on lance une exception. Si la quantité à vendre est supérieure à la quantité possédée, on lance une exception. Sinon, on met à jour la quantité de l'action dans le portefeuille et si la quantité devient 0, on supprime l'action du portefeuille
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
                if(listAction[1] == 0){
                    portefeuille.remove(action);
                }
            }else{
                throw new IllegalArgumentException("La quantite à vendre est supèrieur à la quantité posseder");
            }
        }
    }

    public Map<Action, Double[]> getPortefeuille(){
        return portefeuille;
    }
}
