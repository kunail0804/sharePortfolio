package fr.utc.miage.shares;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionComposeeTest {

    private static final String FOO_SHARE1 = "Foo Share 1";
    private static final String FOO_SHARE2 = "Foo Share 2";
    private static final String FOO_SHARE3 = "Foo Share 3";
    private static final String COMPOSITE_SHARE = "Composite Share";
    
    // pourcentages
    private static final float PERCENT_35 = 35.0f;
    private static final float PERCENT_50 = 50.0f;
    private static final float PERCENT_15 = 15.0f;
    private static final float PERCENT_25 = 25.0f;
    
    // daily values
    private static final float VALUE_100 = 100.0f;
    private static final float VALUE_200 = 200.0f;
    private static final float VALUE_150 = 150.0f;
    
    private ActionSimple action1;
    private ActionSimple action2;
    private ActionSimple action3;
    private ActionComposee actionComposee;
    private Jour jour;
    
    @BeforeEach
    public void setUp() {
        action1 = new ActionSimple(FOO_SHARE1);
        action2 = new ActionSimple(FOO_SHARE2);
        action3 = new ActionSimple(FOO_SHARE3);
        actionComposee = new ActionComposee(COMPOSITE_SHARE);
        jour = new Jour(2025, 1);
        
        action1.enrgCours(jour, VALUE_100);
        action2.enrgCours(jour, VALUE_200);
        action3.enrgCours(jour, VALUE_150);
    }
    
    //Test #33: Failure pourcentage < 100
    @Test
    public void testValiderComposition_PourcentageMoins100() {
        actionComposee.addAction(action1, PERCENT_35);
        actionComposee.addAction(action2, PERCENT_50);
        
        assertFalse(actionComposee.validerComposition());
        assertThrows(IllegalStateException.class, () -> {
            actionComposee.valeur(jour);
        });
    }
    
    //Test #34: Failure pourcentage > 100
    @Test
    public void testValiderComposition_PourcentagePlus100() {
        actionComposee.addAction(action1, PERCENT_35);
        actionComposee.addAction(action2, PERCENT_50);
        actionComposee.addAction(action3, PERCENT_25);

        assertFalse(actionComposee.validerComposition());
        assertThrows(IllegalStateException.class, () -> {
            actionComposee.valeur(jour);
        });
    }
    
    // Test #35: Failure au moins 1 action

    @Test
    public void testHasAuMoinsUneAction_PasDAction() {
        assertFalse(actionComposee.hasAuMoinsUneAction());
        assertThrows(IllegalStateException.class, () -> {
            actionComposee.valeur(jour);
        });
    }
    
    // Test #36: Success
    @Test
    public void testValiderComposition_Success() {
        actionComposee.addAction(action1, PERCENT_35);
        actionComposee.addAction(action2, PERCENT_50);
        actionComposee.addAction(action3, PERCENT_15);
        
        assertTrue(actionComposee.hasAuMoinsUneAction());
        assertTrue(actionComposee.validerComposition());
        assertEquals(157.5, actionComposee.valeur(jour), 0.0001f);
    }
    
}

