package persistence.Personatges;

import business.Personatge.Personatge;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import persistence.PersistenceException;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
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
        FileReader reader = null;
        try {
            reader = new FileReader("data/characters.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Personatge>>(){}.getType();
        JsonArray content = JsonParser.parseReader(reader).getAsJsonArray();
        // JsonArray personatge = content.getAsJsonArray();
        List<Personatge> personatgesJsonArray = new ArrayList<>();
                //gson.fromJson(reader, tipoLista);
        for (JsonElement element : content) {
            JsonObject object = element.getAsJsonObject();
            String name = object.get("name").getAsString();
            System.out.println(name);
        }
        if (personatgesJsonArray == null){
            personatgesJsonArray = new ArrayList<Personatge>();
        }

        return personatgesJsonArray;
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
