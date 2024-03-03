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

    public GeneratorController(Scanner scanner, String game) {
        GeneratorController.scanner = scanner;
        System.out.println("Willkommen beim Quicktipp-Generator 'Keller's Quicktips'!\n");
        startGeneration(game);
        mainLoop();
    }

    private void mainLoop(){
        String userInput = "-1";

        while (!userInput.equals("exit")) {
            userInput = scanner.nextLine();

            switch (userInput) {
                case "lotto" -> startGeneration("lotto");
                case "eurojackpot" -> startGeneration("eurojackpot");
                case "enter unlucky" -> updateUnluckyNumbers();
                case "view unlucky" -> displayUnluckyNumber();
                case "delete unlucky" -> deleteUnluckyNumbers();
                case "exit" -> printGoodbye();
                default -> {
                    System.out.println("Die Eingabe '" + userInput + "' korrespondiert mit keiner der angebotenen Optionen.");
                    printOptions();
                }
            }
        }
    }

    public void startGeneration(String game) {
        //todo unlucky numbers berücksichtigen
        switch (game) {
            case "eurojackpot" -> new EurojackpotGenerator(unluckyService).printTip();
            case "lotto" -> new LottoGenerator(unluckyService).printTip();
            default -> {
                System.out.println("Der eingegebene Parameter war ungültig. " +
                                   "Die gültigen Parameter sind 'lotto' und 'eurojackpot'.\n");
            }
        }
        printOptions();
    }

    private void printOptions() {
        System.out.println("""
                Geben Sie eines der folgenden Wörter ein, um eine Option auszuwählen:
                    1. lotto            - Quicktip für Lotto 6aus49 generieren
                    2. eurojackpot      - Quicktip für Eurojackpot 5aus50 plus 2aus10 generieren
                    3. enter unlucky    - neue Unglückszahlen speichern (die alten werden gelöscht)
                    4. view unlucky     - gespeicherte Unglückszahlen anzeigen
                    5. delete unlucky   - gespeicherte Unglückszahlen löschen
                    6. exit             - Programm schließen
                Was würden Sie gerne tun?
                """);
    }

    private void updateUnluckyNumbers() {
        // todo refactor this method
        ArrayList<Integer> newUnluckyNumberList = new ArrayList<>();
        String userInput = "-1";
        printUnluckyOptions();

        while (!userInput.equals("done") && !userInput.equals("abort")) {
            userInput = scanner.nextLine();

            if (userInput.matches("[0-9]+")) {
                newUnluckyNumberList.add(Integer.valueOf(userInput));
            } else if (userInput.matches("[0-9]+(\\s*,\\s*[0-9]+)+")) {
                String[] newNumbers = userInput.split("\\s*,\\s*");
                for (String n : newNumbers){
                    newUnluckyNumberList.add(Integer.valueOf(n));
                }
            } else {
                switch (userInput) {
                    case "abort" -> System.out.println("Es wurden keine neuen Unglückszahlen gespeichert.");
                    case "done" -> {
                        if (newUnluckyNumberList.isEmpty()) {
                            System.out.println("Es wurden keine neuen Unglückszahlen einegeben, die gespeichert werden könnten.");
                        } else {
                            // todo check for amount of new numbers (max 6)
                            // todo check for duplicates in user input
                            unluckyService.saveUnluckyNumbers(newUnluckyNumberList);
                            System.out.println("Die neuen Unglückszahlen wurden gespeichert.");
                        }
                    }
                    default -> {
                        System.out.println("Die Eingabe '" + userInput + "' korrespondiert mit keiner der angebotenen Optionen.");
                        printUnluckyOptions();
                    }
                }
            }
        }
        printOptions();
    }

    private void printUnluckyOptions() {
        System.out.println("""
                Um neue Unglückszahlen zu speichern, müssen Sie zunächst die Zahlen eingeben und im Anschluss mit 'done' bestätigen.
                Sie können bis zu sechs Unglückszahlen speichern.
                Geben Sie eines der folgenden Dinge ein, um eine Option auszuwählen:
                    1. [zahl|zahlenreihe]   - Unglückszahl oder Unglückszahlenreihe (getrennt durch Kommata) eingeben, bspw. 13 oder 13,26,39
                    2. abort                - keine neuen Unglückszahlen speichern
                    3. done                 - eingegebene Unglückszahlen speichern
                Was würden Sie gerne tun?
                """);
    }

    private void displayUnluckyNumber() {
        ArrayList<Integer> unluckyNumberList = unluckyService.getUnluckyNumbersList();

        if (unluckyNumberList.isEmpty()) System.out.println("Es gibt keine gespeicherten Unglückszahlen.\n");
        else System.out.println("Die gespeicherten Unglückszahlen sind: " + unluckyNumberList);

        printOptions();
    }

    private void deleteUnluckyNumbers() {
        unluckyService.deleteUnluckyNumbers();
        System.out.println("Die Unglückszahlen wurden gelöscht.");
        printOptions();
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