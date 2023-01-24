import business.*;
import persistence.Aventuras.AventurasAPIDAO;
import persistence.Aventuras.AventurasDAO;
import persistence.Aventuras.AventurasJsonDAO;
import persistence.Monstres.MonstresAPIDAO;
import persistence.Monstres.MonstresDAO;
import persistence.Monstres.MonstresJsonDAO;
import persistence.Personatges.PersonatgesAPIDAO;
import persistence.Personatges.PersonatgesDAO;
import persistence.Personatges.PersonatgesJsonDAO;
import presentation.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        UiManager uiManager = new UiManager();


        AventurasDAO aventurasDAO = new AventurasAPIDAO();
        MonstresDAO monstresDAO = new MonstresAPIDAO();
        PersonatgesDAO personatgesDAO = new PersonatgesAPIDAO();


        //PersonatgesDAO personatgesDAO = new PersonatgesJsonDAO();
        //MonstresDAO monstresDAO = new MonstresJsonDAO();
        //AventurasDAO aventurasDAO = new AventurasJsonDAO();
        //controller.loadData();

        PersonatgeManager personatgeManager = new PersonatgeManager(personatgesDAO);
        MonstreManager monstreManager = new MonstreManager(monstresDAO);
        AventuraManager aventuraManager = new AventuraManager(aventurasDAO, monstresDAO, personatgesDAO);

        Controller controller = new Controller(uiManager, personatgeManager, monstreManager, aventuraManager);
        controller.run();

    }


}