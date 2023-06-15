package business.Personatge;

import business.Monstre.Monstre;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Classe Personatges amb els seus respectius getters i setters.
 */
public abstract class Personatge {

    @SerializedName("name")
    @Expose
    protected String nom;

    @SerializedName("player")
    @Expose
    protected String nomJugador;

    @SerializedName("xp")
    @Expose
    protected int experiencia;

    @SerializedName("body")
    @Expose
    protected int cos;

    @SerializedName("mind")
    @Expose
    protected int ment;

    @SerializedName("spirit")
    @Expose
    protected int esperit;

    @SerializedName("class")
    @Expose
    protected String classe;
    protected int nivell;
    protected int pdvMax;
    protected int pdvActual;
    protected int iniciativa;
    protected String tipusDeMal;

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
     * @param tipusDeMal Tipus de mal a l'atacar
     */
    Personatge(String nom, String nomJugador, int nivell, int cos, int ment, int esperit, String classe, int experiencia, int pdvMax, int pdvActual, int iniciativa, String tipusDeMal) {
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
    Personatge(String nom, String nomJugador, int experiencia, int cos, int ment, int esperit, String classe) {
        this.nom = nom;
        this.nomJugador = nomJugador;
        this.experiencia = experiencia;
        this.cos= cos;
        this.ment = ment;
        this.esperit = esperit;
        this.classe = classe;
    }

    /**
     * Getter que retorna el nom d'un personatge
     * @return Nom d'un personatge
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter que retorna el nom del jugador d'un personatge
     * @return Nom del jugador d'un personatge
     */
    public String getNomJugador() {
        return nomJugador;
    }

    /**
     * Getter que retorna el nivell d'un personatge
     * @return Nivell d'un personatge
     */
    public int getNivell() {
        return nivell;
    }

    /**
     * Getter que retorna el tipus d'estadística "cos" d'un personatge
     * @return Cos d'un personatge
     */
    public int getCos() {
        return cos;
    }

    /**
     * Getter que retorna el tipus d'estadística "ment" d'un personatge
     * @return Ment d'un personatge
     */
    public int getMent() {
        return ment;
    }

    /**
     * Getter que retorna el tipus d'estadística "esperit" d'un personatge
     * @return Esperit d'un personatge
     */
    public int getEsperit() {
        return esperit;
    }

    /**
     * Getter que retorna la classe d'un personatge
     * @return Classe d'un personatge
     */
    public String getClasse() {
        return classe;
    }

    /**
     * Getter que retorna l'experiencia d'un personatge
     * @return Experiencia d'un personatge
     */
    public int getExperiencia() {
        return experiencia;
    }

    /**
     * Getter que retorna la vida maxima d'un personatge
     * @return Vida maxima d'un personatge
     */
    public int getPdvMax() {
        return pdvMax;
    }

    /**
     * Getter que retorna la vida actual d'un personatge
     * @return Vida actual d'un personatge
     */
    public int getPdvActual() {
        return pdvActual;
    }

    /**
     * Getter que retorna la iniciativa d'un personatge. Aquest determina l'ordre d'atac en combat.
     * @return Iniciativa d'un personatge
     */
    public int getIniciativa() {
        return iniciativa;
    }

    /**
     * Getter que retorna el tipus de mal d'un personatge
     * @return Tipus de mal d'un personatge
     */
    public String getTipusDeMal() {
        return tipusDeMal;
    }

    /**
     * Setter que estableix el nom d'un personatge
     * @param nom Nom que tindrà el personatge
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /***
     * Setter que estableix el nivell d'un personatge
     * @param nivell Nivell que tindrà el personatge
     */
    public void setNivell(int nivell) {
        this.nivell = nivell;
    }

    /**
     * Setter que estableix l'estadística "cos" d'un personatge
     * @param cos Cos que tindrà el personatge
     */
    public void setCos(int cos) {
        this.cos = cos;
    }

    /**
     * Setter que estableix l'estadística "ment" d'un personatge
     * @param ment Ment que tindrà el personatge
     */
    public void setMent(int ment) {
        this.ment = ment;
    }

    /**
     * Setter que estableix l'estadística "esperit" d'un personatge
     * @param esperit Esperit que tindrà el personatge
     */
    public void setEsperit(int esperit) {
        this.esperit = esperit;
    }

    /**
     * Setter que estableix la classe d'un personatge
     * @param classe Classe que tindrà el personatge
     */
    public void setClasse(String classe) {
        this.classe = classe;
    }

    /**
     * Setter que estableix l'experiencia d'un personatge
     * @param experiencia Experiencia que tindrà el personatge
     */
    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    /**
     * Setter que estableix els punts de vida maxims d'un personatge
     * @param pdvMax Punts de vida maxims que tindrà el personatge
     */
    public void setPdvMax(int pdvMax) {
        this.pdvMax = pdvMax;
    }

    /**
     * Setter que estableix els punts de vida actuals d'un personatge
     * @param pdvActual Punts de vida actuals que tindrà el personatge
     */
    public void setPdvActual(int pdvActual) {
        this.pdvActual = pdvActual;
    }

    /**
     * Setter que estableix la iniciativa d'un personatge
     * @param iniciativa Iniciativa que tindrà el personatge
     */
    public void setIniciativa(int iniciativa) {
        this.iniciativa = iniciativa;
    }

    /**
     * Mètode que inicialitza el nivell, punts de vida maxims i la iniciativa d'un personatge.
     */
    public void inicialitzaPersonatges () {
        nivell = (experiencia / 100) + 1;
        pdvMax = calcularPdvMax();
        iniciativa = calcularIniciativa();
        indicarTipusMal();
    }

    /**
     * Mètode que implementa el tipus de mal del personatge
     */
    public abstract void indicarTipusMal();

    /**
     * Mètode que redueix el mal que rep un personatge
     * @param mal Mal que rep
     * @param monstre Monstre que realitza el mal
     * @return Retorna el mal després de ser reduït
     */
    public int reduirMal (int mal, Monstre monstre)
    {
        return mal;
    }

    /**
     * Mètode que calcula el mal que fa el personatge
     * @return mal que fa el personatge
     */
    public int atacarPersonatge() {
        int mal = (int) (Math.random() * (6)) + 1 + cos;
        return mal;
    }

    /**
     * Mètode que calcula els punts de vida màxims d'un personatge i els actualitza
     * @return Retorna els punts de vida calculats
     */
    public int calcularPdvMax () {
        int calculPdv = (10 + cos) * nivell;
        return calculPdv;
    }

    /**
     * Mètode que realitza l'acció de suport d'un personatge
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    public abstract void suportPersonatge(List<Personatge> personatges, List<String> frase);

    /**
     * Mètode que cura la vida d'un personatge
     * @return Retorna la cura realitzada
     */
    public int curarPersonatge() {
        int cura = (int) (Math.random() * (8)) + 1 + ment;

        return cura;
    }

    /**
     * Mètode que realitza l'acció del descans curt
     * @param personatges Llista de personatges
     * @param frase Llista de frases generades per l'acció
     */
    public abstract void curaDescansCurt(List<Personatge> personatges, List<String> frase);

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

        mal = reduirMal(mal, monstre);

        int pdvPostAtac = pdvActual - mal;
        pdvActual = pdvPostAtac;
        if (pdvActual < 0) {
            pdvActual = 0;
        }
    }

