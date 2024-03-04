package main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * GeneratorController is responsible for reading and responding to user interaction.
 *
 * The first quicktip is generated according to the parameter given at the start of the program,
 * after that the controller loops, asking for tasks and fulfilling them, until the user exits the program.
 */
public class GeneratorController {

    private static Scanner scanner;
    private static UnluckyNumbersService unluckyService;

    // the scope of unlucky numbers is set to 50, as that is the highest possible value across all lotteries
    private static final int NUMBER_SCOPE = 50;
    // the max amount of unlucky numbers
    private static final int MAX_UNLUCKY_NUMBERS = 6;

    // menu option keywords
    private static final String LOTTO = "lotto";
    private static final String EURO = "eurojackpot";
    private static final String EXIT = "exit";
    private static final String HELP = "help";
    private static final String ENTER = "enter unlucky";
    private static final String VIEW = "view unlucky";
    private static final String DELETE = "delete unlucky";
    private static final String DONE = "done";
    private static final String ABORT = "abort";

    public GeneratorController(Scanner scanner, UnluckyNumbersService unluckyNumbersService, String[] args) {
        GeneratorController.scanner = scanner;
        GeneratorController.unluckyService = unluckyNumbersService;
        System.out.println("Willkommen beim Quicktipp-Generator 'Keller's Quicktips'!\n");
        QuicktipLogger.info("Controller was instantiated.");

        String game = LOTTO;
        ArrayList<Integer> newUnluckyNumbersList = new ArrayList<>();
        game = processArguments(args, game, newUnluckyNumbersList);
        saveUnluckyNumbers(newUnluckyNumbersList);
        startGeneration(game);
        printOptions();
        mainLoop();
    }

    /**
     * processArguments parses the arguments given by the user at the start of the program and matches them to either
     * a sequence of numbers which are then pared into a list which is saved as the new unlucky numbers or
     * the game parameters which determines which game the first tip is generated for.
     * If an entered argument is not recognised then that information is printed to the console and the argument is ignored.
     * If too many (>2) arguments are entered, all but the first two are ignored and that information is printed to the console.
     * @param args - arguments given by the user
     * @param game - the game for the first quicktip (default is lotto, is only changed if user entered a game parameter)
     * @param newUnluckyNumbersList - list for new unlucky numbers
     * @return
     */
    private String processArguments(String[] args, String game, ArrayList<Integer> newUnluckyNumbersList) {
        for (int i = 0; i < args.length; i++) {
            if (i >= 2) {
                QuicktipLogger.info("User added too many parameters at start of the application.");
                System.out.println("Der Quicktip Generator akzeptiert nur zwei Aufrufparameter. Nur die ersten beiden werden berücksichtigt.");
            } else if (args[i].matches("[0-9]+(,[0-9]+)*")) {
                String[] newNumbers = args[i].trim().split(",");
                for (String n : newNumbers){
                    int newNumber = Integer.parseInt(n);
                    checkNumberAndAddToUnluckyList(newNumber, newUnluckyNumbersList);
                }
            } else if (args[i].equalsIgnoreCase("lotto") || args[i].equalsIgnoreCase("eurojackpot")) {
                game = args[i];
            } else {
                QuicktipLogger.info("User provided invalid parameters at start of the application.");
                System.out.println("Der eingegebene Parameter '" + args[i] + "' ist ungültig. Die gültigen Parameter " +
                        "sind '" + LOTTO + "', '" + EURO + "' und Zahlenreihen (durch Kommata getrennt und ohne Leerzeichen).\n");
            }
        }
        QuicktipLogger.info("User input has been handled.");
        return game;
    }

    /**
     * mainLoop scans for user input until the user enters the keyword which exits the whole program
     */
    private void mainLoop(){
        QuicktipLogger.info("mainLoop of " + this.getClass().getName() + " has been entered.");
        String userInput = "-1";

        while (!userInput.equals(EXIT)) {
            userInput = scanner.nextLine();

            switch (userInput) {
                case LOTTO -> startGeneration(LOTTO);
                case EURO -> startGeneration(EURO);
                case ENTER -> collectUnluckyNumbers();
                case VIEW -> printUnluckyNumbers();
                case DELETE -> deleteUnluckyNumbers();
                case HELP -> printOptions();
                case EXIT -> printGoodbye();
                default -> {
                    QuicktipLogger.info("User input did not match any of the main options.");
                    System.out.println("Die Eingabe '" + userInput + "' korrespondiert mit keiner der angebotenen Optionen.");
                    printOptions();
                }
            }
        }
    }

    /**
     * startGeneration calls the printTip method of an instance of a subclass of GeneratorImpl,
     * choosing the right subclass according to the entered parameter.
     * @param game - selects the game for which a quicktip is generated
     */
    public void startGeneration(String game) {
        QuicktipLogger.info("User requested a quicktip for " + game + ".");
        switch (game) {
            case LOTTO -> new LottoGenerator(unluckyService).printTip();
            case EURO -> new EurojackpotGenerator(unluckyService).printTip();
        }
    }

