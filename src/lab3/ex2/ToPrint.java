package lab3.ex2;

public class ToPrint implements Runnable {
    private int toPrintNumber;
    private Printer printer;

    public ToPrint(Printer printer, int toPrintNumber) {
        this.printer = printer;
        this.toPrintNumber = toPrintNumber;
    }

    public void run() {
        printer.print(toPrintNumber);
    }
}
