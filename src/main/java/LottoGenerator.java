package main.java;

public class LottoGenerator extends GeneratorImpl {

    private static final int SCOPE = 49;
    private static final int TIP_SET_SIZE = 6;

    public void printTip() {
        System.out.println("\n" +
                           "Die folgenden Lottozahlen wurden für Sie generiert:\n" +
                           generateTip(TIP_SET_SIZE, SCOPE) + "\n");
    }
}