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

    public MonstresJsonDAO() {
        try {
            fr = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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


        List<Monstre> monstresList = new ArrayList<>();

        for (int i = 0; i < monstresJsonArray.size(); i++) {

            String nom = monstresJsonArray.get(i).getNom();

            String nivellDificultat = monstresJsonArray.get(i).getNivellDificultat();

            int experiencia = monstresJsonArray.get(i).getExperiencia();

            int pdv = monstresJsonArray.get(i).getPdv();

            int iniciativa = monstresJsonArray.get(i).getIniciativa();

            String tipusDau = monstresJsonArray.get(i).getTipusDau();

            String tipusDeMal = monstresJsonArray.get(i).getTipusDeMal();


            monstresList.add(new Monstre(nom, nivellDificultat, experiencia, pdv, iniciativa, tipusDau, tipusDeMal));
        }
        return monstresList;
    }
}
