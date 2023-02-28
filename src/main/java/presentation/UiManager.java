package presentation;

import business.Personatge.Personatge;

import java.util.*;

/**
 * Aquesta classe s'ocupa de printar la informació per pantalla i demanar dades al usuari.
 */
public class UiManager {
    private Scanner scanner;

    /**
     * Mètode constructor
     */
    public UiManager() {
        scanner = new Scanner(System.in);
    }

    /**
     * Mètode que rep un missatge de l'usuari i el printa per pantalla
     * @param message Missatge a printar
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Mètode que rep un missatge de l'usuari tipus String i el retorna en tipus Integer
     * @param message Missatge que rep
     * @return Integer per utilitzar com a variable
     */
    public int askForInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("This isn't an integer!");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Mètode que reb un missatge del usuari i el retorna
     * @param message Missatge que reb
     * @return String per utilitzar com a variable
     */
    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Mètode que mostra per pantalla l'inici del programa
     * @return Opció elegida de persistencia
     */
    public int start(){
        int opcio;
        System.out.println(
            "   ____  _               __       __    ____ ___   ___   _____\n" +
                    "  / __/ (_)__ _   ___   / /___   / /   / __// _ \\ / _ \\ / ___/\n" +
                    " _\\ \\  / //  ' \\ / _ \\ / // -_) / /__ _\\ \\ / , _// ___// (_ / \n" +
                    "/___/ /_//_/_/_// .__//_/ \\__/ /____//___//_/|_|/_/    \\___/  \n" +
                    "               /_/\n");

        System.out.println("Welcome to Simple LSRPG.\n");
        System.out.println("Do you want to use your local or cloud data?");
        System.out.println("\t1) Local data");
        System.out.println("\t2) Cloud data\n");

        opcio = this.askForInteger("-> Answer:");

        while(opcio != 1 && opcio != 2){
            System.out.println("Invalid number!");
            opcio = this.askForInteger("-> Answer:");
        }
        System.out.println("\nLoading data...");
        return opcio;
    }




    /**
     * Mètode que mostra per pantalla el menú principal
     * @param checkPersonatges Retorna true quan hi ha 3 personatges o més a l'aventura
     */
    public void menuPrincipal(boolean checkPersonatges) {
        System.out.println("The tavern keeper looks at you and says: ");
        System.out.println("Welcome adventurer! How can i help you?\n");
        System.out.println("\t 1) Character creation");
        System.out.println("\t 2) List Characters");
        System.out.println("\t 3) Create adventure");
        if (checkPersonatges) {
            System.out.println("\t 4) Start adventure");
        } else {
            System.out.println("\t 4) Start adventure (disabled: create 3 characters first)");
        }

        System.out.println("\t 5) Exit");
    }

    /**
     * Mètode que mostra per pantalla una llista de personatges
     * @param personatges Llista de personatges a mostrar
     */
    public void showCharList(List<String> personatges) {
        System.out.println("");
        for (int i = 0; i < personatges.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + personatges.get(i));
        }
        System.out.println("\n\t0. Back\n");
    }

    /**
     * Mètode que mostra per pantalla una llista de monstres
     * @param monstres Llista de monstres a mostrar
     */
    public void showMonsterList(List<String> monstres) {
        for (int i = 0; i < monstres.size(); i++) {
            System.out.println("\t" + (i+1) + ". " + monstres.get(i));
        }
    }

    /**
     * Mètode que mostra per pantalla una llista indexada dels personatges que es van afegint a la party
     * @param personatges Llista de personatges
     * @param numOfCharacters Quantitat de personatges totals
     * @param countPJ Numeró de personatge que estem afegint
     */
    public void showPartyList(List<Personatge> personatges, int numOfCharacters, int countPJ) {
        System.out.println("\n--------------------------------");
        System.out.println("Your party (" + countPJ + " / " + numOfCharacters + "):");
        for (int i = 0; i < numOfCharacters; i++) {
            if (i < countPJ) {
                System.out.println("\t" + (i+1) + ". " + personatges.get(i).getNom()    );
            } else {
                System.out.println("\t" + (i+1) + ". Empty");
            }
        }
        System.out.println("--------------------------------");
    }

    /**
     * Mètode que mostra per pantalla una llista de tipus String
     * @param llista Llista de String
     */
    public void showBasicList (List<String> llista) {
        for (int i=0; i<llista.size(); i++) {
            System.out.println("\t" + llista.get(i));
        }
    }

    /**
     * Mètode que mostra per pantalla una llista de tipus String sense espaiat
     * @param llista Llista de String
     */
    public void showListNoT (List<String> llista) {
        for (int i=0; i<llista.size(); i++) {
            System.out.println(llista.get(i));
        }
    }
}


