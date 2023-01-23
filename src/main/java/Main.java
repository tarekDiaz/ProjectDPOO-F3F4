import business.*;
import persistence.*;
import presentation.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AventurasAPIDAO aventurasAPIDAO = new AventurasAPIDAO();
        MonstresAPIDAO monstresAPIDAO = new MonstresAPIDAO();
        PersonatgesAPIDAO personatgesAPIDAO = new PersonatgesAPIDAO();

        Personatge personatgeprova = new Personatge("tadia", "tarek", 2, 1, 2, 3, "Adventurer", 200, 100, 50, 20);

        personatgesAPIDAO.nouPersonatge(personatgeprova);

        personatgesAPIDAO.borrar("tadia");

        List<Monstre> monstres = monstresAPIDAO.readMonstresFromJson();

        List<Aventura> aventuras = aventurasAPIDAO.readAventuraFromJson();

        List<Personatge> personatges = personatgesAPIDAO.readPersonatgeFromJson();



        /*PersonatgesJsonDAO personatgesJsonDAO = new PersonatgesJsonDAO();
        MonstresJsonDAO monstresJsonDAO = new MonstresJsonDAO();
        AventurasJsonDAO aventurasJsonDAO = new AventurasJsonDAO();
        PersonatgeManager personatgeManager = new PersonatgeManager(personatgesJsonDAO);
        MonstreManager monstreManager = new MonstreManager(monstresJsonDAO);
        AventuraManager aventuraManager = new AventuraManager(aventurasJsonDAO, monstresJsonDAO, personatgesJsonDAO);
        UiManager uiManager = new UiManager();
        Controller controller = new Controller(uiManager, personatgeManager, monstreManager, aventuraManager);
        controller.run();
         */
    }


}