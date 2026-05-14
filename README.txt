================================================================
  MINI-PROJECT OOP — Medical Clinic Management System
  Module : Object-Oriented Programming | Level L2 Computer Science
================================================================
You have to download the project:
1-Click Code
2-Click download zip
PREREQUISITES
-------------
- Java JDK 11 or higher (tested with OpenJDK 21)
- javac and java commands available in PATH

PROJECT STRUCTURE
-----------------
ProjetPOO_Clinique/
├── src/
│   ├── modele/
│   │   ├── Personne.java          (abstract base class)
│   │   ├── Patient.java           (extends Personne)
│   │   ├── PersonnelMedical.java  (abstract intermediate class)
│   │   ├── Medecin.java           (extends PersonnelMedical)
│   │   ├── Infirmier.java         (extends PersonnelMedical)
│   │   ├── Consultation.java      (links Patient and Doctor)
│   │   ├── Ordonnance.java        (linked to a Consultation)
│   │   └── Medicament.java        (prescribed in an Ordonnance)
│   ├── gestion/
│   │   └── CliniqueMedicale.java  (central management class)
│   ├── exception/
│   │   ├── PatientInexistantException.java
│   │   ├── MedecinIndisponibleException.java
│   │   ├── DossierMedicalException.java
│   │   └── OrdonnanceInvalideException.java
│   └── main/
│       └── Main.java              (entry point + console menu)
└── README.txt

COMPILATION
-----------
From the project root directory:
	put yourself into the folder when download it(like cd path)
  Windows (PowerShell / cmd):
    javac -encoding UTF-8 -d out src\modele\*.java src\exception\*.java src\gestion\*.java src\main\*.java

  Linux / macOS:
    javac -encoding UTF-8 -d out src/modele/*.java src/exception/*.java src/gestion/*.java src/main/*.java

EXECUTION
---------
  Windows : java -cp out main.Main
  Linux   : java -cp out main.Main

AVAILABLE FEATURES
------------------
  Menu 1 — Patient Management
    - Register a new patient (with blood type validation)
    - View a patient's medical file
    - Add a medical history entry
    - List all patients

  Menu 2 — Medical Staff Management
    - Add a doctor
    - Add a nurse
    - Display all staff (polymorphism demonstration)

  Menu 3 — Consultations
    - Create a consultation (doctor availability check)
    - Display all consultations

  Menu 4 — Prescriptions
    - Create a prescription for the last consultation
    - Add medicines with dosage instructions
    - Validate and display the prescription

  Menu 5 — Search
    - Search consultations by patient or by doctor

  Menu 6 — Dashboard
    - Global statistics and today's revenue

NOTE
----
The application starts with no pre-loaded data.
Use the menus to add patients, doctors, nurses, and consultations.

================================================================
