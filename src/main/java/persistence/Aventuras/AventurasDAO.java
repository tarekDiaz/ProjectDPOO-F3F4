package persistence.Aventuras;

import business.Aventura.Aventura;

import java.util.List;

/**
 *Interfície que engloba les classes d'aventures que accedeixen a persistència a traves de Json o de la API amb els mètodes que tenen en comú
 */
public interface AventurasDAO {
    /**
     * Mètode que retorna la llista de les aventures que es troben a persistència
     * @return llista de classe Aventura amb les aventures
     */
    public List<Aventura> readAventura();
    /**
     * Mètode que escriu una nova aventura a persistència
     * @param aventura aventura que es vol afegir
     */
    public void writeAventura (Aventura aventura);

}

