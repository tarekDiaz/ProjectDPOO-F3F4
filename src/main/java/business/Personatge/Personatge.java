package business.Personatge;

import business.Monstre.Monstre;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Classe Personatges amb els seus respectius getters i setters.
 */
public abstract class Personatge {

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

    /**
     * Mètode constructor amb tots els atributs
     * @param nom Nom
     * @param nomJugador Nom del Jugador que crea el personatge
     * @param nivell Nivell
     * @param cos Estadística de cos
     * @param ment Estadística de ment
     * @param esperit Estadística d'esperit
     * @param classe Classe del personatge
     * @param experiencia Experiència total
     * @param pdvMax Punts de vida totals
     * @param pdvActual Punts de vida durant el combat
     * @param iniciativa Numero d'iniciativa
     * @param tipusDeMal Tipus de mal al atacar
     */
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

    /**
     * Mètode constructor amb els atributs del JSON
     * @param nom Nom
     * @param nomJugador Nom del Jugador que crea el personatge
     * @param experiencia Experiència total
     * @param cos Estadística de cos
     * @param ment Estadística de ment
     * @param esperit Estadística d'esperit
     * @param classe Classe del personatge
     */
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

    public void indicarTipusMal() {
        this.setTipusDeMal("Physical");
    }

    /**
     * Mètode que redueix el mal que rep un personatge
     * @param mal Mal que rep
     * @param monstre Monstre que realitza el mal
     * @return
     */
    public int reduirMal (int mal, Monstre monstre) {

        return mal;
    }

    /**
     * Mètode que calcula el mal que fa el personatge
     * @return mal que fa el personatge
     */
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (6)) + 1 + getCos();
        return mal;
    }

    /**
     * Mètode que calcula els punts de vida màxims d'un personatge i els actualitza
     * @return Retorna els punts de vida calculats
     */
    public int calcularPdvMax () {
        int calculPdv = (10 + getCos()) * getNivell();
        return calculPdv;
    }

    /**
     * Mètode que realitza l'acció de suport d'un personatge
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    public void suportPersonatge(List<Personatge> personatges, List<String> frase) {
        this.setEsperit(getEsperit()+1);
        frase.add(this.getNom() + " uses Self-Motivated. Their Spirit increases in +1.");
    }

    /**
     * Mètode que cura la vida d'un personatge
     * @return Retorna la cura realitzada
     */
    public int curarPersonatge() {
        int cura = (int) (Math.random() * (8)) + 1 + getMent();

        return cura;
    }

    /**
     * Mètode que realitza l'acció del descans curt
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    public void curaDescansCurt(List<Personatge> personatges, List<String> frase) {
        String fraseaux = "";
        int cura = curarPersonatge();
        if (this.getPdvActual() == this.getPdvMax()) {
            fraseaux = " But was already full hp.";
        }
        int pdvPostCura = this.getPdvActual() + cura;
        if (pdvPostCura > this.getPdvMax()) {
            this.setPdvActual(getPdvMax());
        } else {
            this.setPdvActual(pdvPostCura);
        }
        frase.add(this.getNom() + " uses Bandage Time. Heals " + cura + " hit points." + fraseaux);
    }

    /**
     * Mètode que actualitza la vida d'un personatge que rep mal
     * @param mal Mal que rep el personatge
     * @param dau Dau (atac fallat, normal o critic)
     * @param monstre Monstre que ataca al personatge
     */
    public void rebreMalPersonatge(int mal, int dau, Monstre monstre) {
        if (dau == 1) {
            mal = 0;
        }
        if (dau == 10) {
            mal = mal * 2;
        }

        mal = this.reduirMal(mal, monstre);

        int pdvPostAtac = this.getPdvActual() - mal;
        this.setPdvActual(pdvPostAtac);
        if (this.getPdvActual() < 0) {
            this.setPdvActual(0);
        }
    }

    /**
     * Mètode que calcula la iniciativa del personatge
     * @return numero de la iniciativa
     */
    public int calcularIniciativa () {
        int n = (int) (Math.random() * (12)) + 1;
        int iniciativa = n + getEsperit();
        return iniciativa;
    }

    /**
     * Mètode que crea una frase amb el nom i punts de vida maxims i actuals i l'afegeix a la llista
     * @param llista Llista on afegir les frases
     * @return Retorna la llista
     */
    public List<String> writePartyHP (List<String> llista) {
        llista.add("- " + getNom() + "   " + getPdvActual() + " / " + getPdvMax() + " hit points");

        return llista;
    }

    /**
     * Mètode que retorna el nom de l'atac que realitza el personatge
     * @return Retorna el nom de l'atac que realitza el personatge
     */
    public String retornaNomAtac () {
        String nomAtac = "Sword Slash";

        return nomAtac;
    }

    /**
     * Mètode que realitza l'acció d'un personatge durant la batalla
     * @param personatges Llista de personatges del combat
     * @param monstres Llista de monstres del combat
     * @param frase Frase generada per l'acció
     * @param posMenorMonstre Posició del monstre amb menys vida
     * @param posMajorMonstre Posició del mosntre amb més vida
     * @return Retorna la frase generada per l'acció
     */
    public String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre, int posMajorMonstre) {
        int mal = this.atacarPersonatge();
        frase = "\n" + getNom() + " attacks " + monstres.get(posMenorMonstre).getNom() + " with " + this.retornaNomAtac() + ".";

        int dau = (int) (Math.random() * (10)) + 1;

        monstres.get(posMenorMonstre).monstreRebMal(mal, dau, this.getTipusDeMal());

        if (dau == 1) {
            frase = frase + "\nFails and deals 0 " + getTipusDeMal() + " damage.";
        }
        if (dau > 1 && dau < 10) {
            frase = frase + "\nHits and deals " + mal + " " + getTipusDeMal() + " damage.";
        }
        if (dau == 10) {
            frase = frase + "\nCritical Hit and deals " + (mal * 2) + " " + getTipusDeMal() + " damage.";
        }
        if (monstres.get(posMenorMonstre).estaInconscient()) {
            frase = frase + "\n" + monstres.get(posMenorMonstre).getNom() + " dies.";
        }
        return frase;
    }

    /**
     * Mètode que evoluciona un personatge
     * @param personatges Llista de personatges de l'aventura
     * @param posPersonatge Posició del personatge a evolucionar
     * @return
     */
    public String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge) {
        Personatge evolucio = null;
        String frase = null;
        if (this.getNivell() >= 4) {
            evolucio = new Guerrer(this.getNom(), this.getNomJugador(), this.getNivell(), this.getCos(), this.getMent(), this.getEsperit(), "Warrior", this.getExperiencia(), this.getPdvMax(), this.getPdvActual(), this.getIniciativa(), this.getTipusDeMal());
            personatges.add(posPersonatge + 1, evolucio);
            personatges.remove(posPersonatge);
            frase = this.getNom() + " evolves to Warrior!";
        }
        return frase;
    }

    /**
     * Mètode que comprova si un personatge està inconscient o no
     * @return Retorna un boolean que comprova si el personatge esta inconscient o no
     */
    public boolean estaInconscient() {
        boolean x = false;

        if (this.getPdvActual() == 0) {
            x = true;
        }

        return x;
    }
}
