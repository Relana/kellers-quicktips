package test.java;

import main.java.UnluckyNumbersService;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UnluckyNumbersServiceTest {
    private static final String TEST_FILE = "unlucky-numbers-test.txt";

    @Test
    void givenUnluckyNumbersService_whenSetUnluckyNumbersIsCalled_thenNumbersAreWrittenToFile() {
        UnluckyNumbersService unluckyNumbersService = new UnluckyNumbersService(TEST_FILE);
        ArrayList<Integer> newUnluckyNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 13));
        unluckyNumbersService.setUnluckyNumbers(newUnluckyNumbers);

        File unluckyFile = new File(TEST_FILE);
        ArrayList<Integer> savedUnluckyNumbers = new ArrayList<>();
        try (Scanner reader = new Scanner(unluckyFile).useDelimiter(",")) {
            while (reader.hasNext()) {
                int nextNumber = Integer.parseInt(reader.next());
                savedUnluckyNumbers.add(nextNumber);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        assertEquals(newUnluckyNumbers, savedUnluckyNumbers);
    }

    @Test
    void givenUnluckyNumbersService_whenGetUnluckyNumbersIsCalled_thenNumbersAreReadCorrectly() {
        UnluckyNumbersService unluckyNumbersService = new UnluckyNumbersService(TEST_FILE);
        ArrayList<Integer> newUnluckyNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 13));
        File unluckyFile = new File(TEST_FILE);
        try {
            FileWriter writer = new FileWriter(unluckyFile, false);
            String newUnluckyString = "";
            for (int n : newUnluckyNumbers) {
                newUnluckyString = newUnluckyString + n +",";
            }
            writer.write(newUnluckyString);
            writer.close();
            System.out.println("Die neuen Unglückszahlen wurden gespeichert.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        ArrayList<Integer> savedUnluckyNumbers = unluckyNumbersService.getUnluckyNumbers();

        assertEquals(newUnluckyNumbers, savedUnluckyNumbers);
    }

    @Test
    void givenUnluckyNumbersService_whenDeleteUnluckyNumbersIsCalled_thenNumbersAreDeletedInFile() {
        UnluckyNumbersService unluckyNumbersService = new UnluckyNumbersService(TEST_FILE);
        ArrayList<Integer> newUnluckyNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 13));
        unluckyNumbersService.setUnluckyNumbers(newUnluckyNumbers);
        unluckyNumbersService.deleteUnluckyNumbers();

        File unluckyFile = new File(TEST_FILE);
        ArrayList<Integer> savedUnluckyNumbers = new ArrayList<>();
        try (Scanner reader = new Scanner(unluckyFile).useDelimiter(",")) {
            while (reader.hasNext()) {
                int nextNumber = Integer.parseInt(reader.next());
                savedUnluckyNumbers.add(nextNumber);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        assertEquals(0, savedUnluckyNumbers.size());
    }
}