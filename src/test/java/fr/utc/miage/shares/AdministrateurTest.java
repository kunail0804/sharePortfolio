/*
 * Copyright 2025 David Navarre <David.Navarre at irit.fr>.
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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for Administrateur functionality.
 * Tests composite action creation, action labels, price recording,
 * administrator login, and action activation/deactivation.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
public class AdministrateurTest {
    
    private Administrateur admin = new Administrateur("admin", "admin");
    private Jour jour = new Jour(0, 0);

    private static final String ACTION1_LIBELLE = "Action1";
    private static final String ACTION2_LIBELLE = "Action2";
    private static final String COMPOSITE_LIBELLE = "CompositeAction";

    private static final float PERCENT_50 = 50.0f;
    private static final float VALUE_100 = 100.0f;
    private static final float VALUE_200 = 200.0f;

    /**
     * Tests creation of a composite action with two simple actions.
     * Verifies that the composite action value is calculated correctly.
     * Expected value: (100 * 0.5) + (200 * 0.5) = 150
     */
    @Test
    void testCreateCompositeAction() {
        ActionSimple action1 = new ActionSimple(ACTION1_LIBELLE);
        ActionSimple action2 = new ActionSimple(ACTION2_LIBELLE);
        ActionComposee compositeAction = new ActionComposee(COMPOSITE_LIBELLE);
        Jour jour = new Jour(0,0);
        
        compositeAction.addAction(action1, PERCENT_50);
        compositeAction.addAction(action2, PERCENT_50);
        
        action1.enrgCours(jour, VALUE_100);
        action2.enrgCours(jour, VALUE_200);

        assertEquals(150, compositeAction.valeur(jour));
    }

    /**
     * Tests that getLibelle returns the correct label for an action.
     */
    @Test
    void testLibelleAction() {
        ActionSimple action = new ActionSimple("ActionTest");
        assertEquals("ActionTest", action.getLibelle());
    }

    /**
     * Tests that creating an action with an invalid (empty) label throws an exception.
     */
    @Test
    void testLibelleWithInvalidLibelle() {
        String INVALID_LIBELLE = "";
        assertThrows(IllegalArgumentException.class, () -> new ActionSimple(INVALID_LIBELLE));
    }

    /**
     * Tests that creating an action with a null label throws an exception.
     */
    @Test
    void testLibelleWithNullLibelle() {
        String NULL_LIBELLE = null;
        assertThrows(IllegalArgumentException.class, () -> new ActionSimple(NULL_LIBELLE));
    }

    /**
     * Tests that creating two actions with the same label throws an exception.
     */
    @Test
    void testLibelleWithLibelleNotUnique() {
        ActionSimple action1 = new ActionSimple("ActionTest");
        assertThrows(IllegalArgumentException.class, () -> new ActionSimple(action1.getLibelle()), "Libelle doit être unique");
    }

    /**
     * Tests that recording a negative price value throws an exception.
     */
    @Test
    void testEnregistrerCoursWithNegatifValue() {
        ActionSimple action = new ActionSimple("ActionTest");
        assertThrows(IllegalArgumentException.class, () -> action.enrgCours(jour, -100));
    }

    /**
     * Tests that recording a valid price value does not throw an exception.
     */
    @Test
    void testEnregistrerCoursWithValidValue() {
        ActionSimple action = new ActionSimple("ActionTest");
        assertDoesNotThrow(() -> action.enrgCours(jour, 100));
    }

    /**
     * Tests that administrator login succeeds with valid credentials.
     */
    @Test
    void testLoginWithValidLoginAndValidPassword() {
        assertDoesNotThrow(() -> admin.login("admin", "admin"));
    }

    /**
     * Tests that administrator login fails with valid login but invalid password.
     */
    @Test
    void testLoginWithValidLoginAndInvalidPassword() {
        assertThrows(IllegalArgumentException.class, () -> admin.login("admin", "wrongpassword"));
    }

    /**
     * Tests that administrator login fails with invalid login.
     */
    @Test
    void testLoginWithInvalidLogin() {
        assertThrows(IllegalArgumentException.class, () -> admin.login("wronglogin", "admin"));
    }

    /**
     * Tests calculation of composite action value.
     * Verifies that the composite action correctly calculates weighted average.
     * Expected value: (100 * 0.5) + (200 * 0.5) = 150
     */
    @Test
    void testCalculerValeurActionComposee() {
        ActionSimple action1 = new ActionSimple(ACTION1_LIBELLE);
        ActionSimple action2 = new ActionSimple(ACTION2_LIBELLE);
        ActionComposee compositeAction = new ActionComposee(COMPOSITE_LIBELLE);
        
        compositeAction.addAction(action1, PERCENT_50);
        compositeAction.addAction(action2, PERCENT_50);
        
        action1.enrgCours(jour, VALUE_100);
        action2.enrgCours(jour, VALUE_200);

        assertEquals(150, compositeAction.valeur(jour));
    }

    /**
     * Tests deactivating an action.
     * Verifies that isEtat_action returns false after deactivation.
     */
    @Test
    void testDesactiverAction() {
        ActionSimple action = new ActionSimple("ActionTest");
        action.setEtat_action(false);
        assertFalse(action.isEtat_action());
    }

    /**
     * Tests activating an action.
     * Verifies that isEtat_action returns true after activation.
     */
    @Test
    void testActiverAction(){
        ActionSimple action = new ActionSimple("ActionTest");
        action.setEtat_action(true);
        assertTrue(action.isEtat_action());
    }
}