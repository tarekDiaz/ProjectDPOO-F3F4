package business.Aventura;

import business.Monstre.Monstre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Classe Combat amb els seus respectius getters i setters.
 */
public class Combat {
    @SerializedName("monsters")
    List<Monstre> monstres;

    /**
     * Mètode Constructor
     * @param monstres Llista de Monstres
     */
    public Combat(List<Monstre> monstres) {
        this.monstres = monstres;
    }

    public List<Monstre> getMonstre() {
        return monstres;
    }

}
