package lab3.ex2;

public class ToPrint implements Runnable {
    private int toPrintNumber;
    private Printery printery;

    public ToPrint(Printery printery, int toPrintNumber) {
        this.printery = printery;
        this.toPrintNumber = toPrintNumber;
    }

    public void run() {
        printery.print(toPrintNumber);
    }
}
