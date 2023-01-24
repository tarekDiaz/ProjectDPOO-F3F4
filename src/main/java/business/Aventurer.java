package business;

import java.util.List;

public class Aventurer extends Personatge{
    public Aventurer(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa, tipusDeMal);
    }

    public Aventurer(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    public void evolucionarPersonatge(List<Personatge> personatges) {
        Personatge evolucio = null;
        if (getNivell() == 3) {
            evolucio = new Guerrer(getNom(), getNomJugador(), getNivell(), getCos(), getMent(), getEsperit(), getClasse(), getExperiencia(), getPdvMax(), getPdvActual(), getIniciativa(), getTipusDeMal());
        }
        /*if (getNivell() > 7) {
            Campio evolucio = new Campio(getNom(), getNomJugador(), getNivell(), getCos(), getMent(), getEsperit(), getClasse(), getExperiencia(), getPdvMax(), getPdvActual(), getIniciativa(), getTipusDeMal());
        }*/
    }
}
