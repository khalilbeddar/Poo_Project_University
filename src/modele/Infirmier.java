package modele;

import java.time.LocalDate;

// Nurse class, inherits from PersonnelMedical
public class Infirmier extends PersonnelMedical {

    private String service; // e.g. emergency, pediatrics, surgery
    private String grade;   // e.g. IDE, IADE

    public Infirmier(String identifiant, String nom, String prenom,
                     LocalDate dateNaissance, String telephone,
                     String matricule, LocalDate dateEmbauche, double salaire,
                     String service, String grade) {
        super(identifiant, nom, prenom, dateNaissance, telephone, matricule, dateEmbauche, salaire);
        this.service = service;
        this.grade = grade;
    }

    @Override
    public void afficherProfil() {
        System.out.println("  [NURSE]");
        System.out.println("  ID       : " + getIdentifiant());
        System.out.println("  Name     : " + getPrenom() + " " + getNom());
        System.out.println("  Age      : " + calculerAge() + " years");
        System.out.println("  Phone    : " + getTelephone());
        System.out.println("  Service  : " + service);
        System.out.println("  Grade    : " + grade);
        System.out.println("  Staff ID : " + getMatricule());
    }

    @Override
    public String toString() {
        return "Infirmier{id='" + getIdentifiant() + "', name='" + getPrenom() + " " + getNom() + "', service='" + service + "', grade='" + grade + "'}";
    }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
