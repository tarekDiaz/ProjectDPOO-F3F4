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

    public PersonatgesJsonDAO() {
        try {
            fr = new FileReader(FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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

            return personatgesJsonArray;

            /*
            List<Personatge> personatgeList = new ArrayList<>();

            for (int i = 0; i < personatgesJsonArray.size(); i++) {

                String nom = personatgesJsonArray.get(i).getNom();

                String nomJugador = personatgesJsonArray.get(i).getNomJugador();

                int experiencia = personatgesJsonArray.get(i).getExperiencia();

                int cos = personatgesJsonArray.get(i).getCos();

                int ment = personatgesJsonArray.get(i).getMent();

                int esperit = personatgesJsonArray.get(i).getEsperit();

                String classe = personatgesJsonArray.get(i).getClasse();

                personatgeList.add(new Personatge(nom, nomJugador, experiencia, cos, ment, esperit, classe));
            }

            return personatgeList;
        */
    }

    public void nouPersonatge(Personatge personatge) {
        try (FileWriter fw = new FileWriter(FILE_PATH)){
            FileReader reader = new FileReader(FILE_PATH);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Type tipoLista = new TypeToken<List<Personatge>>(){}.getType();
            List<Personatge> personatgesJSON = gson.fromJson(reader, tipoLista);

            personatgesJSON.add(personatge);

            gson.toJson(personatgesJSON, fw);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void borrar(String nom){
        FileReader reader = null;
        String path = "data/characters.json";
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type tipoLista = new TypeToken<List<Personatge>>(){}.getType();
        List<Personatge> personatgesJsonArray = gson.fromJson(reader, tipoLista);

        for (int i = 0; i < personatgesJsonArray.size(); i++) {
            if(personatgesJsonArray.get(i).getNom().equals(nom)){
                personatgesJsonArray.remove(i);
            }
        }
        String llistaPersonatges = gson.toJson(personatgesJsonArray);

        try (FileWriter pw = new FileWriter(new File(path))){
            pw.write(llistaPersonatges);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
/*
    public static void create(Personatge personatge){
        String path = "src/main/java/persistence/characters.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(personatge);
            String llistaPersonatges = getPersonatges();
            String llista = jsonString + llistaPersonatges;
            out.write(llista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 */
}
