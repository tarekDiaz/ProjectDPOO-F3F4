package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

public class Clergue extends Personatge{

    public Clergue(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa, tipusDeMal);
    }

    public Clergue(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    @Override
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (4)) + 1 + getEsperit();
        return mal;
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

        return cura;
    }

    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        String fraseaux = "";
        int cura = curarPersonatge();
        if (this.getPdvActual() == this.getPdvMax()) {
            fraseaux = " But was already full hp.";
        }
        int pdvPostCura = this.getPdvActual() + cura;
        if (pdvPostCura > this.getPdvMax()) {
            this.setPdvActual(getPdvMax());
        } else {
            this.setPdvActual(pdvPostCura);
        }
        frase.add(this.getNom() + " uses Prayer of self healing. Heals " + cura + " hit points." + fraseaux);
    }

    @Override
    public String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre, int posMajorMonstre) {
        boolean healingDone = false;

        for (int i=0; i<personatges.size() && !healingDone;i++) {
            if (personatges.get(i).getPdvActual() < (personatges.get(i).getPdvMax() / 2)) {
                int cura = curarPersonatge();
                int pdvPostCura = personatges.get(i).getPdvActual() + cura;
                if (pdvPostCura > personatges.get(i).getPdvMax()) {
                    personatges.get(i).setPdvActual(personatges.get(i).getPdvMax());
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
            if (monstres.get(posMenorMonstre).getNivellDificultat().equals("Boss") && monstres.get(posMenorMonstre).getTipusDeMal().equals(this.getTipusDeMal())) {
                mal = mal/2;
            }
            monstres.get(posMenorMonstre).monstreRebMal(mal, dau);
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

    @Override
    public String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge) {
        Personatge evolucio = null;
        String frase = null;
        if (this.getNivell() == 5) {
            evolucio = new Paladi(this.getNom(), this.getNomJugador(), this.getNivell(), this.getCos(), this.getMent(), this.getEsperit(), this.getClasse(), this.getExperiencia(), this.getPdvMax(), this.getPdvActual(), this.getIniciativa(), this.getTipusDeMal());
            personatges.add(posPersonatge + 1, evolucio);
            personatges.remove(posPersonatge);
            frase = this.getNom() + " evolves to Paladin!";
        }
        return frase;
    }

}
