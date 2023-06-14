package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

/**
 * Classe herència de Personatge
 */
public class Mag extends Personatge{

    private int escut;

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
    public Mag(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    /**
     * Getter que retorna l'escut del mag
     * @return valor de l'escut
     */
    public int getEscut() {
        return escut;
    }

    /**
     * Setter que posa valor a l'escut del mag
     * @param escut valor de l'escut
     */
    public void setEscut(int escut) {
        this.escut = escut;
    }

    /**
     * Mètode que posa el tipus de mal d'un mag a "Magical"
     */
    @Override
    public void indicarTipusMal() {
        tipusDeMal =  "Magical";
    }

    /**
     * Mètode que calcula la iniciativa del mag
     * @return numero de la iniciativa
     */
    @Override
    public int calcularIniciativa () {
        int n = (int) (Math.random() * (20)) + 1;
        int iniciativa = n + getMent();
        return iniciativa;
    }

    /**
     * Mètode que redueix el mal que rep un mag
     * @param mal Mal que rep
     * @param monstre Monstre que realitza el mal
     * @return Retorna el mal després de ser reduït
     */
    @Override
    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals(tipusDeMal)) {
            mal = mal - getNivell();
            if (mal < 0) {
                mal = 0;
            }
        }
        return mal;
    }

    /**
     * Mètode que actualitza la vida d'un mag que rep mal
     * @param mal Mal que rep el personatge
     * @param dau Dau (atac fallat, normal o critic)
     * @param monstre Monstre que ataca al personatge
     */
    @Override
    public void rebreMalPersonatge(int mal, int dau, Monstre monstre) {
        if (dau == 1) {
            mal = 0;
        }
        if (dau == 10) {
            mal = mal * 2;
        }

        mal = reduirMal(mal, monstre);

        if (escut > 0) {
            if (mal < escut) {
                int escutPostAtac = escut - mal;
                escut = escutPostAtac;
            }
            else {
                int malExtra = mal - escut;
                escut = 0;
                pdvActual = pdvActual - malExtra;
                if (pdvActual < 0) {
                    pdvActual = 0;
                }
            }
        }
        else {
            int pdvPostAtac = pdvActual - mal;
            pdvActual = pdvPostAtac;
            if (pdvActual < 0) {
                pdvActual = 0;
            }
        }
    }

    /**
     * Mètode que realitza l'acció de suport d'un mag
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        int escut = ((int) (Math.random() * (6)) + 1 + ment) * nivell;
        this.escut = escut;
        frase.add(nom + " uses Mage shield. Shield recharges to " + escut + ".");
    }

    /**
     * Mètode que realitza l'acció del descans curt d'un mag
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        frase.add(nom + " is reading a book.");
    }

    /**
     * Mètode que crea una frase amb el nom, punts de vida maxims i actuals i escut i l'afegeix a la llista
     * @param llista Llista on afegir les frases
     * @return Retorna la llista
     */
    @Override
    public List<String> writePartyHP (List<String> llista) {
        llista.add("- " + getNom() + "   " + getPdvActual() + " / " + getPdvMax() + " hit points (Shield: " + getEscut() + ")");

        return llista;
    }

    /**
     * Mètode que realitza l'acció d'un mag durant la batalla
     * @param personatges Llista de personatges del combat
     * @param monstres Llista de monstres del combat
     * @param frase Frase generada per l'acció
     * @param posMenorMonstre Posició del monstre amb menys vida
     * @param posMajorMonstre Posició del mosntre amb més vida
     * @return Retorna la frase generada per l'acció
     */
    @Override
    public String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre, int posMajorMonstre) {
        int monstresVius = 0;
        String fraseaux = "";

        for (int i=0; i<monstres.size();i++) {
            if (monstres.get(i).getPdv() != 0) {
                monstresVius++;
            }
        }
        if (monstresVius >= 3) {
            int mal = (int) (Math.random() * (4)) + 1 + getMent();
            int dau = (int) (Math.random() * (10)) + 1;
            frase = "\n" + getNom() + " attacks all non-dead enemies with Fireball.";

            if (dau == 1) {
                frase = frase + "\nFails and deals 0 " + getTipusDeMal() + " damage.";
            }
            if (dau > 1 && dau < 10) {
                frase = frase + "\nHits and deals " + mal + " " + getTipusDeMal() + " damage.";
            }
            if (dau == 10) {
                frase = frase + "\nCritical Hit and deals " + (mal * 2) + " " + getTipusDeMal() + " damage.";
            }

            for (int j=0; j<monstres.size();j++) {
                if (monstres.get(j).getPdv() != 0) {

                    monstres.get(j).monstreRebMal(mal, dau, tipusDeMal);

                    if (monstres.get(j).estaInconscient()) {
                        fraseaux = fraseaux + "\n" + monstres.get(j).getNom() + " dies.";
                    }
                }
            }

            frase = frase + fraseaux;
        } else {
            int mal = (int) (Math.random() * (6)) + 1 + getMent();
            int dau = (int) (Math.random() * (10)) + 1;
            frase = "\n" + getNom() + " attacks " + monstres.get(posMajorMonstre).getNom() + " with Arcane Missile.";


            monstres.get(posMajorMonstre).monstreRebMal(mal, dau, tipusDeMal);

            if (dau == 1) {
                frase = frase + "\nFails and deals 0 " + getTipusDeMal() + " damage.";
            }
            if (dau > 1 && dau < 10) {
                frase = frase + "\nHits and deals " + mal + " " + getTipusDeMal() + " damage.";
            }
            if (dau == 10) {
                frase = frase + "\nCritical Hit and deals " + (mal * 2) + " " + getTipusDeMal() + " damage.";
            }
            if (monstres.get(posMajorMonstre).estaInconscient()) {
                frase = frase + "\n" + monstres.get(posMajorMonstre).getNom() + " dies.";
            }
        }
        return frase;
    }

    /**
     * Mètode que evoluciona un personatge. En aquest cas, com un mag mai pot evolucionar, retornarà null
     * @param personatges Llista de personatges de l'aventura
     * @param posPersonatge Posició del personatge a evolucionar
     * @return retorna null, ja que un mag mai pot evolucionar.
     */
    @Override
    public String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge) {
        return null;
    }
}
