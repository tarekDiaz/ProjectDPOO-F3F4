package business;

public class Aventurer extends Personatge{
    public Aventurer(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa);
    }

    public Aventurer(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    public void evolucionarPersonatge() {
        if (getNivell() > 3 && getNivell() < 8) {
            Guerrer evolucio = new Guerrer(getNom(), getNomJugador(), getNivell(), getCos(), getMent(), getEsperit(), getClasse(), getExperiencia(), getPdvMax(), getPdvActual(), getIniciativa());
        }
        if (getNivell() > 7) {
            Campio evolucio = new Campio(getNom(), getNomJugador(), getNivell(), getCos(), getMent(), getEsperit(), getClasse(), getExperiencia(), getPdvMax(), getPdvActual(), getIniciativa());
        }
    }
}
