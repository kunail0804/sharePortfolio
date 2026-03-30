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

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a user of the application. It contains the user's email, password, name and firstname. It also contains a static list of all users and a static variable for the active user.
 * The constructor checks that the email is valid and unique, and adds the user to the list of users. The class also contains methods for logging in, logging out, and resetting the list of users.
 * 
 * Note : it is not an abstract even if we never instantiate it. It is because we have a list of User
 * 
 * @author Guilherme Sampaio &lt;
 */
public class User {
    // We store the list of users in a static variable to be able to manage the users of the application and to check for unique emails when creating new users. We also store the active user in a static variable to manage logins and logouts.
    private static final List<User> users = new ArrayList<>();
    
    private final String email;
    private String password;

    private final String name;
    private final String firstname;

    // We store the active user in a static variable to manage logins and logouts.
    private static User activeUser;

    public User(final String email, final String password, final String name, final String firstname){
        if (email == null || password == null || name == null || firstname == null) {
            throw new IllegalArgumentException("Les champs ne peuvent pas être vides.");
        }
        // We check that the email is valid using a regular expression. If the email is not valid, we throw an exception to indicate that the user cannot be created. We also check that the email is unique in the list of users. If the email is not unique, we throw an exception to indicate that the user cannot be created. If the email is valid and unique, we set the user's email, password, name and firstname, add the user to the list of users and set the active user to this user.
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email invalide.");
        }
        if (User.isUnique(email)){
            this.email = email;
            this.password = password;
            this.name = name;
            this.firstname = firstname;
            users.add(this);
            setActiveUser(this);
        } else{
            // If the email is not unique, we throw an exception to indicate that the user cannot be created
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }
    }

    public static List<User> getUsers() {
        return users;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User user) {
        if (user != null && !users.contains(user)) {
            throw new IllegalArgumentException("L'utilisateur doit être dans la liste des utilisateurs.");
        }
        activeUser = user;
    } 

    // Method to check if the email is unique in the list of users
    public static boolean isUnique(final String email){
        for(int i = 0; i < users.size(); i++){
            if (users.get(i).getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    // Method to log in to the application by verifying that the email and password are correct
    public static User login(final String email, final String password){
        for(int i = 0; i < users.size(); i++){
            if (users.get(i).getEmail().equals(email)){
                if (users.get(i).getPassword().equals(password)){
                    setActiveUser(users.get(i));
                    return users.get(i);
                } else{
                    throw new IllegalArgumentException("Mot de passe incorrect.");
                }
            }
        }
        throw new IllegalArgumentException("Email non trouvé.");
    }

    // Method to log out of the application by setting the active user to null
    public static void logout(){
        setActiveUser(null);
    }

    // Method to reset the list of users by clearing it and setting the active user to null
    public static void resetUsers() {
        users.clear();
        setActiveUser(null);
    }


    @Override
    public String toString() {
        return "User{" + "email=" + email + ", name=" + name + ", firstname=" + firstname + '}';
    }
}
