package exception;

// Thrown when the doctor already has an appointment at that time
public class MedecinIndisponibleException extends Exception {
    public MedecinIndisponibleException(String message) {
        super(message);
    }
}
