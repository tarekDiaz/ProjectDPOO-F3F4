package business.Personatge;
import business.Monstre.Monstre;
import persistence.PersistenceException;
import persistence.Personatges.PersonatgesDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que utilitza els personatges i treballa amb ells
 */
public class PersonatgeManager {
    private final PersonatgesDAO personatgesJsonDAO;

    /**
     * Mètode constructor de la classe
     * @param personatgesJsonDAO personatges de persistència
     */
    public PersonatgeManager(PersonatgesDAO personatgesJsonDAO) {
        this.personatgesJsonDAO = personatgesJsonDAO;
    }

    /**
     * Mètode que crea un nou personatge i l'envia a la classe de persistència per a guardar-lo
     * @param nom nom del personatge
     * @param player nom del jugador
     * @param nivell nivell del personatge
     * @param cos estadística cos del personatge
     * @param ment estadística ment del personatge
     * @param esperit estadística esperit del personatge
     * @param classe classe del personatge
     * @throws PersistenceException
     */
    public void crearPersonatge (String nom, String player, int nivell, int cos, int ment, int esperit, String classe) throws PersistenceException {
        Personatge personatge = null;
        int exp = (nivell * 100) - 100;
        if (classe.equals("Adventurer")) {
            personatge = new Aventurer(nom, player, exp, cos, ment, esperit, classe);
        }
        if (classe.equals("Warrior")) {
            personatge = new Guerrer(nom, player, exp, cos, ment, esperit, classe);
        }
        if (classe.equals("Champion")) {
            personatge = new Campio(nom, player, exp, cos, ment, esperit, classe);
        }
        if (classe.equals("Cleric")) {
            personatge = new Clergue(nom, player, exp, cos, ment, esperit, classe);
        }
        if (classe.equals("Paladin")) {
            personatge = new Paladi(nom, player, exp, cos, ment, esperit, classe);
        }
        if (classe.equals("Mage")) {
            personatge = new Mag(nom, player, exp, cos, ment, esperit, classe);
        }
        personatgesJsonDAO.nouPersonatge(personatge);
    }

    /**
     * Mètode que retorna una llista amb tots els personatges del Json
     * @return Retorna una llista amb tots els personatges del Json
     * @throws PersistenceException
     */
    public List<Personatge> llegirPersonatges() throws PersistenceException {
        List<Personatge> personatgesList = personatgesJsonDAO.readPersonatge();
        return personatgesList;
    }

    /**
     * Mètode que inicialitza els personatges. Els hi posa el nivell, els punts de vida màxims i la iniciativa, ja que
     * aquestes caracteristiques no es troben a la informació de persistència.
     * @param personatges la llista de personatges del programa que es volen inicialitzar
     */
    public void inicialitzaPersonatges (List<Personatge> personatges) {
        for (int i = 0; i < personatges.size(); i++) {
            personatges.get(i).inicialitzaPersonatges();
        }
    }

    /**
     * Mètode que envia el nom del personatge a la classe de persistència per borrar un personatge
     * @param nom nom del personatge a esborrar
     * @throws PersistenceException
     */
    public void borrarPersonatge (String nom) throws PersistenceException {
        personatgesJsonDAO.borrar(nom);
    }

    /**
     * Mètode que genera un número aleatori del 1 al 6
     * @return el numero random que ha sortit
     */
    public int tirarDauStats () {
        int dau = (int) (Math.random() * (6)) + 1;
        return dau;
    }

    /**
     * Mètode que suma els nombres de dos daus
     * @param dau1 nombre del dau 1
     * @param dau2 nombre del dau 2
     * @return Retorna la suma dels daus
     */
    public int sumarDausStats(int dau1, int dau2) {
        int stat = dau1 + dau2;
        return stat;
    }

    /**
     * Mètode que genera el nombre de les estadístiques inicials del personatge a partir de la suma dels daus
     * @param sumaDaus numero de la suma dels daus
     * @return numero final de la estadística del personatge
     */
    public int generarStats(int sumaDaus) {
        int estadistica = 0;
        if (sumaDaus == 2) {
            estadistica = -1;
        }
        if (sumaDaus > 2 && sumaDaus < 6) {
            estadistica = 0;
        }
        if (sumaDaus > 5 && sumaDaus < 10) {
            estadistica = 1;
        }
        if (sumaDaus > 9 && sumaDaus < 12) {
            estadistica = 2;
        }
        if (sumaDaus == 12) {
            estadistica = 3;
        }
        return estadistica;
    }

    /**
     * Mètode que crida al mètode de personatge que calcula la iniciativa
     * @param personatge personatge que es vol calcular la iniciativa
     * @return numero de la iniciativa
     */
    public int calcularIniciativa (Personatge personatge) {
        int iniciativa = personatge.calcularIniciativa();
        return iniciativa;
    }

    /**
     * Mètode que crida al mètode de personatge que comprova si està inconcient o no
     * @param personatge personatge al qual es vol comprovar
     * @return boolean que serà true si està inconscient i false si no ho està.
     */
    public boolean estaInconscient(Personatge personatge) {
        boolean x = personatge.estaInconscient();

        return x;
    }

