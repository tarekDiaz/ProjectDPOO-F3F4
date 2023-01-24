import business.*;
import persistence.Aventuras.AventurasAPIDAO;
import persistence.Aventuras.AventurasJsonDAO;
import persistence.Monstres.MonstresAPIDAO;
import persistence.Monstres.MonstresJsonDAO;
import persistence.Personatges.PersonatgesAPIDAO;
import persistence.Personatges.PersonatgesJsonDAO;
import presentation.*;

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