package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ShareholderTest {
    @Test
    public void testConstructorWithCorrectParametersShouldWork(){
        Shareholder portefeuille = new Shareholder("test@example.com", "password", "Doe", "John");
      
        assertEquals("test@example.com", portefeuille.getEmail());
        assertEquals("Doe", portefeuille.getName());
        assertEquals("John", portefeuille.getFirstname());
        assertEquals("password", portefeuille.getPassword());
        assertNotNull(portefeuille.getPortefeuille());
    }

@Test
void testAddActionShouldWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    sh.addAction(action, 100.0, 2);

    Double[] data = sh.getPortefeuille().get(action);

    assertNotNull(data);
    assertEquals(100.0, data[0]);
    assertEquals(2.0, data[1]);
}

@Test
void testAddActionNegativePriceShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    assertThrows(IllegalArgumentException.class, () -> {
        sh.addAction(action, -10.0, 2);
    });
}

@Test
void testAddActionNullActionShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");

    assertThrows(IllegalArgumentException.class, () -> {
        sh.addAction(null, 100.0, 2);
    });
}

@Test
void testAddInvalidQuantityShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    assertThrows(IllegalArgumentException.class, () -> {
        sh.addAction(action, 100.0, 0);
    });
}

@Test
void testAddNegativeQuantityShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    assertThrows(IllegalArgumentException.class, () -> {
        sh.addAction(action, 100.0, -5);
    });
}

@Test
void testSellActionShouldWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    sh.addAction(action, 100.0, 5);
    sh.sellAction(action, 3);

    Double[] data = sh.getPortefeuille().get(action);

    assertEquals(2.0, data[1]);
}

@Test
void testSellExactQuantityShouldWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    sh.addAction(action, 100.0, 3);
    sh.sellAction(action, 3);

    Double[] data = sh.getPortefeuille().get(action);

    assertEquals(0.0, data[1]);
}

@Test
void testSellTooMuchShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    sh.addAction(action, 100.0, 2);

    assertThrows(IllegalArgumentException.class, () -> {
        sh.sellAction(action, 5);
    });
}

@Test
void testSellInvalidQuantityShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    sh.addAction(action, 100.0, 2);

    assertThrows(IllegalArgumentException.class, () -> {
        sh.sellAction(action, 0);
    });
}

@Test
void testSellNegativeQuantityShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    sh.addAction(action, 100.0, 2);

    assertThrows(IllegalArgumentException.class, () -> {
        sh.sellAction(action, -3);
    });
}

@Test
void testSellNullActionShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");

    assertThrows(IllegalArgumentException.class, () -> {
        sh.sellAction(null, 1);
    });
}

@Test
void testSellNonExistingActionShouldNotWork() {
    Shareholder sh = new Shareholder("test@mail.com", "pwd", "Doe", "John");
    Action action = new ActionSimple("AAPL");

    assertThrows(IllegalArgumentException.class, () -> {
        sh.sellAction(action, 1);
    });
}   
    
}
