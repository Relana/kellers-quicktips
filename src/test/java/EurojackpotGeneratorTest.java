package test.java;

import main.java.EurojackpotGenerator;
import main.java.UnluckyNumbersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class EurojackpotGeneratorTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private static final String TEST_FILE = "unlucky-numbers-test.txt";

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void givenEurojackpotGenerator_whenPrintTipIsCalled_thenOutputContainsExpectedMessage() {
        String tipMessage = "Die folgenden Eurojackpotzahlen wurden für Sie generiert:";

        new EurojackpotGenerator(new UnluckyNumbersService(TEST_FILE)).printTip();
        assertTrue(outputStreamCaptor.toString().contains(tipMessage));
    }
}