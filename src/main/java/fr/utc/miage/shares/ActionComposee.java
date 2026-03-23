package fr.utc.miage.shares;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fr.utc.miage.shares.Action;
import fr.utc.miage.shares.ActionSimple;

public class ActionComposee extends Action {

    private static final float TOTAL_POURCENTAGE = 100.0f;
    private final Map<ActionSimple, Float> composition;

    public ActionComposee(String libelle) {
        super(libelle);
        this.composition = new HashMap<>();
    }

    public void addAction(ActionSimple action, float pourcentage) {
        if (pourcentage < 0 || pourcentage > TOTAL_POURCENTAGE) {
            throw new IllegalArgumentException("Le pourcentage doit être compris entre 0 et 100");
        }
        this.composition.put(action, pourcentage);
    }

    // valider le pourcentage total est 100%
    public boolean validerComposition() {
        float total = 0.0f;
        for (float pourcentage : composition.values()) {
            total += pourcentage;
        }
        return Math.abs(total - TOTAL_POURCENTAGE) < 0.0001f;
    }

    public Map<ActionSimple, Float> getComposition() {
        return composition;
    }
    
    // au moins une action dans la composition
    public boolean hasAuMoinsUneAction() {
        return !composition.isEmpty();
    }

    @Override
    public float valeur(Jour j) {
        if (!validerComposition()) {
            throw new IllegalStateException("La composition n'est pas valide (total != 100%)");
        }
        
        float valeurTotale = 0.0f;
        for (Map.Entry<ActionSimple, Float> entry : composition.entrySet()) {
            ActionSimple action = entry.getKey();
            float pourcentage = entry.getValue();
            valeurTotale += action.valeur(j) * (pourcentage / TOTAL_POURCENTAGE);
        }
        return valeurTotale;
    }
}
