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
     * Mètode contructor del controller
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
     * Mètode que entra a una de les opcions del programa depenent el paràmetre d'entrada
     * @param option Opció elegida del menu principal
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
     * Executa l'opció 1 del programa. Crea un personatge nou.
     */
    private void opcio1() {
        String nom, player, classe;
        int nivell, cos, ment, esperit;

        nom = o1AskForName();
        player = o1AskForPlayerName();
        nivell = o1AskForLevel();
        ui.showMessage("'Great, let me get a closer look at you...'");
        ui.showMessage("\nGenerating your stats...\n");
        cos = o1GenerarStat();
        ment = o1GenerarStat();
        esperit = o1GenerarStat();
        o1MostrarStats(cos, ment, esperit);
        ui.showMessage("`\nTavern keeper: 'Looking good!");
        ui.showMessage("'And lastly, ?'\n");
        classe = o1AskForClass(nivell);
        personatgeManager.crearPersonatge(nom, player, nivell, cos, ment, esperit, classe);

        ui.showMessage("\nTavern keeper: 'Any decent party needs one of those.'");
        ui.showMessage("'I guess that means you are a " + classe + " by now, nice!");
        ui.showMessage("\nThe new character " + nom + " has been created.");
    }

    /**
     * Mètode de l'opció que demana un nom a l'usuari i el retorna en forma de String
     * @return String del nom donat per l'usuari
     */
    private String o1AskForName () {
        String nom;
        ui.showMessage("Tavern keeper: 'Oh, so you are new to this land.'");
        ui.showMessage("'What's your name?'\n");
        nom = ui.askForString("-> Enter your name: ");
        ui.showMessage("\nTavern keeper: 'Hello, " + nom + ", be welcome.'");
        return nom;
    }

    /**
     * Mètode de l'opció 1 que demana a l'usuari el nom del jugador
     * @return String del nom del jugador donat per l'usuari
     */
    private String o1AskForPlayerName() {
        String player;
        ui.showMessage("'And now, if I may break the fourth wall, who is your Player?\n'");
        player = ui.askForString("-> Enter the player's name: ");
        ui.showMessage("\nTavern keeper: 'I see, i see...'");
        return player;
    }

    /**
     * Mètode de l'opció 1 que demana a l'usuari el nivell del personatge
     * @return el nivell del personatge
     */
    private int o1AskForLevel() {
        int nivell;
        ui.showMessage("'Now, are you an experienced adventurer?'\n");
        nivell = ui.askForInteger("-> Enter the character's level [1..10]: ");
        ui.showMessage("\nTavern keeper: 'Oh, so you are level " + nivell + "!'");
        return nivell;
    }

    /**
     * Mètode de l'opció 1 que genera el nombre d'un stat del personatge a partir de dos daus
     * @return el nombre que tindrà el stat
     */
    private int o1GenerarStat() {
        int dau1, dau2;
        int stat;
        dau1 = personatgeManager.tirarDauStats();
        dau2 = personatgeManager.tirarDauStats();
        stat = personatgeManager.sumarDausStats(dau1, dau2);
        ui.showMessage("Body:   You rolled " + stat + " (" + dau1 + " and " + dau2 + ").");
        stat = personatgeManager.generarStats(stat);
        return stat;
    }

    /**
     * Mètode de l'opció 1 que mostra els stats del personatge
     * @param cos el numero del stat de cos
     * @param ment el numero del stat de ment
     * @param esperit el numero del stat d'esperit
     */
    private void o1MostrarStats(int cos, int ment, int esperit) {
        ui.showMessage("\nYour stats are:");
        ui.showMessage("\t- Body: " + cos);
        ui.showMessage("\t- Mind: " + ment);
        ui.showMessage("\t- Spirit: " + esperit);
    }

    /**
     * Mètode de lopció 1 que demana a l'usuari la classe del personatge que pot variar a partir del nivell del personatge.
     * @param nivell nivell del personatge
     * @return nom de la classe del personatge
     */
    private String o1AskForClass(int nivell) {
        String classe;
        classe = ui.askForString("-> Enter the character's initial class [Adventurer, Cleric, Mage]: ");
        while (!classe.equals("Adventurer") && !classe.equals("Cleric")  && !classe.equals("Mage")) {
            ui.showMessage("\nEnter a valid class.\n");
            classe = ui.askForString("-> Enter the character's initial class [Adventurer, Cleric, Mage]: ");
        }

        classe = personatgeManager.classeDepenentDeLvl(classe, nivell);
        return classe;
    }


    /**
     * Executa l'opció 2 del programa. Llista els personatges a partir d'un nom de jugador i dona l'opció d'esborrar un personatge.
     */
    private void opcio2() {
        String nomPersonatge;

        ui.showMessage("Tavern keeper: 'Lads! They want to see you!'");
        if (personatgeManager.llegirPersonatges().isEmpty()){
            ui.showMessage("\nThere are no characters available!");
        }
        else{
            ui.showMessage("'Who piques your interest?'\n");

            nomPersonatge = o2AskforPersonatge();
            o2ShowFullPJInfo(nomPersonatge);
            o2DecideToDelete(nomPersonatge);
        }
    }

    /**
     *Mètode de l'opció 2 que demana a l'usuari el nom del jugador i de personatge que es vol elegir.
     * @return el nom del personatge elegit.
     */
    private String o2AskforPersonatge() {
        int totalNumPersonatge, numPersonatge;
        boolean back=true;
        String player, nomPersonatge=null;

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
                    nomPersonatge = personatgeManager.llistarPersonatges().get(numPersonatge - 1);
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
                    nomPersonatge = personatgeManager.llistarPersonatgesPlayer(player).get(numPersonatge - 1);
                }
            }
        }
        return nomPersonatge;
    }

    /**
     * Mètode de l'opció 2 que mostra la informació d'un personatge.
     * @param nomPersonatge el nom del personatge que es vol mostrar la informació.
     */
    private void o2ShowFullPJInfo(String nomPersonatge) {
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
    }

    /**
     * Mètode de l'opció 2 que decideix si es vol eliminar o no un personatge.
     * @param nomPersonatge personatge que es vol eliminar o no.
     */
    private void o2DecideToDelete(String nomPersonatge) {
        String nomPersonatgeDelete;

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

    /**
     * Executa l'opció 3 del programa. Crear una aventura nova.
     */
    private void opcio3() {
        Boolean continua;
        String nomAventura = o3AskNameAventura();
        int numCombats = o3AskNumCombats();
        Aventura aventura = aventuraManager.crearAventura(nomAventura, new ArrayList<>(), numCombats);

        for (int i=0; i<numCombats; i++) {
            continua = false;
            while (!continua) {
                ui.showMessage("\n* Encounter " + (i + 1) + " / " + numCombats);
                ui.showMessage("* Monsters in encounter");
                if (aventura.getCombats().get(i).getMonstre().isEmpty()) {
                    ui.showMessage("\t# Empty\n");
                } else {
                    ui.showMonsterList(aventuraManager.generarLlistaMonstres(aventura.getCombats().get(i)));
                }

                int opcioMonstres = o3ElegirOpcioEncounter();

                switch (opcioMonstres) {
                    case 1:
                        o3AddMonstreAventura(aventura, i);
                        break;
                    case 2:
                        o3DeleteMonstreAventura(aventura,i);
                        break;
                    case 3:
                        continua = true;
                        break;
                }
            }
        }
        aventuraManager.guardarAventuraJSON(aventura);
        ui.showMessage("Tavern keeper: 'Great plan lad! I hope you won’t die!'\n");
        ui.showMessage("The new adventure " + aventura.getNom() + " has been created.");
    }

    /**
     * Mètode de l'opció 3 que demana el nom de l'aventura que es vol crear
     * @return el nom de l'aventura elegit
     */
    private String o3AskNameAventura(){
        ui.showMessage("Tavern keeper: 'Planning an adventure? Good luck with that!'\n");
        String nomAventura = ui.askForString("-> Name your adventure: ");
        ui.showMessage("\nTaven keeper: 'You plan to undertake " + nomAventura + ", really?'");
        return nomAventura;
    }

    /**
     * Mètode de l'opció 3 que demana quants combats es volen tindre en l'aventura
     * @return el nombre de combats elegit
     */
    private int o3AskNumCombats(){
        ui.showMessage("'How long will that take?'\n");
        int numCombats = ui.askForInteger("-> How many encounters do you want [1..4]: ");
        while (numCombats < 1 || numCombats > 4) {
            ui.showMessage("\nTavern keeper: 'I'm worried thats not possible, choose a correct amount of encounters'\n");
            numCombats = ui.askForInteger("-> How many encounters do you want [1..4]: ");
        }
        ui.showMessage("\nTavern keeper: " + numCombats + " encounters?'\n");

        return numCombats;
    }

    /**
     * Mètode de l'opció 3 que demana quina opció es vol elegir en la creació de l'aventura: afegir monstre, eliminar monstre o continuar.
     * @return el nombre de l'opció elegida
     */
    private int o3ElegirOpcioEncounter(){
        ui.showMessage("1. Add Monster");
        ui.showMessage("2. Remove monster");
        ui.showMessage("3. Continue\n");
        int opcioMonstres = ui.askForInteger("-> Enter an option [1..3]: ");
        while (opcioMonstres < 1 || opcioMonstres > 3) {
            ui.showMessage("Choose a correct option please\n");
            opcioMonstres = ui.askForInteger("-> Enter an option [1..3]: ");
        }
        return opcioMonstres;
    }

    /**
     * Mètode de l'opció 3 que afegeix els monstres a l'aventura.
     * @param aventura la classe aventura en la que s'afegiran els monstres
     * @param i nombre del combat en el qual s'afegiran els monstres.
     */
    private void o3AddMonstreAventura(Aventura aventura, int i){
        ui.showMonsterList(monstreManager.llistarMonstres());
        int totalNumMonstres = monstreManager.llistarMonstres().size();
        int numMonstre = ui.askForInteger("-> Choose a monster to add [1.." + totalNumMonstres + "]: ");
        while (numMonstre < 1 || numMonstre > totalNumMonstres) {
            ui.showMessage("That's not a valid number.\n");
            numMonstre = ui.askForInteger("-> Choose a monster to add [1.." + totalNumMonstres + "]: ");
        }
        if (monstreManager.retornaMonstreComplert(numMonstre).getNivellDificultat().equals("Boss") && aventuraManager.checkBossCombat(aventura, i)) {
            ui.showMessage("\nYou can't add more than one boss to the battle.");
        }
        else {
            int quantitatMonstre = ui.askForInteger("-> How many " + monstreManager.retornaMonstreComplert(numMonstre).getNom() + "(s) do you want to add: ");
            if (monstreManager.retornaMonstreComplert(numMonstre).getNivellDificultat().equals("Boss") && quantitatMonstre > 1) {
                ui.showMessage("\nYou can't add more than one boss to the battle.");
            } else {
                aventuraManager.afegirMonstreCombat(aventura, numMonstre, quantitatMonstre, i);
            }
        }
    }

    /**
     * Mètode de l'opció 3 que elimina els monstres d'un combat de l'aventura
     * @param aventura aventura en la que s'eliminaran els monstres
     * @param i nombre de combat de l'aventura
     */
    private void o3DeleteMonstreAventura(Aventura aventura, int i){
        if (!aventura.getCombats().get(i).getMonstre().isEmpty()) {
            int monsterDelete = ui.askForInteger("-> Which monster do you want to delete: ");
            while (monsterDelete < 1 || monsterDelete > aventuraManager.generarLlistaMonstres(aventura.getCombats().get(i)).size()) {
                ui.showMessage("\nThe combat doesn't have this amount of monsters in it. Enter a valid number.\n");
                monsterDelete = ui.askForInteger("-> Which monster do you want to delete: ");

            }
            String monstresEliminats = aventuraManager.borrarMonstreCombat(aventura, monsterDelete, i);
            ui.showMessage("\n" + monstresEliminats +" were removed from the encounter.");
        } else {
            ui.showMessage("\nIt's not possible to delete monsters if there are none added.");
        }
    }

    /**
     * Executa l'opció 4 del programa. Començar una aventura.
     */
    private void opcio4() {
        int numAventuras, numOfCharacters;
        int i;
        boolean dead = false;
        List<Personatge> personatgesOrdenats = new ArrayList<>();
        List<Monstre> monstresOrdenats = new ArrayList<>();
        List<String> iniciativesOrdenades;

        ui.showMessage("Tavern keeper: 'So, you are looking to go on an adventure?'");
        if (aventuraManager.llegirAventures().isEmpty()){
            ui.showMessage("'Sorry, there are not adventures available!");
        }
        else{
            numAventuras = o4ChooseAventura();
            Aventura currentAventura = aventuraManager.retornaAventuraComplerta(numAventuras);
            ui.showMessage("\nTavern keeper: '" + currentAventura.getNom() + " it is!'" );

            numOfCharacters = o4NumCharacters();
            o4ChooseCharacters(currentAventura, numOfCharacters);

            ui.showMessage("\nTavern keeper: 'Great, good luck on your adventure lads!'\n");
            ui.showMessage("The '" + currentAventura.getNom() + "' will start soon...\n");

            for (i=0; i<currentAventura.getCombats().size() && !dead; i++) {
                monstreManager.inicialitzarBosses(currentAventura.getCombats().get(i).getMonstre());
                personatgesOrdenats.clear();
                monstresOrdenats.clear();

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

                o4CombatStage(currentAventura, personatgesOrdenats, monstresOrdenats);

            }
            if (monstreManager.totalMonstersUnconscius(monstresOrdenats) && i == currentAventura.getCombats().size()) {
                ui.showMessage("\nCongratulations, your party completed '" + currentAventura.getNom() + "'");
            }
        }
    }

    /**
     * Mètode de l'opció 4 que demana a l'usuari quina aventura es vol jugar.
     * @return el nombre de l'aventura que elegeix l'usuari.
     */
    private int o4ChooseAventura(){
        ui.showMessage("'Where do you fancy going?'\n");
        ui.showMessage("Available adventures:");
        ui.showMonsterList(aventuraManager.llistarAventuras());
        ui.showMessage("");
        int numAventuras = ui.askForInteger("-> Choose an adventure: ");
        while (numAventuras > aventuraManager.llistarAventuras().size()) {
            ui.showMessage("\nEnter a valid number.\n");
            numAventuras = ui.askForInteger("-> Choose an adventure: ");
        }
        return numAventuras;
    }

    /**
     * Mètode de l'opció 4 que demana el nombre de personatges que jugaran l'aventura
     * @return el nombre de personatges que jugaran l'aventura
     */
    private int o4NumCharacters(){
        ui.showMessage("'And how many people shall join you?'\n");
        int numOfCharacters = ui.askForInteger("-> Choose a number of characters [3..5]: ");
        while (numOfCharacters < 3 || numOfCharacters > 5) {
            ui.showMessage("\nEnter a valid number.\n");
            numOfCharacters = ui.askForInteger("-> Choose a number of characters [3..5]: ");
        }
        ui.showMessage("Tavern keeper: 'Great, " + numOfCharacters + " it is.'");
        return numOfCharacters;
    }

    /**
     * Mètode de l'opció 4 que elegeix quins personatges jugaran l'aventura.
     * @param currentAventura l'aventura que es jugarà
     * @param numOfCharacters el nombre total de personatges que jugaran l'aventura
     */
    private void o4ChooseCharacters(Aventura currentAventura, int numOfCharacters){
        int countPJ = 0;
        ui.showMessage("'Who among these lads shall join you?'\n");
        do{
            ui.showPartyList(currentAventura.getPersonatges(), numOfCharacters, countPJ);
            ui.showMessage("Available characters: ");
            ui.showMonsterList(personatgeManager.llistarPersonatges());
            int personatgeAfegir = ui.askForInteger("\n-> Choose character " + (countPJ+1) + " in your party: ");
            while (personatgeAfegir > personatgeManager.llistarPersonatges().size() || personatgeAfegir < 1) {
                ui.showMessage("\nEnter a valid integer.\n");
                personatgeAfegir = ui.askForInteger("-> Choose character " + (countPJ+1) + " in your party: ");
            }
            aventuraManager.afegirPersonatgeAventura(currentAventura, personatgeAfegir);
            countPJ++;
        }while (countPJ < numOfCharacters);
        personatgeManager.inicialitzaPersonatges(currentAventura.getPersonatges());

        ui.showPartyList(currentAventura.getPersonatges(), numOfCharacters, countPJ);
    }

    /**
     * Mètode de l'opció 4 que simula el combat que es fa dins de l'aventura.
     * @param currentAventura aventura actual que es juga
     * @param personatgesOrdenats llista dels personatges de l'aventura ordenats per iniciativa
     * @param monstresOrdenats llista dels monstres ordenats per iniciativa
     */
    private void o4CombatStage(Aventura currentAventura, List<Personatge> personatgesOrdenats, List<Monstre> monstresOrdenats){
        boolean dead = false;
        int roundCounter = 0;
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
                o4combatWin(currentAventura, personatgesOrdenats, monstresOrdenats);
            }
            if (personatgeManager.totalPartyUnconscius(personatgesOrdenats)) {
                ui.showMessage("\nTavern keeper: 'Lad, wake up. Yes, your party fell unconscious.'");
                ui.showMessage("'Don't worry, you are safe back at the Tavern.'");
                dead = true;
            }
        }
    }

    /**
     * Mètode de l'opció 4 que s'executa quan els personatges guanyen el combat
     * @param currentAventura aventura que es juga
     * @param personatgesOrdenats llista dels personatges ordenats per iniciativa
     * @param monstresOrdenats llista dels monstres ordenats per iniciativa
     */
    private void o4combatWin(Aventura currentAventura, List<Personatge> personatgesOrdenats,List<Monstre> monstresOrdenats){

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

}