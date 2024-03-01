package test.java;

import main.java.GeneratorController;
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
    private final String lotto = "lotto";
    private final String euro = "eurojackpot";
    private final String exit = "exit";
    private final String foo = "foo";

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void givenGeneratorController_whenCreated_thenOutputContainsWelcomeMessage() {
        String welcomeMessage = "Willkommen beim Quicktipp-Generator 'Keller's Quicktips'!";

        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(exit,lotto);
        assertTrue(outputStreamCaptor.toString().contains(welcomeMessage));
        outputStreamCaptor.reset();

        createGeneratorController(exit,euro);
        assertTrue(outputStreamCaptor.toString().contains(welcomeMessage));
        outputStreamCaptor.reset();

        createGeneratorController(exit,euro);
        assertTrue(outputStreamCaptor.toString().contains(welcomeMessage));
    }

    @Test
    void givenGeneratorController_whenExited_thenOutputContainsGoodbye() {
        String goodbyeMessage = "Bis zum nächsten Mal. Viel Glück!";

        createGeneratorController(exit,lotto);
        assertTrue(outputStreamCaptor.toString().contains(goodbyeMessage));
        outputStreamCaptor.reset();


        createGeneratorController(exit,euro);
        assertTrue(outputStreamCaptor.toString().contains(goodbyeMessage));
        outputStreamCaptor.reset();

        createGeneratorController(exit, foo);
        assertTrue(outputStreamCaptor.toString().contains(goodbyeMessage));
    }

    @Test
    void givenGeneratorControllerWithLotto_whenStartGenerationIsCalledWithEurojackpot_thenOutputContainsEurojackpot() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(exit,lotto).startGeneration(euro);

        assertTrue(outputStreamCaptor.toString().contains("Die folgenden Eurojackpotzahlen wurden für Sie generiert:"));
    }

    @Test
    void givenGeneratorControllerWithLotto_whenInputIsEurojackpot_thenOutputContainsEurojackpot() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(euro + "\n" + exit,lotto);

        assertTrue(outputStreamCaptor.toString().contains("Die folgenden Eurojackpotzahlen wurden für Sie generiert:"));
    }

    @Test
    void givenGeneratorController_whenCreatedWithUnvalidParameter_thenOutputContainsErrormessage() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(exit, foo);
        assertTrue(outputStreamCaptor.toString().contains("Der eingegebene Parameter war ungültig. " +
                                                          "Die gültigen Parameter sind 'lotto' und 'eurojackpot'."));
    }

    @Test
    void givenGeneratorControllerWithLotto_whenInputIsUnexpected_thenOutputContainsErrorMessage() {
        // Input "exit" is used so that the mainLoop method of GeneratorControl is actually exited
        createGeneratorController(foo + "\n" + exit,lotto);

        assertTrue(outputStreamCaptor.toString().contains("Die Eingabe '" + foo + "' korrespondiert mit keiner der " +
                                                          "angebotenen Optionen."));
    }

    private GeneratorController createGeneratorController(String scannerInput, String gameParam) {
        return new GeneratorController(new Scanner(scannerInput),gameParam);
    }
}