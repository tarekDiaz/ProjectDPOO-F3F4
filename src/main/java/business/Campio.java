package business;

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
    public int suportPersonatge() {
        int nouEsperit = getEsperit() + 1;
        return nouEsperit;
    }
}
