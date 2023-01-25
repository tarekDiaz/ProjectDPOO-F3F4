package persistence.Personatges;


import business.Personatge.Personatge;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import persistence.ApiHelper;
import persistence.PersistenceException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PersonatgesAPIDAO implements PersonatgesDAO{

    private final String PERSONATGE_URL = "https://balandrau.salle.url.edu/dpoo/S1-Project_46/characters";
    private ApiHelper ap;

    {
        try {
            ap = new ApiHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PersonatgesAPIDAO() throws PersistenceException {
        try{
            ap.getFromUrl(PERSONATGE_URL);
        }catch (IOException e){
            throw new PersistenceException("Couldn't connect to the remote server.", e);
        }
    }

    public void nouPersonatge(Personatge personatge){
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String personatgeJSON = gson.toJson(personatge);

        try {
            ap.postToUrl(PERSONATGE_URL, personatgeJSON);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Personatge> readPersonatge() {

        try {
            String personatgesString = ap.getFromUrl(PERSONATGE_URL);

            Gson gson = new Gson();
            JsonElement personatgesJSON = JsonParser.parseString(personatgesString);

            Type tipoLista = new TypeToken<List<Personatge>>(){}.getType();
            List<Personatge> personatges = gson.fromJson(personatgesJSON, tipoLista);

            return personatges;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrar(String nom){

        try {
            ap.deleteFromUrl(PERSONATGE_URL + "?name=" +nom);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
