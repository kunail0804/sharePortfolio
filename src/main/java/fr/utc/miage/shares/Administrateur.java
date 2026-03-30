package fr.utc.miage.shares;

public class Administrateur extends Utilisateur {


    public Administrateur(String login, String password) {
        super(login, password);
        this.setRole(Role.ADMIN);
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
    public Role getRole() {
        return this.getRole();
    }
}
