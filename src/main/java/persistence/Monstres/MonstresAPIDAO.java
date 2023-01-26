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

public class MonstresAPIDAO implements MonstresDAO{

    private final String MONSTRES_URL = "https://balandrau.salle.url.edu/dpoo/shared/monsters";
    private ApiHelper ap;

    {
        try {
            ap = new ApiHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MonstresAPIDAO() throws PersistenceException {
        try{
            ap.getFromUrl(MONSTRES_URL);
        }catch (IOException e){
            throw new PersistenceException("Couldn't connect to the remote server.", e);
        }
    }

    public List<Monstre> readMonstres() {

        try {
            String monstresString = ap.getFromUrl(MONSTRES_URL);

            Gson gson = new Gson();
            JsonElement monstresJSON = JsonParser.parseString(monstresString);

            Type tipoLista = new TypeToken<List<Monstre>>(){}.getType();
            List<Monstre> monstres = gson.fromJson(monstresJSON, tipoLista);

            return monstres;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
