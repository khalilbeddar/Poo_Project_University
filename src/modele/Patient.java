package modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Patient inherits from Personne and adds medical info
public class Patient extends Personne {

    // Only these blood types are accepted
    private static final String[] VALID_BLOOD_TYPES =
            {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

    private String numeroSecuriteSociale;
    private String groupeSanguin;
    private List<String> antecedentsMedicaux;

    public Patient(String identifiant, String nom, String prenom,
                   LocalDate dateNaissance, String telephone,
                   String numeroSecuriteSociale, String groupeSanguin) {
        // Call parent constructor first (required with inheritance)
        super(identifiant, nom, prenom, dateNaissance, telephone);
        setGroupeSanguin(groupeSanguin);
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.antecedentsMedicaux = new ArrayList<>();
    }

    // Add a medical history entry for this patient
    public void ajouterAntecedent(String antecedent) {
        if (antecedent != null && !antecedent.trim().isEmpty())
            antecedentsMedicaux.add(antecedent);
    }

    // Print the full medical file of this patient
    public void afficherDossier() {
        System.out.println("==========================================");
        System.out.println("           PATIENT MEDICAL FILE          ");
        System.out.println("==========================================");
        afficherProfil();
        System.out.println("  Social Security : " + numeroSecuriteSociale);
        System.out.println("  Blood Type      : " + groupeSanguin);
        System.out.println("  Medical History :");
        if (antecedentsMedicaux.isEmpty()) {
            System.out.println("    No history recorded.");
        } else {
            for (int i = 0; i < antecedentsMedicaux.size(); i++) {
                System.out.println("    " + (i + 1) + ". " + antecedentsMedicaux.get(i));
            }
        }
        System.out.println("------------------------------------------");
    }

    // Override the abstract method from Personne
    @Override
    public void afficherProfil() {
        System.out.println("  [PATIENT]");
        System.out.println("  ID         : " + getIdentifiant());
        System.out.println("  Name       : " + getPrenom() + " " + getNom());
        System.out.println("  Age        : " + calculerAge() + " years");
        System.out.println("  Phone      : " + getTelephone());
        System.out.println("  Blood Type : " + groupeSanguin);
    }

    @Override
    public String toString() {
        return "Patient{id='" + getIdentifiant() + "', name='" + getPrenom() + " " + getNom() + "', blood='" + groupeSanguin + "'}";
    }

    public String getNumeroSecuriteSociale() { return numeroSecuriteSociale; }
    public void setNumeroSecuriteSociale(String nss) { this.numeroSecuriteSociale = nss; }

    public String getGroupeSanguin() { return groupeSanguin; }
    public void setGroupeSanguin(String groupeSanguin) {
        boolean valid = false;
        for (String type : VALID_BLOOD_TYPES) {
            if (type.equals(groupeSanguin)) {
                valid = true;
                break;
            }
        }
        if (!valid)
            throw new IllegalArgumentException("Invalid blood type. Valid: A+, A-, B+, B-, O+, O-, AB+, AB-");
        this.groupeSanguin = groupeSanguin;
    }

    // Return a copy so the original list can't be modified from outside
    public List<String> getAntecedentsMedicaux() {
        return new ArrayList<>(antecedentsMedicaux);
    }
}
