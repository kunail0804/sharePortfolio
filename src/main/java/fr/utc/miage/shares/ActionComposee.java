/*
 * Copyright 2025 David Navarre <David.Navarre at irit.fr>.
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
 * Represents a composite action composed of multiple simple actions with percentages.
 * 
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
public class ActionComposee extends Action {

    private final Map<ActionSimple, Float> composition;

    /**
     * Constructs a composite action with the given label.
     * 
     * @param libelle the name of the composite action
     */
    public ActionComposee(String libelle) {
        super(libelle);
        this.composition = new HashMap<>();
    }

    /**
     * Adds a simple action with its percentage to the composite action.
     * 
     * @param action the simple action to add
     * @param pourcentage the percentage of this action in the composition (0-100)
     * @throws IllegalArgumentException if percentage is not between 0 and 100
     */
    public void addAction(ActionSimple action, float pourcentage) {
        if (pourcentage <= 0 || pourcentage > 100 || action == null) {
            throw new IllegalArgumentException("Le pourcentage doit être compris entre 0 et 100");
        }
        this.composition.put(action, pourcentage);
    }

    /**
     * Validates that the total percentage of all actions equals 100%.
     * 
     * @return true if total equals 100%, false otherwise
     */
    public boolean validerComposition() {
        float total = 0.0f;
        for (float pourcentage : composition.values()) {
            total += pourcentage;
        }
        return (total == 100.0f);
    }

    /**
     * Returns the composition map of simple actions with their percentages.
     * 
     * @return the composition map
     */
    public Map<ActionSimple, Float> getComposition() {
        return composition;
    }
    
    /**
     * Checks if the composite action has at least one action.
     * 
     * @return true if there is at least one action, false otherwise
     */
    public boolean hasAuMoinsUneAction() {
        return !composition.isEmpty();
    }

    /**
     * Calculates the value of the composite action for a given day.
     * 
     * @param j the day to calculate value for
     * @return the total value of the composite action
     * @throws IllegalStateException if the composition is not valid (total != 100%)
     */
    @Override
    public float valeur(Jour j) {
        if (!validerComposition()) {
            throw new IllegalStateException("La composition n'est pas valide (total != 100%)");
        }
        
        float valeurTotale = 0.0f;
        for (Map.Entry<ActionSimple, Float> entry : composition.entrySet()) {
            ActionSimple action = entry.getKey();
            float pourcentage = entry.getValue();
            valeurTotale += action.valeur(j) * (pourcentage / 100.0f);
        }
        return valeurTotale;
    }
}