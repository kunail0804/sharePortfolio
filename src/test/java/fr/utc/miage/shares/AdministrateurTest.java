package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.*;
import fr.utc.miage.shares.Action;
import fr.utc.miage.shares.ActionSimple;
import fr.utc.miage.shares.CompositeAction;
import fr.utc.miage.shares.Administrateur;
import fr.utc.miage.shares.Jour;

import org.junit.jupiter.api.Test;

public class AdministrateurTest {
    Administrateur admin = new Administrateur("admin", "admin");
    Jour jour = new Jour(0, 0);
    //creation actions composees
    @Test
    void testCreateCompositeAction() {
        ActionSimple action1 = new ActionSimple("Action1");
        ActionSimple action2 = new ActionSimple("Action2");
        CompositeAction compositeAction = new CompositeAction("CompositeAction");
        Jour jour = new Jour(0,0);
        compositeAction.addAction(action1);
        compositeAction.addAction(action2);
        assertEquals(300, compositeAction.valeur(jour));
    }

    //libelle action
    @Test
    void testLibelleAction() {
        ActionSimple action = new ActionSimple("ActionTest");
        assertEquals("ActionTest", action.getLibelle());
    }

    @Test
    void testLibelleWithInvalidLibelle() {
        String INVALID_LIBELLE = "";
        assertThrows(IllegalArgumentException.class, () -> new ActionSimple(INVALID_LIBELLE));
    }

    @Test
    void testLibelleWithNullLibelle() {
        String NULL_LIBELLE = null;
        assertThrows(IllegalArgumentException.class, () -> new ActionSimple(NULL_LIBELLE));
    }

    @Test
    void testLibelleWithLibelleNotUnique() {
        ActionSimple action1 = new ActionSimple("ActionTest");
        assertThrows(IllegalArgumentException.class, () -> new ActionSimple(action1.getLibelle()), "Libelle doit être unique");
    }

    //ajouter les valeurs du jour des actions
    @Test
    void testEnregistrerCoursWithNegatifValue() {
        ActionSimple action = new ActionSimple("ActionTest");
        assertThrows(IllegalArgumentException.class, () -> action.enrgCours(jour, -100));
    }

    @Test
    void testEnregistrerCoursWithValidValue() {
        ActionSimple action = new ActionSimple("ActionTest");
        assertDoesNotThrow(() -> action.enrgCours(jour, 100));
    }

    //connection compte administrateur
    @Test
    void testLoginWithValidLoginAndValidPassword() {
        assertDoesNotThrow(() -> admin.login("admin", "admin"));
    }

    @Test
    void testLoginWithValidLoginAndInvalidPassword() {
        assertThrows(IllegalArgumentException.class, () -> admin.login("admin", "wrongpassword"));
    }

    @Test
    void testLoginWithInvalidLogin() {
        assertThrows(IllegalArgumentException.class, () -> admin.login("wronglogin", "admin"));
    }

    //Calculer la valeur d'une action composee
    @Test
    void testCalculerValeurActionComposee() {
        ActionSimple action1 = new ActionSimple("Action1");
        ActionSimple action2 = new ActionSimple("Action2");
        CompositeAction compositeAction = new CompositeAction("CompositeAction");
        compositeAction.addAction(action1);
        compositeAction.addAction(action2);
        action1.enrgCours(jour, 100);
        action2.enrgCours(jour, 200);
        assertEquals(300, compositeAction.valeur(jour));
    }


}
