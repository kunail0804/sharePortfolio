/*
 * Copyright 2025 David Navarre <David.Navarre at irit.fr>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.utc.miage.shares;

import java.util.Objects;

/**
 * This class embeds the common behavior of any Action object.
 *
 * @author David Navarre <David.Navarre at irit.fr>
 */
public abstract class Action {

    private final String libelle;

    /**
     * Get the value of libelle.
     *
     * @return the value of libelle
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Builds an Action object from a string parameter.
     *
     * @param libelle the name of the action object
     * @throws IllegalArgumentException if the libelle is null or empty
     */
    protected Action(final String libelle) {
        if (libelle == null || libelle.trim().isEmpty()) {
            throw new IllegalArgumentException("Libelle cannot be null or empty");
        }

        this.libelle = libelle;
    }

    /**
     * Provides the value of the action object for a given day.
     *
     * @param j the day for which to calculate the value
     * @return the calculated value of the action for the specified day
     */
    public abstract float valeur(Jour j);

    /**
     * Checks if the action object is the same as another one.
     * @param a
     * @return
     */
    public boolean isSame(final Action a) {
        return this.getLibelle().equals(a.getLibelle());
    }

    /**
     * Returns a hash code value for the action object.
     * 
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.libelle);
        return hash;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two actions are considered equal if they are of the same class 
     * and have the exact same libelle.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Action other = (Action) obj;
        return Objects.equals(this.libelle, other.libelle);
    }

    /**
     * Returns a string representation of the action.
     *
     * @return the libelle of the action
     */
    @Override
    public String toString() {
        return this.getLibelle();
    }
}