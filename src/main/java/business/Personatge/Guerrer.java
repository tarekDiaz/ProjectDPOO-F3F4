package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

public class Guerrer extends Aventurer{
    public Guerrer(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa, tipusDeMal);
    }

    public Guerrer(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        super(nom, nomJugador, experiencia, cos, ment, esperit, classe);
    }

    @Override
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (10)) + 1 + getCos();
        return mal;
    }


    @Override
    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals(this.getTipusDeMal())) {
            mal = mal/2;
        }
        return mal;
    }
    @Override
    public String retornaNomAtac () {
        String nomAtac = "Improved Sword Slash";

        return nomAtac;
    }

    @Override
    public String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge) {
        Personatge evolucio = null;
        String frase = null;
        if (this.getNivell() == 8) {
            evolucio = new Campio(this.getNom(), this.getNomJugador(), this.getNivell(), this.getCos(), this.getMent(), this.getEsperit(), this.getClasse(), this.getExperiencia(), this.getPdvMax(), this.getPdvActual(), this.getIniciativa(), this.getTipusDeMal());
            personatges.add(posPersonatge + 1, evolucio);
            personatges.remove(posPersonatge);
            frase = this.getNom() + " evolves to Champion!";
        }
        return frase;
    }
}
