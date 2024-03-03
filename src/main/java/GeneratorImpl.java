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
        ArrayList<Integer> quicktip = new ArrayList<>();
        while (quicktip.size() < tipSetSize) {
            int number = random.nextInt(scope) + 1;
            number = checkForDuplicates(number, quicktip, scope);
            // todo refactor so that unlucky numbers are disregarded earlier?
            if (!unluckyService.getUnluckyNumbersList().contains(number)) quicktip.add(number);
        }
        Collections.sort(quicktip);
        return quicktip;
    }

    @Override
    public int checkForDuplicates(int number, ArrayList<Integer> quicktip, int scope) {
        for (Integer i : quicktip) {
            if (i == number) {
                number = random.nextInt(scope) + 1;
                number = checkForDuplicates(number, quicktip, scope);
            }
        }
        return number;
    }

    public static UnluckyNumbersService getUnluckyService() {
        return unluckyService;
    }
}