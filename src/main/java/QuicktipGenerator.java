package main.java;

import java.util.Scanner;

/**
 * QuicktipGenerator is the main class of a java console application intended
 * to aid users in selecting their numbers for Lotto and Eurojackpot.
 * It also allows the user to save, delete, and update their unlucky numbers
 * which will not be included in the generated quicktips.
 *
 * @author Relana Streckenbach
 */
public class QuicktipGenerator {

    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Entry point of the application
     * @param args - selects the game ("lotto", "eurojackpot"), if left empty the generator defaults to "lotto"
     */
    public static void main(String[] args) {
        String game = "lotto";
        if (args.length > 0) {
            game = args[0];
        }
        new GeneratorController(scanner, game);
        scanner.close();
    }
}