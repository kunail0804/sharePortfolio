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

import java.util.List;

import org.junit.jupiter.api.Test;

class ActionComposeeTest {
    private final ActionSimple action1 = new ActionSimple("Test1");
    private final ActionSimple action2 = new ActionSimple("Test2");

    @Test
    void testConstructorShouldWork() {
        assertDoesNotThrow(() -> {
            new ActionComposee("Test", List.of(action1, action2));
        });
    }

    @Test
    void testGetActionsSimplesShouldReturnTheListOfActionsSimples() {
        final ActionComposee actionComposee = new ActionComposee("Test", List.of(action1, action2));
        assertEquals(List.of(action1, action2), actionComposee.getActionsSimples());
    }

    @Test
    void testValeurShouldThrowException() {
        final ActionComposee actionComposee = new ActionComposee("Test", List.of(action1, action2));
        assertThrows(UnsupportedOperationException.class, () -> {
            actionComposee.valeur(new Jour(1,1));
        });
    }

    @Test
    void testGetValeursShouldReturnTheListOfValuesOfTheSimpleActions() {
        final ActionComposee actionComposee = new ActionComposee("Test", List.of(action1, action2));
        assertEquals(List.of(0f, 0f), actionComposee.getValeurs(new Jour(1,1)));
    }
}
