package presentation;

import business.Aventura.Aventura;
import business.Aventura.AventuraManager;
import business.Monstre.Monstre;
import business.Monstre.MonstreManager;
import business.Personatge.Personatge;
import business.Personatge.PersonatgeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Aquesta classe s'ocupa d'executar el programa
 */
public class Controller {
    private UiManager ui;

    private PersonatgeManager personatgeManager;

    private MonstreManager monstreManager;
    private AventuraManager aventuraManager;

    /**
     * Mètode contructor
     * @param ui UiManager
     * @param personatgeManager PersonatgeManager
     * @param monstreManager MonstreManager
     * @param aventuraManager AventuraManager
     */
    public Controller(UiManager ui, PersonatgeManager personatgeManager, MonstreManager monstreManager, AventuraManager aventuraManager) {
        this.ui = ui;
        this.personatgeManager = personatgeManager;
        this.monstreManager = monstreManager;
        this.aventuraManager = aventuraManager;
    }

    /**
     * Mètode que s'ocupa d'executar el programa sencer
     */
    public void run() {
        int opcio;
        do {
            ui.showMessage("");
            ui.menuPrincipal(personatgeManager.checkPersonatgesSize());
            opcio = ui.askForInteger("\nYour answer: ");
            executeMenuPrincipal(opcio);
        } while (opcio != 5);
    }

    /**
     * Mètode que entra a una de les opcions del programa depenent el parametre d'entrada
     * @param option Opció elegida del menu pricipal
     */
    private void executeMenuPrincipal(int option) {
        ui.showMessage("");
        switch (option) {
            case 1:
                opcio1();
                break;
            case 2:
                opcio2();
                break;
            case 3:
                opcio3();
                break;
            case 4:
                if (personatgeManager.checkPersonatgesSize()) {
                    opcio4();
                }
                else {
                    ui.showMessage("This option is disabled.");
                }
                break;
            case 5:
                ui.showMessage("Tavern keeper: 'Are you leaving already? See you soon, adventurer.'");
                break;
            default:
                ui.showMessage("Choose a correct option");
                break;
        }
    }

    /**
     * Executa la opció 1
     */
    private void opcio1() {
        String nom, player, classe;
        int nivell, cos, ment, esperit;
        int dau1, dau2;
        ui.showMessage("Tavern keeper: 'Oh, so you are new to this land.'");
        ui.showMessage("'What's your name?'\n");
        nom = ui.askForString("-> Enter your name: ");
        ui.showMessage("\nTavern keeper: 'Hello, " + nom + ", be welcome.'");
        ui.showMessage("'And now, if I may break the fourth wall, who is your Player?\n'");
        player = ui.askForString("-> Enter the player's name: ");
        ui.showMessage("\nTavern keeper: 'I see, i see...'");
        ui.showMessage("'Now, are you an experienced adventurer?'\n");
        nivell = ui.askForInteger("-> Enter the character's level [1..10]: ");
        ui.showMessage("\nTavern keeper: 'Oh, so you are level " + nivell + "!'");
        ui.showMessage("'Great, let me get a closer look at you...'");
        ui.showMessage("\nGenerating your stats...\n");
        dau1 = personatgeManager.tirarDauStats();
        dau2 = personatgeManager.tirarDauStats();
        cos = personatgeManager.sumarDausStats(dau1, dau2);
        ui.showMessage("Body:   You rolled " + cos + " (" + dau1 + " and " + dau2 + ").");
        cos = personatgeManager.generarStats(cos);
        dau1 = personatgeManager.tirarDauStats();
        dau2 = personatgeManager.tirarDauStats();
        ment = personatgeManager.sumarDausStats(dau1, dau2);
        ui.showMessage("Mind:   You rolled " + ment + " (" + dau1 + " and " + dau2 + ").");
        ment = personatgeManager.generarStats(ment);
        dau1 = personatgeManager.tirarDauStats();
        dau2 = personatgeManager.tirarDauStats();
        esperit = personatgeManager.sumarDausStats(dau1, dau2);
        ui.showMessage("Spirit: You rolled " + esperit + " (" + dau1 + " and " + dau2 + ").");
        esperit = personatgeManager.generarStats(esperit);
        ui.showMessage("\nYour stats are:");
        ui.showMessage("\t- Body: " + cos);
        ui.showMessage("\t- Mind: " + ment);
        ui.showMessage("\t- Spirit: " + esperit);
        ui.showMessage("`\nTavern keeper: 'Looking good!");
        ui.showMessage("'And lastly, ?'\n");
        classe = ui.askForString("-> Enter the character's initial class [Adventurer, Cleric, Mage]: ");
        while (!classe.equals("Adventurer") && !classe.equals("Cleric")  && !classe.equals("Mage")) {
            ui.showMessage("\nEnter a valid class.\n");
            classe = ui.askForString("-> Enter the character's initial class [Adventurer, Cleric, Mage]: ");
        }

        classe = personatgeManager.classeDepenentDeLvl(classe, nivell);
        personatgeManager.crearPersonatge(nom, player, nivell, cos, ment, esperit, classe);

        ui.showMessage("\nTavern keeper: 'Any decent party needs one of those.'");
        ui.showMessage("'I guess that means you are a " + classe + " by now, nice!");
        ui.showMessage("\nThe new character " + nom + " has been created.");
    }

