package main.java;

import java.util.ArrayList;
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

    public GeneratorController(Scanner scanner, UnluckyNumbersService unluckyNumbersService, String game) {
        GeneratorController.scanner = scanner;
        GeneratorController.unluckyService = unluckyNumbersService;
        System.out.println("Willkommen beim Quicktipp-Generator 'Keller's Quicktips'!\n");
        startGeneration(game);
        printOptions();
        mainLoop();
    }

    /**
     * mainLoop scans for user input until the user enters the keyword which exits the whole program
     */
    private void mainLoop(){
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
        switch (game) {
            case LOTTO -> new LottoGenerator(unluckyService).printTip();
            case EURO -> new EurojackpotGenerator(unluckyService).printTip();
            default -> {
                System.out.println("Der eingegebene Parameter war ungültig. " +
                                   "Die gültigen Parameter sind '" + LOTTO + "' und '" + EURO + "'.\n");
            }
        }
    }

    /**
     * printOptions prints the main menu options to the command line
     */
    private void printOptions() {
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
        String userInput = "-1";
        printUnluckyOptions();

        ArrayList<Integer> newUnluckyNumbersList = new ArrayList<>();
        while (!userInput.equals(DONE) && !userInput.equals(ABORT)) {
            userInput = scanner.nextLine();

            // this regex checks for single numbers and multiple numbers divided by commas
            // whitespaces at both ends and between a number and comma are accepted
            if (userInput.matches("\\s*[0-9]+(\\s*,\\s*[0-9]+)*\\s*")) {
                String[] newNumbers = userInput.trim().split("\\s*,\\s*");
                for (String n : newNumbers){
                    int newNumber = Integer.parseInt(n);
                    checkNumberAndAddToUnluckyList(newNumber, newUnluckyNumbersList);
                }
            } else {
                switch (userInput) {
                    case ABORT -> System.out.println("Es wurden keine neuen Unglückszahlen gespeichert.");
                    case DONE -> {
                        if (newUnluckyNumbersList.isEmpty()) {
                            System.out.println("Es wurden keine neuen Unglückszahlen eingegeben, die gespeichert werden könnten.");
                        } else {
                            saveUnluckyNumbers(newUnluckyNumbersList);
                        }
                    }
                    case HELP -> printUnluckyOptions();
                    default -> {
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
            // todo if a big list of numbers is entered, this is printed for every number after the sixth. It works but it is not pretty.
            System.out.println("Es wurden bereits " + MAX_UNLUCKY_NUMBERS + " Unglückszahlen eingegegeben. " +
                               "Mehr können nicht eingegeben werden.\n" +
                    "Die bereits eingegebenen Unglückszahlen sind " + newUnluckyNumbersList + "\n" +
                    "Sie können diese mit " + DONE + " speichern oder mit '" + ABORT + "' verwerfen.");
        } else if (newUnluckyNumbersList.contains(newNumber)) {
            System.out.println("Die eingegebene Zahl '" + newNumber + "' wurde bereits hinzugefügt");
        } else if (newNumber > NUMBER_SCOPE){
            System.out.println("Die eingegebene Zahl '" + newNumber + "' liegt nicht im gültigen Zahlenraum von " +
                               "1-" + NUMBER_SCOPE + " und wird nicht den neuen Unglückszahlen hinzugefügt.");
        } else {
            newUnluckyNumbersList.add(newNumber);
        }
    }

    /**
     * saveUnluckyNumbers calls the method saveUnluckyNumbers of the UnluckyNumbersService instance saved to the field
     * unluckyService and passes the newUnluckyNumbersList to it.
     * It also tells the user that the unlucky numbers were saved.
     * @param newUnluckyNumbersList - ArrayList of numbers to be saved
     */
    private void saveUnluckyNumbers(ArrayList<Integer> newUnluckyNumbersList) {
        unluckyService.saveUnluckyNumbers(newUnluckyNumbersList);
        System.out.println("Die neuen Unglückszahlen " + newUnluckyNumbersList + " wurden gespeichert.");
    }

    /**
     * printUnluckyOptions prints the menu options which are currently, i.e. when entering new unlucky numbers,
     * available to the user to the command line.
     */
    private void printUnluckyOptions() {
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
        if (unluckyService.unluckyNumbersIsEmpty()) {
            System.out.println("Es gibt keine gespeicherten Unglückszahlen.\n");
        } else {
            ArrayList<Integer> unluckyNumberList = unluckyService.getUnluckyNumbersList();
            System.out.println("Die gespeicherten Unglückszahlen sind: " + unluckyNumberList);
        }
    }

    /**
     * deleteUnluckyNumbers deletes the saved unlucky numbers.
     */
    private void deleteUnluckyNumbers() {
        unluckyService.deleteUnluckyNumbers();
        System.out.println("Die Unglückszahlen wurden gelöscht.");

    }

    /**
     * printGoodbye prints a goodbye message to the command line.
     */
    private void printGoodbye() {
        System.out.println("Bis zum nächsten Mal. Viel Glück!");
    }

    /**
     * Getter method for unluckyService field
     * @return instance of UnluckyNumbersService saved in field unluckyService of this object
     */
    public static UnluckyNumbersService getUnluckyService() {
        return unluckyService;
    }

    /**
     * Setter method for unluckyService field
     * @param fileName - String containing the relative path to the .txt-document the UnluckyNumbersService should use to store numbers
     */
    public static void setUnluckyService(String fileName) {
        unluckyService = new UnluckyNumbersService(fileName);
    }
}