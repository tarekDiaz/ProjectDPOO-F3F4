package business;

import java.util.List;

public class Aventura {
    String nom;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Combat> getCombats() {
        return combats;
    }

    public void setCombats(List<Combat> combats) {
        this.combats = combats;
    }

    public List<Personatge> getPersonatges() {
        return personatges;
    }

    public void setPersonatges(List<Personatge> personatges) {
        this.personatges = personatges;
    }
}
