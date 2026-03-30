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
 * Allows the creation of simple Action objects.
 * A simple action has daily prices recorded for specific days.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
public class ActionSimple extends Action {

    private static final int DEFAULT_ACTION_VALUE = 0;

    // map of the value of the action for each day
    private final Map<Jour, Float> mapCours;

    /**
     * Constructs a simple action with the given label.
     *
     * @param libelle the name of the simple action
     */
    public ActionSimple(final String libelle) {
        // call the constructor of the superclass
        super(libelle);
        // initialize the map of the value of the action for each day
        this.mapCours = new HashMap<>();
    }

    /**
     * Records the price of the action for a given day.
     * Only records if no price has been recorded for that day yet.
     *
     * @param j the day to record the price for
     * @param v the price value to record
     */
    public void enrgCours(final Jour j, final float v) {
        if (v <= 0) {
            throw new IllegalArgumentException("La valeur doit être strictement positive");
        }
        if (!this.mapCours.containsKey(j)) {
            this.mapCours.put(j, v);
        }
        else {
            throw new IllegalArgumentException("Cours déjà enregistré pour ce jour");
        }
    }

    /**
     * Calculates the value of the action for a given day.
     * Returns the recorded price if it exists, otherwise returns 0.
     *
     * @param j the day to calculate value for
     * @return the value of the action on that day, or 0 if not recorded
     */
    @Override
    public float valeur(final Jour j) {
        if (this.mapCours.containsKey(j)) {
            return this.mapCours.get(j);
        } else {
            return DEFAULT_ACTION_VALUE;
        }
    }
}