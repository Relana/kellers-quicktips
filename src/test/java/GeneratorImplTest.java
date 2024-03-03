package test.java;

import main.java.GeneratorImpl;
import main.java.UnluckyNumbersService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorImplTest {
    //Lotto 6aus49
    private static final int LOTTO_SCOPE = 49;
    private static final int LOTTO_SET_SIZE = 6;
    //Eurojackpot 5aus50
    private static final int EURO_SCOPE = 50;
    private static final int EURO_SET_SIZE = 5;
    //Eurojackpot 2aus10
    private static final int EURO_SCOPE_2 = 10;
    private static final int EURO_SET_SIZE_2 = 2;

    private static final int UNLUCKY_NUMBER = 13;

    private static final String TEST_FILE = "unlucky-numbers-test.txt";
    private static final GeneratorImpl generator = new GeneratorImpl(new UnluckyNumbersService(TEST_FILE));

    @Test
    void givenGenerateTipIsCalled_whenParametersAreLotto_thenReturnValueSizeIsAsExpected() {
        ArrayList<Integer> tip = generator.generateTip(LOTTO_SET_SIZE, LOTTO_SCOPE);
        assertEquals(LOTTO_SET_SIZE, tip.size());
    }

    @Test
    void givenGenerateTipIsCalled_whenParametersAreEuro_thenReturnValueSizeIsAsExpected() {
        ArrayList<Integer> tip = generator.generateTip(EURO_SET_SIZE, EURO_SCOPE);
        assertEquals(EURO_SET_SIZE, tip.size());
    }

    @Test
    void givenGenerateTipIsCalled_whenParametersAreEuro2_thenReturnValueSizeIsAsExpected() {
        ArrayList<Integer> tip = generator.generateTip(EURO_SET_SIZE_2, EURO_SCOPE_2);
        assertEquals(EURO_SET_SIZE_2, tip.size());
    }

    @Test
    void givenGenerateTipIsCalled_whenParametersAreLotto_thenReturnValuesAreInScope() {
        ArrayList<Integer> tip = generator.generateTip(LOTTO_SET_SIZE, LOTTO_SCOPE);
        for (int number : tip) {
            assertTrue(number < LOTTO_SCOPE + 1);
        }
    }

    @Test
    void givenGenerateTipIsCalled_whenParametersAreEuro_thenReturnValuesAreInScope() {
        ArrayList<Integer> tip = generator.generateTip(EURO_SET_SIZE, EURO_SCOPE);
        for (int number : tip) {
            assertTrue(number < EURO_SCOPE + 1);
        }
    }

    @Test
    void givenGenerateTipIsCalled_whenParametersAreEuro2_thenReturnValuesAreInScope() {
        ArrayList<Integer> tip = generator.generateTip(EURO_SET_SIZE_2, EURO_SCOPE_2);
        for (int number : tip) {
            assertTrue(number < EURO_SCOPE_2 + 1);
        }
    }

    @Test
    void givenUnluckyNumbersAreSaved_whenGenerateTipIsCalled_thenReturnValuesAreNotUnlucky() {
        ArrayList<Integer> newUnluckyNumbers = new ArrayList<>();
        newUnluckyNumbers.add(UNLUCKY_NUMBER);
        GeneratorImpl.getUnluckyService().saveUnluckyNumbers(newUnluckyNumbers);

        ArrayList<Integer> unluckyNumbers = GeneratorImpl.getUnluckyService().getUnluckyNumbersList();

        ArrayList<Integer> tip = generator.generateTip(LOTTO_SET_SIZE, LOTTO_SCOPE);
        for (int number : tip) {
            assertFalse(unluckyNumbers.contains(number));
        }
        tip = generator.generateTip(EURO_SET_SIZE, EURO_SCOPE);
        for (int number : tip) {
            assertFalse(unluckyNumbers.contains(number));
        }
        tip = generator.generateTip(EURO_SET_SIZE_2, EURO_SCOPE_2);
        for (int number : tip) {
            assertFalse(unluckyNumbers.contains(number));
        }
    }

    @Test
    void givenUnluckyListIsAlmostFull_whenAndGenerateTipIsCalled_thenReturnValuesAreTheOnlyRemainingLuckyNumbers() {
        ArrayList<Integer> unluckyList = new ArrayList<>();
        for (int i = 1; i < 44; i++) unluckyList.add(i);
        UnluckyNumbersService unluckyService = GeneratorImpl.getUnluckyService();
        unluckyService.saveUnluckyNumbers(unluckyList);

        ArrayList<Integer> tip = generator.generateTip(LOTTO_SET_SIZE, LOTTO_SCOPE);
        assertEquals("[44, 45, 46, 47, 48, 49]", tip.toString());

        unluckyList.add(44);
        unluckyList.add(45);
        unluckyService.saveUnluckyNumbers(unluckyList);
        tip = generator.generateTip(EURO_SET_SIZE, EURO_SCOPE);
        assertEquals("[46, 47, 48, 49, 50]", tip.toString());

        unluckyList.clear();
        for (int i = 1; i < 9; i++) unluckyList.add(i);
        unluckyService.saveUnluckyNumbers(unluckyList);
        tip = generator.generateTip(EURO_SET_SIZE_2, EURO_SCOPE_2);
        assertEquals("[9, 10]", tip.toString());
    }

    @Test
    void givenAListOfNumbersAndAnotherNumber_whenCheckForDuplicatesIsCalled_thenReturnValueIsNotInList() {
        Random random = new Random();

        ArrayList<Integer> tipList = generator.generateTip(LOTTO_SET_SIZE, LOTTO_SCOPE);
        int number = generator.checkForDuplicates(random.nextInt(LOTTO_SCOPE) + 1, tipList, LOTTO_SCOPE);
        assertFalse(tipList.contains(number));

        tipList = generator.generateTip(EURO_SET_SIZE, EURO_SCOPE);
        number = generator.checkForDuplicates(random.nextInt(EURO_SCOPE) + 1, tipList, EURO_SCOPE);
        assertFalse(tipList.contains(number));

        tipList = generator.generateTip(EURO_SET_SIZE_2, EURO_SCOPE_2);
        number = generator.checkForDuplicates(random.nextInt(EURO_SCOPE_2) + 1, tipList, EURO_SCOPE_2);
        assertFalse(tipList.contains(number));
    }

    @Test
    void givenAListOfNumbersAndAnotherNumber_whenCheckForDuplicatesIsCalled_thenReturnValueIsInScope() {
        Random random = new Random();

        ArrayList<Integer> tipList = generator.generateTip(LOTTO_SET_SIZE, LOTTO_SCOPE);
        int number = generator.checkForDuplicates(random.nextInt(LOTTO_SCOPE) + 1, tipList, LOTTO_SCOPE);
        assertTrue(number < LOTTO_SCOPE + 1);

        tipList = generator.generateTip(EURO_SET_SIZE, EURO_SCOPE);
        number = generator.checkForDuplicates(random.nextInt(EURO_SCOPE) + 1, tipList, EURO_SCOPE);
        assertTrue(number < EURO_SCOPE + 1);

        tipList = generator.generateTip(EURO_SET_SIZE_2, EURO_SCOPE_2);
        number = generator.checkForDuplicates(random.nextInt(EURO_SCOPE_2) + 1, tipList, EURO_SCOPE_2);
        assertTrue(number < EURO_SCOPE_2 + 1);
    }
}