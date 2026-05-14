package exception;

// Thrown when a prescription has no medicines or no diagnosis
public class OrdonnanceInvalideException extends Exception {
    public OrdonnanceInvalideException(String message) {
        super(message);
    }
}
