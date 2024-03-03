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
    private static UnluckyNumbersService unluckyService = new UnluckyNumbersService("unlucky-numbers.txt");

    // the scope of unlucky numbers is set to 50, as that is the highest possible value across all lotteries
    private static final int NUMBER_SCOPE = 50;

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

    public GeneratorController(Scanner scanner, String game) {
        GeneratorController.scanner = scanner;
        System.out.println("Willkommen beim Quicktipp-Generator 'Keller's Quicktips'!\n");
        startGeneration(game);
        printOptions();
        mainLoop();
    }

    private void mainLoop(){
        String userInput = "-1";

        while (!userInput.equals(EXIT)) {
            userInput = scanner.nextLine();

            switch (userInput) {
                case LOTTO -> startGeneration(LOTTO);
                case EURO -> startGeneration(EURO);
                case ENTER -> updateUnluckyNumbers();
                case VIEW -> printUnluckyNumber();
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

    private void updateUnluckyNumbers() {
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
                    checkNumberAndAddToUnluckyList(n, newUnluckyNumbersList);
                }
            } else {
                switch (userInput) {
                    case ABORT -> System.out.println("Es wurden keine neuen Unglückszahlen gespeichert.");
                    case DONE -> {
                        if (newUnluckyNumbersList.isEmpty()) {
                            System.out.println("Es wurden keine neuen Unglückszahlen einegeben, die gespeichert werden könnten.");
                        } else {
                            unluckyService.saveUnluckyNumbers(newUnluckyNumbersList);
                            System.out.println("Die neuen Unglückszahlen " + newUnluckyNumbersList + " wurden gespeichert.");
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

    private void checkNumberAndAddToUnluckyList(String number, ArrayList<Integer> newUnluckyNumbersList) {
        int newNumber = Integer.parseInt(number);
        if (newUnluckyNumbersList.size() == 6) {
            // todo if a big list of numbers is entered, this is printed for every number after the sixth. It works but it is not pretty.
            System.out.println("Es wurden bereits sechs Unglückszahlen eingegegeben. Mehr können nicht eingegeben werden.\n" +
                    "Die bereits eingegebenen Unglückszahlen sind " + newUnluckyNumbersList + "\n" +
                    "Sie können diese mit " + DONE + " speichern oder mit '" + ABORT + "' verwerfen.");
        } else if (newUnluckyNumbersList.contains(newNumber)) {
            System.out.println("Die eingegebene Zahl '" + newNumber + "' wurde bereits hinzugefügt");
        } else if (newNumber <= NUMBER_SCOPE){
            newUnluckyNumbersList.add(newNumber);
        } else {
            System.out.println("Die eingegebene Zahl '" + newNumber + "' liegt nicht im gültigen Zahlenraum von " +
                    "1-" + NUMBER_SCOPE + " und wird nicht den neuen Unglückszahlen hinzugefügt.");
        }
    }

    private void printUnluckyOptions() {
        System.out.println("Um neue Unglückszahlen zu speichern, müssen Sie zunächst die Zahlen eingeben und im Anschluss mit '" + DONE + "' bestätigen.\n" +
                           "Sie können bis zu sechs Unglückszahlen, die zwischen 1-" + NUMBER_SCOPE + " liegen, speichern.\n" +
                           "Geben Sie eines der folgenden Dinge ein, um eine Option auszuwählen:\n" +
                           "    1. [zahl|zahlenreihe]   - Unglückszahl oder Unglückszahlenreihe (getrennt durch Kommata) eingeben, bspw. 13 oder 13,26,39\n" +
                           "    2. " + ABORT + "                - keine neuen Unglückszahlen speichern\n" +
                           "    3. " + DONE + "                 - eingegebene Unglückszahlen speichern\n" +
                           "    4. " + HELP + "                 - Optionen anzeigen\n" +
                           "Was würden Sie gerne tun?\n");
    }

    private void printUnluckyNumber() {
        ArrayList<Integer> unluckyNumberList = unluckyService.getUnluckyNumbersList();

        if (unluckyNumberList.isEmpty()) System.out.println("Es gibt keine gespeicherten Unglückszahlen.\n");
        else System.out.println("Die gespeicherten Unglückszahlen sind: " + unluckyNumberList);
    }

    private void deleteUnluckyNumbers() {
        unluckyService.deleteUnluckyNumbers();
        System.out.println("Die Unglückszahlen wurden gelöscht.");
    }

    private void printGoodbye() {
        System.out.println("Bis zum nächsten Mal. Viel Glück!");
    }

    public static UnluckyNumbersService getUnluckyService() {
        return unluckyService;
    }

    public static void setUnluckyService(String fileName) {
        unluckyService = new UnluckyNumbersService(fileName);
    }
}