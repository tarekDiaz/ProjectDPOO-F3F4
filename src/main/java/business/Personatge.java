package business;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public Personatge(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa) {
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

    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (6)) + 1 + getCos();
        return mal;
    }

    public int calcularPdvMax () {
        int calculPdv = (10 + getCos()) * getNivell();
        return calculPdv;
    }

    public int suportPersonatge() {
        int nouEsperit = getEsperit() + 1;
        return nouEsperit;
    }
}
