package fr.utc.miage.shares;

import java.util.HashMap;
import java.util.Map;

public class Shareholder extends User {

    private final Map<Action, Double[]> portefeuille;

    public Shareholder(String email, String password, String name, String firstname) {
        super(email, password, name, firstname);
        this.portefeuille = new HashMap<>();
    }
    
}
