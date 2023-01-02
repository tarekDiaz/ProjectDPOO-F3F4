package business;

import java.util.List;

public class Combat {
    List<Monstre> monstres;

    public Combat(List<Monstre> monstres) {
        this.monstres = monstres;
    }

    public List<Monstre> getMonstre() {
        return monstres;
    }

}
