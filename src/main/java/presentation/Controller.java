package presentation;

import business.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Controller {
    private UiManager ui;

    private PersonatgeManager personatgeManager;

    private MonstreManager monstreManager;
    private AventuraManager aventuraManager;

    public Controller(UiManager ui, PersonatgeManager personatgeManager, MonstreManager monstreManager, AventuraManager aventuraManager) {
        this.ui = ui;
        this.personatgeManager = personatgeManager;
        this.monstreManager = monstreManager;
        this.aventuraManager = aventuraManager;
    }

    public void run() {
        int opcio;
        ui.showMessage(
                "   ____  _               __       __    ____ ___   ___   _____\n" +
                        "  / __/ (_)__ _   ___   / /___   / /   / __// _ \\ / _ \\ / ___/\n" +
                        " _\\ \\  / //  ' \\ / _ \\ / // -_) / /__ _\\ \\ / , _// ___// (_ / \n" +
                        "/___/ /_//_/_/_// .__//_/ \\__/ /____//___//_/|_|/_/    \\___/  \n" +
                        "               /_/\n");
        ui.showMessage("Welcome to Simple LSRPG.\n" + "\nLoading data...");
        if (!personatgeManager.getPersonatgesJsonDAO().getExsists() || !monstreManager.getMonstresJsonDAO().getExsists() || !aventuraManager.getAventurasJsonDAO().getExsists()){
            if(!personatgeManager.getPersonatgesJsonDAO().getExsists()){
                ui.showMessage("Error: The charachters.json file can't be accessed.");
            }
            if(!monstreManager.getMonstresJsonDAO().getExsists()) {
                ui.showMessage("Error: The monsters.json file can't be accessed.");
            }
            if(!aventuraManager.getAventurasJsonDAO().getExsists()){
                ui.showMessage("Error: The adventure.json file can't be accessed.");
            }
        }else{
            ui.showMessage("Data was successfully loaded.");
            do {
                ui.showMessage("");
                ui.menuPrincipal(personatgeManager.checkPersonatgesSize());
                opcio = ui.askForInteger("\nYour answer: ");
                executeMenuPrincipal(opcio);
            } while (opcio != 5);
        }
    }

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
        // no se si soc retrasat o que passa pero no aconegueixo fer el while estic tilt
        while (classe != "Adventurer" && classe != "Cleric" && classe != "Mage") {
            ui.showMessage("\nEnter a valid class.\n");
            classe = ui.askForString("-> Enter the character's initial class [Adventurer, Cleric, Mage]: ");
        }

        // Per saber a quina classe evoluciona pero aqui no fa falta inicialitzar el personatge amb la seva classe
        Personatge personatge = personatgeManager.crearPersonatge(nom, player, nivell, cos, ment, esperit, classe);
        classe = personatgeManager.classeDepenentDeLvl(personatge, nivell);

        ui.showMessage("\nTavern keeper: 'Any decent party needs one of those.'");
        ui.showMessage("'I guess that means you are a " + classe + " by now, nice!");
        ui.showMessage("\nThe new character " + nom + " has been created.");
    }


    private void opcio2() {
        String player, nomPersonatge=null, nomPersonatgeDelete;
        int totalNumPersonatge, numPersonatge;
        boolean back = true;

        ui.showMessage("Tavern keeper: 'Lads! They want to see you!'");
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
                            aventuraManager.borrarMonstreCombat(aventura, monsterDelete, i);
                            //ultima
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
    private void opcio4() {
        int numAventuras, numOfCharacters, countPJ = 0, personatgeAfegir, iniciativa, roundCounter = 0;
        int i = 0, dau;
        String fraseBoss;
        boolean dead = false;
        List<Personatge> personatgesOrdenats = new ArrayList<>();
        List<Monstre> monstresOrdenats = new ArrayList<>();
        List<String> iniciativesOrdenades = new ArrayList<>();

        ui.showMessage("Tavern keeper: 'So, you are looking to go on an adventure?'");
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
        // inicialitzo lvl, pdv i iniciativa
        personatgeManager.inicialitzaPersonatges(currentAventura.getPersonatges());
        // aqui inicialitzo les classes
        personatgeManager.inicialitzaPersonatgesAmbClasse(currentAventura.getPersonatges());

        ui.showPartyList(currentAventura.getPersonatges(), numOfCharacters, countPJ);
        ui.showMessage("\nTavern keeper: 'Great, good luck on your adventure lads!'\n");
        ui.showMessage("The '" + currentAventura.getNom() + "' will start soon...\n");
        for (i=0; i<currentAventura.getCombats().size() && !dead; i++) {
            ui.showMessage("-------------------------");
            ui.showMessage("Starting Encounter " + (i+1) + ": ");
            for (int j = 0; j < aventuraManager.generarLlistaMonstres2(currentAventura.getCombats().get(i)).size(); j++) {
                ui.showMessage("\t" + aventuraManager.generarLlistaMonstres2(currentAventura.getCombats().get(i)).get(j));
            }
            ui.showMessage("-------------------------\n");
            ui.showMessage("\n-------------------------");
            ui.showMessage("*** Preparation stage ***");
            ui.showMessage("-------------------------");
            for (int k = 0; k<currentAventura.getPersonatges().size(); k++) {
                ui.showMessage(currentAventura.getPersonatges().get(k).getNom() + " uses Self-Motivated. Their Spirit increases in +1.");
                personatgeManager.suportPersonatge(currentAventura.getPersonatges().get(k));
            }
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
                                int mal = personatgeManager.atacarPersonatge(personatgesOrdenats.get(contadorPersonatge));
                                int posMenorMonstre = monstreManager.posicioMonstreMenysHP(monstresOrdenats);
                                ui.showMessage("\n" + personatgesOrdenats.get(contadorPersonatge).getNom() + " attacks " + monstresOrdenats.get(posMenorMonstre).getNom() + " with Sword Slash.");
                                dau = (int) (Math.random() * (10)) + 1;
                                //resistencia al mal bosses
                                /*if (monstresOrdenats.get(posMenorMonstre).getNivellDificultat().equals("Boss") && monstresOrdenats.get(posMenorMonstre).getTipusDeMal().equals(personatgesOrdenats.get(contadorPersonatge).getTipusDeMal)) {
                                    mal = mal/2;
                                }*/
                                monstreManager.monstreRebMal(monstresOrdenats.get(posMenorMonstre), mal, dau);
                                ui.AttackMissHitCrit(mal, dau);
                                if (monstreManager.estaInconscient(monstresOrdenats.get(posMenorMonstre))) {
                                    ui.showMessage(monstresOrdenats.get(posMenorMonstre).getNom() + " dies.");
                                }
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
                                // Si el monstre es boss ataca a tothom
                                if (monstresOrdenats.get(contadorMonstre).getNivellDificultat().equals("Boss")) {
                                    fraseBoss = "\n" + monstresOrdenats.get(contadorMonstre).getNom() + " attacks ";
                                    for (int h=0; h<aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().size(); h++) {
                                        if (!personatgeManager.estaInconscient(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h))) {
                                            fraseBoss = fraseBoss + " " + aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h).getNom();
                                            dau = (int) (Math.random() * (10)) + 1;
                                            personatgeManager.rebreMalPersonatge(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h), mal, dau);
                                            ui.AttackMissHitCrit(mal, dau);
                                        }
                                    }
                                    ui.showMessage(fraseBoss);
                                    for (int h=0; h<aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().size(); h++) {
                                        if (personatgeManager.estaInconscient(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h))) {
                                            ui.showMessage(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h).getNom() + " falls unconscious.");
                                        }
                                    }
                                // fins aqui
                                } else {
                                    int rollPersonatge = (int) (Math.random() * (personatgesOrdenats.size()));
                                    while (personatgeManager.estaInconscient(personatgesOrdenats.get(rollPersonatge))) {
                                        rollPersonatge = (int) (Math.random() * (personatgesOrdenats.size()));
                                    }
                                    ui.showMessage("\n" + monstresOrdenats.get(contadorMonstre).getNom() + " attacks " + personatgesOrdenats.get(rollPersonatge).getNom() + ".");
                                    dau = (int) (Math.random() * (10)) + 1;
                                    personatgeManager.rebreMalPersonatge(personatgesOrdenats.get(rollPersonatge), mal, dau);
                                    ui.AttackMissHitCrit(mal, dau);
                                    if (personatgeManager.estaInconscient(personatgesOrdenats.get(rollPersonatge))) {
                                        ui.showMessage(personatgesOrdenats.get(rollPersonatge).getNom() + " falls unconscious.");
                                    }
                                }
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
                        } else {
                            ui.showMessage(currentAventura.getPersonatges().get(q).getNom() + " gains " + xpObtinguda+ " xp.");
                        }
                    }
                    for (int q=0; q<personatgesOrdenats.size();q++) {
                        for (int n=0; n<currentAventura.getPersonatges().size(); n++) {
                            if (currentAventura.getPersonatges().get(q).getNom().equals(personatgesOrdenats.get(n).getNom())) {
                                if (personatgeManager.estaInconscient(personatgesOrdenats.get(n))) {
                                    ui.showMessage(personatgesOrdenats.get(n).getNom() + " is unconscious.");
                                } else {
                                    if (personatgesOrdenats.get(n).getPdvActual() == personatgesOrdenats.get(n).getPdvMax()) {
                                        ui.showMessage(personatgesOrdenats.get(n).getNom() + " uses Bandage time. But it's already full hit points.");
                                    } else {
                                        int cura = personatgeManager.curarPersonatge(personatgesOrdenats.get(n));
                                        ui.showMessage(personatgesOrdenats.get(n).getNom() + " uses Bandage time. Heals " + cura + " hit points.");
                                    }
                                }
                            }
                        }
                    }
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
