package persistence;

/**
 * Classe de tipus exception que recull el missatge que es vol enviar i la excepció quan hi hagi algun error a la
 * persistència del programa.
 */
public class PersistenceException extends Exception{
    /**
     * Constructor de la classe d'excepció de persistència
     * @param message missatge que es vol mostrar
     * @param cause causa de l'excepció
     */
    public PersistenceException(String message, Exception cause) {
        super(message, cause);
    }
}
