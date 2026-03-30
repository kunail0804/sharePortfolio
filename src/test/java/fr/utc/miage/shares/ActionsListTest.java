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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ActionsListTest {
    private static final int DEFAULT_YEAR = 1;
    private static final int DEFAULT_DAY = 1;
    private final ActionSimple action1 = new ActionSimple("Test1");
    private final ActionSimple action2 = new ActionSimple("Test2");

    @Test
    void testConstructorShouldWork() {
        assertDoesNotThrow(ActionsList::new);
    }

    @Test
    void testaddActionDispoShouldWork() {
        final ActionsList actionsDisponible = new ActionsList();
        assertDoesNotThrow(() -> {
            actionsDisponible.addActionDispo(new ActionSimple("Test"));
        });
    }

    @Test
    void testaddActionDispoAddTwoTimesSameActionShouldThrowException() {
        final ActionsList actionsDisponible = new ActionsList();
        actionsDisponible.addActionDispo(action1);
        assertThrows(IllegalArgumentException.class, () -> actionsDisponible.addActionDispo(action1));
    }

    @Test
    void testAddActionDispoAddTwoActionsWithSameNameShouldThrowException() {
        final ActionsList actionsDisponible = new ActionsList();
        final ActionSimple actionDuplicate = new ActionSimple("Test1");
        actionsDisponible.addActionDispo(action1);
        assertThrows(IllegalArgumentException.class, () -> actionsDisponible.addActionDispo(actionDuplicate));
    }

    @Test
    void testgetActionsDispoShouldReturnOnlyAvailableActions() {
        final ActionsList actionsDisponible = new ActionsList();
        actionsDisponible.addActionDispo(action1);
        actionsDisponible.addActionDispo(action2);
        action1.enrgCours(new Jour(DEFAULT_YEAR, DEFAULT_DAY), 10);
        assertEquals(1, actionsDisponible.getActionsDispo(new Jour(DEFAULT_YEAR, DEFAULT_DAY)).size());
        assertEquals(action1, actionsDisponible.getActionsDispo(new Jour(DEFAULT_YEAR, DEFAULT_DAY)).get(0));
    }

    @Test
    void testaddActionInDispoAddTwoTimesSameActionShouldThrowException() {
        final ActionsList actionsDisponible = new ActionsList();
        actionsDisponible.addActionDispo(action1);
        assertThrows(IllegalArgumentException.class, () -> actionsDisponible.addActionDispo(action1));
    }

    @Test
    void testAddActionInDispoAddTwoActionsWithSameNameShouldThrowException() {
        final ActionsList actionsDisponible = new ActionsList();
        final ActionSimple actionDuplicate = new ActionSimple("Test1");
        actionsDisponible.addActionDispo(action1);
        assertThrows(IllegalArgumentException.class, () -> actionsDisponible.addActionDispo(actionDuplicate));
    }

    @Test
    void testgetActionsIndispoShouldReturnOnlyUnavailableActions() {
        final ActionsList actionsIndisponible = new ActionsList();
        actionsIndisponible.addActionIndispo(action1);
        actionsIndisponible.addActionIndispo(action2);
        action1.enrgCours(new Jour(DEFAULT_YEAR, DEFAULT_DAY), 10);
        assertEquals(2, actionsIndisponible.getActionsIndispo().size());
        assertEquals(action1, actionsIndisponible.getActionsIndispo().get(0));
    }

    @Test
    void testDeactivateActionShouldMoveActionToIndisponibleList() {
        final ActionsList actionsDisponible = new ActionsList();
        actionsDisponible.addActionDispo(action1);
        actionsDisponible.deactivateAction(action1);
        assertEquals(0, actionsDisponible.getActionsDispo(new Jour(DEFAULT_YEAR, DEFAULT_DAY)).size());
        assertEquals(1, actionsDisponible.getActionsIndispo().size());
        assertEquals(action1, actionsDisponible.getActionsIndispo().get(0));
    }

    @Test
    void testDeactivateActionNotInDispoShouldThrowException() {
        final ActionsList actionsDisponible = new ActionsList();
        assertThrows(IllegalArgumentException.class, () -> actionsDisponible.deactivateAction(action1)  );
    }

    @Test
    void testActivateActionShouldMoveActionToDispoList() {
        final ActionsList actionsDisponible = new ActionsList();
        actionsDisponible.addActionIndispo(action1);
        actionsDisponible.activateAction(action1);
        Jour jour = new Jour(DEFAULT_YEAR, DEFAULT_DAY);
        action1.enrgCours(jour, 10);
        assertEquals(1, actionsDisponible.getActionsDispo(jour).size());
        assertEquals(0, actionsDisponible.getActionsIndispo().size());
        assertEquals(action1, actionsDisponible.getActionsDispo(jour).get(0));
    }
    @Test
    void testActivateActionNotInIndispoShouldThrowException() {
        final ActionsList actionsDisponible = new ActionsList();
        assertThrows(IllegalArgumentException.class, () -> actionsDisponible.activateAction(action1)  );
    }

    @Test
    void testGetAllActionsShouldWork() {
        final ActionsList actionsDisponible = new ActionsList();
        actionsDisponible.addActionDispo(action1);
        actionsDisponible.addActionDispo(action2);
        assertEquals(2, actionsDisponible.getAllActions().size());  
    }

    @Test
    void testRemoveActionShouldWork() {
        final ActionsList actionsDisponible = new ActionsList();
        actionsDisponible.addActionDispo(action1);
        actionsDisponible.addActionDispo(action2);
        actionsDisponible.removeAction(action1);
        assertEquals(1, actionsDisponible.getAllActions().size());
        assertEquals(action2, actionsDisponible.getAllActions().get(0));
    }

    @Test
    void testEnrgCoursWithTwoTimesSameDateShouldThrowException() {
        final ActionSimple action = new ActionSimple("Test");
        final Jour jour = new Jour(DEFAULT_YEAR, DEFAULT_DAY);
        action.enrgCours(jour, 10);
        assertThrows(IllegalArgumentException.class, () -> action.enrgCours(jour, 20));
    }
}
