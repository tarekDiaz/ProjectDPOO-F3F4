package business.Monstre;

import business.Personatge.Personatge;
import persistence.Monstres.MonstresDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que utilitza monstres i treballa amb ells
 */
public class MonstreManager {
    private MonstresDAO monstresJsonDAO;

    /**
     * Mètode constructor
     * @param monstresJsonDAO Monstres de persistència
     */
    public MonstreManager(MonstresDAO monstresJsonDAO) {
        this.monstresJsonDAO = monstresJsonDAO;
    }

    /**
     * Mètode que crea una llista amb el nom i nivell de dificultat de tots els monstres
     * @return Retorna una llista de tipus String
     */
    public List<String> llistarMonstres(){
        List<Monstre> monstres = monstresJsonDAO.readMonstres();
        List<String> infoMonstres = new ArrayList<>();

        for (int i = 0; i < monstres.size(); i++) {
            infoMonstres.add(monstres.get(i).getNom() + " (" + monstres.get(i).getNivellDificultat() + ")");
        }

        return infoMonstres;
    }

    /**
     * Mètode que retorna un Monstre
     * @param posicio Posició on es troba el monstre a persistència
     * @return Retorna el monstre
     */
    public Monstre retornaMonstreComplert(int posicio) {
        List<Monstre> monstres = monstresJsonDAO.readMonstres();
        Monstre monstre = null;

        monstre = monstres.get(posicio-1);

        return monstre;
    }

    /**
     * Mètode que revisa si tots els monstres d'un combat estan morts
     * @param monstres Llista de monstres del combat
     * @return Retorna un boolean depenent de si estan tots morts o no
     */
    public boolean totalMonstersUnconscius (List<Monstre> monstres) {
        int mort = 0;
        boolean TMU = false;

        for (int i = 0; i < monstres.size(); i++) {
            if (monstres.get(i).getPdv() == 0) {
                mort++;
            }
        }
        if (mort == monstres.size()) {
            TMU = true;
        }
        return TMU;
    }

    /**
     * Mètode que retorna la posició de la llista on es troba el monstre amb menys vida
     * @param monstres Llista de monstres
     * @return Retorna la posició
     */
    public int posicioMonstreMenysHP (List<Monstre> monstres) {
        int posMenor = 0, menor = monstres.get(0).getPdv();
        for (int i=1; i<monstres.size(); i++) {
            if ((monstres.get(i).getPdv() < menor && monstres.get(i).getPdv() != 0) || menor == 0) {
                posMenor = i;
                menor = monstres.get(i).getPdv();
            }
        }
        return posMenor;
    }

    /**
     * Mètode que retorna la posició de la llista on es troba el monstre amb més vida
     * @param monstres Llista de monstres
     * @return Retorna la posició
     */
    public int posicioMonstreMesHP (List<Monstre> monstres) {
        int posMajor = 0, major = monstres.get(0).getPdv();
        for (int i=1; i<monstres.size(); i++) {
            if (monstres.get(i).getPdv() > major) {
                posMajor = i;
                major = monstres.get(i).getPdv();
            }
        }
        return posMajor;
    }

    /**
     * Mètode que crida al mètode de monstre que comprova si aquest està mort
     * @param monstre Monstre
     * @return Retorna un boolea depenent de si el monstra esta mort o no
     */
    public boolean estaInconscient (Monstre monstre) {
        boolean inconscient = monstre.estaInconscient();
        return inconscient;
    }

    /**
     * Mètode que calcula el mal que fa un monstre
     * @param monstre Monstre
     * @return Retorna el mal
     */
    public int damageMonstre (Monstre monstre) {
        String numeroString;
        int numeroInt;

        numeroString = monstre.getTipusDau().substring(1);
        numeroInt = Integer.parseInt(numeroString);

        int dau = (int) (Math.random() * (numeroInt)) + 1;

        return dau;
    }

    /**
     * Mètode que crida a un mètode de monstre que realiza l'acció d'atacar a un personatge.
     * @param monstres Llista monstres
     * @param personatges Llista personatges
     * @param contadorMonstre Posició del monstre que ataca
     * @param mal Mal que realitza el monstre
     * @return Retorna la frase generada durant l'acció
     */
    public String atacarFaseCombat(List<Monstre> monstres, List<Personatge> personatges, int contadorMonstre, int mal) {
        String frase;

        frase = monstres.get(contadorMonstre).atacarFaseCombat(monstres, personatges, contadorMonstre, mal);

        return frase;
    }

    /**
     * Mètode que comprova quins monstres són de dificultat "Boss" i els inicialitza (canvia de classe Monstre a Boss)
     * @param monstres Llista de monstres
     */
    public void inicialitzarBosses(List<Monstre> monstres) {
        List<Monstre> copiaLlista = new ArrayList<>(monstres);
        monstres.clear();
        for (int i=0; i<copiaLlista.size(); i++) {
            if (copiaLlista.get(i).getNivellDificultat().equals("Boss")) {
                monstres.add(new Boss(copiaLlista.get(i).getNom(), copiaLlista.get(i).getNivellDificultat(), copiaLlista.get(i).getExperiencia(), copiaLlista.get(i).getPdv(), copiaLlista.get(i).getIniciativa(), copiaLlista.get(i).getTipusDau(), copiaLlista.get(i).getTipusDeMal()));
            }
            else {
                monstres.add(new Monstre(copiaLlista.get(i).getNom(), copiaLlista.get(i).getNivellDificultat(), copiaLlista.get(i).getExperiencia(), copiaLlista.get(i).getPdv(), copiaLlista.get(i).getIniciativa(), copiaLlista.get(i).getTipusDau(), copiaLlista.get(i).getTipusDeMal()));
            }
        }
    }
}
