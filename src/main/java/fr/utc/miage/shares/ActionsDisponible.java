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

import java.util.ArrayList;
import java.util.List;

/**
 * Allow to create the available actions in the application.
 * 
 * @author Alexandre Crespin
 */
public class ActionsDisponible {

    // list of available actions
    private final ArrayList<Action> actions;

    // constructor
    public ActionsDisponible() {
        this.actions = new ArrayList<>();
    }

    // add an action to the list of available actions
    public void addAction(final Action a) {
        this.actions.add(a);
    }

    // get the list of available actions for a given day
    public List<Action> getActions(final Jour j) {
        final ArrayList<Action> actionsDisponibles = new ArrayList<>();
        for (final Action a : this.actions) {
            if (a.valeur(j) != 0) {
                actionsDisponibles.add(a);
            }
        }
        return actionsDisponibles;
    }

    // get the list of all available actions
    // not useful
    public List<Action> getAllActions() {
        return this.actions;
    }

    // remove an action from the list of available actions
    // not useful use deactivate instead
    public void removeAction(final Action a) {
        this.actions.remove(a);
    }


}
