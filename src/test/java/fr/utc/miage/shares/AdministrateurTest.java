package fr.utc.miage.shares;

import org.junit.jupiter.api.Test;

public class AdministrateurTest {
    @Test
    void testLoginWithValidLoginAndValidPassword() {
        Administrateur admin = new Administrateur("admin", "admin");
        assertNotThrows(() -> admin.login("admin", "admin"));
    }

    @Test
    void testLoginWithValidLoginAndInvalidPassword() {
        Administrateur admin = new Administrateur("admin", "admin");
        assertThrows(IllegalArgumentException.class, () -> admin.login("admin", "wrongpassword"));
    }

    @Test
    void testLoginWithInvalidLogin() {
        Administrateur admin = new Administrateur("admin", "admin");
        assertThrows(IllegalArgumentException.class, () -> admin.login("wronglogin", "admin"));
    }

}
