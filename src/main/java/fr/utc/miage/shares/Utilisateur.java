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
 * Represents a basic user of the application.
 * This class handles user authentication and role management.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
public class Utilisateur {
    
    /** The user's login identifier. */
    private String login;
    
    /** The user's password. */
    private String password;
    
    /** The user's role (USER or ADMIN). */
    protected Role role;

    /**
     * Constructs a new user with the given login and password.
     *
     * @param login the user's login identifier
     * @param password the user's password
     */
    public Utilisateur(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Returns the user's login.
     *
     * @return the user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the user's login.
     *
     * @param login the new login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Returns the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's role.
     *
     * @return the user's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     *
     * @param role the new role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }
}