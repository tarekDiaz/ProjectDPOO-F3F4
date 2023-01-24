package business;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Personatge {

    @SerializedName("name")
    @Expose
    private String nom;

    @SerializedName("player")
    @Expose
    private String nomJugador;

    @SerializedName("xp")
    @Expose
    private int experiencia;

    @SerializedName("body")
    @Expose
    private int cos;

    @SerializedName("mind")
    @Expose
    private int ment;

    @SerializedName("spirit")
    @Expose
    private int esperit;

    @SerializedName("class")
    @Expose
    private String classe;

    private int nivell;
    private int pdvMax;
    private int pdvActual;
    private int iniciativa;

    private String tipusDeMal;

    public Personatge(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
        this.nom = nom;
        this.nomJugador = nomJugador;
        this.nivell = nivell;
        this.cos = cos;
        this.ment = ment;
        this.esperit = esperit;
        this.classe = classe;
        this.experiencia = experiencia;
        this.pdvMax = pdvMax;
        this.pdvActual = pdvActual;
        this.iniciativa = iniciativa;
        this.tipusDeMal = tipusDeMal;
    }

    public Personatge(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        this.nom = nom;
        this.nomJugador = nomJugador;
        this.experiencia = experiencia;
        this.cos= cos;
        this.ment = ment;
        this.esperit = esperit;
        this.classe = classe;
    }

    public JsonObject toJson(){
        JsonObject personatgeJson = new JsonObject();

        personatgeJson.addProperty("name",this.nom);
        personatgeJson.addProperty("player",this.nomJugador);
        personatgeJson.addProperty("xp",this.experiencia);
        personatgeJson.addProperty("mind",this.ment);
        personatgeJson.addProperty("spirit",this.esperit);
        personatgeJson.addProperty("class",this.classe);

        return personatgeJson;
    }

    public String getNom() {
        return nom;
    }

    public String getNomJugador() {
        return nomJugador;
    }

    public int getNivell() {
        return nivell;
    }

    public int getCos() {
        return cos;
    }

    public int getMent() {
        return ment;
    }

    public int getEsperit() {
        return esperit;
    }

    public String getClasse() {
        return classe;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getPdvMax() {
        return pdvMax;
    }

    public int getPdvActual() {
        return pdvActual;
    }

    public int getIniciativa() {
        return iniciativa;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNomJugador(String nomJugador) {
        this.nomJugador = nomJugador;
    }

    public void setNivell(int nivell) {
        this.nivell = nivell;
    }

    public void setCos(int cos) {
        this.cos = cos;
    }

    public void setMent(int ment) {
        this.ment = ment;
    }

    public void setEsperit(int esperit) {
        this.esperit = esperit;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public void setPdvMax(int pdvMax) {
        this.pdvMax = pdvMax;
    }

    public void setPdvActual(int pdvActual) {
        this.pdvActual = pdvActual;
    }

    public void setIniciativa(int iniciativa) {
        this.iniciativa = iniciativa;
    }


    public String getTipusDeMal() {
        return tipusDeMal;
    }

    public void setTipusDeMal(String tipusDeMal) {
        this.tipusDeMal = tipusDeMal;
    }

    public int reduirMal (int mal, Monstre monstre) {

        return mal;
    }

    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (6)) + 1 + getCos();
        return mal;
    }

    public int calcularPdvMax () {
        int calculPdv = (10 + getCos()) * getNivell();
        return calculPdv;
    }

    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        this.setEsperit(getEsperit()+1);
        frase.add(this.getNom() + " uses Self-Motivated. Their Spirit increases in +1.");
    }

    public int curarPersonatge() {
        int cura = (int) (Math.random() * (8)) + 1 + getMent();

        return cura;
    }

    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        int cura = curarPersonatge();
        int pdvPostCura = this.getPdvActual() + cura;
        if (pdvPostCura > this.getPdvMax()) {
            this.setPdvActual(getPdvMax());
        } else {
            this.setPdvActual(pdvPostCura);
        }
        frase.add(this.getNom() + " uses Bandage Time. Heals " + cura + " hit points.");
    }

    public int calcularIniciativa () {
        int n = (int) (Math.random() * (12)) + 1;
        int iniciativa = n + getEsperit();
        return iniciativa;
    }

    public List<String> writePartyHP (List<String> llista) {
        llista.add("- " + getNom() + "   " + getPdvActual() + " / " + getPdvMax() + " hit points");

        return llista;
    }

    public String retornaNomAtac () {
        String nomAtac = "Sword Slash";

        return nomAtac;
    }

    public String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre, int posMajorMonstre) {
        int mal = this.atacarPersonatge();
        frase = "\n" + getNom() + " attacks " + monstres.get(posMenorMonstre).getNom() + " with " + this.retornaNomAtac() + ".";

        int dau = (int) (Math.random() * (10)) + 1;
        //resistencia al mal bosses
        if (monstres.get(posMenorMonstre).getNivellDificultat().equals("Boss") && monstres.get(posMenorMonstre).getTipusDeMal().equals(this.getTipusDeMal())) {
            mal = mal/2;
        }
        monstres.get(posMenorMonstre).monstreRebMal(mal, dau);
        if (dau == 1) {
            frase = frase + "\nFails and deals 0 " + getTipusDeMal() + " damage.";
        }
        if (dau > 1 && dau < 10) {
            frase = frase + "\nHits and deals " + mal + " " + getTipusDeMal() + " damage.";
        }
        if (dau == 10) {
            frase = frase + "\nCritical Hit and deals " + (mal * 2) + " " + getTipusDeMal() + " damage.";
        }
        return frase;
    }
}
