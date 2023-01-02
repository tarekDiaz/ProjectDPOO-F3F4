package business;
import persistence.PersonatgesJsonDAO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PersonatgeManager {
    private PersonatgesJsonDAO personatgesJsonDAO;

    public PersonatgesJsonDAO getPersonatgesJsonDAO() {
        return personatgesJsonDAO;
    }

    public PersonatgeManager(PersonatgesJsonDAO personatgesJsonDAO) {
        this.personatgesJsonDAO = personatgesJsonDAO;
    }

    public void crearPersonatge (String nom, String player, int nivell, int cos, int ment, int esperit){
        int exp = (nivell * 100) - 100;
        Personatge personatge = new Personatge(nom, player, exp, cos, ment, esperit, "Adventurer");
        personatgesJsonDAO.nouPersonatge(personatge);
    }

    public void inicialitzaPersonatges (List<Personatge> personatges) {
        for (int i = 0; i < personatges.size(); i++) {
            personatges.get(i).setNivell((personatges.get(i).getExperiencia() / 100) + 1);
            calcularPdvMax(personatges.get(i));
            personatges.get(i).setIniciativa(calcularIniciativa(personatges.get(i)));
        }
    }

    public void borrarPersonatge (String nom){
        personatgesJsonDAO.borrar(nom);
    }

    public int tirarDauStats () {
        int dau = (int) (Math.random() * (6)) + 1;
        return dau;
    }

    public int sumarDausStats(int dau1, int dau2) {
        int stat = dau1 + dau2;
        return stat;
    }

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

    public int calcularIniciativa (Personatge personatge) {
        int n = (int) (Math.random() * (12)) + 1;
        int iniciativa = n + personatge.getEsperit();
        return iniciativa;
    }

    public boolean estaInconscient(Personatge personatge) {
        boolean x = false;

        if (personatge.getPdvActual() == 0) {
            x = true;
        }

        return x;
    }

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

    //canviat a la fase 4
    public void calcularPdvMax (Personatge personatge) {
        int calculPdv = personatge.calcularPdvMax();
        personatge.setPdvMax(calculPdv);
        personatge.setPdvActual(calculPdv);
    }

    //canviat a la fase 4
    public int atacarPersonatge(Personatge personatge) {
        int mal;
        mal = personatge.atacarPersonatge();
        return mal;
    }

    public void rebreMalPersonatge(Personatge personatge, int mal, int dau) {
        if (dau == 1) {
            mal = 0;
        }
        if (dau == 10) {
            mal = mal * 2;
        }

        int pdvPostAtac = personatge.getPdvActual() - mal;
        personatge.setPdvActual(pdvPostAtac);
        if (personatge.getPdvActual() < 0) {
            personatge.setPdvActual(0);
        }
    }

    public int curarPersonatge(Personatge personatge) {
        int cura = (int) (Math.random() * (8)) + 1 + personatge.getMent();
        int pdvPostCura = personatge.getPdvActual() + cura;
        if (pdvPostCura > personatge.getPdvMax()) {
            personatge.setPdvActual(personatge.getPdvMax());
        } else {
            personatge.setPdvActual(pdvPostCura);
        }
        return cura;
    }

    //falta pensar suport de campio
    public void suportPersonatge(Personatge personatge) {
        int nouEsperit = personatge.suportPersonatge();
        personatge.setEsperit(nouEsperit);
    }

    public Personatge retornaPersonatgeComplert(String nomPersonatge) {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatgeFromJson();
        Personatge personatge = null;

        for (int i = 0; i < personatges.size(); i++) {
            if (nomPersonatge.equals(personatges.get(i).getNom())) {
                personatge = personatges.get(i);
            }
        }
        return personatge;
    }

    public List<String> llistarPersonatges() {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatgeFromJson();
        List<String> noms = new ArrayList<>();

        for (int i = 0; i < personatges.size(); i++) {
            noms.add(personatges.get(i).getNom());
        }
        return noms;
    }

    public List<String> llistarPersonatgesPlayer(String player) {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatgeFromJson();
        List<String> noms = new ArrayList<>();

        for (int i = 0; i < personatges.size(); i++) {
            if (player.equals(personatges.get(i).getNomJugador())) {
                noms.add(personatges.get(i).getNom());
            }
        }
        return noms;
    }

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

    public boolean checkPersonatgesSize () {
        boolean check = true;
        List<Personatge> personatges = personatgesJsonDAO.readPersonatgeFromJson();
        if (personatges.size() < 3) {
            check = false;
        }
        return check;
    }

}



