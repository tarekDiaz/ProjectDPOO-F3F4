package business.Monstre;

import business.Personatge.Personatge;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Classe Monstre amb els seus respectius getters i setters.
 */
public class Monstre {

    @SerializedName("name")
    private String nom;
    @SerializedName("challenge")
    private String nivellDificultat;
    @SerializedName("experience")
    private int experiencia;
    @SerializedName("hitPoints")
    private int pdv;
    @SerializedName("initiative")
    private int iniciativa;
    @SerializedName("damageDice")
    private String tipusDau;
    @SerializedName("damageType")
    private String tipusDeMal;

    /**
     * Mètode constructor
     * @param nom Nom
     * @param nivellDificultat Nivell de dificultat
     * @param experiencia Experiència que atorga al ser vençut
     * @param pdv Punts de vida
     * @param iniciativa Numero d'iniciativa
     * @param tipusDau Valor máxim de dany que fa
     * @param tipusDeMal Tipus de mal a l'atacar
     */
    public Monstre(String nom, String nivellDificultat, int experiencia, int pdv, int iniciativa, String tipusDau, String tipusDeMal) {
        this.nom = nom;
        this.nivellDificultat = nivellDificultat;
        this.experiencia = experiencia;
        this.pdv = pdv;
        this.iniciativa = iniciativa;
        this.tipusDau = tipusDau;
        this.tipusDeMal = tipusDeMal;
    }

    public String getNom() {
        return nom;
    }

    public String getNivellDificultat() {
        return nivellDificultat;
    }

    public String getTipusDeMal() {
        return tipusDeMal;
    }

    public int getPdv() {
        return pdv;
    }

    public void setPdv(int pdv) {
        this.pdv = pdv;
    }

    public int getExperiencia() {
        return experiencia;
    }


    public int getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(int iniciativa) {
        this.iniciativa = iniciativa;
    }


    public String getTipusDau(){return tipusDau;}

    /**
     * Mètode d'override a "equals" que comprova si dos monstres són iguals
     * @param o Monstre al que volem comparar
     * @return Retorna true si és igual i false si és diferent
     */
    @Override
    public boolean equals(Object o)
    {
        Monstre m;
        if(!(o instanceof Monstre))
        {
            return false;
        } else {
            m=(Monstre)o;
            return this.nom.equals(m.getNom()) &&
                    this.nivellDificultat.equals(m.getNivellDificultat()) &&
                    this.experiencia == m.getExperiencia() &&
                    this.pdv == m.getPdv() &&
                    this.iniciativa == m.getIniciativa() &&
                    this.tipusDau.equals(m.getTipusDau()) &&
                    this.tipusDeMal.equals(m.getTipusDeMal());
        }
    }

    /**
     * Mètode que infligeix mal a un monstre
     * @param mal Mal a infligir
     * @param dau Dau (atac fallat, normal o critic)
     * @param tipusDeMalAtac String del tipus de mal que realitza el monstre
     */
    public void monstreRebMal (int mal, int dau, String tipusDeMalAtac) {
        if (dau == 1) {
            mal = 0;
        }
        if (dau == 10) {
            mal = mal * 2;
        }

        int pdvPostAtac = getPdv() - mal;
        setPdv(pdvPostAtac);

        if (getPdv() < 0) {
            setPdv(0);
        }
    }

    /**
     * Mètode que comprova si un monstre està mort
     * @return Retorna un boolean depenent de si el monstre està mort o no
     */
    public boolean estaInconscient () {
        return this.getPdv() == 0;
    }

    /**
     * Mètode que realitza l'atac d'un monstre a un personatge durant el combat i genera una frase
     * @param monstres Llista de monstres
     * @param personatges Llista de personatges
     * @param contadorMonstre Posició del monstre que ataca
     * @param mal Mal que realitza l'atac
     * @return Retorna la frase generada durant l'acció
     */
    public String atacarFaseCombat(List<Monstre> monstres, List<Personatge> personatges, int contadorMonstre, int mal) {
        String frase;
        int dau;

        int rollPersonatge = (int) (Math.random() * (personatges.size()));
        while (personatges.get(rollPersonatge).estaInconscient()) {
            rollPersonatge = (int) (Math.random() * (personatges.size()));
        }
        frase = "\n" + monstres.get(contadorMonstre).getNom() + " attacks " + personatges.get(rollPersonatge).getNom() + ".";
        dau = (int) (Math.random() * (10)) + 1;
        personatges.get(rollPersonatge).rebreMalPersonatge(mal, dau, monstres.get(contadorMonstre));

        if (dau == 1) {
            frase =  frase + "\nFails and deals 0 " + monstres.get(contadorMonstre).getTipusDeMal() + " damage.";
        }
        if (dau > 1 && dau < 10) {
            frase =  frase + "\nHits and deals " + mal + " " + monstres.get(contadorMonstre).getTipusDeMal() +" damage.";
        }
        if (dau == 10) {
            frase =  frase + "\nCritical Hit and deals " + (mal * 2) + " " + monstres.get(contadorMonstre).getTipusDeMal() + " damage.";
        }

        if (personatges.get(rollPersonatge).estaInconscient()) {
            frase = frase + "\n" + personatges.get(rollPersonatge).getNom() + " falls unconscious.";
        }
        return frase;
    }
}

