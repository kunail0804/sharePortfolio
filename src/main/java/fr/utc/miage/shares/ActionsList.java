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
public class ActionsList {

    // list of available actions
    private final ArrayList<Action> actionsDisponibles;

    // list of unavailable actions
    private final ArrayList<Action> actionsIndisponibles;

    // constructor
    public ActionsList() {
        this.actionsDisponibles = new ArrayList<>();
        this.actionsIndisponibles = new ArrayList<>();
    }

    // add an action to the list of available actions
    public void addActionDispo(final Action a) {
        if (a == null || this.actionsDisponibles.contains(a) || this.actionsIndisponibles.contains(a)) {
            throw new IllegalArgumentException("Action already exists in the list");
        }
        for (Action action : this.getAllActions()) {
            if (action.isSame(a)) {
                throw new IllegalArgumentException("Action with the same name already exists in the list");
            }
        }
        this.actionsDisponibles.add(a);
    }

    // get the list of available actions for a given day
    public List<Action> getActionsDispo(final Jour j) {
        if (j == null) {
            throw new IllegalArgumentException("Le jour ne peut pas être null");
        }
        final ArrayList<Action> actionsList = new ArrayList<>();
        for (final Action a : this.actionsDisponibles) {
            if (a.valeur(j) != 0) {
                actionsList.add(a);
            }
        }
        return actionsList;
    }

    // add an action to the list of unavailable actions
    public void addActionIndispo(final Action a) {
        if (a == null || this.actionsDisponibles.contains(a) || this.actionsIndisponibles.contains(a)) {
            throw new IllegalArgumentException("Action already exists in the list");
        }
        for (Action action : this.getAllActions()) {
            if (action.isSame(a)) {
                throw new IllegalArgumentException("Action with the same name already exists in the list");
            }
        }
        this.actionsIndisponibles.add(a);
    }

    // get the list of unavailable actions
    public List<Action> getActionsIndispo() {
        return new ArrayList<>(this.actionsIndisponibles);
    }

    // verifie if an action is in the disponible list
    public boolean isActionDispo(final Action a) {
        if (a == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        return this.actionsDisponibles.contains(a);
    }

    // deactivate an action and move it to the list of unavailable actions
    public void deactivateAction(final Action a) {
        if (a == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        if (!this.actionsDisponibles.contains(a)) {
            throw new IllegalArgumentException("Action not found in available actions");
        }
        this.actionsDisponibles.remove(a);
        this.actionsIndisponibles.add(a);
    }

    // activate an action and move it to the list of available actions
    public void activateAction(final Action a) {
        if (a == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        if (!this.actionsIndisponibles.contains(a)) {
            throw new IllegalArgumentException("Action not found in unavailable actions");
        }
        this.actionsIndisponibles.remove(a);
        this.actionsDisponibles.add(a);
    }

    // get the list of all actions (available and unavailable)
    public List<Action> getAllActions() {
        final ArrayList<Action> allActions = new ArrayList<>();
        allActions.addAll(this.actionsDisponibles);
        allActions.addAll(this.actionsIndisponibles);
        return allActions;
    }

    // remove an action from the list of available actions
    // not useful use deactivate instead
    @Deprecated
    public void removeAction(final Action a) {
        if (a == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        if (!this.actionsDisponibles.contains(a)) {
            throw new IllegalArgumentException("Action not found in available actions");
        }
        this.actionsDisponibles.remove(a);
    }


}
