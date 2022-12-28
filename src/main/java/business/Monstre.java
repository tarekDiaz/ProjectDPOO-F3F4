package business;

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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNivellDificultat() {
        return nivellDificultat;
    }

    public void setNivellDificultat(String nivellDificultat) {
        this.nivellDificultat = nivellDificultat;
    }

    public String getTipusDeMal() {
        return tipusDeMal;
    }

    public void setTipusDeMal(String tipusDeMal) {
        this.tipusDeMal = tipusDeMal;
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

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(int iniciativa) {
        this.iniciativa = iniciativa;
    }

    public String getTipusDau(){return tipusDau;}

    public void setTipusDau(String tipusDau){ this.tipusDau = tipusDau; }
}

