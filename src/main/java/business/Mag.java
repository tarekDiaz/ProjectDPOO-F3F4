package business;

import java.util.List;

public class Mag extends Personatge{

    private int escut;

    public Mag(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, int escut) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa);
        this.escut = escut;
    }

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
    public int calcularIniciativa () {
        int n = (int) (Math.random() * (20)) + 1;
        int iniciativa = n + getMent();
        return iniciativa;
    }

    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        int escut = ((int) (Math.random() * (6)) + this.getMent()) * this.getNivell();
        this.setEscut(escut);
        frase.add(this.getNom() + " uses Mage shield. Shield recharges to " + escut + ".");
    }
    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        frase.add(this.getNom() + " is reading a book");
    }
    @Override
    public List<String> writePartyHP (List<String> llista) {
        llista.add("- " + getNom() + "   " + getPdvActual() + " / " + getPdvMax() + " hit points (Shield: " + getEscut() + ")");

        return llista;
    }
}
