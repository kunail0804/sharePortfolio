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
import org.junit.jupiter.api.Test;

class ActionsListTest {
    private static final int DEFAULT_YEAR = 1;
    private static final int DEFAULT_DAY = 1;
    private final ActionSimple action1 = new ActionSimple("Test1");
    private final ActionSimple action2 = new ActionSimple("Test2");

    @Test
    void testaddActionDispoShouldWork() {
        ActionsList.resetAll();
        assertDoesNotThrow(() -> {
            ActionsList.addActionDispo(new ActionSimple("Test"));
        });
    }

    @Test
    void testaddActionDispoAddTwoTimesSameActionShouldThrowException() {
        ActionsList.resetAll();
        ActionsList.addActionDispo(action1);
        assertThrows(IllegalArgumentException.class, () -> ActionsList.addActionDispo(action1));
    }

    @Test
    void testAddActionDispoAddTwoActionsWithSameNameShouldThrowException() {
        ActionsList.resetAll();
        final ActionSimple actionDuplicate = new ActionSimple("Test1");
        ActionsList.addActionDispo(action1);
        assertThrows(IllegalArgumentException.class, () -> ActionsList.addActionDispo(actionDuplicate));
    }

    @Test
    void testgetActionsDispoShouldReturnOnlyAvailableActions() {
        ActionsList.resetAll();
        ActionsList.addActionDispo(action1);
        ActionsList.addActionDispo(action2);
        action1.enrgCours(new Jour(DEFAULT_YEAR, DEFAULT_DAY), 10);
        assertEquals(1, ActionsList.getActionsDispo(new Jour(DEFAULT_YEAR, DEFAULT_DAY)).size());
        assertEquals(action1, ActionsList.getActionsDispo(new Jour(DEFAULT_YEAR, DEFAULT_DAY)).get(0));
    }

    @Test
    void testaddActionInDispoAddTwoTimesSameActionShouldThrowException() {
        ActionsList.resetAll();
        ActionsList.addActionDispo(action1);
        assertThrows(IllegalArgumentException.class, () -> ActionsList.addActionDispo(action1));
    }

    @Test
    void testAddActionInDispoAddTwoActionsWithSameNameShouldThrowException() {
        ActionsList.resetAll();
        final ActionSimple actionDuplicate = new ActionSimple("Test1");
        ActionsList.addActionDispo(action1);
        assertThrows(IllegalArgumentException.class, () -> ActionsList.addActionDispo(actionDuplicate));
    }

    @Test
    void testgetActionsIndispoShouldReturnOnlyUnavailableActions() {
        ActionsList.resetAll();
        ActionsList.addActionIndispo(action1);
        ActionsList.addActionIndispo(action2);
        action1.enrgCours(new Jour(DEFAULT_YEAR, DEFAULT_DAY), 10);
        assertEquals(2, ActionsList.getActionsIndispo().size());
        assertEquals(action1, ActionsList.getActionsIndispo().get(0));
    }

    @Test
    void testDeactivateActionShouldMoveActionToIndisponibleList() {
        ActionsList.resetAll();
        ActionsList.addActionDispo(action1);
        ActionsList.deactivateAction(action1);
        assertEquals(0, ActionsList.getActionsDispo(new Jour(DEFAULT_YEAR, DEFAULT_DAY)).size());
        assertEquals(1, ActionsList.getActionsIndispo().size());
        assertEquals(action1, ActionsList.getActionsIndispo().get(0));
    }

    @Test
    void testDeactivateActionNotInDispoShouldThrowException() {
        ActionsList.resetAll();

        assertThrows(IllegalArgumentException.class, () -> ActionsList.deactivateAction(action1)  );
    }

    @Test
    void testActivateActionShouldMoveActionToDispoList() {
        ActionsList.resetAll();
        ActionsList.addActionIndispo(action1);
        ActionsList.activateAction(action1);
        Jour jour = new Jour(DEFAULT_YEAR, DEFAULT_DAY);
        action1.enrgCours(jour, 10);
        assertEquals(1, ActionsList.getActionsDispo(jour).size());
        assertEquals(0, ActionsList.getActionsIndispo().size());
        assertEquals(action1, ActionsList.getActionsDispo(jour).get(0));
    }
    @Test
    void testActivateActionNotInIndispoShouldThrowException() {
        ActionsList.resetAll();
        assertThrows(IllegalArgumentException.class, () -> ActionsList.activateAction(action1)  );
    }

    @Test
    void testGetAllActionsShouldWork() {
        ActionsList.resetAll();
        ActionsList.addActionDispo(action1);
        ActionsList.addActionDispo(action2);
        assertEquals(2, ActionsList.getAllActions().size());
    }

    @Test
    void testRemoveActionShouldWork() {
        ActionsList.resetAll();
        ActionsList.addActionDispo(action1);
        ActionsList.addActionDispo(action2);
        ActionsList.removeAction(action1);
        assertEquals(1, ActionsList.getAllActions().size());
        assertEquals(action2, ActionsList.getAllActions().get(0));
    }

    @Test
    void testEnrgCoursWithTwoTimesSameDateShouldThrowException() {
        ActionsList.resetAll();
        final ActionSimple action = new ActionSimple("Test");
        final Jour jour = new Jour(DEFAULT_YEAR, DEFAULT_DAY);
        action.enrgCours(jour, 10);
        assertThrows(IllegalArgumentException.class, () -> action.enrgCours(jour, 20));
    }
}
