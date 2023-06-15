package business.Aventura;

import business.Monstre.Monstre;
import business.Personatge.Personatge;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Classe Aventura amb els seus respectius getters i setters.
 */
public class Aventura {
    @SerializedName("nameAdventure")
    private String nom;
    @SerializedName("combats")
    private List<Combat> combats;
    private List<Personatge> personatges;

    /**
     * Mètode Contructor
     * @param nom Nom de l'Aventura
     * @param combats Llista de combats
     * @param personatges Llista de personatges
     */
    public Aventura(String nom,List<Combat> combats, List<Personatge> personatges) {
        this.nom = nom;
        this.combats = combats;
        this.personatges = personatges;
    }

    /**
     * Mètode que afegeig monstres a un combat
     * @param monstre monstre que es vol afegir
     * @param numCombat numero de combat
     */
    public void addMonstre(Monstre monstre, int numCombat){
        combats.get(numCombat).addMonstre(monstre);
    }

    /**
     * Getter que retorna el nom de l'aventura
     * @return Nom de l'aventura
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter que retorna la llista de combats de l'aventura
     * @return Llista de combats de l'aventura
     */
    public List<Combat> getCombats() {
        return combats;
    }

    /**
     * Getter que retorna la llista de personatges de l'aventura
     * @return lista de personatges de l'aventura
     */
    public List<Personatge> getPersonatges() {
        return personatges;
    }

    /**
     * Setter d'una llista de personatges de l'aventura. Utilitzat per establir quins personatges formaran part d'aquesta.
     * @param personatges Llita de personatges que formaran part de l'aventura
     */
    public void setPersonatges(List<Personatge> personatges) {
        this.personatges = personatges;
    }
}

