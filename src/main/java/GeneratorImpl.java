package main.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * GeneratorImpl implements the Generator interface and is the superclass
 * of LottoGenerator and EurojackpotGenerator.
 */
public class GeneratorImpl implements Generator {

    private final Random random = new Random();

    private static UnluckyNumbersService unluckyService;

    public GeneratorImpl (UnluckyNumbersService unluckyNumbersService) {
        unluckyService = unluckyNumbersService;
    }

    @Override
    public ArrayList<Integer> generateTip(int tipSetSize, int scope) {
        QuicktipLogger.info("Starting to generate new quicktip.");
        ArrayList<Integer> quicktip = new ArrayList<>();
        while (quicktip.size() < tipSetSize) {
            int number = random.nextInt(scope) + 1;
            number = checkForDuplicates(number, quicktip, scope);
            // todo refactor so that unlucky numbers are disregarded earlier?
            if (!unluckyService.getUnluckyNumbersList().contains(number)) {
                QuicktipLogger.info("New unique, lucky number was added to quicktip: " + number);
                quicktip.add(number);
            }
        }
        Collections.sort(quicktip);
        return quicktip;
    }

    @Override
    public int checkForDuplicates(int number, ArrayList<Integer> quicktip, int scope) {
        QuicktipLogger.info("Starting to check new number " + number + " for uniqueness relative to quicktip " + quicktip + ".");
        for (Integer i : quicktip) {
            if (i == number) {
                number = random.nextInt(scope) + 1;
                QuicktipLogger.info("Switching out " + i + " for newly generated " + number + ".");
                number = checkForDuplicates(number, quicktip, scope);
            }
        }

        QuicktipLogger.info(number + " has been found to be unique relative to quicktip " + quicktip + ".");
        return number;
    }

    /**
     * Getter method for unluckyService field
     * @return instance of UnluckyNumbersService saved in field unluckyService of this object
     */
    public static UnluckyNumbersService getUnluckyService() {
        return unluckyService;
    }
}