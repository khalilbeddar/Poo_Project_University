package modele;

import java.time.LocalDate;
import java.time.Period;

// Base class for everyone in the clinic
// Abstract so we can't create a generic "person", only patients or staff
public abstract class Personne {

    private String identifiant;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;

    public Personne(String identifiant, String nom, String prenom,
                    LocalDate dateNaissance, String telephone) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
    }

    // Each subclass must define how to display its own profile
    public abstract void afficherProfil();

    // Calculate age using the difference between birth date and today
    public int calculerAge() {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    public String getIdentifiant() { return identifiant; }
    public void setIdentifiant(String identifiant) {
        if (identifiant == null || identifiant.trim().isEmpty())
            throw new IllegalArgumentException("ID cannot be empty.");
        this.identifiant = identifiant;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) {
        if (nom == null || nom.trim().isEmpty())
            throw new IllegalArgumentException("Last name cannot be empty.");
        this.nom = nom;
    }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) {
        if (prenom == null || prenom.trim().isEmpty())
            throw new IllegalArgumentException("First name cannot be empty.");
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate d) {
        if (d == null || d.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Invalid birth date.");
        this.dateNaissance = d;
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    @Override
    public String toString() {
        return "Personne{id='" + identifiant + "', name='" + prenom + " " + nom + "', age=" + calculerAge() + "}";
    }
}
