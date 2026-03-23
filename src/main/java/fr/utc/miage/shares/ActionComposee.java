/*
 * Copyright 2025 David Navarre &lt;David.Navarre at irit.fr&gt;.
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

import java.util.List;

/**
 * This class allows to create composite actions, which are composed of several simple actions.
 * After the creation of a composite action, it is not possible to add or remove simple actions from it.
 * 
 * @author Alexandre Crespin
 */
public class ActionComposee extends Action {

    private static final int DEFAULT_ACTION_VALUE = 0;

    private final List<ActionSimple> actionsSimples;

    public ActionComposee(final String libelle, List<ActionSimple> actionsSimples) {
        super(libelle);
        this.actionsSimples = actionsSimples;
    }

    public List<ActionSimple> getActionsSimples() {
        return actionsSimples;
    }

    public float getValeur(final Jour j, final ActionSimple a) {
        return a.valeur(j);
    }

    // get the list of values of the simple actions for a given day
    public List<Float> getValeurs(final Jour j) {
        return this.actionsSimples.stream().map(a -> a.valeur(j)).toList();
    }

    // Don't use this function, it is not useful, use getValeurs instead
    @Deprecated
    @Override
    public float valeur(Jour j) {
        throw new UnsupportedOperationException("Unimplemented method 'valeur'");
    }

}
