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

public class AventurasJsonDAO implements AventurasDAO{
    private final String ADVENTURE_PATH = "data/adventure.json";
    private FileReader fr;

    public AventurasJsonDAO() throws PersistenceException {
        try {
            fr = new FileReader(ADVENTURE_PATH);
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Error: The adventure.json file can't be accessed.", e);
        }
    }
    public List<Aventura> readAventura() {
        FileReader reader = null;
        try {
            reader = new FileReader(ADVENTURE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Aventura>>(){}.getType();
        List<Aventura> aventuraJsonArray = gson.fromJson(reader, tipoLista);
        if (aventuraJsonArray == null){
            aventuraJsonArray = new ArrayList<>();
        }

        return aventuraJsonArray;
    }

    public void writeAventura(Aventura aventura){
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}