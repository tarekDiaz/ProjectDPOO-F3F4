package business;
import persistence.Personatges.PersonatgesJsonDAO;

import java.util.ArrayList;
import java.util.List;

public class PersonatgeManager {
    private PersonatgesJsonDAO personatgesJsonDAO;

    public PersonatgesJsonDAO getPersonatgesJsonDAO() {
        return personatgesJsonDAO;
    }

    public PersonatgeManager(PersonatgesJsonDAO personatgesJsonDAO) {
        this.personatgesJsonDAO = personatgesJsonDAO;
    }

    public Personatge crearPersonatge (String nom, String player, int nivell, int cos, int ment, int esperit, String classe){
        int exp = (nivell * 100) - 100;
        Personatge personatge = new Personatge(nom, player, exp, cos, ment, esperit, classe);
        personatgesJsonDAO.nouPersonatge(personatge);
        return personatge;
    }

    public List<Personatge> llegirPersonatges(){
        List<Personatge> personatgesList = personatgesJsonDAO.readPersonatge();
        return personatgesList;
    }

    public void inicialitzaPersonatges (List<Personatge> personatges) {
        for (int i = 0; i < personatges.size(); i++) {
            personatges.get(i).setNivell((personatges.get(i).getExperiencia() / 100) + 1);
            calcularPdvMax(personatges.get(i));
            personatges.get(i).setIniciativa(calcularIniciativa(personatges.get(i)));
        }
    }
    public void inicialitzaPersonatgesAmbClasse (List<Personatge> personatges) {
        List<Personatge> copiaLlista = new ArrayList<>(personatges);
        //copiaLlista = personatges;
        personatges.clear();
        for (int i=0; i<copiaLlista.size(); i++) {
            if (copiaLlista.get(i).getClasse().equals("Adventurer")) {
                if (copiaLlista.get(i).getNivell() < 4) {
                    personatges.add(new Aventurer(copiaLlista.get(i).getNom(), copiaLlista.get(i).getNomJugador(), copiaLlista.get(i).getNivell(), copiaLlista.get(i).getCos(), copiaLlista.get(i).getMent(), copiaLlista.get(i).getEsperit(), copiaLlista.get(i).getClasse(), copiaLlista.get(i).getExperiencia(), copiaLlista.get(i).getPdvMax(), copiaLlista.get(i).getPdvActual(), copiaLlista.get(i).getIniciativa(), "Physical"));
                }
                if (copiaLlista.get(i).getNivell() > 3 && copiaLlista.get(i).getNivell() < 8) {
                    personatges.add(new Guerrer(copiaLlista.get(i).getNom(), copiaLlista.get(i).getNomJugador(), copiaLlista.get(i).getNivell(), copiaLlista.get(i).getCos(), copiaLlista.get(i).getMent(), copiaLlista.get(i).getEsperit(), copiaLlista.get(i).getClasse(), copiaLlista.get(i).getExperiencia(), copiaLlista.get(i).getPdvMax(), copiaLlista.get(i).getPdvActual(), copiaLlista.get(i).getIniciativa(), "Physical"));
                }
                if (copiaLlista.get(i).getNivell() > 7) {
                    personatges.add(new Campio(copiaLlista.get(i).getNom(), copiaLlista.get(i).getNomJugador(), copiaLlista.get(i).getNivell(), copiaLlista.get(i).getCos(), copiaLlista.get(i).getMent(), copiaLlista.get(i).getEsperit(), copiaLlista.get(i).getClasse(), copiaLlista.get(i).getExperiencia(), copiaLlista.get(i).getPdvMax(), copiaLlista.get(i).getPdvActual(), copiaLlista.get(i).getIniciativa(), "Physical"));
                }
            }
            if (copiaLlista.get(i).getClasse().equals("Cleric")) {
                if (copiaLlista.get(i).getNivell() < 5) {
                    personatges.add(new Clergue(copiaLlista.get(i).getNom(), copiaLlista.get(i).getNomJugador(), copiaLlista.get(i).getNivell(), copiaLlista.get(i).getCos(), copiaLlista.get(i).getMent(), copiaLlista.get(i).getEsperit(), copiaLlista.get(i).getClasse(), copiaLlista.get(i).getExperiencia(), copiaLlista.get(i).getPdvMax(), copiaLlista.get(i).getPdvActual(), copiaLlista.get(i).getIniciativa(), "Psychical"));
                }
                if (copiaLlista.get(i).getNivell() > 4) {
                    personatges.add(new Paladi(copiaLlista.get(i).getNom(), copiaLlista.get(i).getNomJugador(), copiaLlista.get(i).getNivell(), copiaLlista.get(i).getCos(), copiaLlista.get(i).getMent(), copiaLlista.get(i).getEsperit(), copiaLlista.get(i).getClasse(), copiaLlista.get(i).getExperiencia(), copiaLlista.get(i).getPdvMax(), copiaLlista.get(i).getPdvActual(), copiaLlista.get(i).getIniciativa(), "Psychical"));
                }
            }
            if (copiaLlista.get(i).getClasse().equals("Mage")) {
                personatges.add(new Mag(copiaLlista.get(i).getNom(), copiaLlista.get(i).getNomJugador(), copiaLlista.get(i).getNivell(), copiaLlista.get(i).getCos(), copiaLlista.get(i).getMent(), copiaLlista.get(i).getEsperit(), copiaLlista.get(i).getClasse(), copiaLlista.get(i).getExperiencia(), copiaLlista.get(i).getPdvMax(), copiaLlista.get(i).getPdvActual(), copiaLlista.get(i).getIniciativa(), 0, "Magical"));
            }
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
        int iniciativa = personatge.calcularIniciativa();
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

    public void rebreMalPersonatge(Personatge personatge, int mal, int dau, Monstre monstre) {
        if (dau == 1) {
            mal = 0;
        }
        if (dau == 10) {
            mal = mal * 2;
        }

        mal = personatge.reduirMal(mal, monstre);

        int pdvPostAtac = personatge.getPdvActual() - mal;
        personatge.setPdvActual(pdvPostAtac);
        if (personatge.getPdvActual() < 0) {
            personatge.setPdvActual(0);
        }
    }

    public int curarPersonatge(Personatge personatge) {
        int cura = personatge.curarPersonatge();
        return cura;
    }

    public List<String> suportPersonatge(List<Personatge> personatges) {
        List<String> frase = new ArrayList<>();
        for (int k = 0; k<personatges.size(); k++) {
            personatges.get(k).suportPersonatge(personatges, frase);
        }
        return frase;
    }

    public List<String> descansCurt(List<Personatge> personatgesAventura, List<Personatge> personatgesOrdenats) {
        List<String> frase = new ArrayList<>();

        for (int q=0; q<personatgesOrdenats.size();q++) {
            for (int n = 0; n < personatgesAventura.size(); n++) {
                if (personatgesAventura.get(q).getNom().equals(personatgesOrdenats.get(n).getNom())) {
                    if (estaInconscient(personatgesOrdenats.get(n))) {
                        frase.add(personatgesOrdenats.get(n).getNom() + " is unconscious.");
                    } else {
                        personatgesOrdenats.get(n).curaDescansCurt(personatgesOrdenats, frase);
                    }
                }
            }
        }
        return frase;
    }


    public Personatge retornaPersonatgeComplert(String nomPersonatge) {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
        Personatge personatge = null;

        for (int i = 0; i < personatges.size(); i++) {
            if (nomPersonatge.equals(personatges.get(i).getNom())) {
                personatge = personatges.get(i);
            }
        }
        return personatge;
    }

    public List<String> llistarPersonatges() {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
        List<String> noms = new ArrayList<>();

        for (int i = 0; i < personatges.size(); i++) {
            noms.add(personatges.get(i).getNom());
        }
        return noms;
    }

    public List<String> llistarPersonatgesPlayer(String player) {
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
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
        List<Personatge> personatges = personatgesJsonDAO.readPersonatge();
        if (personatges.size() < 3) {
            check = false;
        }
        return check;
    }

    public String classeDepenentDeLvl(Personatge personatge, int nivell) {
        String classe = null;
        if (personatge.getClasse().equals("Aventurer")) {
            if (personatge.getNivell() < 4) {
                classe = "Aventurer";
            }
            if (personatge.getNivell() > 3 && personatge.getNivell() < 8) {
                classe = "Guerrer";
            }
            if (personatge.getNivell() > 7) {
                classe = "Campio";
            }
        }
        if (personatge.getClasse().equals("Clergue")) {
            if (personatge.getNivell() < 5) {
                classe = "Clergue";
            }
            if (personatge.getNivell() > 4) {
                classe = "Paladi";
            }
        }
        if (personatge.getClasse().equals("Mag")) {
            classe = "Mag";
        }
        return classe;
    }
}



