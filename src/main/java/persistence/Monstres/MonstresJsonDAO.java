package persistence.Monstres;

import business.Monstre.Monstre;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import persistence.PersistenceException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class MonstresJsonDAO implements MonstresDAO{
    private final String FILE_PATH = "data/monsters.json";
    private FileReader fr;


    public MonstresJsonDAO() throws PersistenceException {
        try {
            fr = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Error: The monsters.json file can't be accessed.", e);
        }
    }

    public List<Monstre> readMonstres() {
        FileReader reader = null;
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

