package business.Aventura;

import business.Monstre.Monstre;
import business.Personatge.Personatge;
import persistence.Aventuras.AventurasDAO;
import persistence.Monstres.MonstresDAO;
import persistence.Personatges.PersonatgesDAO;

import java.util.*;

/**
 * Classe que utilitza Aventures, monstres i personatges i treballa amb elles
 */
public class AventuraManager {
    private AventurasDAO aventurasJsonDAO;
    private MonstresDAO monstresJsonDAO;
    private PersonatgesDAO personatgesJsonDAO;

    /**
     * Mètode Constructor
     * @param aventurasJsonDAO Aventures de persistència
     * @param monstresJsonDAO Monstres de persistència
     * @param personatgesJsonDAO Personatges de persistència
     */
    public AventuraManager(AventurasDAO aventurasJsonDAO, MonstresDAO monstresJsonDAO, PersonatgesDAO personatgesJsonDAO) {
        this.aventurasJsonDAO = aventurasJsonDAO;
        this.monstresJsonDAO = monstresJsonDAO;
        this.personatgesJsonDAO = personatgesJsonDAO;
    }

    /**
     * Mètode de creació d'una aventura
     * @param nom Nom
     * @param combats Combats
     * @param numCombats Nombre total de combats
     * @return Retorna l'aventura creada
     */
    public Aventura crearAventura(String nom, List<Combat> combats, int numCombats) {
        Aventura aventura = new Aventura(nom, combats, null);
        for (int i = 0; i < numCombats; i++) {
            aventura.combats.add(new Combat(new ArrayList<Monstre>()));
        }
        return aventura;
    }

    /**
     * Mètode que afegeix els Monstres al Combat
     * @param aventura Aventura
     * @param posicioMonstre Posició del a la que volem afegir el monstre
     * @param quantitatMonstres Número de monstre totals a afegir
     * @param numCombat Nombre total de combats
     */
    public void afegirMonstreCombat(Aventura aventura, int posicioMonstre, int quantitatMonstres, int numCombat) {
        List<Monstre> monstres = monstresJsonDAO.readMonstres();
        Monstre monstre = null;

        for (int i = 0; i < monstres.size(); i++) {
            if (posicioMonstre == (i+1) ){
                monstre = monstres.get(i);
            }
        }
        for (int j=0; j < quantitatMonstres; j++) {
            aventura.combats.get(numCombat).monstres.add(monstre);
        }
    }

    /**
     * Mètode que revisa si algun dels monstres del combat és de tipus "Boss"
     * @param aventura Aventura
     * @param numCombat Nombre total de combats
     * @return Retorna un boolean indicant si hi ha o no un "Boss"
     */
    public boolean checkBossCombat (Aventura aventura, int numCombat) {
        boolean bossCheck = false;
        for (int i=0; i < aventura.getCombats().get(numCombat).getMonstre().size(); i++) {
            if (aventura.getCombats().get(numCombat).getMonstre().get(i).getNivellDificultat().equals("Boss")) {
                bossCheck = true;
            }
        }
        return bossCheck;
    }

    /**
     * Mètode que genera una llista amb els monstres d'un combat i la seva quantitat
     * @param combat Combat
     * @return Retorna la llista
     */
    public List<String> generarLlistaMonstres(Combat combat) {
        List<Monstre> monstres = monstresJsonDAO.readMonstres();
        List<String> infoMonstresCombat = new ArrayList<>();

        for (int i = 0; i < monstres.size(); i++) {
            for (int j=0; j < combat.getMonstre().size(); j++){
                if (monstres.get(i).getNom().equals(combat.getMonstre().get(j).getNom())) {
                    infoMonstresCombat.add(monstres.get(i).getNom() + " (x" + Collections.frequency(combat.getMonstre(), monstres.get(i)) + ")");
                    j = combat.getMonstre().size();
                }
            }
        }
        return infoMonstresCombat;
    }

    /**
     * Mètode que genera una llista amb els monstres d'un combat i la seva quantitat amb un format diferent
     * @param combat Combat
     * @return Retorna la llista
     */
    public List<String> generarLlistaMonstres2 (Combat combat) {
        List<Monstre> monstres = monstresJsonDAO.readMonstres();
        List<String> infoMonstresCombat = new ArrayList<>();

        for (int i = 0; i < monstres.size(); i++) {
            for (int j=0; j < combat.monstres.size(); j++){
                if (monstres.get(i).getNom().equals(combat.monstres.get(j).getNom())) {
                    infoMonstresCombat.add("- " + Collections.frequency(combat.monstres, monstres.get(i)) + "x " + monstres.get(i).getNom());
                    j = combat.monstres.size();
                }
            }
        }
        return infoMonstresCombat;
    }

