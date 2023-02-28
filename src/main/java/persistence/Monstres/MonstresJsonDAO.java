package persistence.Monstres;

import business.Monstre.Monstre;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import persistence.PersistenceException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
/**
 * Classe que accedeix a les dades dels monstres a través del fitxer Json
 */
public class MonstresJsonDAO implements MonstresDAO{
    private final String FILE_PATH = "data/monsters.json";
    private FileReader fr;

    /**
     * Mètode constructor de la classe
     * @throws PersistenceException la classe no es crea correctament perquè no es pot accedir al fitxer Json
     */
    public MonstresJsonDAO() throws PersistenceException {
        try {
            fr = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Error: The monsters.json file can't be accessed.", e);
        }
    }
    /**
     * Mètode que llegeix i retorna els monstres que es troben al fitxer Json
     * @return llista de classe Monstre dels monstres
     */
    public List<Monstre> readMonstres() {
        FileReader reader;
        try {
            reader = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();

        Type tipoLista = new TypeToken<List<Monstre>>(){}.getType();
        List<Monstre> monstresJsonArray = gson.fromJson(reader, tipoLista);

        return monstresJsonArray;
    }
}

