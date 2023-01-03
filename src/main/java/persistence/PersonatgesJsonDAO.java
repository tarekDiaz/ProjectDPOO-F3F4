package persistence;

import business.Personatge;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PersonatgesJsonDAO {
    private final String FILE_PATH = "data/characters.json";
    private FileReader fr;

    private boolean exsists;

    public boolean getExsists() {
        return exsists;
    }

    public PersonatgesJsonDAO() {
        try {
            fr = new FileReader(FILE_PATH);
            exsists = true;
        } catch (FileNotFoundException e) {
            exsists = false;
        }
    }

    public static List<Personatge> readPersonatgeFromJson() {
        FileReader reader = null;
        try {
            reader = new FileReader("data/characters.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Personatge>>(){}.getType();
        List<Personatge> personatgesJsonArray = gson.fromJson(reader, tipoLista);
        if (personatgesJsonArray == null){
            personatgesJsonArray = new ArrayList<Personatge>();
        }

        return personatgesJsonArray;

    }

    public void nouPersonatge(Personatge personatge) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

            List<Personatge> personatgesJSON = readPersonatgeFromJson();

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
            List<Personatge> personatgesJson = readPersonatgeFromJson();

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
