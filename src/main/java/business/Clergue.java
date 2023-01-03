package business;

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
    public int suportPersonatge() {
        int nouMent = getMent() + 1;
        return nouMent;
    }

}
