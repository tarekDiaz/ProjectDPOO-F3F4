package persistence.Monstres;

import business.Monstre.Monstre;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import persistence.ApiHelper;
import persistence.PersistenceException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
/**
 * Classe que accedeix a les dades dels monstres a través de l'API
 */
public class MonstresAPIDAO implements MonstresDAO{

    private final String MONSTRES_URL = "https://balandrau.salle.url.edu/dpoo/shared/monsters";
    private final ApiHelper ap;

    /**
     * Mètode constructor de la classe
     * @throws PersistenceException la classe no es crea correctament perquè no es pot accedir a la API
     */
    public MonstresAPIDAO() throws PersistenceException {
        try{
            ap = new ApiHelper();
            ap.getFromUrl(MONSTRES_URL);
        }catch (IOException e){
            throw new PersistenceException("Couldn't connect to the remote server.", e);
        }
    }

    /**
     * Mètode que llegeix i retorna els monstres que es troben a l'API
     * @return llista de classe Monstre dels monstres
     */
    public List<Monstre> readMonstres() throws PersistenceException {

        try {
            String monstresString = ap.getFromUrl(MONSTRES_URL);

            Gson gson = new Gson();
            JsonElement monstresJSON = JsonParser.parseString(monstresString);

            Type tipoLista = new TypeToken<List<Monstre>>(){}.getType();
            List<Monstre> monstres = gson.fromJson(monstresJSON, tipoLista);

            return monstres;
        } catch (IOException e) {
            throw new PersistenceException("Couldn't connect to the remote server.",e);
        }
    }
}