    /**
     * Mètode que calcula la iniciativa del personatge
     * @return numero de la iniciativa
     */
    public abstract int calcularIniciativa();

    /**
     * Mètode que crea una frase amb el nom i punts de vida maxims i actuals i l'afegeix a la llista
     * @param llista Llista on afegir les frases
     * @return Retorna la llista
     */
    public List<String> writePartyHP (List<String> llista) {
        llista.add("- " + nom + "   " + pdvActual + " / " + pdvMax + " hit points");

        return llista;
    }

    /**
     * Mètode que realitza l'acció d'un personatge durant la batalla
     * @param personatges Llista de personatges del combat
     * @param monstres Llista de monstres del combat
     * @param frase Frase generada per l'acció
     * @param posMenorMonstre Posició del monstre amb menys vida
     * @param posMajorMonstre Posició del monstre amb més vida
     * @return Retorna la frase generada per l'acció
     */
    public abstract String accioBatalla(List<Personatge> personatges, List<Monstre> monstres, String frase, int posMenorMonstre, int posMajorMonstre);

    /**
     * Mètode que evoluciona un personatge
     * @param personatges Llista de personatges de l'aventura
     * @param posPersonatge Posició del personatge a evolucionar
     * @return Retorna la frase corresponent a l'evolució del personatge
     */
    public abstract String evolucionarPersonatge(List<Personatge> personatges, int posPersonatge);

    /**
     * Mètode que comprova si un personatge està inconscient o no
     * @return Retorna un boolean que comprova si el personatge esta inconscient o no
     */
    public boolean estaInconscient() {
        return pdvActual == 0;
    }
}
