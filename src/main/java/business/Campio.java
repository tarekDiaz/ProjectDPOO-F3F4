package business;

import java.util.List;

public class Campio extends Guerrer{

    public Campio(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa);
    }

    public Campio(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    @Override
    public int calcularPdvMax () {
        int calculPdv = ((10 + getCos()) * getNivell()) + getCos() * getNivell();
        return calculPdv;
    }
    @Override
    //falta pensar suport de campio
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).setEsperit(getEsperit() + 1);
        }
        frase.add(this.getNom() + " uses Motivational speech. Everyone's Spirit increases in +1.");
    }

    @Override
    public int curarPersonatge() {
        int cura = getPdvMax() - getPdvActual();
        setPdvActual(getPdvMax());
        return cura;
    }
}
