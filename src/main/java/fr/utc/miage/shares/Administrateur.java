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

/**
 * Represents an administrator user with special privileges.
 * Extends the base Utilisateur class.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
public class Administrateur extends User {

    /**
     * Constructs an administrator
     *
     * @param email 
     * @param password
     * @param name
     * @param firstname
     */
    public Administrateur(final String email, final String password, final String name, final String firstname) {
        super(email, password, name, firstname);
    }
}