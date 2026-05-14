package modele;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// A consultation links a patient to a doctor at a specific time
public class Consultation {

    public enum Statut { PLANIFIEE, EN_COURS, TERMINEE, ANNULEE }

    // Auto-increment ID for each new consultation
    private static int compteur = 1;

    private String idConsultation;
    private Patient patient;
    private Medecin medecin;
    private LocalDateTime dateHeure;
    private String diagnostic;
    private String notesCliniques;
    private Statut statut;
    private Ordonnance ordonnance;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Constructor WITH clinical notes
    public Consultation(Patient patient, Medecin medecin,
                        LocalDateTime dateHeure, String diagnostic, String notesCliniques) {
        this.idConsultation = "CONS-" + (compteur++);
        this.patient = patient;
        this.medecin = medecin;
        this.dateHeure = dateHeure;
        this.diagnostic = diagnostic;
        this.notesCliniques = notesCliniques;
        this.statut = Statut.PLANIFIEE;
    }

    // Constructor WITHOUT clinical notes (overloaded version)
    public Consultation(Patient patient, Medecin medecin,
                        LocalDateTime dateHeure, String diagnostic) {
        this(patient, medecin, dateHeure, diagnostic, "");
    }

    public void afficherConsultation() {
        System.out.println("  -----------------------------------------");
        System.out.println("  Consultation : " + idConsultation);
        System.out.println("  -----------------------------------------");
        System.out.println("  Patient    : " + patient.getPrenom() + " " + patient.getNom());
        System.out.println("  Doctor     : Dr. " + medecin.getPrenom() + " " + medecin.getNom() + " (" + medecin.getSpecialite() + ")");
        System.out.println("  Date/Time  : " + dateHeure.format(FORMATTER));
        System.out.println("  Diagnosis  : " + diagnostic);
        System.out.println("  Status     : " + statut);
        if (notesCliniques != null && !notesCliniques.trim().isEmpty())
            System.out.println("  Notes      : " + notesCliniques);
        if (ordonnance != null)
            System.out.println("  Prescription ID : " + ordonnance.getIdOrdonnance());
    }

    public String getIdConsultation() { return idConsultation; }
    public Patient getPatient() { return patient; }
    public Medecin getMedecin() { return medecin; }
    public LocalDateTime getDateHeure() { return dateHeure; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public String getNotesCliniques() { return notesCliniques; }
    public void setNotesCliniques(String notesCliniques) { this.notesCliniques = notesCliniques; }

    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }

    public Ordonnance getOrdonnance() { return ordonnance; }
    public void setOrdonnance(Ordonnance ordonnance) { this.ordonnance = ordonnance; }

    @Override
    public String toString() {
        return "Consultation{id='" + idConsultation + "', patient='" + patient.getNom() + "', doctor='Dr. " + medecin.getNom() + "', date='" + dateHeure.format(FORMATTER) + "', status=" + statut + "}";
    }
}
