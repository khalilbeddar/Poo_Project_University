package modele;

import java.time.LocalDate;

// Middle class between Personne and Medecin/Infirmier
// Still abstract because we don't create generic "medical staff"
public abstract class PersonnelMedical extends Personne {

    private String matricule;
    private LocalDate dateEmbauche;
    private double salaire;

    public PersonnelMedical(String identifiant, String nom, String prenom,
                            LocalDate dateNaissance, String telephone,
                            String matricule, LocalDate dateEmbauche, double salaire) {
        super(identifiant, nom, prenom, dateNaissance, telephone);
        this.matricule = matricule;
        this.dateEmbauche = dateEmbauche;
        setSalaire(salaire);
    }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(LocalDate dateEmbauche) { this.dateEmbauche = dateEmbauche; }

    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) {
        // Salary can't be negative
        if (salaire < 0)
            throw new IllegalArgumentException("Salary cannot be negative.");
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return "PersonnelMedical{matricule='" + matricule + "', name='" + getPrenom() + " " + getNom() + "', salary=" + salaire + "}";
    }
}
