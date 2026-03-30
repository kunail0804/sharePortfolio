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
    void testgetActionsDispoShouldReturnOnlyAvailableActions() {
        final ActionsList actionsDisponible = new ActionsList();
        actionsDisponible.addActionDispo(action1);
        actionsDisponible.addActionDispo(action2);
        action1.enrgCours(new Jour(DEFAULT_YEAR, DEFAULT_DAY), 10);
        assertEquals(1, actionsDisponible.getActionsDispo(new Jour(DEFAULT_YEAR, DEFAULT_DAY)).size());
        assertEquals(action1, actionsDisponible.getActionsDispo(new Jour(DEFAULT_YEAR, DEFAULT_DAY)).get(0));
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
