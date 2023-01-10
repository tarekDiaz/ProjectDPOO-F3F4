package business;

public class Guerrer extends Aventurer{
    public Guerrer(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa);
    }

    public Guerrer(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    @Override
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (10)) + 1 + getCos();
        return mal;
    }



    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals("Physical")) {
            mal = mal/2;
        }
        return mal;
    }
}