    /**
     * Mètode que suma l'experiència d'un personatge i comprova si ha pujat de nivell o no.
     * @param personatge personatge al qual es vol sumar l'experiència.
     * @param experienciaMonstre experiència que li dona el monstre
     * @return un boolean que és true quan un personatge ha pujat de nivell
     */
    public boolean sumarExperiencia(Personatge personatge, int experienciaMonstre) {
        int nivellPrevi = personatge.getNivell();
        boolean x = false;

        int experiencia = experienciaMonstre + personatge.getExperiencia();
        personatge.setExperiencia(experiencia);
        int nivell = (personatge.getExperiencia() / 100) + 1;
        personatge.setNivell(nivell);
        if (personatge.getNivell() >= 10) {
            personatge.setNivell(10);
        }

        int nivellPosterior = personatge.getNivell();

        if (nivellPrevi < nivellPosterior) {
            calcularPdvMax(personatge);
            x = true;
        }
        return x;
    }

    /**
     * Mètode que calcula els punts de vida màxims d'un personatge i els actualitza
     * @param personatge personatge al qual es vol calcular
     */
    public void calcularPdvMax (Personatge personatge) {
        int calculPdv = personatge.calcularPdvMax();
        personatge.setPdvMax(calculPdv);
        personatge.setPdvActual(calculPdv);
    }

    /**
     * Mètode que crida al mètode de personatge que realitza l'acció de suport
     * @param personatges Llista de personatges
     * @return Retorna la frase generada per l'acció
     */
    public List<String> suportPersonatge(List<Personatge> personatges) {
        List<String> frase = new ArrayList<>();
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).suportPersonatge(personatges, frase);
        }
        return frase;
    }

    /**
     * Mètode que crida al mètode de personatge que realitza l'acció del descans curt si el personatge no està inconscient.
     * @param personatgesAventura Llista de personatges de l'aventura
     * @param personatgesOrdenats Llista de persoantges de l'aventura ordenada
     * @return Retorna la frase generada per l'acció
     */
    public List<String> descansCurt(List<Personatge> personatgesAventura, List<Personatge> personatgesOrdenats) {
        List<String> frase = new ArrayList<>();

        for (int q=0; q<personatgesOrdenats.size();q++) {
            for (int n = 0; n < personatgesAventura.size(); n++) {
                if (personatgesAventura.get(q).getNom().equals(personatgesOrdenats.get(n).getNom())) {
                    if (estaInconscient(personatgesOrdenats.get(n))) {
                        frase.add(personatgesOrdenats.get(n).getNom() + " is unconscious.");
                    } else {
                        personatgesAventura.get(q).curaDescansCurt(personatgesAventura, frase);
                    }
                }
            }
        }
        return frase;
    }

    /**
     * Retorna un personatge del Json
     * @param nomPersonatge Nom del personatge a retornar
     * @return Personatge complert
     * @throws PersistenceException
     */
    public Personatge retornaPersonatgeComplert(String nomPersonatge) throws PersistenceException {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
        Personatge personatge = null;

        for (int i = 0; i < personatges.size(); i++) {
            if (nomPersonatge.equals(personatges.get(i).getNom())) {
                personatge = personatges.get(i);
            }
        }
        return personatge;
    }

    /**
     * Mètode que retorna una llista amb els noms de tots els personatges de persistència
     * @return Retorna una llista de noms
     * @throws PersistenceException
     */
    public List<String> llistarPersonatges() throws PersistenceException {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
        List<String> noms = new ArrayList<>();

        for (int i = 0; i < personatges.size(); i++) {
            noms.add(personatges.get(i).getNom());
        }
        return noms;
    }

    /**
     * Mètode que retorna una llista amb els noms dels personatges d'un jugador en concret
     * @param player Nom del jugador
     * @return Retorna una llista de noms
     * @throws PersistenceException
     */
    public List<String> llistarPersonatgesPlayer(String player) throws PersistenceException {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
        List<String> noms = new ArrayList<>();

        for (int i = 0; i < personatges.size(); i++) {
            if (player.equals(personatges.get(i).getNomJugador())) {
                noms.add(personatges.get(i).getNom());
            }
        }
        return noms;
    }

    /**
     * Mètode que comprova si tots els personatges estan inconscients
     * @param personatges Llista de personatges
     * @return Retorna un boolean depenent de si tots els personatges estan morts o no
     */
    public boolean totalPartyUnconscius (List<Personatge> personatges) {
        int mort = 0;
        boolean TPU = false;

        for (int i=0; i<personatges.size(); i++) {
            if (personatges.get(i).getPdvActual() == 0) {
                mort++;
            }
        }
        if (mort == personatges.size()) {
            TPU = true;
        }
        return TPU;
    }

    /**
     * Mètode que revisa que el nombre de personatges a persistència sigui major o igual a tres
     * @return Retorna un boolean depenent de si el nombre de personatges es major o igual a tres o no
     * @throws PersistenceException
     */
    public boolean checkPersonatgesSize () throws PersistenceException {
        boolean check = true;
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
        if (personatges.size() < 3) {
            check = false;
        }
        return check;
    }

    /**
     * Mètode que retorna un String amb la classe del personatge
     * @param classe classe del personatge
     * @param nivell nivell del personatge
     * @return Retorna la classe del personatge modificada
     */
    public String classeDepenentDeLvl(String classe, int nivell) {
        if (classe.equals("Adventurer")) {
            if (nivell < 4) {
                classe = "Adventurer";
            }
            if (nivell > 3 && nivell < 8) {
                classe = "Warrior";
            }
            if (nivell > 7) {
                classe = "Champion";
            }
        }
        if (classe.equals("Cleric")) {
            if (nivell < 5) {
                classe = "Cleric";
            }
            if (nivell > 4) {
                classe = "Paladin";
            }
        }
        return classe;
    }
}



