package fr.utc.miage.shares;

import java.util.ArrayList;
import java.util.List;

public class CompositeAction extends Action {

    private final List<Action> actions;

    public CompositeAction(String libelle) {
        super(libelle);
        this.actions = new ArrayList<>();
    }

    public void addAction(ActionSimple action) {
        this.actions.add(action);
    }

    @Override
    public float valeur(Jour j) {
        float totalValue = 0;
        for (Action action : actions) {
            totalValue += action.valeur(j);
        }
        return totalValue;
    }
    
}
