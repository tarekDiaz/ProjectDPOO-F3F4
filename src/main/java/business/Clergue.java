package business;

import java.util.ArrayList;
import java.util.List;

public class Clergue extends Personatge{

    public Clergue(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa);
    }

    public Clergue(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    @Override
    public int calcularIniciativa () {
        int n = (int) (Math.random() * (10)) + 1;
        int iniciativa = n + getEsperit();
        return iniciativa;
    }

    @Override
    //Mateix problema que amb campio
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).setMent(getMent() + 1);
        }
        frase.add(this.getNom() + " uses Prayer of good luck. Everyone's Mind increases in +1.");

    }

}
