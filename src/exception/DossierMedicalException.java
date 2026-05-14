package exception;

// Thrown when trying to access an incomplete or unauthorized medical file
public class DossierMedicalException extends Exception {
    public DossierMedicalException(String message) {
        super(message);
    }
}
