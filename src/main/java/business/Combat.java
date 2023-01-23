package business;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Combat {
    @SerializedName("monsters")
    List<Monstre> monstres;

    public Combat(List<Monstre> monstres) {
        this.monstres = monstres;
    }

    public List<Monstre> getMonstre() {
        return monstres;
    }

}
