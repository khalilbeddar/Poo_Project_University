package main;

import exception.*;
import gestion.CliniqueMedicale;
import modele.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

// Entry point of the application
// Handles all user input and calls the clinic methods
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CliniqueMedicale clinique = new CliniqueMedicale("Clinique Khalil");
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Keep track of last created consultation and prescription for menu flow
    private static Consultation derniereConsultation = null;
    private static Ordonnance derniereOrdonnance = null;

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   WELCOME TO CLINIQUE KHALIL");
        System.out.println("   Medical Management System v1.0");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Your choice: ");
            if (choice == 1) {
                menuPatients();
            } else if (choice == 2) {
                menuPersonnel();
            } else if (choice == 3) {
                menuConsultations();
            } else if (choice == 4) {
                menuOrdonnances();
            } else if (choice == 5) {
                menuRecherche();
            } else if (choice == 6) {
                clinique.afficherTableauDeBord();
            } else if (choice == 0) {
                running = false;
            } else {
                System.out.println("  Invalid option, try again.");
            }
        }

        System.out.println("\n  Goodbye!\n");
        scanner.close();
    }

    // ---- Main menu ----

    private static void printMainMenu() {
        System.out.println("\n==========================================");
        System.out.println("             MAIN MENU");
        System.out.println("==========================================");
        System.out.println("  1. Patients");
        System.out.println("  2. Medical staff");
        System.out.println("  3. Consultations");
        System.out.println("  4. Prescriptions");
        System.out.println("  5. Search");
        System.out.println("  6. Dashboard");
        System.out.println("  0. Exit");
        System.out.println("------------------------------------------");
    }

    // ---- Patient submenu ----

    private static void menuPatients() {
        System.out.println("\n-- PATIENTS --");
        System.out.println("  1. Register new patient");
        System.out.println("  2. View patient file");
        System.out.println("  3. Add medical history entry");
        System.out.println("  4. Show all patients");
        System.out.println("  0. Back");
        int choice = readInt("Your choice: ");
        if (choice == 1) {
            registerPatient();
        } else if (choice == 2) {
            viewPatientFile();
        } else if (choice == 3) {
            addMedicalHistory();
        } else if (choice == 4) {
            clinique.afficherTousLesPatients();
        }
    }

    private static void registerPatient() {
        System.out.println("\n  -- New Patient --");
        String id = readString("  ID          : ");
        String nom = readString("  Last name   : ");
        String prenom = readString("  First name  : ");
        LocalDate dob = readDate("  Birth date (dd/MM/yyyy): ");
        String tel = readString("  Phone       : ");
        String nss = readString("  Social Sec. : ");
        System.out.println("  Valid blood types: A+, A-, B+, B-, O+, O-, AB+, AB-");
        String blood = readString("  Blood type  : ");

        try {
            Patient p = new Patient(id, nom, prenom, dob, tel, nss, blood);
            clinique.enregistrerPatient(p);
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void viewPatientFile() {
        String id = readString("  Patient ID: ");
        try {
            Patient p = clinique.rechercherPatient(id);
            p.afficherDossier();
        } catch (PatientInexistantException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void addMedicalHistory() {
        String id = readString("  Patient ID   : ");
        try {
            Patient p = clinique.rechercherPatient(id);
            String entry = readString("  History entry: ");
            p.ajouterAntecedent(entry);
            System.out.println("  Entry added.");
        } catch (PatientInexistantException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ---- Staff submenu ----

    private static void menuPersonnel() {
        System.out.println("\n-- MEDICAL STAFF --");
        System.out.println("  1. Add doctor");
        System.out.println("  2. Add nurse");
        System.out.println("  3. Show all staff (polymorphism demo)");
        System.out.println("  0. Back");
        int choice = readInt("Your choice: ");
        if (choice == 1) {
            addDoctor();
        } else if (choice == 2) {
            addNurse();
        } else if (choice == 3) {
            clinique.afficherToutLePersonnel();
        }
    }

    private static void addDoctor() {
        System.out.println("\n  -- New Doctor --");
        String id = readString("  ID          : ");
        String nom = readString("  Last name   : ");
        String prenom = readString("  First name  : ");
        LocalDate dob = readDate("  Birth date (dd/MM/yyyy): ");
        String tel = readString("  Phone       : ");
        String matricule = readString("  Staff ID    : ");
        LocalDate hired = readDate("  Hire date (dd/MM/yyyy) : ");
        double salary = readDouble("  Salary (DA) : ");
        String specialty = readString("  Specialty   : ");
        String order = readString("  Order No.   : ");
        double fee = readDouble("  Consult fee : ");

        try {
            Medecin m = new Medecin(id, nom, prenom, dob, tel, matricule, hired, salary, specialty, order, fee);
            clinique.ajouterMedecin(m);
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void addNurse() {
        System.out.println("\n  -- New Nurse --");
        String id = readString("  ID          : ");
        String nom = readString("  Last name   : ");
        String prenom = readString("  First name  : ");
        LocalDate dob = readDate("  Birth date (dd/MM/yyyy): ");
        String tel = readString("  Phone       : ");
        String matricule = readString("  Staff ID    : ");
        LocalDate hired = readDate("  Hire date (dd/MM/yyyy) : ");
        double salary = readDouble("  Salary (DA) : ");
        String service = readString("  Service     : ");
        String grade = readString("  Grade       : ");

        try {
            Infirmier inf = new Infirmier(id, nom, prenom, dob, tel, matricule, hired, salary, service, grade);
            clinique.ajouterInfirmier(inf);
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ---- Consultation submenu ----

    private static void menuConsultations() {
        System.out.println("\n-- CONSULTATIONS --");
        System.out.println("  1. Create consultation");
        System.out.println("  2. Show all consultations");
        System.out.println("  0. Back");
        int choice = readInt("Your choice: ");
        if (choice == 1) {
            createConsultation();
        } else if (choice == 2) {
            showConsultations();
        }
    }

    private static void createConsultation() {
        System.out.println("\n  -- New Consultation --");
        String patientId = readString("  Patient ID : ");
        String doctorId = readString("  Doctor ID  : ");

        try {
            Patient patient = clinique.rechercherPatient(patientId);
            Medecin medecin = clinique.rechercherMedecin(doctorId);
            if (medecin == null) {
                System.out.println("  No doctor found with that ID.");
                return;
            }

            LocalDateTime dateHeure = readDateTime("  Date/Time (dd/MM/yyyy HH:mm): ");
            String diagnostic = readString("  Diagnosis  : ");
            String notes = readString("  Notes (leave empty if none): ");

            derniereConsultation = clinique.creerConsultation(patient, medecin, dateHeure, diagnostic, notes);

        } catch (PatientInexistantException e) {
            System.out.println("  Patient error: " + e.getMessage());
        } catch (MedecinIndisponibleException e) {
            System.out.println("  Doctor unavailable: " + e.getMessage());
        }
    }

    private static void showConsultations() {
        List<Consultation> list = clinique.getConsultations();
        if (list.isEmpty()) {
            System.out.println("  No consultations recorded.");
            return;
        }
        for (Consultation c : list) {
            c.afficherConsultation();
        }
    }

    // ---- Prescription submenu ----

    private static void menuOrdonnances() {
        System.out.println("\n-- PRESCRIPTIONS --");
        System.out.println("  1. Create prescription (for last consultation)");
        System.out.println("  2. Add medicine to prescription");
        System.out.println("  3. Validate and display prescription");
        System.out.println("  0. Back");
        int choice = readInt("Your choice: ");
        if (choice == 1) {
            createPrescription();
        } else if (choice == 2) {
            addMedicine();
        } else if (choice == 3) {
            validatePrescription();
        }
    }

    private static void createPrescription() {
        if (derniereConsultation == null) {
            System.out.println("  No recent consultation. Create a consultation first.");
            return;
        }
        try {
            derniereOrdonnance = clinique.creerOrdonnance(derniereConsultation);
        } catch (OrdonnanceInvalideException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void addMedicine() {
        if (derniereOrdonnance == null) {
            System.out.println("  No open prescription. Create one first.");
            return;
        }
        System.out.println("\n  -- Add Medicine --");
        String name = readString("  Medicine name   : ");
        String dosage = readString("  Dosage (e.g. 500mg): ");
        String duration = readString("  Duration        : ");
        String sideEffects = readString("  Side effects    : ");
        String instructions = readString("  Instructions    : ");

        Medicament med = new Medicament(name, dosage, duration, sideEffects);
        derniereOrdonnance.ajouterMedicament(med, instructions);
        System.out.println("  Medicine added: " + name);
    }

    private static void validatePrescription() {
        if (derniereOrdonnance == null) {
            System.out.println("  No prescription to validate.");
            return;
        }
        try {
            clinique.validerOrdonnance(derniereOrdonnance);
            derniereOrdonnance.afficherOrdonnance();
        } catch (OrdonnanceInvalideException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ---- Search submenu ----

    private static void menuRecherche() {
        System.out.println("\n-- SEARCH --");
        System.out.println("  1. Consultations by patient");
        System.out.println("  2. Consultations by doctor");
        System.out.println("  0. Back");
        int choice = readInt("Your choice: ");
        if (choice == 1) {
            String id = readString("  Patient ID: ");
            List<Consultation> results = clinique.rechercherConsultationsParPatient(id);
            if (results.isEmpty()) {
                System.out.println("  No consultations found.");
            } else {
                for (Consultation c : results) {
                    c.afficherConsultation();
                }
            }
        } else if (choice == 2) {
            String id = readString("  Doctor ID: ");
            List<Consultation> results = clinique.rechercherConsultationsParMedecin(id);
            if (results.isEmpty()) {
                System.out.println("  No consultations found.");
            } else {
                for (Consultation c : results) {
                    c.afficherConsultation();
                }
            }
        }
    }

    // ---- Input helper methods ----
    // These protect against bad input so the program doesn't crash

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim(), DATE_FMT);
            } catch (DateTimeParseException e) {
                System.out.println("  Wrong format. Use dd/MM/yyyy");
            }
        }
    }

    private static LocalDateTime readDateTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDateTime.parse(scanner.nextLine().trim(), DT_FMT);
            } catch (DateTimeParseException e) {
                System.out.println("  Wrong format. Use dd/MM/yyyy HH:mm");
            }
        }
    }
}
