package persistence.Aventuras;

import business.Aventura.Aventura;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import persistence.PersistenceException;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe que accedeix a les dades de les aventures a través del fitxer Json
 */
public class AventurasJsonDAO implements AventurasDAO{
    private final String ADVENTURE_PATH = "data/adventure.json";
    private final FileReader fr;
    /**
     * Mètode constructor de la classe
     * @throws PersistenceException la classe no es crea correctament perquè no es pot accedir al fitxer Json
     */
    public AventurasJsonDAO() throws PersistenceException {
        try {
            fr = new FileReader(ADVENTURE_PATH);
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Error: The adventure.json file can't be accessed.", e);
        }
    }
    /**
     * Mètode que retorna la llista de les aventures que es troben al fitxer Json
     * @return llista de classe Aventura amb les aventures
     */
    public List<Aventura> readAventura() throws PersistenceException {
        FileReader reader;
        try {
            reader = new FileReader(ADVENTURE_PATH);
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Error: The adventure.json file can't be accessed.", e);
        }
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Aventura>>(){}.getType();
        List<Aventura> aventuraJsonArray = gson.fromJson(reader, tipoLista);
        if (aventuraJsonArray == null){
            aventuraJsonArray = new ArrayList<>();
        }

        return aventuraJsonArray;
    }
    /**
     * Mètode que escriu una nova aventura al fitxer Json
     * @param aventura aventura que es vol afegir
     */
    public void writeAventura(Aventura aventura) throws PersistenceException {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            List<Aventura> aventuraListJson = readAventura();
            List<Aventura> aventuraList = new ArrayList<Aventura>();


            if (aventuraListJson != null){
                aventuraList = aventuraListJson;
            }
            aventuraList.add(aventura);

            Writer fw = new FileWriter(ADVENTURE_PATH);
            gson.toJson(aventuraList, fw);
            fw.flush();
            fw.close();

        } catch (IOException | PersistenceException e) {
            throw new PersistenceException("Error: The adventure.json file can't be accessed.", e);
        }
    }

}