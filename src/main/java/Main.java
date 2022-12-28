import business.*;
import persistence.AventurasJsonDAO;
import persistence.MonstresJsonDAO;
import persistence.PersonatgesJsonDAO;
import presentation.*;

public class Main {
    public static void main(String[] args) {
        PersonatgesJsonDAO personatgesJsonDAO = new PersonatgesJsonDAO();
        MonstresJsonDAO monstresJsonDAO = new MonstresJsonDAO();
        AventurasJsonDAO aventurasJsonDAO = new AventurasJsonDAO();
        PersonatgeManager personatgeManager = new PersonatgeManager(personatgesJsonDAO);
        MonstreManager monstreManager = new MonstreManager(monstresJsonDAO);
        AventuraManager aventuraManager = new AventuraManager(aventurasJsonDAO, monstresJsonDAO, personatgesJsonDAO);
        UiManager uiManager = new UiManager();
        Controller controller = new Controller(uiManager, personatgeManager, monstreManager, aventuraManager);
        controller.run();
    }


}