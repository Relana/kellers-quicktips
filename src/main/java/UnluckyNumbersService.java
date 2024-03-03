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
        try {
            unluckyNumbers = new File(fileName);
            if (unluckyNumbers.createNewFile()) {
                // todo diese Infos in logger, nicht an user
                System.out.println("txt erstellt: " + unluckyNumbers.getName());
            } else {
                System.out.println("txt existiert bereits.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * saveUnluckyNumbers takes a list of new numbers and writes them to the unluckyNumbers file,
     * separated by commas. Old numbers are overwritten
     * @param newUnluckyNumbers - list of numbers to be saved
     */
    public void saveUnluckyNumbers(ArrayList<Integer> newUnluckyNumbers){
        try {
            FileWriter writer = new FileWriter(unluckyNumbers, false);
            String newUnluckyString = "";
            for (int n : newUnluckyNumbers) {
                newUnluckyString = newUnluckyString + n +",";
            }
            writer.write(newUnluckyString);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * getUnluckyNumbersList reads the numbers saved in the unluckyNumbers file and returns them as an ArrayList
     * @return list of saved unlucky numbers
     */
    public ArrayList<Integer> getUnluckyNumbersList(){
        ArrayList<Integer> savedUnluckyNumbers = new ArrayList<>();

        try (Scanner reader = new Scanner(unluckyNumbers).useDelimiter(",")) {
            while (reader.hasNext()) {
                int nextNumber = Integer.parseInt(reader.next());
                savedUnluckyNumbers.add(nextNumber);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return savedUnluckyNumbers;
    }

    /**
     * deleteUnluckyNumbers deletes the saved numbers by writing with an empty string over the saved numbers in the
     * unluckyNumbers file.
     */
    public void deleteUnluckyNumbers(){
        try {
            FileWriter writer = new FileWriter(unluckyNumbers, false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean unluckyNumbersIsEmpty(){
        return getUnluckyNumbersList().isEmpty();
    }
}
