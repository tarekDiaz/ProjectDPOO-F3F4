package persistence.Personatges;


import business.Personatge.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import persistence.ApiHelper;
import persistence.PersistenceException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe que accedeix a les dades dels personatges a través de l'API
 */
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
    /**
     * Mètode constructor de la classe
     * @throws PersistenceException la classe no es crea correctament perquè no es pot accedir a la API
     */
    public PersonatgesAPIDAO() throws PersistenceException {
        try{
            ap.getFromUrl(PERSONATGE_URL);
        }catch (IOException e){
            throw new PersistenceException("Couldn't connect to the remote server.", e);
        }
    }

    /**
     * Mètode que afegeix un nou personatge a l'API
     * @param personatge personatge que es vol afegir
     */
    public void nouPersonatge(Personatge personatge){
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

        String personatgeJSON = gson.toJson(personatge);

        try {
            ap.postToUrl(PERSONATGE_URL, personatgeJSON);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Mètode que llegeix i retorna la llista de personatges guardats a l'API
     * @return llista de tipus Personatge amb els personatges del Cloud
     */
    public List<Personatge> readPersonatge() {

        try {
            String personatgesString = ap.getFromUrl(PERSONATGE_URL);

            Gson gson = new Gson();
            JsonArray personatgesJSON = JsonParser.parseString(personatgesString).getAsJsonArray();

            //Type tipoLista = new TypeToken<List<Personatge>>(){}.getType();
            //List<Personatge> personatges = gson.fromJson(personatgesJSON, tipoLista);
            List<Personatge> personatges = new ArrayList<>();


            for (JsonElement element : personatgesJSON) {
                JsonObject object = element.getAsJsonObject();
                String nom = object.get("name").getAsString();
                String player = object.get("player").getAsString();
                int exp = object.get("xp").getAsInt();
                int cos = object.get("body").getAsInt();
                int ment = object.get("mind").getAsInt();
                int esperit = object.get("spirit").getAsInt();
                String classe = object.get("class").getAsString();

                if (classe.equals("Adventurer")) {
                    personatges.add(new Aventurer(nom, player, exp, cos, ment, esperit, classe));
                }
                if (classe.equals("Warrior")) {
                    personatges.add(new Guerrer(nom, player, exp, cos, ment, esperit, classe));
                }
                if (classe.equals("Champion")) {
                    personatges.add(new Campio(nom, player, exp, cos, ment, esperit, classe));
                }
                if (classe.equals("Cleric")) {
                    personatges.add(new Clergue(nom, player, exp, cos, ment, esperit, classe));
                }
                if (classe.equals("Paladin")) {
                    personatges.add(new Paladi(nom, player, exp, cos, ment, esperit, classe));
                }
                if (classe.equals("Mage")) {
                    personatges.add(new Mag(nom, player, exp, cos, ment, esperit, classe));
                }
            }
            return personatges;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mètode que borra un personatge a partir del nom del personatge
     * @param nom nom del personatge que es vol borrar
     */
    public void borrar(String nom){

        try {
            ap.deleteFromUrl(PERSONATGE_URL + "?name=" +nom);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
