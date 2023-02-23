package persistence.Personatges;

import business.Personatge.*;
import com.google.gson.*;
import persistence.PersistenceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que accedeix a les dades dels personatges a través del fitxer Json
 */
public class PersonatgesJsonDAO implements PersonatgesDAO{
    private final String FILE_PATH = "data/characters.json";
    private FileReader fr;

    /**
     * Mètode constructor de la classe
     * @throws PersistenceException la classe no es crea correctament perquè no es pot accedir al fitxer
     */
    public PersonatgesJsonDAO() throws PersistenceException {
        try {
            fr = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Error: The characters.json file can't be accessed.", e);
        }
    }
    /**
     * Mètode que llegeix i retorna la llista de personatges guardats al fitxer Json
     * @return llista de tipus Personatge amb els personatges del fitxer
     */
    public List<Personatge> readPersonatge() {
        FileReader reader;
        try {
            reader = new FileReader("data/characters.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JsonArray content = JsonParser.parseReader(reader).getAsJsonArray();

        List<Personatge> personatges = new ArrayList<>();

        for (JsonElement element : content) {
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
    }
    
    /**
     * Mètode que afegeix un nou personatge al fitxer Json
     * @param personatge personatge que es vol afegir
     */
    public void nouPersonatge(Personatge personatge) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

            List<Personatge> personatgesJSON = readPersonatge();

            personatgesJSON.add(personatge);

            FileWriter fw = new FileWriter(FILE_PATH);

            gson.toJson(personatgesJSON, fw);

            fw.flush();
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrar(String nom){
        try {
            List<Personatge> personatgesJson = readPersonatge();

            for (int i = 0; i < personatgesJson.size(); i++) {
                if(personatgesJson.get(i).getNom().equals(nom)){
                    personatgesJson.remove(i);
                }
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
            FileWriter fw = new FileWriter(FILE_PATH);

            gson.toJson(personatgesJson, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
