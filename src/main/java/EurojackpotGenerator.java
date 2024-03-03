package main.java;

public class EurojackpotGenerator extends GeneratorImpl {

    private static final int SCOPE_A = 50;
    private static final int TIP_SET_SIZE_A = 5;
    private static final int SCOPE_B = 10;
    private static final int TIP_SET_SIZE_B = 2;

    public EurojackpotGenerator(UnluckyNumbersService service) {
        super(service);
    }

    public void printTip() {
        System.out.println("\n" +
                           "Die folgenden Eurojackpotzahlen wurden für Sie generiert:\n" +
                           generateTip(TIP_SET_SIZE_A, SCOPE_A) + "\n" +
                           generateTip(TIP_SET_SIZE_B, SCOPE_B) + "\n");
    }
}