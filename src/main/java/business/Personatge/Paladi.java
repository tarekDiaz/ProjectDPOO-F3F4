package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

public class Paladi extends Clergue{
    public Paladi(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa, tipusDeMal);
    }

    public Paladi(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    @Override
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (8)) + 1 + getEsperit();
        return mal;
    }

    @Override
    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals(this.getTipusDeMal())) {
            mal = mal/2;
        }
        return mal;
    }
    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        int suma = (int) (Math.random() * (3)) + 1;
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).setMent(getMent() + suma);
        }
        frase.add(this.getNom() + " uses Blessing of good luck. Everyone's Mind increases in +" + suma + ".");
    }

    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        int cura = curarPersonatge();
        for (int i=0;i<personatges.size();i++) {
            if (personatges.get(i).getPdvActual() != 0) {
                int pdvPostCura = personatges.get(i).getPdvActual() + cura;
                if (pdvPostCura > personatges.get(i).getPdvMax()) {
                    personatges.get(i).setPdvActual(personatges.get(i).getPdvMax());
                } else {
                    personatges.get(i).setPdvActual(pdvPostCura);
                }
            }
        }
        frase.add(this.getNom() + " uses Prayer of mass healing. Heals " + cura + " hit points to the non-unconscious characters in the party");
    }

    @Override
    public String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre, int posMajorMonstre) {
        boolean healingDone = false;

        for (int i=0; i<personatges.size() && !healingDone;i++) {
            if (personatges.get(i).getPdvActual() < (personatges.get(i).getPdvMax() / 2)) {
                int cura = curarPersonatge();
                for (int j=0; j<personatges.size();j++) {
                    if (personatges.get(i).getPdvActual() != 0) {
                        int pdvPostCura = personatges.get(j).getPdvActual() + cura;
                        if (pdvPostCura > personatges.get(j).getPdvMax()) {
                            personatges.get(j).setPdvActual(personatges.get(j).getPdvMax());
                        } else {
                            personatges.get(j).setPdvActual(pdvPostCura);
                        }
                    }
                }
                frase = "\n" + this.getNom() + " uses Prayer of mass healing. Heals " + cura + " hit points to the non-unconscious characters in the party.";
                healingDone = true;
            }
        }
        if (!healingDone) {
            int mal = atacarPersonatge();
            frase = "\n" + getNom() + " attacks " + monstres.get(posMenorMonstre).getNom() + " with Never on my watch.";

            int dau = (int) (Math.random() * (10)) + 1;

            monstres.get(posMenorMonstre).monstreRebMal(mal, dau, this.getTipusDeMal());

            if (dau == 1) {
                frase = frase + "\nFails and deals 0 " + getTipusDeMal() + " damage.";
            }
            if (dau > 1 && dau < 10) {
                frase = frase + "\nHits and deals " + mal + " " + getTipusDeMal() + " damage.";
            }
            if (dau == 10) {
                frase = frase + "\nCritical Hit and deals " + (mal * 2) + " " + getTipusDeMal() + " damage.";
            }
            if (monstres.get(posMenorMonstre).estaInconscient()) {
                frase = frase + "\n" + monstres.get(posMenorMonstre).getNom() + " dies.";
            }
        }
        return frase;
    }
}
