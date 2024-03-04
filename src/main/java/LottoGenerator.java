package main.java;

public class LottoGenerator extends GeneratorImpl {

    private static final int SCOPE = 49;
    private static final int TIP_SET_SIZE = 6;

    public LottoGenerator(UnluckyNumbersService service) {
        super(service);
        QuicktipLogger.info("LottoGenerator was instantiated.");
    }

    public void printTip() {
        System.out.println("Die folgenden Lottozahlen wurden für Sie generiert:\n" +
                           generateTip(TIP_SET_SIZE, SCOPE) + "\n");
        QuicktipLogger.info("New Lotto quicktip was printed to the command line.");
    }
}