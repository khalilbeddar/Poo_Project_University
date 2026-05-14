package gestion;

import exception.*;
import modele.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Main class that manages all clinic data and operations
public class CliniqueMedicale {

    private String nomClinique;
    private List<Patient> patients;
    private List<Medecin> medecins;
    private List<Infirmier> infirmiers;
    private List<Consultation> consultations;
    private List<Ordonnance> ordonnances;

    public CliniqueMedicale(String nomClinique) {
        this.nomClinique = nomClinique;
        this.patients = new ArrayList<>();
        this.medecins = new ArrayList<>();
        this.infirmiers = new ArrayList<>();
        this.consultations = new ArrayList<>();
        this.ordonnances = new ArrayList<>();
    }

    // ---- Patient management ----

    public void enregistrerPatient(Patient patient) {
        patients.add(patient);
        System.out.println("Patient registered: " + patient.getPrenom() + " " + patient.getNom());
    }

    // Search patient by ID, throw exception if not found
    public Patient rechercherPatient(String id) throws PatientInexistantException {
        for (Patient p : patients) {
            if (p.getIdentifiant().equalsIgnoreCase(id)) {
                return p;
            }
        }
        throw new PatientInexistantException("No patient found with ID: " + id);
    }

    public void afficherTousLesPatients() {
        if (patients.isEmpty()) {
            System.out.println("  No patients registered.");
            return;
        }
        for (Patient p : patients) {
            p.afficherProfil();
            System.out.println("  ------------------------------------------");
        }
    }

    // ---- Staff management ----

    public void ajouterMedecin(Medecin medecin) {
        medecins.add(medecin);
        System.out.println("Doctor added: Dr. " + medecin.getPrenom() + " " + medecin.getNom());
    }

    public void ajouterInfirmier(Infirmier infirmier) {
        infirmiers.add(infirmier);
        System.out.println("Nurse added: " + infirmier.getPrenom() + " " + infirmier.getNom());
    }

    // Find doctor by ID, returns null if not found
    public Medecin rechercherMedecin(String id) {
        for (Medecin m : medecins) {
            if (m.getIdentifiant().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    // Show all staff using polymorphism
    // We put doctors and nurses in one List<Personne> and call afficherProfil()
    // Java automatically calls the right version for each object type
    public void afficherToutLePersonnel() {
        List<Personne> personnel = new ArrayList<>();
        personnel.addAll(medecins);
        personnel.addAll(infirmiers);

        if (personnel.isEmpty()) {
            System.out.println("  No staff registered.");
            return;
        }

        for (Personne p : personnel) {
            p.afficherProfil(); // polymorphic call
            System.out.println("  ------------------------------------------");
        }
    }

    // ---- Consultation management ----

    // Create consultation after checking doctor availability
    public Consultation creerConsultation(Patient patient, Medecin medecin,
                                          LocalDateTime dateHeure, String diagnostic,
                                          String notesCliniques)
            throws MedecinIndisponibleException {
        try {
            if (!medecin.estDisponible(dateHeure)) {
                throw new MedecinIndisponibleException(
                        "Dr. " + medecin.getNom() + " is already booked at " + dateHeure + ". Choose another time.");
            }

            Consultation consultation;
            if (notesCliniques != null && !notesCliniques.trim().isEmpty()) {
                consultation = new Consultation(patient, medecin, dateHeure, diagnostic, notesCliniques);
            } else {
                // Use the overloaded constructor without notes
                consultation = new Consultation(patient, medecin, dateHeure, diagnostic);
            }

            medecin.reserverCreneau(dateHeure);
            consultations.add(consultation);
            System.out.println("Consultation created: " + consultation.getIdConsultation());
            return consultation;

        } catch (MedecinIndisponibleException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        } finally {
            // This always runs, whether it worked or not
            System.out.println("  [Scheduling attempt finished]");
        }
    }

    // ---- Prescription management ----

    public Ordonnance creerOrdonnance(Consultation consultation) throws OrdonnanceInvalideException {
        try {
            if (consultation.getDiagnostic() == null || consultation.getDiagnostic().trim().isEmpty()) {
                throw new OrdonnanceInvalideException("Cannot create a prescription without a diagnosis.");
            }
            Ordonnance ordonnance = new Ordonnance(consultation);
            consultation.setOrdonnance(ordonnance);
            ordonnances.add(ordonnance);
            System.out.println("Prescription created: " + ordonnance.getIdOrdonnance());
            return ordonnance;

        } catch (OrdonnanceInvalideException e) {
            System.out.println("Invalid prescription: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("  [Prescription creation finished]");
        }
    }

    // Check the prescription has at least one medicine before we consider it done
    public void validerOrdonnance(Ordonnance ordonnance) throws OrdonnanceInvalideException {
        if (ordonnance.estVide()) {
            throw new OrdonnanceInvalideException(
                    "Prescription " + ordonnance.getIdOrdonnance() + " has no medicines.");
        }
        System.out.println("Prescription " + ordonnance.getIdOrdonnance() + " is valid.");
    }

    // ---- Search ----

    public List<Consultation> rechercherConsultationsParPatient(String idPatient) {
        List<Consultation> result = new ArrayList<>();
        for (Consultation c : consultations) {
            if (c.getPatient().getIdentifiant().equalsIgnoreCase(idPatient)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Consultation> rechercherConsultationsParMedecin(String idMedecin) {
        List<Consultation> result = new ArrayList<>();
        for (Consultation c : consultations) {
            if (c.getMedecin().getIdentifiant().equalsIgnoreCase(idMedecin)) {
                result.add(c);
            }
        }
        return result;
    }

    // ---- Dashboard ----

    public void afficherTableauDeBord() {
        // Count today's revenue by adding up fees from today's consultations
        double revenusJour = 0;
        for (Consultation c : consultations) {
            if (c.getDateHeure().toLocalDate().equals(LocalDate.now())) {
                revenusJour += c.getMedecin().getTarifConsultation();
            }
        }

        System.out.println("==========================================");
        System.out.println("  DASHBOARD - " + nomClinique);
        System.out.println("==========================================");
        System.out.println("  Registered patients    : " + patients.size());
        System.out.println("  Doctors                : " + medecins.size());
        System.out.println("  Nurses                 : " + infirmiers.size());
        System.out.println("  Total consultations    : " + consultations.size());
        System.out.println("  Prescriptions issued   : " + ordonnances.size());
        System.out.println("  Today's revenue        : " + revenusJour + " DA");
        System.out.println("------------------------------------------");
    }

    public List<Patient> getPatients() { return new ArrayList<>(patients); }
    public List<Medecin> getMedecins() { return new ArrayList<>(medecins); }
    public List<Infirmier> getInfirmiers() { return new ArrayList<>(infirmiers); }
    public List<Consultation> getConsultations() { return new ArrayList<>(consultations); }
    public String getNomClinique() { return nomClinique; }

    @Override
    public String toString() {
        return "CliniqueMedicale{name='" + nomClinique + "', patients=" + patients.size() + ", doctors=" + medecins.size() + "}";
    }
}
