package persistence.Aventuras;

import business.Aventura.Aventura;
import persistence.PersistenceException;

import java.util.List;

/**
 *Interfície que engloba les classes d'aventures que accedeixen a persistència a través de Json o de l'API amb els mètodes que tenen en comú.
 */
public interface AventurasDAO {

    /**
     * Mètode que retorna la llista de les aventures que es troben a persistència
     * @return llista de classe Aventura amb les aventures
     * @throws PersistenceException
     */
    List<Aventura> readAventura() throws PersistenceException;

    /**
     * Mètode que escriu una nova aventura a persistència
     * @param aventura aventura que es vol afegir
     * @throws PersistenceException
     */
    void writeAventura (Aventura aventura) throws PersistenceException;

}

