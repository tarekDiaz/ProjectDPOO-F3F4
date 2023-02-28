package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

/**
 * Classe herència d'Aventurer
 */
public class Guerrer extends Aventurer{

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
    public Guerrer(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
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
    public Guerrer(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    /**
     * Mètode que calcula el mal que fa el guerrer
     * @return mal que fa el personatge
     */
    @Override
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (10)) + 1 + getCos();
        return mal;
    }

    /**
     * Mètode que redueix el mal que rep un guerrer
     * @param mal Mal que rep
     * @param monstre Monstre que realitza el mal
     * @return Retorna el mal després de ser reduit
     */
    @Override
    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals(this.getTipusDeMal())) {
            mal = mal/2;
        }
        return mal;
    }

    /**
     * Mètode que retorna el nom de l'atac que realitza el guerrer
     * @return Retorna el nom de l'atac que realitza el personatge
     */
    @Override
    public String retornaNomAtac () {
        String nomAtac = "Improved Sword Slash";

        return nomAtac;
    }

    /**
     * Mètode que evoluciona un guerrer
     * @param personatges Llista de personatges de l'aventura
     * @param posPersonatge Posició del personatge a evolucionar
     * @return Retorna la frase corresponent a la evolució del guerrer
     */
    @Override
    public String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge) {
        Personatge evolucio;
        String frase = null;
        if (this.getNivell() >= 8) {
            evolucio = new Campio(this.getNom(), this.getNomJugador(), this.getNivell(), this.getCos(), this.getMent(), this.getEsperit(), "Champion", this.getExperiencia(), this.getPdvMax(), this.getPdvActual(), this.getIniciativa(), this.getTipusDeMal());
            personatges.add(posPersonatge + 1, evolucio);
            personatges.remove(posPersonatge);
            frase = this.getNom() + " evolves to Champion!";
        }
        return frase;
    }
}
