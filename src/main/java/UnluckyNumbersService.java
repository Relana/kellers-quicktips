package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

    public void setUnluckyNumbers(ArrayList<Integer> newUnluckyNumbers){
        try {
            FileWriter writer = new FileWriter(unluckyNumbers, false);
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
    }

    public ArrayList<Integer> getUnluckyNumbers(){
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

    public void deleteUnluckyNumbers(){
        try {
            FileWriter writer = new FileWriter(unluckyNumbers, false);
            writer.write("");
            writer.close();
            System.out.println("Die Unglückszahlen wurden gelöscht.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
