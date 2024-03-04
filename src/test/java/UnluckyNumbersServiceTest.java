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
    private static final UnluckyNumbersService unluckyNumbersService = new UnluckyNumbersService(TEST_FILE);
    private static final ArrayList<Integer> someUnluckyNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 13));

    @Test
    void givenSaveUnluckyNumbersIsCalled_whenNumbersAreReadFromFile_thenNumbersAreTheSame() {
        unluckyNumbersService.saveUnluckyNumbers(someUnluckyNumbers);
        ArrayList<Integer> savedUnluckyNumbers = getNumbersFromFile();
        assertEquals(someUnluckyNumbers, savedUnluckyNumbers);
    }

    @Test
    void givenSaveUnluckyNumbersIsCalled_whenOperationWasSuccessful_thenReturnValueIsTrue() {
        assertTrue(unluckyNumbersService.saveUnluckyNumbers(someUnluckyNumbers));
    }

    @Test
    void givenFileIsFilledWithUnluckyNumbers_whenGetUnluckyNumbersIsCalled_thenNumbersAreReadCorrectly() {
        fillFileWithNumbers();
        ArrayList<Integer> savedUnluckyNumbers = unluckyNumbersService.getUnluckyNumbersList();
        assertEquals(someUnluckyNumbers, savedUnluckyNumbers);
    }

    @Test
    void givenFileIsFullOfUnluckyNumbers_whenDeleteUnluckyNumbersIsCalled_thenNumbersAreDeletedInFile() {
        unluckyNumbersService.saveUnluckyNumbers(someUnluckyNumbers);
        unluckyNumbersService.deleteUnluckyNumbers();

        ArrayList<Integer> savedUnluckyNumbers = getNumbersFromFile();
        assertEquals(0, savedUnluckyNumbers.size());
    }

    @Test
    void givenFileIsFullOfUnluckyNumbers_whenDeleteUnluckyNumbersWasSuccessful_thenReturnValueIsTrue() {
        unluckyNumbersService.saveUnluckyNumbers(someUnluckyNumbers);
        boolean success = unluckyNumbersService.deleteUnluckyNumbers();

        assertTrue(success);
    }

    private ArrayList<Integer> getNumbersFromFile() {
        ArrayList<Integer> savedUnluckyNumbers = new ArrayList<>();
        try (Scanner reader = new Scanner(new File(TEST_FILE)).useDelimiter(",")) {
            while (reader.hasNextInt()) {
                int nextNumber = Integer.parseInt(reader.next());
                savedUnluckyNumbers.add(nextNumber);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while attempting to read from " + TEST_FILE + ".");
            e.printStackTrace();
        }
        return savedUnluckyNumbers;
    }

    private void fillFileWithNumbers() {
        File unluckyFile = new File(TEST_FILE);
        try (FileWriter writer = new FileWriter(unluckyFile, false)) {
            String newUnluckyString = "";
            for (int n : someUnluckyNumbers) {
                newUnluckyString = newUnluckyString + n +",";
            }
            writer.write(newUnluckyString);
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to write to " + TEST_FILE + ".");
            e.printStackTrace();
        }
    }
}