    /**
     * printOptions prints the main menu options to the command line
     */
    private void printOptions() {
        QuicktipLogger.info("Main menu options were printed to the command line.");
        System.out.println("Geben Sie eines der folgenden Wörter ein, um eine Option auszuwählen:\n" +
                           "    1. " + LOTTO + "            - Quicktip für Lotto 6aus49 generieren\n" +
                           "    2. " + EURO + "      - Quicktip für Eurojackpot 5aus50 plus 2aus10 generieren\n" +
                           "    3. " + ENTER + "    - neue Unglückszahlen speichern (die alten werden gelöscht)\n" +
                           "    4. " + VIEW + "     - gespeicherte Unglückszahlen anzeigen\n" +
                           "    5. " + DELETE + "   - gespeicherte Unglückszahlen löschen\n" +
                           "    6. " + HELP + "             - Optionen anzeigen\n" +
                           "    7. " + EXIT + "             - Programm schließen\n" +
                           "Was würden Sie gerne tun?\n");
    }

    /**
     * collectUnluckyNumbers scans user input for numbers, checks them and adds them to an array until the user enters
     * the keyword which causes them (or the first six entered) to be saved or until the user aborts the process of
     * entering new unlucky numbers.
     */
    private void collectUnluckyNumbers() {
        QuicktipLogger.info("User requested to enter new unlucky numbers.");
        String userInput = "-1";
        printUnluckyOptions();

        ArrayList<Integer> newUnluckyNumbersList = new ArrayList<>();
        while (!userInput.equals(DONE) && !userInput.equals(ABORT)) {
            userInput = scanner.nextLine();

            // this regex checks for single numbers and multiple numbers divided by commas
            // whitespaces at both ends and between a number and comma are accepted
            if (userInput.matches("\\s*[0-9]+(\\s*,\\s*[0-9]+)*\\s*")) {
                QuicktipLogger.info("User entered new unlucky numbers to be saved: " + userInput);
                String[] newNumbers = userInput.trim().split("\\s*,\\s*");
                for (String n : newNumbers){
                    int newNumber = Integer.parseInt(n);
                    checkNumberAndAddToUnluckyList(newNumber, newUnluckyNumbersList);
                }
            } else {
                switch (userInput) {
                    case ABORT -> {
                        QuicktipLogger.info("User aborted the proces of entering new unlucky numbers.");
                        System.out.println("Es wurden keine neuen Unglückszahlen gespeichert.");
                    }
                    case DONE -> {
                        QuicktipLogger.info("User requested to saved entered unlucky numbers.");
                        saveUnluckyNumbers(newUnluckyNumbersList);
                    }
                    case HELP -> printUnluckyOptions();
                    default -> {
                        QuicktipLogger.info("User input did not match any of the enter-unlucky options..");
                        System.out.println("Die Eingabe '" + userInput + "' korrespondiert mit keiner der angebotenen Optionen.");
                        printUnluckyOptions();
                    }
                }
            }
        }
        printOptions();
    }

    /**
     * checkNumberAndAddToUnluckyList checks whether the newUnluckyNumbersList is already full, whether the number to be
     * added is already in newUnluckyNumberList, and whether the number is outside of the allowed range.
     * If the answer to all of those questions is 'no', the number is added to the list.
     * @param newNumber - number to be checked and potentially added
     * @param newUnluckyNumbersList - list of already added new unlucky numbers
     */
    private void checkNumberAndAddToUnluckyList(int newNumber, ArrayList<Integer> newUnluckyNumbersList) {
        if (newUnluckyNumbersList.size() == MAX_UNLUCKY_NUMBERS) {
            QuicktipLogger.info("User exceeded limit for new unlucky numbers.");
            // todo if a big list of numbers is entered, this is printed for every number after the sixth. It works but it is not pretty.
            System.out.println("Es wurden bereits " + MAX_UNLUCKY_NUMBERS + " Unglückszahlen eingegegeben. " +
                               "Mehr können nicht eingegeben werden.\n" +
                    "Die bereits eingegebenen Unglückszahlen sind " + newUnluckyNumbersList + "\n" +
                    "Sie können diese mit '" + DONE + "' speichern oder mit '" + ABORT + "' verwerfen.");
        } else if (newUnluckyNumbersList.contains(newNumber)) {
            QuicktipLogger.info("User tried to add " + newNumber + " twice to new unlucky numbers.");
            System.out.println("Die eingegebene Zahl '" + newNumber + "' wurde bereits hinzugefügt");
        } else if (newNumber > NUMBER_SCOPE){
            QuicktipLogger.info("User tried to add " + newNumber + ", which it is out of scope, to new unlucky numbers.");
            System.out.println("Die eingegebene Zahl '" + newNumber + "' liegt nicht im gültigen Zahlenraum von " +
                               "1-" + NUMBER_SCOPE + " und wird nicht den neuen Unglückszahlen hinzugefügt.");
        } else {
            QuicktipLogger.info("User added " + newNumber + " to new unlucky numbers.");
            newUnluckyNumbersList.add(newNumber);
        }
    }

