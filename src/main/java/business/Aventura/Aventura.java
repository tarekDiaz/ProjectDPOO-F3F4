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

    public void addMonstre(Monstre monstre, int numCombat){
        combats.get(numCombat).addMonstre(monstre);
    }

    public String getNom() {
        return nom;
    }

    public List<Combat> getCombats() {
        return combats;
    }

    public List<Personatge> getPersonatges() {
        return personatges;
    }

    public void setPersonatges(List<Personatge> personatges) {
        this.personatges = personatges;
    }
}

