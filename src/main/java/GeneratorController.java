package main.java;

import java.util.Scanner;

/**
 * GeneratorController is responsible for reading and responding to user interaction.
 *
 * The first quicktip is generated according to the parameter given at the start of the program,
 * after that the controller loops, asking for tasks and fulfilling them, until the user exits the program.
 */
public class GeneratorController {

    public static Scanner scanner;

    public GeneratorController(Scanner scanner, String game) {
        GeneratorController.scanner = scanner;
        System.out.println("Willkommen beim Quicktipp-Generator 'Keller's Quicktips'!\n");
        startGeneration(game);
        mainLoop();
    }

    private void mainLoop(){
        String userInput = "-1";

        while (!userInput.equals("exit")) {
            userInput = scanner.next();

            switch (userInput) {
                case "lotto" -> startGeneration("lotto");
                case "eurojackpot" -> startGeneration("eurojackpot");
                case "exit" -> printGoodbye();
                default -> {
                    System.out.println("Die Eingabe '" + userInput + "' korrespondiert mit keiner der angebotenen Optionen.");
                    printOptions();
                }
            }
        }
    }

    public void startGeneration(String game) {
        switch (game) {
            case "eurojackpot" -> new EurojackpotGenerator().printTip();
            case "lotto" -> new LottoGenerator().printTip();
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

                1. lotto        - Quicktip für Lotto 6aus49 generieren
                2. eurojackpot  - Quicktip für Eurojackpot 5aus50 plus 2aus10 generieren
                3. exit         - Programm schließen

                Was würden Sie gerne tun?
                """);
    }

    private void printGoodbye() {
        System.out.println("Bis zum nächsten Mal. Viel Glück!");
    }
}