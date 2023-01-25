import business.Aventura.AventuraManager;
import business.Monstre.MonstreManager;
import business.Personatge.PersonatgeManager;
import persistence.Aventuras.AventurasAPIDAO;
import persistence.Aventuras.AventurasDAO;
import persistence.Aventuras.AventurasJsonDAO;
import persistence.Monstres.MonstresAPIDAO;
import persistence.Monstres.MonstresDAO;
import persistence.Monstres.MonstresJsonDAO;
import persistence.PersistenceException;
import persistence.Personatges.PersonatgesAPIDAO;
import persistence.Personatges.PersonatgesDAO;
import persistence.Personatges.PersonatgesJsonDAO;
import presentation.*;

public class Main {
    public static void main(String[] args) {
        PersonatgesDAO personatgesDAO;
        MonstresDAO monstresDAO;
        AventurasDAO aventurasDAO;

        UiManager uiManager = new UiManager();
        int opcio = uiManager.start();

        try{
            if (opcio == 1){
                personatgesDAO = new PersonatgesJsonDAO();
                monstresDAO = new MonstresJsonDAO();
                aventurasDAO = new AventurasJsonDAO();
            }else{
                try{
                    aventurasDAO = new AventurasAPIDAO();
                    monstresDAO = new MonstresAPIDAO();
                    personatgesDAO = new PersonatgesAPIDAO();
                }catch (PersistenceException e){
                    uiManager.showMessage(e.getMessage());
                    uiManager.showMessage("Reverting to local data.\n");
                    personatgesDAO = new PersonatgesJsonDAO();
                    monstresDAO = new MonstresJsonDAO();
                    aventurasDAO = new AventurasJsonDAO();
                }
            }
            PersonatgeManager personatgeManager = new PersonatgeManager(personatgesDAO);
            MonstreManager monstreManager = new MonstreManager(monstresDAO);
            AventuraManager aventuraManager = new AventuraManager(aventurasDAO, monstresDAO, personatgesDAO);
            Controller controller = new Controller(uiManager, personatgeManager, monstreManager, aventuraManager);
            controller.run();
        }catch (PersistenceException e){
            uiManager.showMessage(e.getMessage());
        }
    }
}