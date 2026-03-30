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

    private Shareholder shareholder;
    private ActionSimple apple;
    private ActionSimple tesla;
    private Jour jourEvaluation;

    public void initialize() {
        User.resetUsers();
        
        shareholder = new Shareholder("test@irit.fr", "password123", "Doe", "John");
        apple = new ActionSimple("Apple");
        tesla = new ActionSimple("Tesla");
        jourEvaluation = new Jour(2023, 10);
    }


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
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);

        Double[] data = sh.getPortefeuille().get(action);

        assertNotNull(data);
        assertEquals(100.0, data[0]);
        assertEquals(2.0, data[1]);

        Shareholder.resetUsers();
    }

    @Test
    void testAddActionExistingActionShouldWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);
        sh.addAction(action, 15.0, 3);

        Double[] data = sh.getPortefeuille().get(action);

        assertNotNull(data);
        assertEquals(115.0, data[0]);
        assertEquals(5.0, data[1]);

        Shareholder.resetUsers();
    }

    @Test
    void testAddActionNegativePriceShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, -10.0, 2);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testAddActionNotNull(){
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        Double[] listAction = null;

        assertEquals(listAction, sh.getPortefeuille().get(action));

        Shareholder.resetUsers();

    }

    @Test
    void testAddActionNullActionShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(null, 100.0, 2);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testAddInvalidQuantityShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, 100.0, 0);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testAddNegativeQuantityShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, 100.0, -5);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellActionShouldWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 5);
        sh.sellAction(action, 3);

        Double[] data = sh.getPortefeuille().get(action);

        assertEquals(2.0, data[1]);

        Shareholder.resetUsers();
    }

    @Test
    void testSellExactAllQuantityRemoveActionShouldWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 3);
        sh.sellAction(action, 3);

        Double[] data = sh.getPortefeuille().get(action);

        assertNull(data);

        Shareholder.resetUsers();
    }

    @Test
    void testSellTooMuchShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, 5);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellInvalidQuantityShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, 0);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellNegativeQuantityShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, -3);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellNullActionShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(null, 1);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellNonExistingActionShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, 1);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testGetBalanceForAction_Profit() {
        initialize();

        shareholder.addAction(apple, 1000.0, 10);
        
        apple.enrgCours(jourEvaluation, 120.0f);
        
        double balance = shareholder.getBalanceForAction(apple, jourEvaluation);
        assertEquals(200.0, balance, 0.001, "Le calcul de la plus-value (profit) est incorrect.");
    }

    @Test
    void testGetBalanceForAction_Loss() {
        initialize();

        shareholder.addAction(tesla, 2000.0, 5);

        tesla.enrgCours(jourEvaluation, 300.0f);
        
        double balance = shareholder.getBalanceForAction(tesla, jourEvaluation);
        assertEquals(-500.0, balance, 0.001, "Le calcul de la moins-value (perte) est incorrect.");
    }

    @Test
    void testGetTotalBalance() {
        initialize();

        shareholder.addAction(apple, 1000.0, 10);
        shareholder.addAction(tesla, 2000.0, 5);
        
        apple.enrgCours(jourEvaluation, 120.0f);
        tesla.enrgCours(jourEvaluation, 350.0f);
        
        double totalBalance = shareholder.getTotalBalance(jourEvaluation);
        assertEquals(-50.0, totalBalance, 0.001, "Le calcul de la balance totale du portefeuille est incorrect.");
    }

    @Test
    void testGetBalanceForAction_Exceptions() {
        initialize();

        assertThrows(IllegalArgumentException.class, () -> {
            shareholder.getBalanceForAction(null, jourEvaluation);
        }, "Devrait lever une exception si l'action est null.");

        assertThrows(IllegalArgumentException.class, () -> {
            shareholder.getBalanceForAction(apple, jourEvaluation);
        }, "Devrait lever une exception si l'action n'est pas dans le portefeuille.");
    }
    
}
