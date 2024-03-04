package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * UnluckyNumbersService is responsible for writing, retrieving and deleting (unlucky) numbers in a file.
 */
public class UnluckyNumbersService {

    private File unluckyNumbers;

    public UnluckyNumbersService(String fileName){
        QuicktipLogger.info("UnluckyNumbersService was instantiated.");
        try {
            unluckyNumbers = new File(fileName);
            if (unluckyNumbers.createNewFile()) {
                QuicktipLogger.info("txt file for saving unlucky numbers was created.");
            } else {
                QuicktipLogger.info("txt file for saving unlucky numbers already existed.");
            }
        } catch (IOException e) {
            QuicktipLogger.warn("An error occurred while creating " + this.getClass().getName() + " with path " + fileName + ".", e);
        }
    }

    /**
     * saveUnluckyNumbers takes a list of new numbers and writes them to the unluckyNumbers file,
     * separated by commas. Old numbers are overwritten
     * @param newUnluckyNumbers - list of numbers to be saved
     * @return bool value - true if saving was successful, otherwise false
     */
    public boolean saveUnluckyNumbers(ArrayList<Integer> newUnluckyNumbers){
        QuicktipLogger.info("Attempting to save new unlucky numbers.");
        boolean success = false;
        try (FileWriter writer = new FileWriter(unluckyNumbers, false)) {
            String newUnluckyString = "";
            for (int n : newUnluckyNumbers) {
                newUnluckyString = newUnluckyString + n + ",";
            }
            writer.write(newUnluckyString);
            success = true;
            QuicktipLogger.info("Successfully saved new unlucky numbers.");
        } catch (IOException e) {
            QuicktipLogger.warn("An error occurred while attempting to write to " + unluckyNumbers.getName() + ".", e);
        }
        return success;
    }

    /**
     * getUnluckyNumbersList reads the numbers saved in the unluckyNumbers file and returns them as an ArrayList
     * @return list of saved unlucky numbers, is null if numbers could not be read
     */
    public ArrayList<Integer> getUnluckyNumbersList(){
        QuicktipLogger.info("Attempting to read unlucky numbers.");
        ArrayList<Integer> savedUnluckyNumbers = new ArrayList<>();

        try (Scanner reader = new Scanner(unluckyNumbers).useDelimiter(",")) {
            while (reader.hasNext()) {
                int nextNumber = Integer.parseInt(reader.next());
                savedUnluckyNumbers.add(nextNumber);
            }
        } catch (FileNotFoundException e) {
            savedUnluckyNumbers = null;
            QuicktipLogger.warn("An error occurred while attempting to read from " + unluckyNumbers.getName() + ".", e);
        }
        QuicktipLogger.info("Returning read unlucky numbers.");
        return savedUnluckyNumbers;
    }

    /**
     * deleteUnluckyNumbers deletes the saved numbers by writing with an empty string over the saved numbers in the
     * unluckyNumbers file.
     * @return bool value - true if deletion was successful, otherwise false
     */
    public boolean deleteUnluckyNumbers(){
        QuicktipLogger.info("Attempting to delete unlucky numbers.");
        boolean success = false;
        try(FileWriter writer = new FileWriter(unluckyNumbers, false)) {
            writer.write("");
            success = true;
            QuicktipLogger.info("Succeeded in deleting unlucky numbers.");
        } catch (IOException e) {
            QuicktipLogger.warn("An error occurred while attempting to delete numbers in " + unluckyNumbers.getName() + ".", e);
        }
        return success;
    }

    public boolean unluckyNumbersIsEmpty(){
        return getUnluckyNumbersList().isEmpty();
    }
}
