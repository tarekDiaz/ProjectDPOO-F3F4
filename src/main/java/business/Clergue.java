package business;

import java.util.ArrayList;
import java.util.List;

public class Clergue extends Personatge{

    public Clergue(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa);
    }

    public Clergue(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    @Override
    public int calcularIniciativa () {
        int n = (int) (Math.random() * (10)) + 1;
        int iniciativa = n + getEsperit();
        return iniciativa;
    }

    @Override
    //Mateix problema que amb campio
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).setMent(getMent() + 1);
        }
        frase.add(this.getNom() + " uses Prayer of good luck. Everyone's Mind increases in +1.");
    }

    @Override
    public int curarPersonatge() {
        int cura = (int) (Math.random() * (10)) + 1 + getMent();
        int pdvPostCura = getPdvActual() + cura;
        if (pdvPostCura > getPdvMax()) {
            setPdvActual(getPdvMax());
        } else {
            setPdvActual(pdvPostCura);
        }
        return cura;
    }

    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        int cura = (int) (Math.random() * (10)) + 1 + this.getMent();
        int pdvPostCura = this.getPdvActual() + cura;
        if (pdvPostCura > this.getPdvMax()) {
            this.setPdvActual(getPdvMax());
        } else {
            this.setPdvActual(pdvPostCura);
        }
        frase.add(this.getNom() + " uses Prayer of self healing. Heals " + cura + " hit points.");
    }

    public String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre) {
        boolean healingDone = false;

        for (int i=0; i<personatges.size() && !healingDone;i++) {
            if (personatges.get(i).getPdvActual() < (personatges.get(i).getPdvMax() / 2)) {
                int cura = (int) (Math.random() * (10)) + 1 + getMent();
                int pdvPostCura = getPdvActual() + cura;
                if (pdvPostCura > getPdvMax()) {
                    personatges.get(i).setPdvActual(getPdvMax());
                } else {
                    personatges.get(i).setPdvActual(pdvPostCura);
                }
                frase = "\n" + this.getNom() + " uses Prayer of healing. Heals " + cura + " hit points to " + personatges.get(i).getNom() + ".";
                healingDone = true;
            }
        }
        if (!healingDone) {
            int mal = atacarPersonatge();
            frase = "\n" + getNom() + " attacks " + monstres.get(posMenorMonstre).getNom() + " with Not on my watch.";

            int dau = (int) (Math.random() * (10)) + 1;
            //resistencia al mal bosses
            /*if (monstresOrdenats.get(posMenorMonstre).getNivellDificultat().equals("Boss") && monstresOrdenats.get(posMenorMonstre).getTipusDeMal().equals(personatgesOrdenats.get(contadorPersonatge).getTipusDeMal)) {
                mal = mal/2;
            }*/
            monstres.get(posMenorMonstre).monstreRebMal(mal, dau);
            if (dau == 1) {
                frase = frase + "\nFails and deals 0 physical damage.";
            }
            if (dau > 1 && dau < 10) {
                frase = frase + "\nHits and deals " + mal + " physical damage.";
            }
            if (dau == 10) {
                frase = frase + "\nCritical Hit and deals " + (mal * 2) + " physical damage.";
            }
        }
        return frase;
    }

}
