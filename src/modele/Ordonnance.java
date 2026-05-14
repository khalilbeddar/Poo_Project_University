package modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// A prescription belongs to one consultation
// It holds a list of medicines and their dosage instructions
public class Ordonnance {

    private static int compteur = 1;

    private String idOrdonnance;
    private Consultation consultation;
    private LocalDate dateEmission;

    // Each entry is: [medicine name, instructions, dosage, duration]
    private List<String[]> prescriptions;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Ordonnance(Consultation consultation) {
        this.idOrdonnance = "ORD-" + (compteur++);
        this.consultation = consultation;
        this.dateEmission = LocalDate.now();
        this.prescriptions = new ArrayList<>();
    }

    // Add a medicine with how to take it
    public void ajouterMedicament(Medicament medicament, String posologie) {
        prescriptions.add(new String[]{
            medicament.getNom(),
            posologie,
            medicament.getDosage(),
            medicament.getDureeTraitement()
        });
    }

    public void afficherOrdonnance() {
        System.out.println("==========================================");
        System.out.println("             PRESCRIPTION                ");
        System.out.println("==========================================");
        System.out.println("  Prescription ID : " + idOrdonnance);
        System.out.println("  Date issued     : " + dateEmission.format(FORMATTER));
        System.out.println("  Patient         : " + consultation.getPatient().getPrenom() + " " + consultation.getPatient().getNom());
        System.out.println("  Doctor          : Dr. " + consultation.getMedecin().getPrenom() + " " + consultation.getMedecin().getNom());
        System.out.println("  Diagnosis       : " + consultation.getDiagnostic());
        System.out.println("  --- Prescribed Medicines ---");
        if (prescriptions.isEmpty()) {
            System.out.println("  No medicines added yet.");
        } else {
            int i = 1;
            for (String[] p : prescriptions) {
                System.out.println("  " + i + ". " + p[0] + " (" + p[2] + ")");
                System.out.println("     Instructions : " + p[1]);
                System.out.println("     Duration     : " + p[3]);
                i++;
            }
        }
        System.out.println("------------------------------------------");
    }

    // Check if prescription has no medicines
    public boolean estVide() {
        return prescriptions.isEmpty();
    }

    public String getIdOrdonnance() { return idOrdonnance; }
    public Consultation getConsultation() { return consultation; }
    public LocalDate getDateEmission() { return dateEmission; }

    public List<String[]> getPrescriptions() {
        return new ArrayList<>(prescriptions);
    }

    @Override
    public String toString() {
        return "Ordonnance{id='" + idOrdonnance + "', consultation='" + consultation.getIdConsultation() + "', medicines=" + prescriptions.size() + "}";
    }
}
