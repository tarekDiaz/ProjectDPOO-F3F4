package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

/**
 * Classe herència de Clergue
 */
public class Paladi extends Clergue{

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
    public Paladi(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
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
    public Paladi(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    /**
     * Mètode que calcula el mal que fa el Paladí
     * @return mal que fa el personatge
     */
    @Override
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (8)) + 1 + getEsperit();
        return mal;
    }

    /**
     * Mètode que redueix el mal que rep un paladí
     * @param mal Mal que rep
     * @param monstre Monstre que realitza el mal
     * @return Retorna el mal despres de ser reduit
     */
    @Override
    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals(this.getTipusDeMal())) {
            mal = mal/2;
        }
        return mal;
    }

    /**
     * Mètode que realitza l'acció de suport d'un paladí
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        int suma = (int) (Math.random() * (3)) + 1;
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).setMent(getMent() + suma);
        }
        frase.add(this.getNom() + " uses Blessing of good luck. Everyone's Mind increases in +" + suma + ".");
    }

    /**
     * Mètode que realitza l'acció del descans curt d'un paladí
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
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

    /**
     * Mètode que realitza l'acció d'un paladí durant la batalla
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
