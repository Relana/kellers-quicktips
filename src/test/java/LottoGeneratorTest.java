package test.java;

import main.java.LottoGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LottoGeneratorTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void givenLottoGenerator_whenPrintTipIsCalled_thenOutputContainsExpectedMessage() {
        String tipMessage = "Die folgenden Lottozahlen wurden für Sie generiert:";

        new LottoGenerator().printTip();
        assertTrue(outputStreamCaptor.toString().contains(tipMessage));
    }
}