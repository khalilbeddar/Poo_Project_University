package modele;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Doctor class, inherits from PersonnelMedical
public class Medecin extends PersonnelMedical {

    private String specialite;
    private String numeroOrdre;
    private double tarifConsultation;

    // List of booked time slots to check availability
    private List<LocalDateTime> creneauxReserves;

    public Medecin(String identifiant, String nom, String prenom,
                   LocalDate dateNaissance, String telephone,
                   String matricule, LocalDate dateEmbauche, double salaire,
                   String specialite, String numeroOrdre, double tarifConsultation) {
        super(identifiant, nom, prenom, dateNaissance, telephone, matricule, dateEmbauche, salaire);
        this.specialite = specialite;
        this.numeroOrdre = numeroOrdre;
        setTarifConsultation(tarifConsultation);
        this.creneauxReserves = new ArrayList<>();
    }

    // Returns true if the doctor has no appointment at that time
    public boolean estDisponible(LocalDateTime dateHeure) {
        return !creneauxReserves.contains(dateHeure);
    }

    // Book a time slot when an appointment is created
    public void reserverCreneau(LocalDateTime dateHeure) {
        creneauxReserves.add(dateHeure);
    }

    // Free up a slot if appointment is cancelled
    public void libererCreneau(LocalDateTime dateHeure) {
        creneauxReserves.remove(dateHeure);
    }

    @Override
    public void afficherProfil() {
        System.out.println("  [DOCTOR]");
        System.out.println("  ID           : " + getIdentifiant());
        System.out.println("  Name         : Dr. " + getPrenom() + " " + getNom());
        System.out.println("  Age          : " + calculerAge() + " years");
        System.out.println("  Phone        : " + getTelephone());
        System.out.println("  Specialty    : " + specialite);
        System.out.println("  Order No.    : " + numeroOrdre);
        System.out.println("  Consult fee  : " + tarifConsultation + " DA");
        System.out.println("  Staff ID     : " + getMatricule());
    }

    @Override
    public String toString() {
        return "Medecin{id='" + getIdentifiant() + "', name='Dr. " + getPrenom() + " " + getNom() + "', specialty='" + specialite + "', fee=" + tarifConsultation + "}";
    }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getNumeroOrdre() { return numeroOrdre; }
    public void setNumeroOrdre(String numeroOrdre) { this.numeroOrdre = numeroOrdre; }

    public double getTarifConsultation() { return tarifConsultation; }
    public void setTarifConsultation(double tarif) {
        // Fee must be positive, free consultations don't make sense here
        if (tarif <= 0)
            throw new IllegalArgumentException("Consultation fee must be greater than 0.");
        this.tarifConsultation = tarif;
    }

    public List<LocalDateTime> getCreneauxReserves() {
        return new ArrayList<>(creneauxReserves);
    }
}
