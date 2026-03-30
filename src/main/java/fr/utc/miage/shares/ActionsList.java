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
    private static final ArrayList<Action> actionsDisponibles = new ArrayList<>();

    // list of unavailable actions
    private static final ArrayList<Action> actionsIndisponibles = new ArrayList<>();

    // add an action to the list of available actions
    public static void addActionDispo(final Action a) {
        if (actionsDisponibles.contains(a) || actionsIndisponibles.contains(a)) {
            throw new IllegalArgumentException("Action already exists in the list");
        }
        for (Action action : getAllActions()) {
            if (action.isSame(a)) {
                throw new IllegalArgumentException("Action with the same name already exists in the list");
            }
        }
        actionsDisponibles.add(a);
    }

    // get the list of available actions for a given day
    public static List<Action> getActionsDispo(final Jour j) {
        final ArrayList<Action> actionsList = new ArrayList<>();
        for (final Action a : actionsDisponibles) {
            if (a.valeur(j) != 0) {
                actionsList.add(a);
            }
        }
        return actionsList;
    }

    // add an action to the list of unavailable actions
    public static void addActionIndispo(final Action a) {
        if (actionsDisponibles.contains(a) || actionsIndisponibles.contains(a)) {
            throw new IllegalArgumentException("Action already exists in the list");
        }
        for (Action action : getAllActions()) {
            if (action.isSame(a)) {
                throw new IllegalArgumentException("Action with the same name already exists in the list");
            }
        }
        actionsIndisponibles.add(a);
    }

    // get the list of unavailable actions
    public static List<Action> getActionsIndispo() {
        return new ArrayList<>(actionsIndisponibles);
    }

    // deactivate an action and move it to the list of unavailable actions
    public static void deactivateAction(final Action a) {
        if (!actionsDisponibles.contains(a)) {
            throw new IllegalArgumentException("Action not found in available actions");
        }
        actionsDisponibles.remove(a);
        actionsIndisponibles.add(a);
    }

    // activate an action and move it to the list of available actions
    public static void activateAction(final Action a) {
        if (!actionsIndisponibles.contains(a)) {
            throw new IllegalArgumentException("Action not found in unavailable actions");
        }
        actionsIndisponibles.remove(a);
        actionsDisponibles.add(a);
    }

    // get the list of all actions (available and unavailable)
    public static List<Action> getAllActions() {
        final ArrayList<Action> allActions = new ArrayList<>();
        allActions.addAll(actionsDisponibles);
        allActions.addAll(actionsIndisponibles);
        return allActions;
    }

    // remove an action from the list of available actions
    // not useful use deactivate instead
    @Deprecated
    public static void removeAction(final Action a) {
        actionsDisponibles.remove(a);
    }

    public static void resetAll(){
        actionsDisponibles.clear();
        actionsIndisponibles.clear();
    }


}
