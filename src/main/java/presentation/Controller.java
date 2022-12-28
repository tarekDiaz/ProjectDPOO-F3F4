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
        do {
            ui.showMessage("");
            ui.menuPrincipal(personatgeManager.checkPersonatgesSize());
            opcio = ui.askForInteger("Your answer: ");
            executeMenuPrincipal(opcio);
        } while (opcio != 5);
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
        String nom, player;
        int nivell, cos, ment, esperit;
        int dau1, dau2;
        ui.showMessage("\nTavern keeper: 'Oh, so you are new to this land.'");
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
        ui.showMessage("Body:   You rolled" + cos + "(" + dau1 + "and " + dau2 + ").");
        cos = personatgeManager.generarStats(cos);
        dau1 = personatgeManager.tirarDauStats();
        dau2 = personatgeManager.tirarDauStats();
        ment = personatgeManager.sumarDausStats(dau1, dau2);
        ui.showMessage("Mind:   You rolled" + ment + "(" + dau1 + "and " + dau2 + ").");
        ment = personatgeManager.generarStats(ment);
        dau1 = personatgeManager.tirarDauStats();
        dau2 = personatgeManager.tirarDauStats();
        esperit = personatgeManager.sumarDausStats(dau1, dau2);
        ui.showMessage("Spirit: You rolled" + esperit + "(" + dau1 + "and " + dau2 + ").");
        esperit = personatgeManager.generarStats(esperit);
        ui.showMessage("The new character " + nom + " has been created.");

        personatgeManager.crearPersonatge(nom, player, nivell, cos, ment, esperit);
    }


    private void opcio2() {
        String player, nomPersonatge, nomPersonatgeDelete;
        int totalNumPersonatge, numPersonatge;
        ui.showMessage("Tavern keeper: 'Lads! They want to see you!'");
        ui.showMessage("'Who piques your interest?'\n");
        player = ui.askForString("-> Enter the name of the Player to filter: ");
        if (player.isEmpty()) {
            ui.showMessage("\nYou watch as all adventurers get up from their chairs and approach you.");
            ui.showCharList(personatgeManager.llistarPersonatges());
            totalNumPersonatge = personatgeManager.llistarPersonatges().size();
            numPersonatge = ui.askForInteger("Who would you like to meet [0.." + totalNumPersonatge + "]: ");
            nomPersonatge = personatgeManager.llistarPersonatges().get(numPersonatge-1);
        } else {
            ui.showMessage("\nYou watch as some adventurers get up from their chairs and approach you.");
            ui.showCharList(personatgeManager.llistarPersonatgesPlayer(player));
            totalNumPersonatge = personatgeManager.llistarPersonatgesPlayer(player).size();
            numPersonatge = ui.askForInteger("Who would you like to meet [0.." + totalNumPersonatge + "]: ");
            nomPersonatge = personatgeManager.llistarPersonatgesPlayer(player).get(numPersonatge-1);
        }

        ui.showMessage("\nTavern keeper: 'Hey " + nomPersonatge + "get here; the boss wants to see you!'\n");
        ui.showMessage("*Name: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNom());
        ui.showMessage("*Player: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNomJugador());
        ui.showMessage("*Class: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getClasse());
        //El nivell no funciona pero perque no esta inicialitzat
        ui.showMessage("*Level: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNivell());
        ui.showMessage("*XP: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getExperiencia());
        ui.showMessage("*Body: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getCos());
        ui.showMessage("*Mind: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getMent());
        ui.showMessage("*Spirit: " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getEsperit());

        do {
            ui.showMessage("\n[Enter name to delete, or press enter to cancel]");
            nomPersonatgeDelete = ui.askForString("Do you want to delete " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNom() + "? ");
            if (nomPersonatgeDelete.isEmpty()) {
                ui.showMessage("\nTavern keeper: 'That's enough " + personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNom() + ", you can go back with your buddys'\n");
            } else {
                if (!nomPersonatge.equals(nomPersonatgeDelete)) {
                    ui.showMessage("\nTavern keeper: 'That's not who we are talking about. Make a decision.'");
                }
            }
            if (nomPersonatge.equals(nomPersonatgeDelete)) {
                ui.showMessage("\nTavern keeper: 'I'm sorry kiddo, but you have to leave.'");
                ui.showMessage("\nCharacter "+ personatgeManager.retornaPersonatgeComplert(nomPersonatge).getNom() + " left the Guild.\n");
                personatgeManager.borrarPersonatge(nomPersonatge);
            }
        } while ((!nomPersonatge.equals(nomPersonatgeDelete)) && !nomPersonatgeDelete.isEmpty());
    }

    private void opcio3() {
        String nomAventura;
        int numCombats, opcioMonstres, totalNumMonstres, numMonstre, quantitatMonstre, monsterDelete;
        Aventura aventura;
        Boolean continuee = false;

        ui.showMessage("\nTavern keeper: 'Planning an adventure? Good luck with that!'\n");
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
                ui.showMessage("*Monsters in encounter");
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
                        quantitatMonstre = ui.askForInteger("-> How many " + monstreManager.retornaMonstreComplert(numMonstre).getNom() + "(s) do you want to add: ");
                        aventuraManager.afegirMonstreCombat(aventura, numMonstre, quantitatMonstre, i);
                        break;
                    case 2:
                        if (!aventura.getCombats().get(i).getMonstre().isEmpty()) {
                            monsterDelete = ui.askForInteger("-> Which monster do you want to delete: ");
                            while (monsterDelete > aventuraManager.generarLlistaMonstres(aventura.getCombats().get(i)).size()) {
                                ui.showMessage("\nThe combat doesn't have so many monsters in it. Enter a valid number.\n");
                                monsterDelete = ui.askForInteger("-> Which monster do you want to delete: ");

                            }
                            aventuraManager.borrarMonstreCombat(aventura, monsterDelete, i);
                            //ultima
                        } else {
                            ui.showMessage("\nIt's not possible to delete monsters if there are none added.\n");
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
        ui.showMessage("The new adventure " + aventura.getNom() + " has been created.\n");



    }
    private void opcio4() {
        int numAventuras, numOfCharacters, countPJ = 0, personatgeAfegir, iniciativa, roundCounter = 0;
        int i = 0;
        String fraseBoss;
        List<Personatge> personatgesOrdenats = new ArrayList<>();
        List<Monstre> monstresOrdenats = new ArrayList<>();

        ui.showMessage("Tavern keeper: 'So, you are looking to go on an adventure?'");
        ui.showMessage("'Where do you fancy going?'\n");

        ui.showMessage("Available adventures:");
        ui.showMonsterList(aventuraManager.llistarAventuras());
        numAventuras = ui.askForInteger("-> Choose an adventure: ");
        while (numAventuras > aventuraManager.llistarAventuras().size()) {
            ui.showMessage("\nEnter a valid number.\n");
            numAventuras = ui.askForInteger("-> Choose an adventure: ");
        }
        ui.showMessage("Tavern keeper: '" + aventuraManager.retornaAventuraComplerta(numAventuras).getNom() + " it is!'" );
        ui.showMessage("'And how many people shall join you?'\n");
        numOfCharacters = ui.askForInteger("-> Choose a number of characters [3..5]: ");
        while (numOfCharacters < 3 || numOfCharacters > 5) {
            ui.showMessage("\nEnter a valid number.\n");
            numOfCharacters = ui.askForInteger("-> Choose a number of characters [3..5]: ");
        }
        ui.showMessage("Tavern keeper: 'Great, " + numOfCharacters + " it is.'");
        ui.showMessage("'Who among these lads shall join you?'\n");
        do{
            ui.showPartyList(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges(), numOfCharacters, countPJ);
            ui.showMessage("Available characters: ");
            ui.showMonsterList(personatgeManager.llistarPersonatges());
            personatgeAfegir = ui.askForInteger("-> Choose character " + (countPJ+1) + " in your party: ");
            while (personatgeAfegir > personatgeManager.llistarPersonatges().size() || personatgeAfegir < 1) {
                ui.showMessage("\nEnter a valid integer.\n");
                personatgeAfegir = ui.askForInteger("-> Choose character " + (countPJ+1) + " in your party: ");
            }
            aventuraManager.afegirPersonatgeAventura(aventuraManager.retornaAventuraComplerta(numAventuras), personatgeAfegir);
            countPJ++;
        }while (countPJ < numOfCharacters);
        ui.showPartyList(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges(), numOfCharacters, countPJ);
        ui.showMessage("\nTavern keeper: 'Great, good luck on your adventure lads!'\n");
        ui.showMessage("The '" + aventuraManager.retornaAventuraComplerta(numAventuras).getNom() + "' will start soon...\n");
        for (i=0; i<aventuraManager.retornaAventuraComplerta(numAventuras).getCombats().size(); i++) {
            ui.showMessage("\n-------------------------");
            ui.showMessage("Starting Encounter " + (i+1) + ": ");
            for (int j = 0; j < aventuraManager.generarLlistaMonstres2(aventuraManager.retornaAventuraComplerta(numAventuras).getCombats().get(i)).size(); j++) {
                ui.showMessage("\t" + aventuraManager.generarLlistaMonstres2(aventuraManager.retornaAventuraComplerta(numAventuras).getCombats().get(i)).get(j));
            }
            ui.showMessage("-------------------------\n");
            ui.showMessage("\n-------------------------");
            ui.showMessage("*** Preparation stage ***");
            ui.showMessage("-------------------------");
            for (int k = 0; k<aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().size(); k++) {
                ui.showMessage(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(k).getNom() + "uses Self-Motivated. Their Spirit increases in +1.");
                personatgeManager.suportPersonatge(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(k));
            }
            for (int k = 0; k<aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().size(); k++) {
                iniciativa = personatgeManager.calcularIniciativa(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(k));
                personatgeManager.retornaPersonatgeComplert(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(k).getNom()).setIniciativa(iniciativa);
                aventuraManager.ordenarPersonatgesSegonsIniciatives(personatgesOrdenats, aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(k));
            }
            for (int k = 0; k<aventuraManager.retornaAventuraComplerta(numAventuras).getCombats().get(i).getMonstre().size(); k++) {
                aventuraManager.ordenarMonstresSegonsIniciatives(monstresOrdenats, aventuraManager.retornaAventuraComplerta(numAventuras).getCombats().get(i).getMonstre().get(k));
            }
            for (int u = 0; u<aventuraManager.mostrarOrdreIniciativas(personatgesOrdenats, monstresOrdenats).size(); u++) {
                ui.showMessage("/t" + aventuraManager.mostrarOrdreIniciativas(personatgesOrdenats, monstresOrdenats).get(u));
            }
            ui.showMessage("\n--------------------");
            ui.showMessage("*** Combat stage ***");
            ui.showMessage("--------------------\n");
            while (!personatgeManager.totalPartyUnconscius(personatgesOrdenats) || !monstreManager.totalMonstersUnconscius(monstresOrdenats)) {
                roundCounter++;
                ui.showMessage("Round " + roundCounter + ": ");
                ui.showMessage("Party:");
                int contadorPersonatge = 0;
                int contadorMonstre = 0;
                ui.showBasicList(aventuraManager.showPartyHP(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges()));
                for (int p=0; p<(monstresOrdenats.size() + personatgesOrdenats.size()); p++) {
                    if (aventuraManager.nomsOrdreIniciativas(personatgesOrdenats, monstresOrdenats).get(p).equals(personatgesOrdenats.get(contadorPersonatge).getNom())) {
                        if (personatgeManager.estaInconscient(personatgesOrdenats.get(contadorPersonatge))) {
                            contadorPersonatge++;
                        } else {
                            int mal = personatgeManager.atacarPersonatge(personatgesOrdenats.get(contadorPersonatge));
                            int posMenorMonstre = monstreManager.posicioMonstreMenysHP(monstresOrdenats);
                            ui.showMessage("\n" + personatgesOrdenats.get(contadorPersonatge).getNom() + " attacks " + monstresOrdenats.get(posMenorMonstre).getNom() + " with Sword Slash.");
                            ui.AttackMissHitCrit(mal);
                            // no funciona perk a la fase 2 tots els atacs eran fisics (falta crearho quan creem la resta de pj)
                            if (monstresOrdenats.get(posMenorMonstre).getTipusDeMal().equals(personatgesOrdenats.get(contadorPersonatge).getTipusDeMal)) {
                                mal = mal/2;
                            }
                            monstreManager.monstreRebMal(monstresOrdenats.get(posMenorMonstre), mal);
                            if (monstreManager.estaInconscient(monstresOrdenats.get(posMenorMonstre))) {
                                ui.showMessage(monstresOrdenats.get(posMenorMonstre).getNom() + " dies.");
                            }
                            contadorPersonatge++;
                        }
                    }
                    if (aventuraManager.nomsOrdreIniciativas(personatgesOrdenats, monstresOrdenats).get(p).equals(monstresOrdenats.get(contadorMonstre).getNom())) {
                        if (monstreManager.estaInconscient(monstresOrdenats.get(contadorMonstre))) {
                            contadorMonstre++;
                        } else {
                            int mal = monstreManager.damageMonstre(monstresOrdenats.get(contadorMonstre));
                            if (monstresOrdenats.get(contadorMonstre).getNivellDificultat().equals("Boss")) {
                                fraseBoss = "\n" + monstresOrdenats.get(contadorMonstre).getNom() + " attacks ";
                                for (int h=0; h<aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().size(); h++) {
                                    if (!personatgeManager.estaInconscient(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h))) {
                                        fraseBoss = fraseBoss + aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h).getNom();
                                        ui.AttackMissHitCrit(mal);
                                        personatgeManager.rebreMalPersonatge(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h), mal);
                                    }
                                }
                                ui.showMessage(fraseBoss);
                                for (int h=0; h<aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().size(); h++) {
                                    if (personatgeManager.estaInconscient(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h))) {
                                        ui.showMessage(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(h).getNom() + " falls unconscious.");
                                    }
                                }

                            } else {
                                int dau = (int) (Math.random() * (personatgesOrdenats.size()));
                                while (personatgeManager.estaInconscient(personatgesOrdenats.get(dau))) {
                                    dau = (int) (Math.random() * (personatgesOrdenats.size()));
                                }
                                ui.showMessage("\n" + monstresOrdenats.get(contadorMonstre).getNom() + " attacks " + personatgesOrdenats.get(dau).getNom());
                                ui.AttackMissHitCrit(mal);
                                personatgeManager.rebreMalPersonatge(personatgesOrdenats.get(dau), mal);
                                if (personatgeManager.estaInconscient(personatgesOrdenats.get(dau))) {
                                    ui.showMessage(personatgesOrdenats.get(dau).getNom() + " falls unconscious.");
                                }
                            }
                            contadorMonstre++;
                        }
                    }
                }
                ui.showMessage("\nEnd of round " + roundCounter);
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
                        boolean pujaNivell = personatgeManager.sumarExperiencia(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(q), xpObtinguda);
                        if (pujaNivell) {
                            ui.showMessage(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(q).getNom() + " gains " + xpObtinguda + ". " + aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(q).getNom() + " levels up. They are now lvl " + aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(q).getNivell());
                        } else {
                            ui.showMessage(aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(q).getNom() + " gains " + xpObtinguda);
                        }
                    }
                    for (int q=0; q<personatgesOrdenats.size();q++) {
                        for (int n=0; n<aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().size(); n++) {
                            if (aventuraManager.retornaAventuraComplerta(numAventuras).getPersonatges().get(q).getNom().equals(personatgesOrdenats.get(n).getNom())) {
                                if (personatgeManager.estaInconscient(personatgesOrdenats.get(n))) {
                                    ui.showMessage(personatgesOrdenats.get(n).getNom() + "is unconscious.");
                                } else {
                                    int cura = personatgeManager.curarPersonatge(personatgesOrdenats.get(n));
                                    ui.showMessage(personatgesOrdenats.get(n).getNom() + "uses Bandage time. Heals " + cura + " hit points.");
                                }
                            }
                        }
                    }
                }
                if (personatgeManager.totalPartyUnconscius(personatgesOrdenats)) {
                    ui.showMessage("Tavern keeper: 'Lad, wake up. Yes, your party fell unconscious.'");
                    ui.showMessage("'Don't worry, you are safe back at the Tavern.'");
                    // se suposa que hauria de acabar la opcio 4 pero no esstic segur de si el break ho acaba o no.
                    break;
                }
            }

        }
        if (monstreManager.totalMonstersUnconscius(monstresOrdenats) && i == aventuraManager.retornaAventuraComplerta(numAventuras).getCombats().size()) {
            ui.showMessage("Congratulations, your party completed '" + aventuraManager.retornaAventuraComplerta(numAventuras).getNom() + "'");
        }
    }
}
