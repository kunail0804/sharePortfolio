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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ShareholderTest {
    @Test
    void testConstructorWithCorrectParametersShouldWork(){
        Shareholder sh = new Shareholder("test@example.com", "password", "Doe", "John");
      
        assertEquals("test@example.com", sh.getEmail());
        assertEquals("Doe", sh.getName());
        assertEquals("John", sh.getFirstname());
        assertEquals("password", sh.getPassword());
        assertNotNull(sh.getPortefeuille());

        Shareholder.resetUsers();
    }

    @Test
    void testNewAddActionShouldWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);

        action.enrgCours(new Jour(), 100);
        sh.addAction(action, new Jour(), 2);

        Integer data = sh.getPortefeuille().get(action);

        assertNotNull(data);
        assertEquals(100.0, action.valeur(new Jour()));
        assertEquals(2, data);

    }

    @Test
    void testAddActionExistingActionShouldWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);
        action.enrgCours(new Jour(), 100);

        sh.addAction(action, new Jour(), 2);
        sh.addAction(action, new Jour(), 3);

        Integer data = sh.getPortefeuille().get(action);

        assertNotNull(data);
        assertEquals(5, data);
        
    }

    @Test
    void testAddActionNegativePriceShouldNotWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, new Jour(), -2);
        });
    }

    @Test
    void testAddActionNotNull(){
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);

        Integer listAction = null;

        assertEquals(listAction, sh.getPortefeuille().get(action));
    }

    @Test
    void testAddActionNullActionShouldNotWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(null, new Jour(), 2);
        });
    }

    @Test
    void testAddInvalidQuantityShouldNotWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, new Jour(), 0);
        });

    }

    @Test
    void testAddNegativeQuantityShouldNotWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, new Jour(), -5);
        });

    }

    @Test
    void testSellActionShouldWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);
        action.enrgCours(new Jour(), 100);

        sh.addAction(action, new Jour(), 5);
        sh.sellAction(action, 3);

        Integer data = sh.getPortefeuille().get(action);

        assertEquals(2, data);
    }

    @Test
    void testSellExactAllQuantityRemoveActionShouldWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);
        action.enrgCours(new Jour(), 100);
        sh.addAction(action, new Jour(), 3);
        sh.sellAction(action, 3);

        Integer data = sh.getPortefeuille().get(action);

        assertNull(data);
    }

    @Test
    void testSellTooMuchShouldNotWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);
        action.enrgCours(new Jour(), 100);

        sh.addAction(action, new Jour(), 2);

        assertThrows(IllegalArgumentException.class, () -> sh.sellAction(action, 5));
    }

    @Test
    void testSellInvalidQuantityShouldNotWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);
        action.enrgCours(new Jour(), 100);
        sh.addAction(action, new Jour(), 2);

        assertThrows(IllegalArgumentException.class, () -> sh.sellAction(action, 0));
    }

    @Test
    void testSellNegativeQuantityShouldNotWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);
        action.enrgCours(new Jour(), 100);

        sh.addAction(action, new Jour(), 2);

        assertThrows(IllegalArgumentException.class, () -> sh.sellAction(action, -3));
    }

    @Test
    void testSellNullActionShouldNotWork() {
        Shareholder.resetUsers();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(null, 1);
        });
    }

    @Test
    void testSellNonExistingActionShouldNotWork() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action = new ActionSimple("AAPL");
        ActionsList.addActionDispo(action);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, 1);
        });
    }

    @Test
    void testGetvaleurportefeuilleactuelWithEmptyPortfolioShouldReturnZero() {
        Shareholder.resetUsers();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        assertEquals(0.0, sh.getValeurPortefeuilleActuel());
    }

    @Test
    void testGetvaleurportefeuilleactuelWithNonEmptyPortfolioShouldReturnCorrectValue() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action1 = new ActionSimple("AAPL");
        ActionSimple action2 = new ActionSimple("GOOGL");
        Jour today = new Jour();
        ActionsList.addActionDispo(action1);
        ActionsList.addActionDispo(action2);
        action1.enrgCours(today, 150f);
        action2.enrgCours(today,250f);
        sh.addAction(action1, today, 5);
        sh.addAction(action2, today, 3);
        assertEquals(150.0 * 5 + 250.0 * 3, sh.getValeurPortefeuilleActuel());
    }

    @Test
    void testGetValeurPortefeuilleActuelWithActionsBoughtInThePastShouldReturnDifferentCurrentValue() {
        Shareholder.resetUsers();
        ActionsList.resetAll();
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        ActionSimple action1 = new ActionSimple("AAPL");
        ActionSimple action2 = new ActionSimple("GOOGL");
        ActionsList.addActionDispo(action1);
        ActionsList.addActionDispo(action2);
        Jour pastDay = new Jour(2024, 1);
        action1.enrgCours(pastDay, 150f);
        action2.enrgCours(pastDay,250f);
        action1.enrgCours(new Jour(), 200f);
        action2.enrgCours(new Jour(),300f);
        sh.addAction(action1, pastDay, 5);
        sh.addAction(action2, pastDay, 3);
        assertEquals(200.0 * 5 + 300.0 * 3, sh.getValeurPortefeuilleActuel());
    }
}