    /**
     * saveUnluckyNumbers calls the method saveUnluckyNumbers of the UnluckyNumbersService instance saved to the field
     * unluckyService and passes the sorted newUnluckyNumbersList to it.
     * It also tells the user if the unlucky numbers were saved.
     * @param newUnluckyNumbersList - ArrayList of numbers to be saved
     */
    private void saveUnluckyNumbers(ArrayList<Integer> newUnluckyNumbersList) {
        if (newUnluckyNumbersList.isEmpty()) {
            QuicktipLogger.info("User tried to save unlucky numbers before entering any.");
            System.out.println("Es wurden keine neuen Unglückszahlen eingegeben, die gespeichert werden könnten.");
        } else {
            Collections.sort(newUnluckyNumbersList);
            if (unluckyService.saveUnluckyNumbers(newUnluckyNumbersList)){
                QuicktipLogger.info("New unlucky numbers were saved.");
                System.out.println("Die neuen Unglückszahlen " + newUnluckyNumbersList + " wurden gespeichert.");
            } else {
                QuicktipLogger.info("New unlucky numbers could not be saved.");
                System.out.println("Die neuen Unglückszahlen konnten nicht gespeichert werden.");
            }
        }
    }

    /**
     * printUnluckyOptions prints the menu options which are currently, i.e. when entering new unlucky numbers,
     * available to the user to the command line.
     */
    private void printUnluckyOptions() {
        QuicktipLogger.info("Enter-unlucky menu options were printed to the command line.");
        System.out.println("Um neue Unglückszahlen zu speichern, müssen Sie zunächst die Zahlen eingeben und im Anschluss mit '" + DONE + "' bestätigen.\n" +
                           "Sie können bis zu " + MAX_UNLUCKY_NUMBERS + " Unglückszahlen, die zwischen 1-" + NUMBER_SCOPE + " liegen, speichern.\n" +
                           "Geben Sie eines der folgenden Dinge ein, um eine Option auszuwählen:\n" +
                           "    1. [zahl|zahlenreihe]   - Unglückszahl oder Unglückszahlenreihe (getrennt durch Kommata) eingeben, bspw. 13 oder 13,26,39\n" +
                           "    2. " + ABORT + "                - keine neuen Unglückszahlen speichern\n" +
                           "    3. " + DONE + "                 - eingegebene Unglückszahlen speichern\n" +
                           "    4. " + HELP + "                 - Optionen anzeigen\n" +
                           "Was würden Sie gerne tun?\n");
    }

    /**
     * printUnluckyNumbers prints either the saved unlucky numbers or the information that no unlucky numbers have been saved.
     */
    private void printUnluckyNumbers() {
        QuicktipLogger.info("User requested unlucky numbers to be printed.");
        ArrayList<Integer> unluckyNumberList = unluckyService.getUnluckyNumbersList();
        if (unluckyNumberList == null) {
            QuicktipLogger.info("Unlucky numbers could not be read.");
            System.out.println("Die gespeicherten Unglückszahlen konnten nicht gelesen werden.");
        } else if (unluckyNumberList.isEmpty()) {
            QuicktipLogger.info("There are no unlucky numbers to print.");
            System.out.println("Es gibt keine gespeicherten Unglückszahlen.\n");
        } else {
            QuicktipLogger.info("Unlucky numbers were printed to console.");
            System.out.println("Die gespeicherten Unglückszahlen sind: " + unluckyNumberList);
        }
    }

    /**
     * deleteUnluckyNumbers deletes the saved unlucky numbers.
     */
    private void deleteUnluckyNumbers() {
        QuicktipLogger.info("User requested unlucky numbers to be deleted.");
        if (unluckyService.deleteUnluckyNumbers()) {
            QuicktipLogger.info("Unlucky numbers have been deleted.");
            System.out.println("Die Unglückszahlen wurden gelöscht.");
        } else {
            QuicktipLogger.info("Unlucky numbers could not be deleted.");
            System.out.println("Die Unglückszahlen konnten nicht gelöscht werden.");
        }

    }

    /**
     * printGoodbye prints a goodbye message to the command line.
     */
    private void printGoodbye() {
        QuicktipLogger.info("Application was exited by user.");
        System.out.println("Bis zum nächsten Mal. Viel Glück!");
    }

    /**
     * Getter method for unluckyService field
     * @return instance of UnluckyNumbersService saved in field unluckyService of this object
     */
    public static UnluckyNumbersService getUnluckyService() {
        return unluckyService;
    }
}