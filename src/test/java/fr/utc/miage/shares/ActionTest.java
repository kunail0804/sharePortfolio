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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ActionTest {

    private static final String FOO_SHARE1 = "Foo Share 1";
    private static final String FOO_SHARE2 = "Foo Share 2";

    private ActionSimple action;
    private Jour jour1;
    private Jour jour2;

    public void initialize() {
        action = new ActionSimple("Google");
        jour1 = new Jour(2025, 10);
        jour2 = new Jour(2025, 11);
    }

    @Test
    void testGetLibelleReturnConstructorParameter() {
        final Action action = new ActionImpl(FOO_SHARE1);
        final String result = action.getLibelle();

        Assertions.assertEquals(FOO_SHARE1, result,
                "Property Libelle value should be the same as the parameter used for construction");
    }

    @Test
    void testToStringReturnConstructorParameter() {
        final Action action = new ActionImpl(FOO_SHARE1);
        final String result = action.toString();

        Assertions.assertEquals(FOO_SHARE1, result,
                "Property Libelle value should be the same as the parameter used for construction");
    }

    @Test
    void testEqualsWithSameObject() {
        final Action action = new ActionImpl(FOO_SHARE1);

        Assertions.assertEquals(action, action);
    }

    @Test
    void testEqualsWithSimilarObject() {
        final Action action1 = new ActionImpl(FOO_SHARE1);
        final Action action2 = new ActionImpl(FOO_SHARE1);

        Assertions.assertEquals(action1, action2);
    }

    @Test
    void testEqualsWithDifferentObject() {
        final Action action1 = new ActionImpl(FOO_SHARE1);
        final Action action2 = new ActionImpl(FOO_SHARE2);

        Assertions.assertNotEquals(action1, action2);
    }

    @Test
    void testEqualsWithNullObject() {
        final Action action1 = new ActionImpl(FOO_SHARE1);
        final Action action2 = null;

        Assertions.assertNotEquals(action1, action2);
    }

    @Test
    void testEqualsWithObjectFromOtherClass() {
        final Action action1 = new ActionImpl(FOO_SHARE1);
        final Integer action2 = 0;

        Assertions.assertNotEquals(action1, action2);
    }

    @Test
    void testHashCode() {
        final Action action = new ActionImpl(FOO_SHARE1);
        Assertions.assertDoesNotThrow(action::hashCode, "hashcode must always provide a value");
    }

    private static class ActionImpl extends Action {

        public ActionImpl(final String aLabel) {
            super(aLabel);
        }

        @Override
        public float valeur(final Jour aJour) {
            return 0.0F;
        }
    }

    
    @Test
    public void testGetHistoriqueCours() {
        initialize();
        
        action.enrgCours(jour1, 150.0f);
        action.enrgCours(jour2, 155.0f);

        Map<Jour, Float> historique = action.getHistoriqueCours();

        assertEquals(2, historique.size());
        assertEquals(150.0f, historique.get(jour1));
        assertEquals(155.0f, historique.get(jour2));

        Jour jour3 = new Jour(2025, 12);
        historique.put(jour3, 999.0f);

        assertEquals(2, action.getHistoriqueCours().size(), "L'encapsulation a échoué : la map interne a été modifiée par l'extérieur.");
    }

}
