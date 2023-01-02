package persistence;

import business.Monstre;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MonstresJsonDAO {
    private final String FILE_PATH = "data/monsters.json";
    private FileReader fr;
    private boolean exsists;

    public boolean getExsists() {
        return exsists;
    }

    public MonstresJsonDAO() {
        try {
            fr = new FileReader(FILE_PATH);
            exsists = true;
        } catch (FileNotFoundException e) {
            exsists = false;
        }
    }

    public List<Monstre> readMonstresFromJson() {
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

