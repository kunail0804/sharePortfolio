package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ShareholderTest {
    @Test
    void testConstructorWithCorrectParametersShouldWork(){
        Shareholder sh = new Shareholder("test@example.com", "password", "Doe", "John");
      
        assertEquals("test@example.com", sh.getEmail());
        assertEquals("Doe", sh.getName());
        assertEquals("John", sh.getFirstname());
        assertEquals("password", sh.getPassword());
        assertNotNull(sh.getPortefeuille());

        Shareholder.resetUsers();
    }

    @Test
    void testNewAddActionShouldWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);

        Double[] data = sh.getPortefeuille().get(action);

        assertNotNull(data);
        assertEquals(100.0, data[0]);
        assertEquals(2.0, data[1]);

        Shareholder.resetUsers();
    }

    @Test
    void testAddActionExistingActionShouldWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);
        sh.addAction(action, 15.0, 3);

        Double[] data = sh.getPortefeuille().get(action);

        assertNotNull(data);
        assertEquals(115.0, data[0]);
        assertEquals(5.0, data[1]);

        Shareholder.resetUsers();
    }

    @Test
    void testAddActionNegativePriceShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, -10.0, 2);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testAddActionNotNull(){
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        Double[] listAction = null;

        assertEquals(listAction, sh.getPortefeuille().get(action));

        Shareholder.resetUsers();

    }

    @Test
    void testAddActionNullActionShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(null, 100.0, 2);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testAddInvalidQuantityShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, 100.0, 0);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testAddNegativeQuantityShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.addAction(action, 100.0, -5);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellActionShouldWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 5);
        sh.sellAction(action, 3);

        Double[] data = sh.getPortefeuille().get(action);

        assertEquals(2.0, data[1]);

        Shareholder.resetUsers();
    }

    @Test
    void testSellExactAllQuantityRemoveActionShouldWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 3);
        sh.sellAction(action, 3);

        Double[] data = sh.getPortefeuille().get(action);

        assertNull(data);

        Shareholder.resetUsers();
    }

    @Test
    void testSellTooMuchShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, 5);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellInvalidQuantityShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, 0);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellNegativeQuantityShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        sh.addAction(action, 100.0, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, -3);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellNullActionShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(null, 1);
        });

        Shareholder.resetUsers();
    }

    @Test
    void testSellNonExistingActionShouldNotWork() {
        Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
        Action action = new ActionSimple("AAPL");

        assertThrows(IllegalArgumentException.class, () -> {
            sh.sellAction(action, 1);
        });

        Shareholder.resetUsers();
    }   
    
}
