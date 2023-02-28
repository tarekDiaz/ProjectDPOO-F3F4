package persistence.Personatges;

import business.Personatge.Personatge;

import java.util.List;
/**
 *Interfície que engloba les classes de personatges que accedeixen a persistència a través de Json o de l'API amb els mètodes que tenen en comú.
 */
public interface PersonatgesDAO {
    /**
     * Mètode que afegeix un nou personatge a persistència
     * @param personatge personatge que es vol afegir
     */
    void nouPersonatge(Personatge personatge);

    /**
     * Mètode que llegeix i retorna la llista de personatges guardats a persistència
     * @return llista de tipus Personatge amb els personatges de persistència
     */
    List<Personatge> readPersonatge();

    /**
     * Mètode que borra un personatge a partir del nom del personatge
     * @param nom nom del personatge que es vol esborrar
     */
    void borrar(String nom);
}
