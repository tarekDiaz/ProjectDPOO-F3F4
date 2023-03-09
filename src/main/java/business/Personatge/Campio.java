package business.Personatge;

import java.util.List;

/**
 * Classe herència de Guerrer
 */
public class Campio extends Guerrer{

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
    public Campio(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
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
    public Campio(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    /**
     * Mètode que calcula els punts de vida màxims d'un campió i els actualitza
     * @return Retorna els punts de vida calculats
     */
    @Override
    public int calcularPdvMax () {
        int calculPdv = ((10 + getCos()) * getNivell()) + getCos() * getNivell();
        return calculPdv;
    }

    /**
     * Mètode que realitza l'acció de suport d'un campió
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).setEsperit(getEsperit() + 1);
        }
        frase.add(this.getNom() + " uses Motivational speech. Everyone's Spirit increases in +1.");
    }
    /**
     * Mètode que realitza l'acció del descans curt d'un campió
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        String fraseaux = "";
        int cura = this.getPdvMax() - this.getPdvActual();
        if (this.getPdvActual() == this.getPdvMax()) {
            fraseaux = " because was already full hp";
        }
        this.setPdvActual(getPdvMax());
        frase.add(this.getNom() + " uses Improved Bandage Time. Heals " + cura + " hit points" + fraseaux + ".");
    }

    /**
     * Mètode que cura la vida d'un campió
     * @return Retorna la cura realitzada
     */
    @Override
    public int curarPersonatge() {
        int cura = getPdvMax() - getPdvActual();
        setPdvActual(getPdvMax());
        return cura;
    }

    /**
     * Mètode que evoluciona un personatge. En aquest cas, com un campió mai pot evolucionar, retornarà null
     * @param personatges Llista de personatges de l'aventura
     * @param posPersonatge Posició del personatge a evolucionar
     * @return retorna null, ja que un mag mai pot evolucionar.
     */
    @Override
    public String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge) {
        return null;
    }
}
