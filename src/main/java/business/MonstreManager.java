package business;

import persistence.Monstres.MonstresDAO;
import persistence.Monstres.MonstresJsonDAO;

import java.util.ArrayList;
import java.util.List;

public class MonstreManager {
    private MonstresDAO monstresJsonDAO;

    public MonstreManager(MonstresDAO monstresJsonDAO) {
        this.monstresJsonDAO = monstresJsonDAO;
    }

    /*
    public MonstresJsonDAO getMonstresJsonDAO() {
        return monstresJsonDAO;
    }
     */

    public List<String> llistarMonstres(){
        List<Monstre> monstres = monstresJsonDAO.readMonstres();
        List<String> infoMonstres = new ArrayList<>();

        String nom, dificultat;

        for (int i = 0; i < monstres.size(); i++) {
            infoMonstres.add(monstres.get(i).getNom() + " (" + monstres.get(i).getNivellDificultat() + ")");
        }

        return infoMonstres;
    }

    public Monstre retornaMonstreComplert(int posicio) {
        List<Monstre> monstres = monstresJsonDAO.readMonstres();
        Monstre monstre = null;

        monstre = monstres.get(posicio-1);

        return monstre;
    }

    public boolean totalMonstersUnconscius (List<Monstre> monstres) {
        int mort = 0;
        boolean TMU = false;

        for (int i = 0; i < monstres.size(); i++) {
            if (monstres.get(i).getPdv() == 0) {
                mort++;
            }
        }
        if (mort == monstres.size()) {
            TMU = true;
        }
        return TMU;
    }

    public int posicioMonstreMenysHP (List<Monstre> monstres) {
        int posMenor = 0, menor = monstres.get(0).getPdv();
        for (int i=1; i<monstres.size(); i++) {
            if ((monstres.get(i).getPdv() < menor && monstres.get(i).getPdv() != 0) || menor == 0) {
                posMenor = i;
                menor = monstres.get(i).getPdv();
            }
        }
        return posMenor;
    }

    public int posicioMonstreMesHP (List<Monstre> monstres) {
        int posMajor = 0, major = monstres.get(0).getPdv();
        for (int i=1; i<monstres.size(); i++) {
            if (monstres.get(i).getPdv() > major) {
                posMajor = i;
                major = monstres.get(i).getPdv();
            }
        }
        return posMajor;
    }

    public void monstreRebMal (Monstre monstre, int mal, int dau) {
        monstre.monstreRebMal(mal, dau);
    }

    public boolean estaInconscient (Monstre monstre) {
        boolean inconscient = false;
        if (monstre.getPdv() == 0) {
            inconscient = true;
        }
        return inconscient;
    }

    public int damageMonstre (Monstre monstre) {
        String numeroString;
        int numeroInt;

        numeroString = monstre.getTipusDau().substring(1);
        numeroInt = Integer.parseInt(numeroString);

        int dau = (int) (Math.random() * (numeroInt)) + 1;

        return dau;
    }
}
