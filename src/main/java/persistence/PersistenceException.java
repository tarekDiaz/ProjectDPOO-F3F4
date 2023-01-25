package persistence;

public class PersistenceException extends Exception{
    public PersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
