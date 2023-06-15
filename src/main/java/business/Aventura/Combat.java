package business.Aventura;

import business.Monstre.Monstre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Classe Combat amb els seus respectius getters i setters.
 */
public class Combat {
    @SerializedName("monsters")
    private List<Monstre> monstres;

    /**
     * Mètode Constructor
     * @param monstres Llista de Monstres
     */
    public Combat(List<Monstre> monstres) {
        this.monstres = monstres;
    }

    /**
     * Getter que retorna la llista de monstres del combat
     * @return Llista de monstres del combat
     */
    public List<Monstre> getMonstre() {
        return monstres;
    }

    /**
     * Mètode per afegir monstres dins d'un combat
     * @param monstre Monstre a afegir
     */
    public void addMonstre(Monstre monstre){
        monstres.add(monstre);
    }
}
