package persistence.Monstres;

import business.Monstre.Monstre;

import java.util.List;
/**
 *Interfície que engloba les classes de monstres que accedeixen a persistència a traves de Json o de la API amb els mètodes que tenen en comú
 */
public interface MonstresDAO {
    /**
     * Mètode que llegeix i retorna els monstres que es troben a persistència
     * @return llista de classe Monstre dels monstres de persistència
     */
    public List<Monstre> readMonstres();
}
