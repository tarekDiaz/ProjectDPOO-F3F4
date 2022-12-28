package presentation;

import business.Personatge;

import java.util.*;

public class UiManager {
    private Scanner scanner;

    public UiManager() {
        scanner = new Scanner(System.in);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

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

    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

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

    public void showCharList(List<String> personatges) {
        for (int i = 0; i < personatges.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + personatges.get(i));
        }
        System.out.println("\n\t0. Back\n");
    }

    public void showMonsterList(List<String> monstres) {
        for (int i = 0; i < monstres.size(); i++) {
            System.out.println("\t" + (i+1) + ". " + monstres.get(i));
        }
    }

    public void showPartyList(List<Personatge> personatges, int numOfCharacters, int countPJ) {
        System.out.println("--------------------------------");
        System.out.println("Your party (" + countPJ + " / " + numOfCharacters + "):");
        for (int i = 0; i < numOfCharacters; i++) {
            if (i < countPJ) {
                System.out.println("\t" + (i+1) + ". " + personatges.get(i));
            } else {
                System.out.println("\t" + (i+1) + "Empty");
            }
        }
        System.out.println("--------------------------------");
    }

    public void showBasicList (List<String> llista) {
        for (int i=0; i<llista.size(); i++) {
            System.out.println("\t" + llista.get(i));
        }
    }

    public void AttackMissHitCrit (int mal) {
        int roll = (int) (Math.random() * (10)) + 1;
        if (roll == 1) {
            System.out.println("Fails and deals 0 physical damage");
        }
        if (roll > 1 || roll < 10) {
            System.out.println("Hits and deals " + mal + " physical damage");
        }
        if (roll == 10) {
            System.out.println("Critical Hit and deals " + mal + " physical damage");
        }
    }
}