    /**
     * Executa la opció 2
     */
    private void opcio2() {
        String player, nomPersonatge=null, nomPersonatgeDelete;
        int totalNumPersonatge, numPersonatge;
        boolean back = true;

        ui.showMessage("Tavern keeper: 'Lads! They want to see you!'");
        if (personatgeManager.llegirPersonatges().isEmpty()){
            ui.showMessage("\nThere are no characters available!");
        }
        else{
            ui.showMessage("'Who piques your interest?'\n");
            while (back) {
                back = false;
                player = ui.askForString("-> Enter the name of the Player to filter: ");
                while (personatgeManager.llistarPersonatgesPlayer(player).isEmpty() && !player.isEmpty()) {
                    player = ui.askForString("\n'That's not a Player's name. Enter a valid one: ");
                }

                if (player.isEmpty()) {
                    ui.showMessage("\nYou watch as all adventurers get up from their chairs and approach you.");
                    ui.showCharList(personatgeManager.llistarPersonatges());
                    totalNumPersonatge = personatgeManager.llistarPersonatges().size();
                    numPersonatge = ui.askForInteger("Who would you like to meet [0.." + totalNumPersonatge + "]: ");
                    if (numPersonatge == 0) {
                        back = true;
                        ui.showMessage("\nTavern keeper: 'You did not want to do this, it's fine, let's just go back.\n");
                    } else {
                        nomPersonatge = personatgeManager.llistarPersonatges().get(numPersonatge-1);
                    }
                } else {
                    ui.showMessage("\nYou watch as some adventurers get up from their chairs and approach you.");
                    ui.showCharList(personatgeManager.llistarPersonatgesPlayer(player));
                    totalNumPersonatge = personatgeManager.llistarPersonatgesPlayer(player).size();
                    numPersonatge = ui.askForInteger("Who would you like to meet [0.." + totalNumPersonatge + "]: ");
                    if (numPersonatge == 0) {
                        back = true;
                        ui.showMessage("\nTavern keeper: 'You did not want to do this, it's fine, let's just go back.\n");
                    } else {
                        nomPersonatge = personatgeManager.llistarPersonatgesPlayer(player).get(numPersonatge-1);
                    }
                }
            }

            ui.showMessage("\nTavern keeper: 'Hey " + nomPersonatge + " get here; the boss wants to see you!'\n");
            ui.showMessage("*Name: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNom());
            ui.showMessage("*Player: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNomJugador());
            ui.showMessage("*Class: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getClasse());
            int nivell = (personatgeManager.retornaPersonatgeComplert(nomPersonatge).getExperiencia() / 100) + 1;
            ui.showMessage("*Level: " + nivell);
            ui.showMessage("*XP: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getExperiencia());
            ui.showMessage("*Body: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getCos());
            ui.showMessage("*Mind: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getMent());
            ui.showMessage("*Spirit: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getEsperit());

            do {
                ui.showMessage("\n[Enter name to delete, or press enter to cancel]");
                nomPersonatgeDelete = ui.askForString("Do you want to delete " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNom() + "? ");
                if (nomPersonatgeDelete.isEmpty()) {
                    ui.showMessage("\nTavern keeper: 'That's enough " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNom() + ", you can go back with your buddys'");
                } else {
                    if (!nomPersonatge.equals(nomPersonatgeDelete)) {
                        ui.showMessage("\nTavern keeper: 'That's not who we are talking about. Make a decision.'");
                    }
                }
                if (nomPersonatge.equals(nomPersonatgeDelete)) {
                    ui.showMessage("\nTavern keeper: 'I'm sorry kiddo, but you have to leave.'");
                    ui.showMessage("\nCharacter "+ personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNom() + " left the Guild.");
                    personatgeManager.borrarPersonatge(nomPersonatge);
                }
            } while ((!nomPersonatge.equals(nomPersonatgeDelete)) && !nomPersonatgeDelete.isEmpty());
        }
    }

    /**
     * Executa la opció 3
     */
    private void opcio3() {
        String nomAventura;
        int numCombats, opcioMonstres, totalNumMonstres, numMonstre, quantitatMonstre = 0, monsterDelete;
        Aventura aventura;
        Boolean continuee = false;

        ui.showMessage("Tavern keeper: 'Planning an adventure? Good luck with that!'\n");
        nomAventura = ui.askForString("-> Name your adventure: ");
        ui.showMessage("\nTaven keeper: 'You plan to undertake " + nomAventura + ", really?'");
        ui.showMessage("'How long will that take?'\n");
        numCombats = ui.askForInteger("-> How many encounters do you want [1..4]: ");
        while (numCombats < 1 || numCombats > 4) {
            ui.showMessage("\nTavern keeper: 'I'm worried thats not possible, choose a correct amount of encounters'\n");
            numCombats = ui.askForInteger("-> How many encounters do you want [1..4]: ");
        }
        aventura = aventuraManager.crearAventura(nomAventura, new ArrayList<>(), numCombats);
        ui.showMessage("\nTavern keeper: " + numCombats + " encounters?'\n");
        for (int i=0;i<numCombats;i++) {
            continuee = false;
            while (!continuee) {
                ui.showMessage("\n* Encounter " + (i + 1) + " / " + numCombats);
                ui.showMessage("* Monsters in encounter");
                if (aventura.getCombats().get(i).getMonstre().isEmpty()) {
                    ui.showMessage("\t# Empty\n");
                } else {
                    ui.showMonsterList(aventuraManager.generarLlistaMonstres(aventura.getCombats().get(i)));
                }
                ui.showMessage("1. Add Monster");
                ui.showMessage("2. Remove monster");
                ui.showMessage("3. Continue\n");
                opcioMonstres = ui.askForInteger("-> Enter an option [1..3]: ");
                while (opcioMonstres < 1 || opcioMonstres > 3) {
                    ui.showMessage("Choose a correct option please\n");
                    opcioMonstres = ui.askForInteger("-> Enter an option [1..3]: ");
                }
                switch (opcioMonstres) {
                    case 1:
                        ui.showMonsterList(monstreManager.llistarMonstres());
                        totalNumMonstres = monstreManager.llistarMonstres().size();
                        numMonstre = ui.askForInteger("-> Choose a monster to add [1.." + totalNumMonstres + "]: ");
                        while (numMonstre < 1 || numMonstre > totalNumMonstres) {
                            ui.showMessage("That's not a valid number.\n");
                            numMonstre = ui.askForInteger("-> Choose a monster to add [1.." + totalNumMonstres + "]: ");
                        }
                        if (monstreManager.retornaMonstreComplert(numMonstre).getNivellDificultat().equals("Boss") && aventuraManager.checkBossCombat(aventura, i)) {
                            ui.showMessage("\nYou can't add more than one boss to the battle.");
                        }
                        else {
                            quantitatMonstre = ui.askForInteger("-> How many " + monstreManager.retornaMonstreComplert(numMonstre).getNom() + "(s) do you want to add: ");
                            if (monstreManager.retornaMonstreComplert(numMonstre).getNivellDificultat().equals("Boss") && quantitatMonstre > 1) {
                                ui.showMessage("\nYou can't add more than one boss to the battle.");
                            } else {
                                aventuraManager.afegirMonstreCombat(aventura, numMonstre, quantitatMonstre, i);
                            }
                        }
                        break;
                    case 2:
                        if (!aventura.getCombats().get(i).getMonstre().isEmpty()) {
                            monsterDelete = ui.askForInteger("-> Which monster do you want to delete: ");
                            while (monsterDelete < 1 || monsterDelete > aventuraManager.generarLlistaMonstres(aventura.getCombats().get(i)).size()) {
                                ui.showMessage("\nThe combat doesn't have this amount of monsters in it. Enter a valid number.\n");
                                monsterDelete = ui.askForInteger("-> Which monster do you want to delete: ");

                            }
                            String monstresEliminats = aventuraManager.borrarMonstreCombat(aventura, monsterDelete, i);
                            ui.showMessage("\n" + monstresEliminats +" were removed from the encounter.");
                        } else {
                            ui.showMessage("\nIt's not possible to delete monsters if there are none added.");
                        }
                        break;
                    case 3:
                        continuee = true;
                        break;
                }
            }
        }
        aventuraManager.guardarAventuraJSON(aventura);
        ui.showMessage("Tavern keeper: 'Great plan lad! I hope you won’t die!'\n");
        ui.showMessage("The new adventure " + aventura.getNom() + " has been created.");
    }

    /**
     * Executa la opció 4
     */
    private void opcio4() {
        int numAventuras, numOfCharacters, countPJ = 0, personatgeAfegir, roundCounter = 0;
        int i = 0;
        boolean dead = false;
        List<Personatge> personatgesOrdenats = new ArrayList<>();
        List<Monstre> monstresOrdenats = new ArrayList<>();
        List<String> iniciativesOrdenades = new ArrayList<>();

        ui.showMessage("Tavern keeper: 'So, you are looking to go on an adventure?'");
        if (aventuraManager.llegirAventures().isEmpty()){
            ui.showMessage("'Sorry, there are not adventures available!");
        }
        else{
            ui.showMessage("'Where do you fancy going?'\n");

            ui.showMessage("Available adventures:");
            ui.showMonsterList(aventuraManager.llistarAventuras());
            ui.showMessage("");
            numAventuras = ui.askForInteger("-> Choose an adventure: ");
            while (numAventuras > aventuraManager.llistarAventuras().size()) {
                ui.showMessage("\nEnter a valid number.\n");
                numAventuras = ui.askForInteger("-> Choose an adventure: ");
            }
            Aventura currentAventura = aventuraManager.retornaAventuraComplerta(numAventuras);
            ui.showMessage("\nTavern keeper: '" + currentAventura.getNom() + " it is!'" );
            ui.showMessage("'And how many people shall join you?'\n");
            numOfCharacters = ui.askForInteger("-> Choose a number of characters [3..5]: ");
            while (numOfCharacters < 3 || numOfCharacters > 5) {
                ui.showMessage("\nEnter a valid number.\n");
                numOfCharacters = ui.askForInteger("-> Choose a number of characters [3..5]: ");
            }
            ui.showMessage("Tavern keeper: 'Great, " + numOfCharacters + " it is.'");
            ui.showMessage("'Who among these lads shall join you?'\n");

            do{
                ui.showPartyList(currentAventura.getPersonatges(), numOfCharacters, countPJ);
                ui.showMessage("Available characters: ");
                ui.showMonsterList(personatgeManager.llistarPersonatges());
                personatgeAfegir = ui.askForInteger("\n-> Choose character " + (countPJ+1) + " in your party: ");
                while (personatgeAfegir > personatgeManager.llistarPersonatges().size() || personatgeAfegir < 1) {
                    ui.showMessage("\nEnter a valid integer.\n");
                    personatgeAfegir = ui.askForInteger("-> Choose character " + (countPJ+1) + " in your party: ");
                }
                aventuraManager.afegirPersonatgeAventura(currentAventura, personatgeAfegir);
                countPJ++;
            }while (countPJ < numOfCharacters);
            personatgeManager.inicialitzaPersonatges(currentAventura.getPersonatges());

            ui.showPartyList(currentAventura.getPersonatges(), numOfCharacters, countPJ);
            ui.showMessage("\nTavern keeper: 'Great, good luck on your adventure lads!'\n");
            ui.showMessage("The '" + currentAventura.getNom() + "' will start soon...\n");
            for (i=0; i<currentAventura.getCombats().size() && !dead; i++) {

                monstreManager.inicialitzarBosses(currentAventura.getCombats().get(i).getMonstre());

                ui.showMessage("-------------------------");
                ui.showMessage("Starting Encounter " + (i+1) + ": ");
                for (int j = 0; j < aventuraManager.generarLlistaMonstres2(currentAventura.getCombats().get(i)).size(); j++) {
                    ui.showMessage("\t" + aventuraManager.generarLlistaMonstres2(currentAventura.getCombats().get(i)).get(j));
                }
                ui.showMessage("-------------------------\n");
                ui.showMessage("\n-------------------------");
                ui.showMessage("*** Preparation stage ***");
                ui.showMessage("-------------------------");

                List<String> frasesPreparation = personatgeManager.suportPersonatge(currentAventura.getPersonatges());
                ui.showListNoT(frasesPreparation);

                personatgesOrdenats.clear();
                monstresOrdenats.clear();
                for (int k = 0; k<currentAventura.getPersonatges().size(); k++) {
                    aventuraManager.ordenarPersonatgesSegonsIniciatives(personatgesOrdenats, currentAventura.getPersonatges().get(k));
                }
                for (int k = 0; k<currentAventura.getCombats().get(i).getMonstre().size(); k++) {
                    aventuraManager.ordenarMonstresSegonsIniciatives(monstresOrdenats, currentAventura.getCombats().get(i).getMonstre().get(k));
                }
                ui.showMessage("\nRolling iniciative... ");
                iniciativesOrdenades = aventuraManager.mostrarOrdreIniciativas(personatgesOrdenats, monstresOrdenats);
                for (int u = 0; u<iniciativesOrdenades.size(); u++) {
                    ui.showMessage("\t" + iniciativesOrdenades.get(u));
                }
                ui.showMessage("\n\n--------------------");
                ui.showMessage("*** Combat stage ***");
                ui.showMessage("--------------------");
                while (!personatgeManager.totalPartyUnconscius(personatgesOrdenats) && !monstreManager.totalMonstersUnconscius(monstresOrdenats) && !dead) {
                    roundCounter++;
                    ui.showMessage("\nRound " + roundCounter + ": ");
                    ui.showMessage("Party:");
                    int contadorPersonatge = 0;
                    int contadorMonstre = 0;
                    ui.showBasicList(aventuraManager.showPartyHP(currentAventura.getPersonatges()));
                    for (int p=0; p<(monstresOrdenats.size() + personatgesOrdenats.size()) && !monstreManager.totalMonstersUnconscius(monstresOrdenats) && !personatgeManager.totalPartyUnconscius(personatgesOrdenats); p++) {
                        if (contadorPersonatge < personatgesOrdenats.size()) {
                            if (aventuraManager.nomsOrdreIniciativas(personatgesOrdenats, monstresOrdenats).get(p).equals(personatgesOrdenats.get(contadorPersonatge).getNom())) {
                                if (personatgeManager.estaInconscient(personatgesOrdenats.get(contadorPersonatge))) {
                                    contadorPersonatge++;
                                } else {
                                    int posMenorMonstre = monstreManager.posicioMonstreMenysHP(monstresOrdenats);
                                    int posMajorMonstre = monstreManager.posicioMonstreMesHP(monstresOrdenats);
                                    ui.showMessage(aventuraManager.accioDurantCombat(personatgesOrdenats, monstresOrdenats, contadorPersonatge, posMenorMonstre, posMajorMonstre));
                                    contadorPersonatge++;
                                }
                            }
                        }
                        if (contadorMonstre < monstresOrdenats.size()) {
                            if (aventuraManager.nomsOrdreIniciativas(personatgesOrdenats, monstresOrdenats).get(p).equals(monstresOrdenats.get(contadorMonstre).getNom())) {
                                if (monstreManager.estaInconscient(monstresOrdenats.get(contadorMonstre))) {
                                    contadorMonstre++;
                                } else {
                                    int mal = monstreManager.damageMonstre(monstresOrdenats.get(contadorMonstre));
                                    ui.showMessage(monstreManager.atacarFaseCombat(monstresOrdenats, personatgesOrdenats, contadorMonstre, mal));
                                    contadorMonstre++;
                                }
                            }
                        }
                    }
                    ui.showMessage("\nEnd of round " + roundCounter + ".");
                    if (monstreManager.totalMonstersUnconscius(monstresOrdenats)) {
                        ui.showMessage("All enemys are defeated.\n");
                        ui.showMessage("------------------------");
                        ui.showMessage("*** Short rest stage ***");
                        ui.showMessage("------------------------");
                        int xpObtinguda = 0;
                        for (int q=0; q<monstresOrdenats.size();q++) {
                            xpObtinguda = monstresOrdenats.get(q).getExperiencia() + xpObtinguda;
                        }
                        for (int q=0; q<personatgesOrdenats.size();q++) {
                            boolean pujaNivell = personatgeManager.sumarExperiencia(currentAventura.getPersonatges().get(q), xpObtinguda);
                            if (pujaNivell) {
                                ui.showMessage(currentAventura.getPersonatges().get(q).getNom() + " gains " + xpObtinguda + " xp. " + currentAventura.getPersonatges().get(q).getNom() + " levels up. They are now lvl " + currentAventura.getPersonatges().get(q).getNivell() + ".");
                                personatgeManager.calcularPdvMax(currentAventura.getPersonatges().get(q));
                                String fraseEvo = aventuraManager.evolucionaPersonatges(currentAventura.getPersonatges(), currentAventura.getPersonatges().get(q), q);
                                if (fraseEvo != null) {
                                    ui.showMessage(fraseEvo);
                                }
                            } else {
                                ui.showMessage(currentAventura.getPersonatges().get(q).getNom() + " gains " + xpObtinguda+ " xp.");
                            }
                        }
                        ui.showMessage("");
                        List<String> frasesDescans = personatgeManager.descansCurt(currentAventura.getPersonatges(), personatgesOrdenats);
                        ui.showListNoT(frasesDescans);
                    }
                    if (personatgeManager.totalPartyUnconscius(personatgesOrdenats)) {
                        ui.showMessage("\nTavern keeper: 'Lad, wake up. Yes, your party fell unconscious.'");
                        ui.showMessage("'Don't worry, you are safe back at the Tavern.'");
                        dead = true;
                    }
                }

            }
            if (monstreManager.totalMonstersUnconscius(monstresOrdenats) && i == currentAventura.getCombats().size()) {
                ui.showMessage("\nCongratulations, your party completed '" + currentAventura.getNom() + "'");
            }
        }
    }

}
