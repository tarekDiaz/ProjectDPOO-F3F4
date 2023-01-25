package business.Monstre;

import com.google.gson.annotations.SerializedName;

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


    @Override
    public boolean equals(Object o)
    {
        Monstre m;
        if(!(o instanceof Monstre))
        {
            return false;
        }

        else
        {
            m=(Monstre)o;
            if(this.nom.equals(m.getNom()) &&
                    this.nivellDificultat.equals(m.getNivellDificultat()) &&
                    this.experiencia == m.getExperiencia() &&
                    this.pdv == m.getPdv() &&
                    this.iniciativa == m.getIniciativa() &&
                    this.tipusDau.equals(m.getTipusDau()) &&
                    this.tipusDeMal.equals(m.getTipusDeMal()))
            {
                return true;
            }
        }
        return false;
    }

    public void monstreRebMal (int mal, int dau) {
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

    public boolean estaInconscient () {
        boolean inconscient = false;
        if (this.getPdv() == 0) {
            inconscient = true;
        }
        return inconscient;
    }
}

