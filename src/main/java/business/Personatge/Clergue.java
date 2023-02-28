package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

/**
 * Classe herència de Personatge
 */
public class Clergue extends Personatge{

    /**
     * Mètode constructor amb tots els atributs
     * @param nom Nom
     * @param nomJugador Nom del Jugador que crea el personatge
     * @param nivell Nivell
     * @param cos Estadística de cos
     * @param ment Estadística de ment
     * @param esperit Estadística d'esperit
     * @param classe Classe del personatge
     * @param experiencia Experiència total
     * @param pdvMax Punts de vida totals
     * @param pdvActual Punts de vida durant el combat
     * @param iniciativa Numero d'iniciativa
     * @param tipusDeMal Tipus de mal al atacar
     */
    public Clergue(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa, tipusDeMal);
    }

    /**
     * Mètode constructor amb els atributs del JSON
     * @param nom Nom
     * @param nomJugador Nom del Jugador que crea el personatge
     * @param experiencia Experiència total
     * @param cos Estadística de cos
     * @param ment Estadística de ment
     * @param esperit Estadística d'esperit
     * @param classe Classe del personatge
     */
    public Clergue(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    /**
     * Mètode que "seteja" el tipus de mal d'un Clergue a "Psychical"
     */
    @Override
    public void indicarTipusMal() {
        this.setTipusDeMal("Psychical");
    }
    /**
     * Mètode que calcula el mal que fa un clergue
     * @return mal que fa el personatge
     */
    @Override
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (4)) + 1 + getEsperit();
        return mal;
    }

    /**
     * Mètode que calcula la iniciativa del clergue
     * @return numero de la iniciativa
     */
    @Override
    public int calcularIniciativa () {
        int n = (int) (Math.random() * (10)) + 1;
        int iniciativa = n + getEsperit();
        return iniciativa;
    }

    /**
     * Mètode que realitza l'acció de suport d'un clergue
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).setMent(getMent() + 1);
        }
        frase.add(this.getNom() + " uses Prayer of good luck. Everyone's Mind increases in +1.");
    }

    /**
     * Mètode realitza la cura de vida d'un clergue
     * @return Retorna la cura realitzada
     */
    @Override
    public int curarPersonatge() {
        int cura = (int) (Math.random() * (10)) + 1 + getMent();

        return cura;
    }

    /**
     * Mètode que realitza l'acció del descans curt d'un clergue
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
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

    /**
     * Mètode que realitza l'acció d'un clergue durant la batalla
     * @param personatges Llista de personatges del combat
     * @param monstres Llista de monstres del combat
     * @param frase Frase generada per l'acció
     * @param posMenorMonstre Posició del monstre amb menys vida
     * @param posMajorMonstre Posició del mosntre amb més vida
     * @return Retorna la frase generada per l'acció
     */
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

    /**
     * Mètode que evoluciona un clergue
     * @param personatges Llista de personatges de l'aventura
     * @param posPersonatge Posició del personatge a evolucionar
     * @return Retorna la frase corresponent a la evolució del clergue
     */
    @Override
    public String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge) {
        Personatge evolucio;
        String frase = null;
        if (this.getNivell() >= 5) {
            evolucio = new Paladi(this.getNom(), this.getNomJugador(), this.getNivell(), this.getCos(), this.getMent(), this.getEsperit(), "Paladin", this.getExperiencia(), this.getPdvMax(), this.getPdvActual(), this.getIniciativa(), this.getTipusDeMal());
            personatges.add(posPersonatge + 1, evolucio);
            personatges.remove(posPersonatge);
            frase = this.getNom() + " evolves to Paladin!";
        }
        return frase;
    }

}
