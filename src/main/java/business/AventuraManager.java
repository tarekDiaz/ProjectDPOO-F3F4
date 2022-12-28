package business;

import persistence.AventurasJsonDAO;
import persistence.MonstresJsonDAO;
import persistence.PersonatgesJsonDAO;

import java.util.*;

public class AventuraManager {
    private AventurasJsonDAO aventurasJsonDAO;
    private MonstresJsonDAO monstresJsonDAO;
    private PersonatgesJsonDAO personatgesJsonDAO;

    public AventuraManager(AventurasJsonDAO aventurasJsonDAO, MonstresJsonDAO monstresJsonDAO, PersonatgesJsonDAO personatgesJsonDAO) {
        this.aventurasJsonDAO = aventurasJsonDAO;
        this.monstresJsonDAO = monstresJsonDAO;
        this.personatgesJsonDAO = personatgesJsonDAO;
    }

    public Aventura crearAventura(String nom, List<Combat> combats, int numCombats) {
        Aventura aventura = new Aventura(nom, combats, null);
        for (int i = 0; i < numCombats; i++) {
            aventura.combats.add(new Combat(new ArrayList<Monstre>()));
        }
        return aventura;
    }

    public void afegirMonstreCombat(Aventura aventura, int posicioMonstre, int quantitatMonstres, int numCombat) {
        List<Monstre> monstres = monstresJsonDAO.readMonstresFromJson();
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

    public List<String> generarLlistaMonstres(Combat combat) {
        List<Monstre> monstres = monstresJsonDAO.readMonstresFromJson();
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

    public List<String> generarLlistaMonstres2 (Combat combat) {
        List<Monstre> monstres = monstresJsonDAO.readMonstresFromJson();
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

    public void borrarMonstreCombat (Aventura aventura, int monstreDelete, int numCombat) {
        List<String> infoMonstresCombat = generarLlistaMonstres(aventura.getCombats().get(numCombat));
        String stringMonstre;

        stringMonstre = infoMonstresCombat.get(monstreDelete-1);

        StringTokenizer tokens = new StringTokenizer(stringMonstre);
        String nomMonstre = tokens.nextToken();
        for (int i=0; i<aventura.combats.get(numCombat).monstres.size(); i++) {
            if (aventura.combats.get(numCombat).monstres.get(i).getNom().equals(nomMonstre)) {
                aventura.combats.get(numCombat).monstres.remove(i);
                i--;
            }
        }
    }
    public List<String> llistarAventuras () {
        List<Aventura> aventuras = aventurasJsonDAO.readAventuraFromJson();
        List<String> noms = new ArrayList<>();

        for (int i = 0; i < aventuras.size(); i++) {
            noms.add(aventuras.get(i).getNom());
        }
        return noms;
    }
    public Aventura retornaAventuraComplerta(int numAventura) {
        List<Aventura> aventuras = aventurasJsonDAO.readAventuraFromJson();
        Aventura aventura = null;

        aventura = aventuras.get(numAventura-1);
        return aventura;
    }

    /*public void creaLlistaPersonatges (Aventura aventura, int numPersonatges) {
        for (int i=0; i<numPersonatges; i++) {
            aventura.personatges.add();
        }
    }*/

    public void afegirPersonatgeAventura (Aventura aventura, int numPersonatge) {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatgeFromJson();

        aventura.personatges.add(personatges.get(numPersonatge));
    }

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

    public void ordenarMonstresSegonsIniciatives (List<Monstre> monstres, Monstre monstre) {

        monstres.add(monstre);
        if (monstres.size() > 1) {
            Collections.sort(monstres, new Comparator<Monstre>() {
                public int compare(Monstre m1, Monstre m2) {
                    return new Integer (m2.getIniciativa()).compareTo(new Integer(m1.getIniciativa()));
                }
            });
        }
    }

    public List<String> mostrarOrdreIniciativas (List<Personatge> personatges, List<Monstre> monstres) {
        List<String> llistaOrdenadaFinal = new ArrayList<>();
        int i, contadorPersonatges = 0, contadorMonstres = 0;

        for (i=0; i<(personatges.size() + monstres.size()); i++) {
            if (personatges.size() == contadorPersonatges) {
                llistaOrdenadaFinal.add("- " + monstres.get(contadorMonstres).getIniciativa() + " " + monstres.get(contadorMonstres).getNom());
                contadorMonstres++;
            } else {
                if (monstres.size() == contadorMonstres) {
                    llistaOrdenadaFinal.add("- " + personatges.get(contadorPersonatges).getIniciativa() + " " + personatges.get(contadorPersonatges).getNom());
                    contadorPersonatges++;
                }
                else {
                    if (personatges.get(contadorPersonatges).getIniciativa() > monstres.get(contadorMonstres).getIniciativa()) {
                        llistaOrdenadaFinal.add("- " + personatges.get(contadorPersonatges).getIniciativa() + " " + personatges.get(contadorPersonatges).getNom());
                        contadorPersonatges++;
                    } else {
                        if (personatges.get(contadorPersonatges).getIniciativa() > monstres.get(contadorMonstres).getIniciativa()) {
                            llistaOrdenadaFinal.add("- " + monstres.get(contadorMonstres).getIniciativa() + " " + monstres.get(contadorMonstres).getNom());
                            contadorMonstres++;
                        }
                    }
                }
            }
        }
        return llistaOrdenadaFinal;
    }

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
                    if (personatges.get(contadorPersonatges).getIniciativa() > monstres.get(contadorMonstres).getIniciativa()) {
                        nomsOrdenats.add(personatges.get(contadorPersonatges).getNom());
                        contadorPersonatges++;
                    } else {
                        if (personatges.get(contadorPersonatges).getIniciativa() > monstres.get(contadorMonstres).getIniciativa()) {
                            nomsOrdenats.add(monstres.get(contadorMonstres).getNom());
                            contadorMonstres++;
                        }
                    }
                }
            }
        }
        return nomsOrdenats;
    }

    public List<String> showPartyHP (List<Personatge> personatges) {
        List<String> llista = new ArrayList<>();

        for (int i=0; i<personatges.size(); i++) {
            llista.add("- " + personatges.get(i).getNom() + "   " + personatges.get(i).getPdvActual() + " / " + personatges.get(i).getPdvMax() + " hit points");
        }
        return llista;
    }

    public void guardarAventuraJSON (Aventura aventura){
        aventurasJsonDAO.writeAventuraJson(aventura);
    }

}