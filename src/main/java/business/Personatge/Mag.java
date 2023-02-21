package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

/**
 * Classe herència de Personatge
 */
public class Mag extends Personatge{

    private int escut;

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
     * @param escut Numero d'escut
     */
    public Mag(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, int escut, String tipusDeMal) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa, tipusDeMal);
        this.escut = escut;
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
    public Mag(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    public int getEscut() {
        return escut;
    }

    public void setEscut(int escut) {
        this.escut = escut;
    }

    @Override
    public void indicarTipusMal() {
        this.setTipusDeMal("Magical");
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
     * @return
     */
    @Override
    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals(this.getTipusDeMal())) {
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

        mal = this.reduirMal(mal, monstre);

        if (this.getEscut() > 0) {
            if (mal < this.getEscut()) {
                int escutPostAtac = this.getEscut() - mal;
                this.setEscut(escutPostAtac);
            }
            else {
                int malExtra = mal - this.getEscut();
                this.setEscut(0);
                this.setPdvActual(this.getPdvActual()-malExtra);
                if (this.getPdvActual() < 0) {
                    this.setPdvActual(0);
                }
            }
        }
        else {
            int pdvPostAtac = this.getPdvActual() - mal;
            this.setPdvActual(pdvPostAtac);
            if (this.getPdvActual() < 0) {
                this.setPdvActual(0);
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
        int escut = ((int) (Math.random() * (6)) + 1 + this.getMent()) * this.getNivell();
        this.setEscut(escut);
        frase.add(this.getNom() + " uses Mage shield. Shield recharges to " + escut + ".");
    }

    /**
     * Mètode que realitza l'acció del descans curt d'un mag
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        frase.add(this.getNom() + " is reading a book.");
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
            int mal = (int) (Math.random() * (4)) + 1 + getMent();;
            frase = "\n" + getNom() + " attacks all non-dead enemies with Fireball.";

            int dau = (int) (Math.random() * (10)) + 1;
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

                    monstres.get(j).monstreRebMal(mal, dau, this.getTipusDeMal());

                    if (monstres.get(j).estaInconscient()) {
                        fraseaux = fraseaux + "\n" + monstres.get(j).getNom() + " dies.";
                    }
                }
            }

            frase = frase + fraseaux;
        } else {
            int mal = (int) (Math.random() * (6)) + 1 + getMent();;
            frase = "\n" + getNom() + " attacks " + monstres.get(posMajorMonstre).getNom() + " with Arcane Missile.";

            int dau = (int) (Math.random() * (10)) + 1;

            monstres.get(posMajorMonstre).monstreRebMal(mal, dau, this.getTipusDeMal());

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
}
