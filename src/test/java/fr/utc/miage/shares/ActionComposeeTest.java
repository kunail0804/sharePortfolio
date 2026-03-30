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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

/**
 * Test class for ActionComposee.
 * Tests composite action composition validation and value calculation.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
class ActionComposeeTest {

    private static final String FOO_SHARE1 = "Foo Share 1";
    private static final String FOO_SHARE2 = "Foo Share 2";
    private static final String FOO_SHARE3 = "Foo Share 3";
    private static final String COMPOSITE_SHARE = "Composite Share";
    
    // pourcentages
    private static final float PERCENT_35 = 35.0f;
    private static final float PERCENT_50 = 50.0f;
    private static final float PERCENT_15 = 15.0f;
    private static final float PERCENT_25 = 25.0f;
    
    // daily values
    private static final float VALUE_100 = 100.0f;
    private static final float VALUE_200 = 200.0f;
    private static final float VALUE_150 = 150.0f;
    
    private ActionSimple action1;
    private ActionSimple action2;
    private ActionSimple action3;
    private ActionComposee actionComposee;
    private Jour jour;
    
    /**
     * Initializes test objects with default values.
     * Called at the beginning of each test.
     */
    private void init() {
        action1 = new ActionSimple(FOO_SHARE1);
        action2 = new ActionSimple(FOO_SHARE2);
        action3 = new ActionSimple(FOO_SHARE3);
        actionComposee = new ActionComposee(COMPOSITE_SHARE);
        jour = new Jour(2025, 1);
        
        action1.enrgCours(jour, VALUE_100);
        action2.enrgCours(jour, VALUE_200);
        action3.enrgCours(jour, VALUE_150);
    }
    
    /**
     * Test #33: Failure when total percentage is less than 100%.
     * Verifies that composition validation fails and value calculation throws exception.
     */
  


    @Test
    void testValiderComposition_PourcentageDifferent100() {
        init();
        actionComposee.addAction(action1, PERCENT_35);
        assertFalse(actionComposee.validerComposition());
        assertThrows(IllegalStateException.class, () -> {
            actionComposee.valeur(jour);
        });
    }

    @Test
    void testValiderCompositionEgale100() {
        init();
        actionComposee.addAction(action1, PERCENT_35);
        actionComposee.addAction(action2, PERCENT_50);
        actionComposee.addAction(action3, PERCENT_15);
        assertTrue(actionComposee.validerComposition());
    }

    @Test 
    void testAddAction_PourcentagePlus100() {
        init();
        assertThrows(IllegalArgumentException.class, () -> {
            actionComposee.addAction(action1, PERCENT_35);
            actionComposee.addAction(action2, PERCENT_50);
            actionComposee.addAction(action3, PERCENT_25);
        });
    }
    @Test
    void testAddAction_ActionNull() {
        init();
        assertThrows(IllegalArgumentException.class, () -> {
            actionComposee.addAction(null, PERCENT_50);
        });
    }

    @Test
    void testAddAction_PourcentageZero() {
        init();
        assertThrows(IllegalArgumentException.class, () -> {
            actionComposee.addAction(action1, 0.0f);
        });
    }

    @Test
    void getComposition() {
        init();
        actionComposee.addAction(action1, PERCENT_35);
        actionComposee.addAction(action2, PERCENT_50);
        Map<ActionSimple, Float> composition = actionComposee.getComposition();
        assertEquals(2, composition.size());
        assertEquals(PERCENT_35, composition.get(action1));
        assertEquals(PERCENT_50, composition.get(action2));
    }
    
    
    /**
     * Test #35: Failure when composite action has no actions.
     * Verifies that hasAuMoinsUneAction returns false and value calculation throws exception.
     */
    @Test
    void testHasAuMoinsUneAction_PasDAction() {
        init();
        assertFalse(actionComposee.hasAuMoinsUneAction());
        assertThrows(IllegalStateException.class, () -> {
            actionComposee.valeur(jour);
        });
    }
    


}
    
    