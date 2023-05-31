package persistence.Aventuras;

import business.Aventura.Aventura;
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
 * Classe que accedeix a les dades de les aventures a través de l'API
 */
public class AventurasAPIDAO implements AventurasDAO{

    private final String ADVENTURE_URL = "https://balandrau.salle.url.edu/dpoo/S1-Project_46/adventures";
    private final ApiHelper ap;

    /**
     * Mètode constructor de la classe
     * @throws PersistenceException la classe no es crea correctament perquè no es pot accedir a la API
     */
    public AventurasAPIDAO() throws PersistenceException {
        try{
            ap = new ApiHelper();
            ap.getFromUrl(ADVENTURE_URL);
        }catch (IOException e){
            throw new PersistenceException("Couldn't connect to the remote server.", e);
        }
    }

    /**
     * Mètode que retorna la llista de les aventures que es troben a l'API
     * @return llista de classe Aventura amb les aventures
     */
    public List<Aventura> readAventura() {
        try {
            String personatgesString = ap.getFromUrl(ADVENTURE_URL);

            Gson gson = new Gson();
            JsonElement aventurasJSON = JsonParser.parseString(personatgesString);

            Type tipoLista = new TypeToken<List<Aventura>>(){}.getType();
            List<Aventura> aventuras = gson.fromJson(aventurasJSON, tipoLista);

            return aventuras;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Mètode que escriu una nova aventura a l'API
     * @param aventura aventura que es vol afegir al Cloud
     */
    public void writeAventura (Aventura aventura){
        Gson gson = new Gson();

        String aventuraJSON = gson.toJson(aventura);

        try {
            ap.postToUrl(ADVENTURE_URL, aventuraJSON);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
