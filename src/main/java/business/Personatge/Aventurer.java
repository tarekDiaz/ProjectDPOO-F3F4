package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

/**
 * Classe herència de Personatge
 */
public class Aventurer extends Personatge{

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
     * @param tipusDeMal Tipus de mal a l'atacar
     */
    public Aventurer(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
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
    public Aventurer(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    /**
     * Posa el tipus de mal "physical" en els personatges de tipus aventurer
     */
    @Override
    public void indicarTipusMal() {
        this.setTipusDeMal("Physical");
    }

    /**
     * Mètode que realitza l'acció de suport d'un aventurer
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        this.setEsperit(getEsperit()+1);
        frase.add(this.getNom() + " uses Self-Motivated. Their Spirit increases in +1.");
    }

    /**
     * Mètode que realitza l'acció del descans curt d'un aventurer
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
        frase.add(this.getNom() + " uses Bandage Time. Heals " + cura + " hit points." + fraseaux);
    }

    /**
     * Mètode que calcula la iniciativa del personatge de tipus aventurer
     * @return numero de la iniciativa
     */
    @Override
    public int calcularIniciativa() {
        int n = (int) (Math.random() * (12)) + 1;
        int iniciativa = n + getEsperit();
        return iniciativa;
    }

    /**
     * Mètode que realitza l'acció d'un personatge de tipus aventurer durant la batalla
     * @param personatges Llista de personatges del combat
     * @param monstres Llista de monstres del combat
     * @param frase Frase generada per l'acció
     * @param posMenorMonstre Posició del monstre amb menys vida
     * @param posMajorMonstre Posició del monstre amb més vida
     * @return Retorna la frase generada per l'acció
     */
    @Override
    public String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre, int posMajorMonstre) {
        int mal = this.atacarPersonatge();
        int dau = (int) (Math.random() * (10)) + 1;

        frase = "\n" + getNom() + " attacks " + monstres.get(posMenorMonstre).getNom() + " with Sword Slash.";

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

        return frase;
    }

    /**
     * Mètode que evoluciona un personatge de tipus aventurer a guerrer
     * @param personatges Llista de personatges de l'aventura
     * @param posPersonatge Posició del personatge a evolucionar
     * @return Retorna la frase corresponent a l'evolució del personatge de tipus aventurer a guerrer
     */
    @Override
    public String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge) {
        Personatge evolucio;
        String frase = null;
        if (this.getNivell() >= 4) {
            evolucio = new Guerrer(this.getNom(), this.getNomJugador(), this.getNivell(), this.getCos(), this.getMent(), this.getEsperit(), "Warrior", this.getExperiencia(), this.getPdvMax(), this.getPdvActual(), this.getIniciativa(), this.getTipusDeMal());
            personatges.add(posPersonatge + 1, evolucio);
            personatges.remove(posPersonatge);
            frase = this.getNom() + " evolves to Warrior!";
        }
        return frase;
    }

}
