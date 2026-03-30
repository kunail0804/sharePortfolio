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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class UserTest {
    
    @Test
    public void testConstructorWithCorrectParametersShouldWork(){
        User user = new User("test@example.com", "password", "Doe", "John");
        List<User> users = new ArrayList<>();
        users.add(user);
        assertEquals(users, User.getUsers());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Doe", user.getName());
        assertEquals("John", user.getFirstname());
        assertEquals("password", user.getPassword());
        assertNotNull(User.getActiveUser());
        User.resetUsers();
    }

    @Test
    public void testConstructorWithInvalidEmailShouldThrowException(){
        
        assertThrows(IllegalArgumentException.class, () -> {
            User user = new User("invalid-email", "password", "Doe", "John");
        });
    
        User.resetUsers();
    }

    @Test
    public void testConstructorWithDuplicateEmailShouldThrowException(){
        User user1 = new User("test@example.com", "password", "Doe", "John");
        assertThrows(IllegalArgumentException.class, () -> {
            User user2 = new User("test@example.com", "password", "Smith", "Jane");
        });
        User.resetUsers();
    }

    @Test
    void testGetNameShouldReturnCorrectName(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertEquals("Doe", user.getName());
        User.resetUsers();
    }

    @Test
    void testGetFirstnameShouldReturnCorrectFirstname(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertEquals("John", user.getFirstname());
        User.resetUsers();
    }

    @Test
    void testGetEmailShouldReturnCorrectEmail(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertEquals("test@example.com", user.getEmail());
        User.resetUsers();
    }

    @Test
    void testGetPasswordShouldReturnCorrectPassword(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertEquals("password", user.getPassword());
        User.resetUsers();
    }

    @Test
    void testSetPasswordWithValidPasswordShouldWork(){
        User user = new User("test@example.com", "password", "Doe", "John");
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
        User.resetUsers();
    }

    @Test
    void testSetPasswordWithEmptyPasswordShouldThrowException(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("");
        });
        User.resetUsers();
    }

    @Test
    void testSetPasswordWithNullPasswordShouldThrowException(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword(null);
        });
        User.resetUsers();
    }

    @Test
    void testLoginWithCorrectCredentialsShouldWork(){
        User user = new User("test@example.com", "password", "Doe", "John");
        User user2 = new User("user2@example.com", "password2", "Smith", "Jane");
        assertEquals(user2, User.getActiveUser());
        User loggedInUser = User.login("test@example.com", "password");
        assertEquals(user, loggedInUser);
        assertEquals(user, User.getActiveUser());
        User.resetUsers();
    }

    @Test
    void testLoginWithIncorrectPasswordShouldThrowException(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertThrows(IllegalArgumentException.class, () -> {
            User.login("test@example.com", "wrongpassword");
        });
        User.resetUsers();
    }

    @Test
    void testLoginWithNonExistentEmailShouldThrowException(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertThrows(IllegalArgumentException.class, () -> {
            User.login("nonexistent@example.com", "password");
        });
        User.resetUsers();
    }

    @Test
    void testLogoutShouldSetActiveUserToNull(){
        User user = new User("test@example.com", "password", "Doe", "John");
        User.login("test@example.com", "password");
        User.logout();
        assertEquals(null, User.getActiveUser());
        User.resetUsers();
    }

    @Test
    void testToStringShouldReturnCorrectString(){
        User user = new User("test@example.com", "password", "Doe", "John");
        assertEquals("User{email=test@example.com, name=Doe, firstname=John}", user.toString());
        User.resetUsers();
    }

    @Test
    void testAjouterFavoris_ShouldAddAction() {
        User user = new User("test@example.com", "password", "Doe", "John");
        ActionSimple action = new ActionSimple("ActionTest");
        
        user.ajouterFavoris(action);
        
        assertEquals(1, user.getFavoris().size());
        assertTrue(user.getFavoris().contains(action));
        User.resetUsers();
    }

    @Test
    void testAjouterFavoris_ActionDejaEnFavoris_ShouldNotDuplicate() {
        User user = new User("test@example.com", "password", "Doe", "John");
        ActionSimple action = new ActionSimple("ActionTest");
        
        user.ajouterFavoris(action);
        assertEquals(1, user.getFavoris().size());
        
        user.ajouterFavoris(action);
        
        assertEquals(1, user.getFavoris().size());
        assertTrue(user.getFavoris().contains(action));
        User.resetUsers();
    }

    @Test
    void testRetirerFavoris_ShouldRemoveAction() {
        User user = new User("test@example.com", "password", "Doe", "John");
        ActionSimple action = new ActionSimple("ActionTest");
        
        user.ajouterFavoris(action);
        assertEquals(1, user.getFavoris().size());
        
        user.retirerFavoris(action);
        
        assertTrue(user.getFavoris().isEmpty());
        User.resetUsers();
    }

    @Test
    void testEstDejaEnFavoris() {
        User user = new User("test@example.com", "password", "Doe", "John");
        ActionSimple action = new ActionSimple("ActionTest");
        
        assertFalse(user.estDejaEnFavoris(action));
        
        user.ajouterFavoris(action);
        assertTrue(user.estDejaEnFavoris(action));
        User.resetUsers();
    }

    @Test
    void testGetFavoris_ListeBienRecuperee() {
        User user = new User("test@example.com", "password", "Doe", "John");
        ActionSimple action1 = new ActionSimple("Action1");
        ActionSimple action2 = new ActionSimple("Action2");
        ActionComposee action3 = new ActionComposee("CompositeAction");
        
        user.ajouterFavoris(action1);
        user.ajouterFavoris(action2);
        user.ajouterFavoris(action3);
        
        List<Action> favoris = user.getFavoris();
        
        assertEquals(3, favoris.size());
        assertTrue(favoris.contains(action1));
        assertTrue(favoris.contains(action2));
        assertTrue(favoris.contains(action3));
        User.resetUsers();
    }

    @Test
    void testGetFavoris_ShouldReturnCopy() {
        User user = new User("test@example.com", "password", "Doe", "John");
        ActionSimple action = new ActionSimple("ActionTest");
        
        user.ajouterFavoris(action);
        
        List<Action> favoris = user.getFavoris();
        favoris.clear();
        
        assertEquals(1, user.getFavoris().size());
        User.resetUsers();
    }
}