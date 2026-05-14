package exception;

// Thrown when no patient is found with the given ID
public class PatientInexistantException extends Exception {
    public PatientInexistantException(String message) {
        super(message);
    }
}
