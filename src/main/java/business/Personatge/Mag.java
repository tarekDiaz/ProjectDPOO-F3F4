package business.Personatge;

import business.Monstre.Monstre;

import java.util.List;

public class Mag extends Personatge{

    private int escut;

    public Mag(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, int escut, String tipusDeMal) {
        super(nom, nomJugador, nivell, cos, ment, esperit, classe, experiencia, pdvMax, pdvActual, iniciativa, tipusDeMal);
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
    public int reduirMal (int mal, Monstre monstre) {
        if (monstre.getTipusDeMal().equals(this.getTipusDeMal())) {
            mal = mal - getNivell();
            if (mal < 0) {
                mal = 0;
            }
        }
        return mal;
    }

    @Override
    public void rebreMalPersonatge(int mal, int dau, Monstre monstre) {
        if (dau == 1) {
            mal = 0;
        }
        if (dau == 10) {
            mal = mal * 2;
        }

        mal = this.reduirMal(mal, monstre);

        if (this.getEscut() > 0) {
            if (mal < this.getEscut()) {
                int escutPostAtac = this.getEscut() - mal;
                this.setEscut(escutPostAtac);
            }
            else {
                int malExtra = mal - this.getEscut();
                this.setEscut(0);
                this.setPdvActual(this.getPdvActual()-malExtra);
                if (this.getPdvActual() < 0) {
                    this.setPdvActual(0);
                }
            }
        }
        else {
            int pdvPostAtac = this.getPdvActual() - mal;
            this.setPdvActual(pdvPostAtac);
            if (this.getPdvActual() < 0) {
                this.setPdvActual(0);
            }
        }
    }

    @Override
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        int escut = ((int) (Math.random() * (6)) + 1 + this.getMent()) * this.getNivell();
        this.setEscut(escut);
        frase.add(this.getNom() + " uses Mage shield. Shield recharges to " + escut + ".");
    }
    @Override
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        frase.add(this.getNom() + " is reading a book.");
    }
    @Override
    public List<String> writePartyHP (List<String> llista) {
        llista.add("- " + getNom() + "   " + getPdvActual() + " / " + getPdvMax() + " hit points (Shield: " + getEscut() + ")");

        return llista;
    }

    @Override
    public String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre, int posMajorMonstre) {
        int monstresVius = 0;
        String fraseaux = "";

        for (int i=0; i<monstres.size();i++) {
            if (monstres.get(i).getPdv() != 0) {
                monstresVius++;
            }
        }
        if (monstresVius >= 3) {
            int mal = (int) (Math.random() * (4)) + 1 + getMent();;
            frase = "\n" + getNom() + " attacks all non-dead enemies with Fireball.";

            int dau = (int) (Math.random() * (10)) + 1;
            for (int j=0; j<monstres.size();j++) {
                if (monstres.get(j).getPdv() != 0) {
                    if (monstres.get(j).getNivellDificultat().equals("Boss") && monstres.get(j).getTipusDeMal().equals(this.getTipusDeMal())) {
                        mal = mal/2;
                    }
                    monstres.get(j).monstreRebMal(mal, dau);
                    if (monstres.get(j).estaInconscient()) {
                        fraseaux = fraseaux + "\n" + monstres.get(j).getNom() + " dies.";
                    }
                }
            }
            if (dau == 1) {
                frase = frase + "\nFails and deals 0 " + getTipusDeMal() + " damage.";
            }
            if (dau > 1 && dau < 10) {
                frase = frase + "\nHits and deals " + mal + " " + getTipusDeMal() + " damage.";
            }
            if (dau == 10) {
                frase = frase + "\nCritical Hit and deals " + (mal * 2) + " " + getTipusDeMal() + " damage.";
            }
            frase = frase + fraseaux;
        } else {
            int mal = (int) (Math.random() * (6)) + 1 + getMent();;
            frase = "\n" + getNom() + " attacks " + monstres.get(posMajorMonstre).getNom() + " with Arcane Missile.";

            int dau = (int) (Math.random() * (10)) + 1;

            if (monstres.get(posMajorMonstre).getNivellDificultat().equals("Boss") && monstres.get(posMajorMonstre).getTipusDeMal().equals(this.getTipusDeMal())) {
                mal = mal/2;
            }
            monstres.get(posMajorMonstre).monstreRebMal(mal, dau);

            if (dau == 1) {
                frase = frase + "\nFails and deals 0 " + getTipusDeMal() + " damage.";
            }
            if (dau > 1 && dau < 10) {
                frase = frase + "\nHits and deals " + mal + " " + getTipusDeMal() + " damage.";
            }
            if (dau == 10) {
                frase = frase + "\nCritical Hit and deals " + (mal * 2) + " " + getTipusDeMal() + " damage.";
            }
            if (monstres.get(posMajorMonstre).estaInconscient()) {
                frase = frase + "\n" + monstres.get(posMajorMonstre).getNom() + " dies.";
            }
        }
        return frase;
    }
}
