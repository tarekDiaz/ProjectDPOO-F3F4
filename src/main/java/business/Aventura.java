package business;

import com.google.gson.annotations.SerializedName;

import java.io.Serial;
import java.util.List;

public class Aventura {
    @SerializedName("nameAdventure")
    String nom;
    @SerializedName("combats")
    List<Combat> combats;
    List<Personatge> personatges;

    public Aventura(String nom,List<Combat> combats, List<Personatge> personatges) {
        this.nom = nom;
        this.combats = combats;
        this.personatges = personatges;
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