    /**
     * Mètode que borra un monstre de un combat
     * @param aventura Aventura
     * @param monstreDelete Posició del monstre que es vol borrar
     * @param numCombat Numero de combat on volem borrar
     * @return Retorna una String amb el nom de monstre i quantitat borrada
     */
    public String borrarMonstreCombat (Aventura aventura, int monstreDelete, int numCombat) {
        List<String> infoMonstresCombat = generarLlistaMonstres(aventura.getCombats().get(numCombat));
        String stringMonstre, monstresEliminats;
        int numeroMonstres = 0;

        stringMonstre = infoMonstresCombat.get(monstreDelete-1);

        StringTokenizer tokens = new StringTokenizer(stringMonstre);
        String nomMonstre = tokens.nextToken();
        for (int i=0; i<aventura.combats.get(numCombat).monstres.size(); i++) {
            if (aventura.combats.get(numCombat).monstres.get(i).getNom().equals(nomMonstre)) {
                aventura.combats.get(numCombat).monstres.remove(i);
                numeroMonstres ++;
                i--;
            }
        }

        return monstresEliminats = numeroMonstres + " " + nomMonstre;
    }

    /**
     * Mètode que llegeix les aventures del Json
     * @return Retorna la llista d'aventures llegida
     */
    public List<Aventura> llegirAventures(){
        List<Aventura> aventuraList = aventurasJsonDAO.readAventura();
        return aventuraList;
    }

    /**
     * Mètode que llegeix les aventures del Json i retorna una llista amb els noms d'aquestes
     * @return Retorna una llista amb els noms de les aventures
     */
    public List<String> llistarAventuras () {
        List<Aventura> aventuras = aventurasJsonDAO.readAventura();
        List<String> noms = new ArrayList<>();

        for (int i = 0; i < aventuras.size(); i++) {
            noms.add(aventuras.get(i).getNom());
        }
        return noms;
    }

    /**
     * Mètode que retorna una única Aventura
     * @param numAventura Posició de l'aventura a la llista d'aventures
     * @return Retorna l'aventura
     */
    public Aventura retornaAventuraComplerta(int numAventura) {
        List<Aventura> aventuras = aventurasJsonDAO.readAventura();
        return aventuras.get(numAventura-1);
    }

    /**
     * Mètode que afegeix un personatge a l'aventura
     * @param aventura Aventura
     * @param numPersonatge Posició del personatge a la llista de personatges
     */
    public void afegirPersonatgeAventura (Aventura aventura, int numPersonatge) {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
        if (aventura.getPersonatges() == null) {
            aventura.setPersonatges(new ArrayList<>());
        }
        aventura.personatges.add(personatges.get(numPersonatge-1));

    }

    /**
     * Mètode que afegeix un personatge i va ordenant-los per ordre d'iniciativa
     * @param personatges Llista de personatges
     * @param personatge Personatge afegit a la llista
     */
    public void ordenarPersonatgesSegonsIniciatives (List<Personatge> personatges, Personatge personatge) {

        personatge.setIniciativa(personatge.getIniciativa() + ((int) (Math.random() * (12)) + 1));
        personatges.add(personatge);
        if (personatges.size() > 1) {
            Collections.sort(personatges, new Comparator<Personatge>() {
                public int compare(Personatge p1, Personatge p2) {
                    return new Integer (p2.getIniciativa()).compareTo(new Integer(p1.getIniciativa()));
                }
            });
        }
    }

    /**
     * Mètode que afegeix un monstre i va ordenant-los per ordre d'iniciativa
     * @param monstres Llista de monstres
     * @param monstre Monstre afegit a la llista
     */
    public void ordenarMonstresSegonsIniciatives (List<Monstre> monstres, Monstre monstre) {

        monstre.setIniciativa(monstre.getIniciativa() + ((int) (Math.random() * (12)) + 1));
        monstres.add(monstre);
        if (monstres.size() > 1) {
            Collections.sort(monstres, new Comparator<Monstre>() {
                public int compare(Monstre m1, Monstre m2) {
                    return new Integer (m2.getIniciativa()).compareTo(new Integer(m1.getIniciativa()));
                }
            });
        }
    }

