package fr.utc.miage.shares;

import javax.management.relation.Role;

public class Administrateur extends Utilisateur {


    public Administrateur(String login, String password) {
        super(login, password);
        this.setRole(Role.admin);
    }

    public void login(String login, String password) {
        if (this.getLogin().equals(login) && this.getPassword().equals(password)) {
            System.out.println("Connexion réussie en tant qu'administrateur.");
        } else {
            throw new IllegalArgumentException("Login ou mot de passe incorrect.");
        }
    }

    //getters and setters
    public void setRole(Role role) {
        this.role = role;
    }
    public String getRole() {
        return this.getRole().toString();
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getLogin() {
        return this.getLogin();
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.getPassword();
    }

    
    
}
