/*
 * Copyright 2024 David Navarre <David.Navarre at irit.fr>.
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Test class for Jour.
 * Tests constructor validation, accessors, hashCode, equals, and toString methods.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
class JourTest {

    private static final int DEFAULT_YEAR = 1;
    private static final int DEFAULT_DAY = 1;
    private static final int INVALID_YEAR = 0;
    private static final int INVALID_DAY = 0;

    /**
     * Tests constructor behavior with valid and invalid parameters.
     * Verifies that valid parameters do not throw exceptions and invalid parameters throw IllegalArgumentException.
     */
    @Test
    void testAllConstructorUsage() {
        Assertions.assertAll("Group of constructor tests",
                () -> assertDoesNotThrow(() -> {
                    new Jour(DEFAULT_YEAR, DEFAULT_DAY);
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new Jour(DEFAULT_YEAR, INVALID_DAY);
                }, "The day argument should be strictly more than 0"),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new Jour(INVALID_YEAR, DEFAULT_DAY);
                }, "The year argument should be strictly more than 0")
        );
    }

    /**
     * Tests that getDay and getYear return the values used during construction.
     */
    @Test
    void testAccessorShouldWork() {
        final Jour jour = getDefaultJour();
        final int resultDay = jour.getDay();
        final int resultYear = jour.getYear();
        Assertions.assertAll("Grouped assertions on accessors",
                () -> assertEquals(DEFAULT_DAY, resultDay, "Day should be the one used for creation"),
                () -> assertEquals(DEFAULT_YEAR, resultYear, "Year should be the one used for creation"));
    }

    /**
     * Tests that hashCode does not throw any exception.
     */
    @Test
    void testHashCode() {
        final Jour jour = getDefaultJour();
        assertDoesNotThrow(jour::hashCode, "hashcode must always provide a value");
    }

    /**
     * Tests that two Jour objects with the same year and day are equal.
     */
    @Test
    void testEqualsWithSameObjectShouldWork() {
        final Jour jour1 = getDefaultJour();
        final Jour jour2 = getDefaultJour();

        assertEquals(jour1, jour2, "Objects Jour with the same day and year should be equals");
    }

    /**
     * Tests that a Jour object is equal to itself.
     */
    @Test
    void testEqualsWithEqualObjectShouldWork() {
        final Jour jour1 = getDefaultJour();

        assertEquals(jour1, jour1, "An Object Jour should be equals to itself");
    }

    /**
     * Tests that two Jour objects with different days are not equal.
     */
    @Test
    void testNotEqualsWithDifferentDaysShouldWork() {
        final Jour jour1 = getDefaultJour();
        final Jour jour2 = new Jour(DEFAULT_YEAR, DEFAULT_DAY + 1);

        assertNotEquals(jour1, jour2, "Objects Jour with different days should not be equals");
    }

    /**
     * Tests that two Jour objects with different years are not equal.
     */
    @Test
    void testNotEqualsWithDifferentYearsShouldWork() {
        final Jour jour1 = getDefaultJour();
        final Jour jour2 = new Jour(DEFAULT_YEAR + 1, DEFAULT_DAY);

        assertNotEquals(jour1, jour2, "Objects Jour with different years should not be equals");
    }

    /**
     * Tests that two Jour objects with different years and days are not equal.
     */
    @Test
    void testNotEqualsWithDifferentYearsAndDaysShouldWork() {
        final Jour jour1 = getDefaultJour();
        final Jour jour2 = new Jour(DEFAULT_YEAR + 1, DEFAULT_DAY + 1);

        assertNotEquals(jour1, jour2, "Objects Jour with different years and days should not be equals");
    }

    /**
     * Tests that a Jour object is not equal to null.
     */
    @Test
    void testNotEqualsWithNullObjectShouldWork() {
        final Jour jour1 = getDefaultJour();
        final Jour jour2 = null;

        assertNotEquals(jour1, jour2, "An Object Jour cannot be equals to null");
    }

    /**
     * Tests that a Jour object is not equal to an object of a different class.
     */
    @Test
    void testNotEqualsWithDifferentClassShouldWork() {
        final Jour jour1 = getDefaultJour();
        final Integer jour2 = 0;

        assertNotEquals(jour1, jour2, "Objects Jour cannot be equals to objects from another class");
    }

    /**
     * Tests that toString returns the expected format.
     * Expected format: "Jour [year=X, day=Y]"
     */
    @Test
    void testToStringWithDedicatedLayout() {
        final Jour jour = getDefaultJour();

        final String result = jour.toString();
        final String expected = "Jour [year=" + DEFAULT_YEAR + ", day=" + DEFAULT_DAY + "]";

        assertEquals(expected, result,
                "ToString should be format as \"Jour [year=\" + year + \", day=\" + day + \"]\"");
    }

    /**
     * Creates a Jour object with default year and day.
     *
     * @return a Jour object with DEFAULT_YEAR and DEFAULT_DAY
     */
    private Jour getDefaultJour() {
        return new Jour(DEFAULT_YEAR, DEFAULT_DAY);
    }
}