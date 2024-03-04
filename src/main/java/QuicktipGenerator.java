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

    private static final Scanner SCANNER = new Scanner(System.in);
    // the path of the .txt file which will contain the unlucky numbers
    private static final String NUMBER_FILE = "unlucky-numbers.txt";

    /**
     * Entry point of the application
     * @param args - are passed down to GeneratorController
     */
    public static void main(String[] args) {
        new GeneratorController(SCANNER, new UnluckyNumbersService(NUMBER_FILE), args);
        SCANNER.close();
    }
}