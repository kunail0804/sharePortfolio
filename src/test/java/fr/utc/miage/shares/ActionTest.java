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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for Action abstract class.
 * Tests getter, toString, equals, hashCode methods.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
class ActionTest {

    private static final String FOO_SHARE1 = "Foo Share 1";
    private static final String FOO_SHARE2 = "Foo Share 2";

    /**
     * Tests that getLibelle returns the same value as the constructor parameter.
     */
    @Test
    void testGetLibelleReturnConstructorParameter() {
        final Action action = new ActionImpl(FOO_SHARE1);
        final String result = action.getLibelle();

        Assertions.assertEquals(FOO_SHARE1, result,
                "Property Libelle value should be the same as the parameter used for construction");
    }

    /**
     * Tests that toString returns the libelle value.
     */
    @Test
    void testToStringReturnConstructorParameter() {
        final Action action = new ActionImpl(FOO_SHARE1);
        final String result = action.toString();

        Assertions.assertEquals(FOO_SHARE1, result,
                "Property Libelle value should be the same as the parameter used for construction");
    }

    /**
     * Tests that equals returns true when comparing the same object.
     */
    @Test
    void testEqualsWithSameObject() {
        final Action action = new ActionImpl(FOO_SHARE1);

        Assertions.assertEquals(action, action);
    }

    /**
     * Tests that equals returns true for two actions with the same libelle.
     */
    @Test
    void testEqualsWithSimilarObject() {
        final Action action1 = new ActionImpl(FOO_SHARE1);
        final Action action2 = new ActionImpl(FOO_SHARE1);

        Assertions.assertEquals(action1, action2);
    }

    /**
     * Tests that equals returns false for two actions with different libelles.
     */
    @Test
    void testEqualsWithDifferentObject() {
        final Action action1 = new ActionImpl(FOO_SHARE1);
        final Action action2 = new ActionImpl(FOO_SHARE2);

        Assertions.assertNotEquals(action1, action2);
    }

    /**
     * Tests that equals returns false when comparing with null.
     */
    @Test
    void testEqualsWithNullObject() {
        final Action action1 = new ActionImpl(FOO_SHARE1);
        final Action action2 = null;

        Assertions.assertNotEquals(action1, action2);
    }

    /**
     * Tests that equals returns false when comparing with an object of a different class.
     */
    @Test
    void testEqualsWithObjectFromOtherClass() {
        final Action action1 = new ActionImpl(FOO_SHARE1);
        final Integer action2 = 0;

        Assertions.assertNotEquals(action1, action2);
    }

    /**
     * Tests that hashCode does not throw any exception.
     */
    @Test
    void testHashCode() {
        final Action action = new ActionImpl(FOO_SHARE1);
        Assertions.assertDoesNotThrow(action::hashCode, "hashcode must always provide a value");
    }

    /**
     * Concrete implementation of Action for testing purposes.
     */
    private static class ActionImpl extends Action {

        /**
         * Constructs an ActionImpl with the given label.
         *
         * @param aLabel the label of the action
         */
        public ActionImpl(final String aLabel) {
            super(aLabel);
        }

        /**
         * Returns the value of the action for a given day.
         * For testing purposes, always returns 0.
         *
         * @param aJour the day to calculate value for
         * @return always 0
         */
        @Override
        public float valeur(final Jour aJour) {
            return 0.0F;
        }
    }

    /**
     * Tests that the default state of an action is inactive (false).
     */
    @Test
    void testEtatActionDefaultValue() {
        final Action action = new ActionImpl(FOO_SHARE1);
        Assertions.assertFalse(action.isActive(), "Default value of etatAction should be false");
    }

    /**
     * Tests that the state of an action can be successfully updated using setEtatAction.
     */
    @Test
    void testSetEtatAction() {
        final Action action = new ActionImpl(FOO_SHARE1);
        action.setEtatAction(true);
        Assertions.assertTrue(action.isActive(), "After setting etatAction to true, isActive should return true");
    }

}