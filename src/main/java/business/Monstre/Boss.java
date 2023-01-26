package business.Monstre;

import business.Personatge.Personatge;

import java.util.List;

public class Boss extends Monstre{
    public Boss(String nom, String nivellDificultat, int experiencia, int pdv, int iniciativa, String tipusDau, String tipusDeMal) {
        super(nom, nivellDificultat, experiencia, pdv, iniciativa, tipusDau, tipusDeMal);
    }

    @Override
    public String atacarFaseCombat(List<Monstre> monstres, List<Personatge> personatges, int contadorMonstre, int mal) {
        String fraseBoss;
        int dau = 0;
        boolean accedit = false;

        fraseBoss = "\n" + monstres.get(contadorMonstre).getNom() + " attacks";
        dau = (int) (Math.random() * (10)) + 1;
        for (int h=0; h<personatges.size(); h++) {
            if (!personatges.get(h).estaInconscient()) {
                fraseBoss = fraseBoss + " " + personatges.get(h).getNom() + ",";
                personatges.get(h).rebreMalPersonatge(mal, dau, monstres.get(contadorMonstre));
                accedit = true;
            }
        }
        if (accedit) {
            fraseBoss = fraseBoss.substring(0, (fraseBoss.length()-1));
        }

        if (dau == 1) {
            fraseBoss =  fraseBoss + ".\nFails and deals 0 " + monstres.get(contadorMonstre).getTipusDeMal() + " damage.";
        }
        if (dau > 1 && dau < 10) {
            fraseBoss =  fraseBoss + ".\nHits and deals " + mal + " " + monstres.get(contadorMonstre).getTipusDeMal() +" damage.";
        }
        if (dau == 10) {
            fraseBoss =  fraseBoss + ".\nCritical Hit and deals " + (mal * 2) + " " + monstres.get(contadorMonstre).getTipusDeMal() + " damage.";
        }
        for (int h=0; h<personatges.size(); h++) {
            if (personatges.get(h).estaInconscient()) {
                fraseBoss = fraseBoss + "\n" + personatges.get(h).getNom() + " falls unconscious.";
            }
        }
        return fraseBoss;
    }

    @Override
    public void monstreRebMal (int mal, int dau, String tipusDeMalAtac) {
        if (dau == 1) {
            mal = 0;
        }
        if (dau == 10) {
            mal = mal * 2;
        }

        if (this.getTipusDeMal().equals(tipusDeMalAtac)) {
            mal = mal/2;
        }

        int pdvPostAtac = getPdv() - mal;
        setPdv(pdvPostAtac);

        if (getPdv() < 0) {
            setPdv(0);
        }
    }
}
