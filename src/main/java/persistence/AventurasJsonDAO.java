package persistence;

import business.Aventura;
import business.Personatge;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AventurasJsonDAO {
    private final String ADVENTURE_PATH = "data/adventure.json";
    private FileReader fr;
    public AventurasJsonDAO() {
        try {
            fr = new FileReader(ADVENTURE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Aventura> readAventuraFromJson() {
        FileReader reader = null;
        try {
            reader = new FileReader(ADVENTURE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();

        Type tipoLista = new TypeToken<List<Aventura>>(){}.getType();
        List<Aventura> aventuraJsonArray = gson.fromJson(reader, tipoLista);

        return aventuraJsonArray;
    }

    public void writeAventuraJson(Aventura aventura){
        try (Writer writer = new FileWriter(ADVENTURE_PATH)){
            FileReader reader = new FileReader(ADVENTURE_PATH);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            List<Aventura> aventuraList = new ArrayList<Aventura>();

            List<Aventura> aventuraListJson = readAventuraFromJson();
            if (aventuraListJson != null){
                aventuraList = aventuraListJson;
            }

            aventuraList.add(aventura);

            gson.toJson(aventuraList, writer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