    /**
     * Mètode que junta i ordena tots els personatges i monstres
     * @param personatges Llista de personatges ordenada
     * @param monstres Llista de monstres ordenada
     * @return Retorna una llista ordenada final
     */
    public List<String> mostrarOrdreIniciativas (List<Personatge> personatges, List<Monstre> monstres) {
        List<String> llistaOrdenadaFinal = new ArrayList<>();
        int i, contadorPersonatges = 0, contadorMonstres = 0;

        for (i=0; i<(personatges.size() + monstres.size()); i++) {
            if (personatges.size() == contadorPersonatges) {
                llistaOrdenadaFinal.add("- " + monstres.get(contadorMonstres).getIniciativa() + " \t" + monstres.get(contadorMonstres).getNom());
                contadorMonstres++;
            } else {
                if (monstres.size() == contadorMonstres) {
                    llistaOrdenadaFinal.add("- " + personatges.get(contadorPersonatges).getIniciativa() + " \t" + personatges.get(contadorPersonatges).getNom());
                    contadorPersonatges++;
                }
                else {
                    if (personatges.get(contadorPersonatges).getIniciativa() >= monstres.get(contadorMonstres).getIniciativa()) {
                        llistaOrdenadaFinal.add("- " + personatges.get(contadorPersonatges).getIniciativa() + " \t" + personatges.get(contadorPersonatges).getNom());
                        contadorPersonatges++;
                    } else {
                        if (personatges.get(contadorPersonatges).getIniciativa() < monstres.get(contadorMonstres).getIniciativa()) {
                            llistaOrdenadaFinal.add("- " + monstres.get(contadorMonstres).getIniciativa() + " \t" + monstres.get(contadorMonstres).getNom());
                            contadorMonstres++;
                        }
                    }
                }
            }
        }
        return llistaOrdenadaFinal;
    }

    /**
     * Mètode que junta i ordena tots els personatges i monstres i rtorna unicament el nom
     * @param personatges Llista de personatges ordenada
     * @param monstres Llista de monstres ordenada
     * @return Retorna una els noms ordenats
     */
    public List<String> nomsOrdreIniciativas (List<Personatge> personatges, List<Monstre> monstres) {
        List<String> nomsOrdenats = new ArrayList<>();
        int i, contadorPersonatges = 0, contadorMonstres = 0;

        for (i=0; i<(personatges.size() + monstres.size()); i++) {
            if (personatges.size() == contadorPersonatges) {
                nomsOrdenats.add(monstres.get(contadorMonstres).getNom());
                contadorMonstres++;
            } else {
                if (monstres.size() == contadorMonstres) {
                    nomsOrdenats.add(personatges.get(contadorPersonatges).getNom());
                    contadorPersonatges++;
                }
                else {
                    if (personatges.get(contadorPersonatges).getIniciativa() >= monstres.get(contadorMonstres).getIniciativa()) {
                        nomsOrdenats.add(personatges.get(contadorPersonatges).getNom());
                        contadorPersonatges++;
                    } else {
                        if (personatges.get(contadorPersonatges).getIniciativa() < monstres.get(contadorMonstres).getIniciativa()) {
                            nomsOrdenats.add(monstres.get(contadorMonstres).getNom());
                            contadorMonstres++;
                        }
                    }
                }
            }
        }
        return nomsOrdenats;
    }

    /**
     * Mètode que crea una llista amb el nom i punts de vida maxims i actuals
     * @param personatges Llista de personatges
     * @return Retorna la llista creada
     */
    public List<String> showPartyHP (List<Personatge> personatges) {
        List<String> llista = new ArrayList<>();

        for (int i=0; i<personatges.size(); i++) {
            personatges.get(i).writePartyHP(llista);
        }
        return llista;
    }

    /**
     * Mètode que guarda una aventura al Json
     * @param aventura Aventura
     */
    public void guardarAventuraJSON (Aventura aventura){
        aventurasJsonDAO.writeAventura(aventura);
    }

    /**
     * Mètode que crida al mètode de realitzar una acció durant la batalla de personatge
     * @param personatges Llista de personatges de l'aventura
     * @param monstres Llista de monstres de l'aventura
     * @param contadorPersonatge Posició del personatge realitzant l'acció
     * @param posMenorMonstre Posició del monstre amb menys vida
     * @param posMajorMonstre Posició del monstre amb més vida
     * @return Retorna la frase generada durant l'acció
     */
    public String accioDurantCombat (List<Personatge> personatges, List<Monstre> monstres, int contadorPersonatge, int posMenorMonstre, int posMajorMonstre) {
        String frase =  null;

        frase = personatges.get(contadorPersonatge).accioBatalla(personatges, monstres, frase, posMenorMonstre, posMajorMonstre);

        return frase;
    }

    /**
     * Mètode que crida al mètode d'evolucionar de personatge durant l'aventura
     * @param personatges Llista de personatges de l'aventura
     * @param personatge Personatge que evoluciona
     * @param posPersonatge Posició on es troba el personatge que evoluciona
     * @return Retorna la frase generada durant l'evolució
     */
    public String evolucionaPersonatges(List<Personatge> personatges, Personatge personatge, int posPersonatge) {

        String frase = personatge.evolucionarPersonatge(personatges, posPersonatge);
        return frase;
    }
}
