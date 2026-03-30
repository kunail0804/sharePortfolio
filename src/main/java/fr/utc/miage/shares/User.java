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
 * @author Guilherme Sampaio &lt;
 */
public class User {
    // On stocke tous les utilisateurs dans une liste statique pour pouvoir vérifier l'unicité des emails et gérer les connexions
    private static final List<User> users = new ArrayList<>();
    
    private final String email;
    private String password;

    private final String name;
    private final String firstname;

    // On stocke l'utilisateur actif dans une variable statique pour pouvoir gérer les connexions et déconnexions
    private static User activeUser;

    public User(final String email, final String password, final String name, final String firstname){
        // On vérifie que l'email est au format valide avec une regex
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
            // Si l'email n'est pas unique, on lance une exception pour indiquer que l'utilisateur ne peut pas être créé
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
        activeUser = user;
    } 

    // Méthode pour vérifier que l'email est unique dans la liste des utilisateurs
    public static boolean isUnique(final String email){
        for(int i = 0; i < users.size(); i++){
            if (users.get(i).getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }

    // Méthode pour se connecter à l'application en vérifiant que l'email et le mot de passe sont corrects
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

    // Méthode pour se déconnecter de l'application en mettant l'utilisateur actif à null
    public static void logout(){
        setActiveUser(null);
    }

    // Méthode pour réinitialiser la liste des utilisateurs en la vidant et en mettant l'utilisateur actif à null
    public static void resetUsers() {
        users.clear();
        setActiveUser(null);
    }


    @Override
    public String toString() {
        return "User{" + "email=" + email + ", name=" + name + ", firstname=" + firstname + '}';
    }
}
