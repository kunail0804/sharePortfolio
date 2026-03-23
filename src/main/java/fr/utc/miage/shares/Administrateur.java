package fr.utc.miage.shares;

public class Administrateur extends Utilisateur {


    public Administrateur(String login, String password) {
        super(login, password);
        this.setRole(Role.admin);
    }
    
}
