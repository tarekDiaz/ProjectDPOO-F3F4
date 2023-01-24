import business.*;
import persistence.*;
import presentation.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        AventurasAPIDAO aventurasAPIDAO = new AventurasAPIDAO();
        MonstresAPIDAO monstresAPIDAO = new MonstresAPIDAO();
        PersonatgesAPIDAO personatgesAPIDAO = new PersonatgesAPIDAO();


        PersonatgesJsonDAO personatgesJsonDAO = new PersonatgesJsonDAO();
        MonstresJsonDAO monstresJsonDAO = new MonstresJsonDAO();
        AventurasJsonDAO aventurasJsonDAO = new AventurasJsonDAO();
        //controller.loadData();

        PersonatgeManager personatgeManager = new PersonatgeManager(personatgesJsonDAO);
        MonstreManager monstreManager = new MonstreManager(monstresJsonDAO);
        AventuraManager aventuraManager = new AventuraManager(aventurasJsonDAO, monstresJsonDAO, personatgesJsonDAO);
        UiManager uiManager = new UiManager();
        Controller controller = new Controller(uiManager, personatgeManager, monstreManager, aventuraManager);

        controller.run();

    }


}