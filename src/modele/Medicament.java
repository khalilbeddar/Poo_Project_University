package modele;

// Represents a medicine that can be added to a prescription
public class Medicament {

    private String nom;
    private String dosage;
    private String dureeTraitement;
    private String contreIndications;

    // Full constructor with all details
    public Medicament(String nom, String dosage, String dureeTraitement, String contreIndications) {
        this.nom = nom;
        this.dosage = dosage;
        this.dureeTraitement = dureeTraitement;
        this.contreIndications = contreIndications;
    }

    // Shorter constructor when there are no known side effects
    public Medicament(String nom, String dosage, String dureeTraitement) {
        this(nom, dosage, dureeTraitement, "None known");
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getDureeTraitement() { return dureeTraitement; }
    public void setDureeTraitement(String dureeTraitement) { this.dureeTraitement = dureeTraitement; }

    public String getContreIndications() { return contreIndications; }
    public void setContreIndications(String contreIndications) { this.contreIndications = contreIndications; }

    @Override
    public String toString() {
        return "Medicament{name='" + nom + "', dosage='" + dosage + "', duration='" + dureeTraitement + "'}";
    }
}
