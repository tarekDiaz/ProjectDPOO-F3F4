package business;

import java.util.List;

public class Paladi extends Clergue{
    public Paladi(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa);
    }

    public Paladi(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }
    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        int suma = (int) (Math.random() * (3)) + 1;
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).setMent(getMent() + suma);
        }
        frase.add(this.getNom() + " uses Blessing of good luck. Everyone's Mind increases in +" + suma + ".");
    }

    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        int cura = (int) (Math.random() * (10)) + 1 + this.getMent();
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

    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals("Psychical")) {
            mal = mal/2;
        }
        return mal;
    }
}
