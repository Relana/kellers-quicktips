package test.java;

import main.java.GeneratorController;
import main.java.UnluckyNumbersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorControllerTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private static final String TEST_FILE = "unlucky-numbers-test.txt";
    private static final String LOTTO = "lotto";
    private static final String EURO = "eurojackpot";
    private static final String EXIT = "exit";
    private static final String FOO = "foo";
    private static final String ENTER = "enter unlucky";
    private static final String VIEW = "view unlucky";
    private static final String DELETE = "delete unlucky";
    private static final String DONE = "done";
    private static final String ABORT = "abort";
    private static final String THREE_NUMBERS = "13, 26, 39";


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        GeneratorController.setUnluckyService(TEST_FILE);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void givenGeneratorController_whenCreated_thenOutputContainsWelcomeMessage() {
        String welcomeMessage = "Willkommen beim Quicktipp-Generator 'Keller's Quicktips'!";
        checkForDifferentArgs(welcomeMessage);
    }

    @Test
    void givenGeneratorController_whenExited_thenOutputContainsGoodbye() {
        String goodbyeMessage = "Bis zum nächsten Mal. Viel Glück!";
        checkForDifferentArgs(goodbyeMessage);
    }

    private void checkForDifferentArgs(String message) {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(EXIT, LOTTO);
        assertTrue(outputStreamCaptor.toString().contains(message));
        outputStreamCaptor.reset();

        createGeneratorController(EXIT, EURO);
        assertTrue(outputStreamCaptor.toString().contains(message));
        outputStreamCaptor.reset();

        createGeneratorController(EXIT, FOO);
        assertTrue(outputStreamCaptor.toString().contains(message));
    }

    @Test
    void givenGeneratorControllerWithLotto_whenStartGenerationIsCalledWithEurojackpot_thenOutputContainsEurojackpot() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(EXIT, LOTTO).startGeneration(EURO);

        assertTrue(outputStreamCaptor.toString().contains("Die folgenden Eurojackpotzahlen wurden für Sie generiert:"));
    }

    @Test
    void givenGeneratorControllerWithGivenGeneratorController_whenCreatedWithInvalidParameter_thenOutputContainsErrormessage() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(EURO + "\n" + EXIT, LOTTO);

        assertTrue(outputStreamCaptor.toString().contains("Die folgenden Eurojackpotzahlen wurden für Sie generiert:"));
    }

    @Test
    void givenGeneratorController_whenCreatedWithInvalidParameter_thenOutputContainsErrormessage() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(EXIT, FOO);
        assertTrue(outputStreamCaptor.toString().contains("Der eingegebene Parameter war ungültig. " +
                                                          "Die gültigen Parameter sind 'lotto' und 'eurojackpot'."));
    }

    @Test
    void givenGeneratorControllerWithLotto_whenInputIsUnexpected_thenOutputContainsErrorMessage() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(FOO + "\n" + EXIT, LOTTO);

        assertTrue(outputStreamCaptor.toString().contains("Die Eingabe '" + FOO + "' korrespondiert mit keiner der " +
                "angebotenen Optionen."));
    }

    @Test
    void givenGeneratorControllerWithLotto_whenInputIsViewUnlucky_thenOutputContainsUnluckyNumbersMessage() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(VIEW + "\n" + EXIT, LOTTO);
        UnluckyNumbersService unluckyService = GeneratorController.getUnluckyService();
        if (unluckyService.unluckyNumbersIsEmpty()) {
            assertTrue(outputStreamCaptor.toString().contains("Es gibt keine gespeicherten Unglückszahlen."));
        } else {
            assertTrue(outputStreamCaptor.toString().contains("Die gespeicherten Unglückszahlen sind: "));
        }
    }

    @Test
    void givenGeneratorControllerWithLotto_whenInputIsDeleteUnlucky_thenOutputContainsDeleteUnluckyNumbersMessage() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(DELETE + "\n" + EXIT, LOTTO);

        assertTrue(outputStreamCaptor.toString().contains("Die Unglückszahlen wurden gelöscht."));
    }

    @Test
    void givenGeneratorControllerWithLotto_whenInputIsEnterUnlucky_thenOutputContainsEnterUnluckyNumbersMenuMessage() {
        // Input "abort" and "exit" is used so that the loop in updateUnluckyNumbers and the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(ENTER + "\n" + ABORT + "\n" + EXIT, LOTTO);

        assertTrue(outputStreamCaptor.toString().contains("Um neue Unglückszahlen zu speichern, müssen Sie zunächst die Zahlen eingeben und im Anschluss mit 'done' bestätigen.\n" +
                "Sie können bis zu sechs Unglückszahlen speichern."));
    }

    @Test
    void givenEnterUnluckyWasSelected_whenInputIsInvalid_thenOutputContainsErrorMessage() {
        // Input "abort" and "exit" is used so that the loop in updateUnluckyNumbers and the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(ENTER + "\n" + FOO + "\n" + ABORT + "\n" + EXIT, LOTTO);

        assertTrue(outputStreamCaptor.toString().contains("Die Eingabe '" + FOO + "' korrespondiert mit keiner der angebotenen Optionen."));
    }

    @Test
    void givenEnterUnluckyWasSelected_whenInputIsValidAndDoneIsSelected_thenUnluckyNumbersAreActuallySaved() {
        // Input "done" and "exit" is used so that the loop in updateUnluckyNumbers and the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(ENTER + "\n" + THREE_NUMBERS + "\n" + DONE + "\n" + EXIT, LOTTO);

        assertEquals("[" + THREE_NUMBERS + "]",
                GeneratorController.getUnluckyService().getUnluckyNumbersList().toString());
    }

    private GeneratorController createGeneratorController(String scannerInput, String gameParam) {
        return new GeneratorController(new Scanner(scannerInput),gameParam);
    }
}