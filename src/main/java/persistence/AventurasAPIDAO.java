package persistence;

import business.Aventura;
import business.Personatge;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class AventurasAPIDAO {

    private final String ADVENTURE_URL = "https://balandrau.salle.url.edu/dpoo/S1-Project_46/adventures";
    private ApiHelper ap;

    {
        try {
            ap = new ApiHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AventurasAPIDAO(){

    }
    public List<Aventura> readAventuraFromJson() {
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

    public void writeAventuraJson (Aventura aventura){
        Gson gson = new Gson();

        String aventuraJSON = gson.toJson(aventura);

        try {
            ap.postToUrl(ADVENTURE_URL, aventuraJSON);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
